package uk.co.techblue.service.interfaces;

import uk.co.techblue.dto.CommandContext;

/**
 * The Interface ExecutorService.
 */
public interface ExecutorService extends Service {

    /**
     * Schedule request.
     * 
     * @param commandName the command name
     * @param commandContext the command context
     * @return the long
     */
    Long scheduleRequest(String commandName, CommandContext commandContext);

    /**
     * Cancel request.
     * 
     * @param requestId the request id
     */
    void cancelRequest(Long requestId);

    /**
     * Sets the interval.
     * 
     * @param interval the new interval
     */
    void setInterval(Integer interval);

    /**
     * Gets the interval.
     * 
     * @return the interval
     */
    Integer getInterval();

    /**
     * Sets the number of retries.
     * 
     * @param numberOfRetries the new number of retries
     */
    void setNumberOfRetries(Integer numberOfRetries);

    /**
     * Gets the number of retries.
     * 
     * @return the number of retries
     */
    Integer getNumberOfRetries();

    /**
     * Sets the thread pool size.
     * 
     * @param threadPoolSize the new thread pool size
     */
    void setThreadPoolSize(Integer threadPoolSize);

    /**
     * Gets the thread pool size.
     * 
     * @return the thread pool size
     */
    Integer getThreadPoolSize();

}
