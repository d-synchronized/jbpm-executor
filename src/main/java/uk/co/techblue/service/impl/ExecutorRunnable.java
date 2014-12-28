package uk.co.techblue.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.jboss.seam.transaction.Transactional;
import org.slf4j.LoggerFactory;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.dto.ExecutionResults;
import uk.co.techblue.entity.RequestInfo;
import uk.co.techblue.entity.ErrorInfo;
import uk.co.techblue.entity.RequestStatus;
import uk.co.techblue.service.interfaces.Command;
import uk.co.techblue.service.interfaces.CommandCallback;

public class ExecutorRunnable implements Runnable {

    /** The logger. */
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ExecutorRunnable.class);

    /** The em. */
    @Inject
    private EntityManager em;

    /** The bean manager. */
    @Inject
    private BeanManager beanManager;

    /** The command cache. */
    private final Map<String, Command> commandCache = new HashMap<String, Command>();

    /** The callback cache. */
    private final Map<String, CommandCallback> callbackCache = new HashMap<String, CommandCallback>();

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Transactional
    public void run() {
        logger.info(" >>> Executor Thread {0} Waking Up!!!", this.toString());
        List<?> resultList = em.createQuery(
                "Select r from RequestInfo as r where r.requestStatus ='QUEUED' or r.requestStatus = 'RETRYING' ORDER BY r.time DESC")
                .getResultList();
        logger.info(" >>> Pending Requests = {0}", resultList.size());
        if (resultList.size() > 0) {
            uk.co.techblue.entity.RequestInfo r = null;
            Throwable exception = null;
            try {
                r = (RequestInfo) resultList.get(0);
                r.setRequestStatus(RequestStatus.RUNNING);
                em.merge(r);
                logger.info(" >> Processing Request Id: {0}", r.getId());
                logger.info(" >> Request Status ={0}", r.getRequestStatus());
                logger.info(" >> Command Name to execute = {0}", r.getCommandName());
                Command cmd = this.findCommand(r.getCommandName());
                CommandContext ctx = null;
                byte[] reqData = r.getRequestData();
                if (reqData != null) {
                    try {
                        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(reqData));
                        ctx = (CommandContext) in.readObject();
                    } catch (IOException e) {
                        ctx = null;
                        e.printStackTrace();
                    }
                }
                ExecutionResults results = cmd.execute(ctx);
                if (ctx != null && ctx.getCommandContextData().get("callbacks") != null) {
                    final Object contextData = ctx.getCommandContextData().get("callbacks");
                    logger.info(" ### Callback: {0}", contextData);
                    String[] callbacksArray = ((String) contextData).split(",");
                    ;
                    List<String> callbacks = (List<String>) Arrays.asList(callbacksArray);
                    for (String callbackName : callbacks) {
                        CommandCallback handler = this.findCommandCallback(callbackName);
                        handler.onCommandDone(ctx, results);
                    }
                } else {
                    logger.info(" ### Callbacks: NULL");
                }
                if (results != null) {
                    try {
                        ByteArrayOutputStream bout = new ByteArrayOutputStream();
                        ObjectOutputStream out = new ObjectOutputStream(bout);
                        out.writeObject(results);
                        byte[] respData = bout.toByteArray();
                        r.setResponseData(respData);
                    } catch (IOException e) {
                        r.setResponseData(null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                exception = e;
            }
            if (exception != null) {
                logger.error("{0} >>> Before - Error Handling!!!{1}",
                        new Object[] { System.currentTimeMillis(), exception.getMessage() });

                ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), ExceptionUtils.getFullStackTrace(exception
                        .fillInStackTrace()));
                errorInfo.setRequestInfo(r);
                r.getErrorInfo().add(errorInfo);
                logger.warn(" >>> Error Number: {0}", r.getErrorInfo().size());
                if (r.getRetries() > 0) {
                    r.setRequestStatus(RequestStatus.RETRYING);
                    r.setRetries(r.getRetries() - 1);
                    r.setExecutions(r.getExecutions() + 1);
                    logger.error(" >>> Retrying ({0}) still available!", r.getRetries());
                } else {
                    logger.error(" >>> Error no retries left!");
                    r.setRequestStatus(RequestStatus.ERROR);
                    r.setExecutions(r.getExecutions() + 1);
                }
                em.merge(r);
                logger.info(" >>> After - Error Handling!!!");
            } else {
                r.setRequestStatus(RequestStatus.DONE);
                em.merge(r);
            }
        }
    }

    /**
     * Find command.
     * 
     * @param name the name
     * @return the command
     */
    private Command findCommand(String name) {

        synchronized (commandCache) {
            if (!commandCache.containsKey(name)) {
                Set<Bean<?>> beans = beanManager.getBeans(name);
                if (!beans.iterator().hasNext()) {
                    throw new IllegalArgumentException("Unknown Command implemenation with name '" + name + "'");
                }
                Bean<?> bean = beans.iterator().next();
                commandCache.put(name,
                        (Command) beanManager.getReference(bean, Command.class, beanManager.createCreationalContext(bean)));
            }
        }

        return commandCache.get(name);
    }

    /**
     * Find command callback.
     * 
     * @param name the name
     * @return the command callback
     */
    private CommandCallback findCommandCallback(String name) {

        synchronized (callbackCache) {
            if (!callbackCache.containsKey(name)) {
                Set<Bean<?>> beans = beanManager.getBeans(name);
                if (!beans.iterator().hasNext()) {
                    throw new IllegalArgumentException("Unknown CommandCallback implemenation with name '" + name + "'");
                }
                Bean<?> bean = beans.iterator().next();
                callbackCache.put(
                        name,
                        (CommandCallback) beanManager.getReference(bean, CommandCallback.class,
                                beanManager.createCreationalContext(bean)));
            }
        }

        return callbackCache.get(name);
    }
}
