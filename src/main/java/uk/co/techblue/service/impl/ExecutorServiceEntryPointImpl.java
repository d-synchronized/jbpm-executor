/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.techblue.service.impl;

import java.util.List;

import javax.inject.Inject;

import uk.co.techblue.dto.CommandContext;
import uk.co.techblue.entity.ErrorInfo;
import uk.co.techblue.entity.RequestInfo;
import uk.co.techblue.service.interfaces.ExecutorQueryService;
import uk.co.techblue.service.interfaces.ExecutorRequestAdminService;
import uk.co.techblue.service.interfaces.ExecutorService;
import uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint;

/**
 * The Class ExecutorServiceEntryPointImpl.
 */
public class ExecutorServiceEntryPointImpl implements ExecutorServiceEntryPoint {
    
    /** The executor. */
    @Inject
    private ExecutorService executor;
    
    /** The query service. */
    @Inject
    private ExecutorQueryService queryService;
    
    /** The admin service. */
    @Inject
    private ExecutorRequestAdminService adminService;

    /**
     * Instantiates a new executor service entry point impl.
     */
    public ExecutorServiceEntryPointImpl() {
    }

    /**
     * Gets the executor.
     *
     * @return the executor
     */
    public ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Sets the executor.
     *
     * @param executor the new executor
     */
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    /**
     * Gets the query service.
     *
     * @return the query service
     */
    public ExecutorQueryService getQueryService() {
        return queryService;
    }

    /**
     * Sets the query service.
     *
     * @param queryService the new query service
     */
    public void setQueryService(ExecutorQueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Gets the admin service.
     *
     * @return the admin service
     */
    public ExecutorRequestAdminService getAdminService() {
        return adminService;
    }

    /**
     * Sets the admin service.
     *
     * @param adminService the new admin service
     */
    public void setAdminService(ExecutorRequestAdminService adminService) {
        this.adminService = adminService;
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getQueuedRequests()
     */
    public List<RequestInfo> getQueuedRequests() {
        return queryService.getQueuedRequests();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getExecutedRequests()
     */
    public List<RequestInfo> getExecutedRequests() {
        return queryService.getExecutedRequests();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getInErrorRequests()
     */
    public List<RequestInfo> getInErrorRequests() {
        return queryService.getInErrorRequests();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getCancelledRequests()
     */
    public List<RequestInfo> getCancelledRequests() {
        return queryService.getCancelledRequests();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getAllErrors()
     */
    public List<ErrorInfo> getAllErrors() {
        return queryService.getAllErrors();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getAllRequests()
     */
    public List<RequestInfo> getAllRequests() {
        return queryService.getAllRequests();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#clearAllRequests()
     */
    public int clearAllRequests() {
        return adminService.clearAllRequests();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#clearAllErrors()
     */
    public int clearAllErrors() {
        return adminService.clearAllErrors();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#scheduleRequest(java.lang.String, uk.co.techblue.dto.CommandContext)
     */
    public Long scheduleRequest(String commandName, CommandContext ctx) {
        return executor.scheduleRequest(commandName, ctx);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#cancelRequest(java.lang.Long)
     */
    public void cancelRequest(Long requestId) {
        executor.cancelRequest(requestId);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#init()
     */
    public void init() {
        executor.init();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#destroy()
     */
    public void destroy() {
        executor.destroy();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getInterval()
     */
    public int getInterval() {
        return executor.getInterval();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#setInterval(int)
     */
    public void setInterval(int waitTime) {
        executor.setInterval(waitTime);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getRetries()
     */
    public int getRetries() {
        return executor.getNumberOfRetries();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#setRetries(int)
     */
    public void setRetries(int defaultNroOfRetries) {
        executor.setNumberOfRetries(defaultNroOfRetries);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#getThreadPoolSize()
     */
    public int getThreadPoolSize() {
        return executor.getThreadPoolSize();
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint#setThreadPoolSize(int)
     */
    public void setThreadPoolSize(int nroOfThreads) {
        executor.setThreadPoolSize(nroOfThreads);
    }

}
