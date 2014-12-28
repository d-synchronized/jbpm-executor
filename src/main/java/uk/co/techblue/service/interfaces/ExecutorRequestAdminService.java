/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.techblue.service.interfaces;

/**
 * The Interface ExecutorRequestAdminService.
 */
public interface ExecutorRequestAdminService {

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
}
