package uk.co.techblue.service.interfaces;

import java.util.List;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.entity.ErrorInfo;
import uk.co.techblue.entity.RequestInfo;

/**
 * The Interface ExecutorServiceEntryPoint.
 */
public interface ExecutorServiceEntryPoint {

    /**
     * Gets the queued requests.
     *
     * @return the queued requests
     */
    public List<RequestInfo> getQueuedRequests();

    /**
     * Gets the executed requests.
     *
     * @return the executed requests
     */
    public List<RequestInfo> getExecutedRequests();

    /**
     * Gets the in error requests.
     *
     * @return the in error requests
     */
    public List<RequestInfo> getInErrorRequests();

    /**
     * Gets the cancelled requests.
     *
     * @return the cancelled requests
     */
    public List<RequestInfo> getCancelledRequests();

    /**
     * Gets the all errors.
     *
     * @return the all errors
     */
    public List<ErrorInfo> getAllErrors();

    /**
     * Gets the all requests.
     *
     * @return the all requests
     */
    public List<RequestInfo> getAllRequests();

    /**
     * Clear all requests.
     *
     * @return the int
     */
    public int clearAllRequests();

    /**
     * Clear all errors.
     *
     * @return the int
     */
    public int clearAllErrors();

    /**
     * Schedule request.
     *
     * @param commandName the command name
     * @param ctx the ctx
     * @return the long
     */
    public Long scheduleRequest(String commandName, CommandContext ctx);

    /**
     * Cancel request.
     *
     * @param requestId the request id
     */
    public void cancelRequest(Long requestId);

    /**
     * Inits the.
     */
    public void init();

    /**
     * Destroy.
     */
    public void destroy();

    /**
     * Gets the interval.
     *
     * @return the interval
     */
    public int getInterval();

    /**
     * Sets the interval.
     *
     * @param waitTime the new interval
     */
    public void setInterval(int waitTime);

    /**
     * Gets the retries.
     *
     * @return the retries
     */
    public int getRetries();

    /**
     * Sets the retries.
     *
     * @param defaultNroOfRetries the new retries
     */
    public void setRetries(int defaultNroOfRetries);

    /**
     * Gets the thread pool size.
     *
     * @return the thread pool size
     */
    public int getThreadPoolSize();

    /**
     * Sets the thread pool size.
     *
     * @param nroOfThreads the new thread pool size
     */
    public void setThreadPoolSize(int nroOfThreads);
}
