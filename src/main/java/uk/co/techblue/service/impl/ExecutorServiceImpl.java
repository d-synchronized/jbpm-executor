package uk.co.techblue.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.jboss.seam.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.entity.RequestInfo;
import uk.co.techblue.entity.RequestStatus;
import uk.co.techblue.service.interfaces.ExecutorService;

@Transactional
public class ExecutorServiceImpl implements ExecutorService {

    private Logger logger = LoggerFactory.getLogger(ExecutorServiceImpl.class);

    private int threadPoolSize = 1;

    private int numberOfRetries = 3;

    private int interval = 3;

    private ScheduledExecutorService scheduledExecutorService;

    private ScheduledFuture<?> handle;

    @Inject
    private ExecutorRunnable task;

    @Inject
    private EntityManager em;

    public ExecutorServiceImpl() {

    }

    @Override
    public void init() {
        scheduledExecutorService = Executors.newScheduledThreadPool(threadPoolSize);
        handle = scheduledExecutorService.scheduleAtFixedRate(task, 2, interval, TimeUnit.SECONDS);
    }

    @Override
    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Override
    public Integer getInterval() {
        return interval;
    }

    @Override
    public void setNumberOfRetries(Integer numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
    }

    @Override
    public Integer getNumberOfRetries() {
        return numberOfRetries;
    }

    @Override
    public void setThreadPoolSize(Integer threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    @Override
    public Integer getThreadPoolSize() {
        return threadPoolSize;
    }

    public Long scheduleRequest(String commandId, CommandContext ctx) {

        if (ctx == null) {
            throw new IllegalStateException("A Context Must Be Provided! ");
        }
        String businessKey = (String) ctx.getCommandContextData().get("businessKey");
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setCommandName(commandId);
        requestInfo.setKey(businessKey);
        requestInfo.setRequestStatus(RequestStatus.QUEUED);
        requestInfo.setMessage("Ready to execute");
        if (ctx.getCommandContextData().get("retries") != null) {
            requestInfo.setRetries((Integer) ctx.getCommandContextData().get("retries"));
        } else {
            requestInfo.setRetries(numberOfRetries);
        }
        if (ctx != null) {
            try {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                ObjectOutputStream oout = new ObjectOutputStream(bout);
                oout.writeObject(ctx);
                requestInfo.setRequestData(bout.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
                requestInfo.setRequestData(null);
            }
        }

        em.persist(requestInfo);

        logger.info(" >>> Scheduling request for Command: {0} - requestId: {1} with {2} retries", new Object[] { commandId,
                requestInfo.getId(), requestInfo.getRetries() });
        return requestInfo.getId();
    }

    public void cancelRequest(Long requestId) {
        logger.info(" >>> Before - Cancelling Request with Id: {0}", requestId);
        String eql = "Select r from RequestInfo as r where (r.requestStatus ='QUEUED' or r.requestStatus ='RETRYING') and id = :id";
        List<?> result = em.createQuery(eql).setParameter("id", requestId).getResultList();
        if (result.isEmpty()) {
            return;
        }
        RequestInfo r = (RequestInfo) result.iterator().next();

        em.lock(r, LockModeType.PESSIMISTIC_READ);
        r.setRequestStatus(RequestStatus.CANCELLED);
        em.merge(r);
        logger.info(" >>> After - Cancelling Request with Id: {0}", requestId);
    }

    public void destroy() {
        logger.info(" >>>>> Destroying Executor !!!");
        handle.cancel(true);
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }
    }

}
