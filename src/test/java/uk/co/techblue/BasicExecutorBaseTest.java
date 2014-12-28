package uk.co.techblue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.entity.ErrorInfo;
import uk.co.techblue.entity.RequestInfo;
import uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint;

/**
 * Basic tests showing the different characteristics of the Executor Service.
 */
public abstract class BasicExecutorBaseTest {

    @Inject
    protected ExecutorServiceEntryPoint executor;
    public static final Map<String, Object> cachedEntities = new HashMap<String, Object>();

    @Before
    public void setUp() {
        executor.setThreadPoolSize(1);
        executor.setInterval(3);
        executor.init();
    }

    @After
    public void tearDown() {
        executor.clearAllRequests();
        executor.clearAllErrors();
        executor.destroy();
    }

    /**
     * Tests a simple command request.
     * 
     * @throws InterruptedException
     */
    @Test
    public void simpleExcecutionTest() throws InterruptedException {
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setCommandContextData(new HashMap<String, Object>());
        ctxCMD.getCommandContextData().put("businessKey", UUID.randomUUID().toString());

        // A job is scheduled by using its CDI @Name
        executor.scheduleRequest("PrintOutCmd", ctxCMD);

        Thread.sleep(10000);

        // after 10 seconds we should have no errors, no queued requests and
        // one executed request.
        List<RequestInfo> inErrorRequests = executor.getInErrorRequests();
        List<RequestInfo> queuedRequests = executor.getQueuedRequests();
        List<RequestInfo> executedRequests = executor.getExecutedRequests();
    }

    /**
     * Tests callback execution after a command was successfully executed.
     * 
     * @throws InterruptedException
     */
    @Test
    public void callbackTest() throws InterruptedException {

        CommandContext commandContext = new CommandContext();
        commandContext.setCommandContextData(new HashMap<String, Object>());
        // We register a business key in the command context so we can add
        // extra information on it.
        commandContext.getCommandContextData().put("businessKey", UUID.randomUUID().toString());

        // We are going to put a new AtomicLong in the context. The idea is
        // that the callback we will register will get this 'entity' and increments
        // its value.
        cachedEntities.put((String) commandContext.getCommandContextData().get("businessKey"), new AtomicLong(1));

        // A job is scheduled. Using commandContext we can register a callback
        // using its CDI name.
        commandContext.getCommandContextData().put("callbacks", "SimpleIncrementCallback");
        executor.scheduleRequest("PrintOutCmd", commandContext);

        Thread.sleep(10000);

        // after 10 seconds we should have no errors, no queued requests and
        // one executed request.
        List<RequestInfo> inErrorRequests = executor.getInErrorRequests();
        List<RequestInfo> queuedRequests = executor.getQueuedRequests();
        List<RequestInfo> executedRequests = executor.getExecutedRequests();
    }

    /**
     * Test showing the exception handling mechanism of the Executor Service.
     * 
     * @throws InterruptedException
     */
    @Test
    public void executorExceptionTest() throws InterruptedException {

        CommandContext commandContext = new CommandContext();
        commandContext.setCommandContextData(new HashMap<String, Object>());
        commandContext.getCommandContextData().put("businessKey", UUID.randomUUID().toString());
        cachedEntities.put((String) commandContext.getCommandContextData().get("businessKey"), new AtomicLong(1));

        // Same callback as the precious test
        commandContext.getCommandContextData().put("callbacks", "SimpleIncrementCallback");

        // no retries please.
        commandContext.getCommandContextData().put("retries", 0);

        // The command we are registering will cause an exception.
        executor.scheduleRequest("ThrowExceptionCmd", commandContext);

        Thread.sleep(10000);

        // After 10 seconds, we should have a failing request.
        List<RequestInfo> inErrorRequests = executor.getInErrorRequests();
        System.out.println("Error: " + inErrorRequests.get(0));

        List<ErrorInfo> errors = executor.getAllErrors();
        System.out.println(" >>> Errors: " + errors);

    }

    /**
     * Test showing the retry mechanism for failing commands.
     * 
     * @throws InterruptedException
     */
    @Test
    public void defaultRequestRetryTest() throws InterruptedException {
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setCommandContextData(new HashMap<String, Object>());
        ctxCMD.getCommandContextData().put("businessKey", UUID.randomUUID().toString());

        // The command we are registering will cause an exception.
        // Remeber that the default number of reties is 3.
        executor.scheduleRequest("ThrowExceptionCmd", ctxCMD);

        Thread.sleep(12000);

        // After 12 seconds we should have 4 errors: 1 corresponding to the
        // first time the command failed. The other 3 correspond to the 3
        // retries.
        List<RequestInfo> inErrorRequests = executor.getInErrorRequests();

        List<ErrorInfo> errors = executor.getAllErrors();
        System.out.println(" >>> Errors: " + errors);
        // Three retries means 4 executions in total 1(regular) + 3(retries)
    }

    /**
     * Test showing how a request can be canceled.
     * 
     * @throws InterruptedException
     */
    @Test
    public void cancelRequestTest() throws InterruptedException {
        CommandContext ctxCMD = new CommandContext();
        ctxCMD.setCommandContextData(new HashMap<String, Object>());
        ctxCMD.getCommandContextData().put("businessKey", UUID.randomUUID().toString());

        // Schedule a task.
        Long requestId = executor.scheduleRequest("PrintOutCmd", ctxCMD);

        // cancel the task immediately
        executor.cancelRequest(requestId);

        // We should see the canceled task now
        List<RequestInfo> cancelledRequests = executor.getCancelledRequests();

    }
}
