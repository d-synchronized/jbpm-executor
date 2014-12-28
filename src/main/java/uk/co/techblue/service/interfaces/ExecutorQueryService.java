package uk.co.techblue.service.interfaces;

import java.util.List;

import uk.co.techblue.entity.ErrorInfo;
import uk.co.techblue.entity.RequestInfo;

/**
 * The Interface ExecutorQueryService.
 */
public interface ExecutorQueryService {
    
    /**
     * Gets the queued requests.
     *
     * @return the queued requests
     */
    List<RequestInfo> getQueuedRequests();

    /**
     * Gets the executed requests.
     *
     * @return the executed requests
     */
    List<RequestInfo> getExecutedRequests();

    /**
     * Gets the in error requests.
     *
     * @return the in error requests
     */
    List<RequestInfo> getInErrorRequests();

    /**
     * Gets the cancelled requests.
     *
     * @return the cancelled requests
     */
    List<RequestInfo> getCancelledRequests();

    /**
     * Gets the all errors.
     *
     * @return the all errors
     */
    List<ErrorInfo> getAllErrors();

    /**
     * Gets the all requests.
     *
     * @return the all requests
     */
    List<RequestInfo> getAllRequests();
}
