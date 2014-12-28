/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.techblue;

import org.junit.After;
import org.junit.Before;

import uk.co.techblue.service.impl.ExecutorModule;

public class NoCDIExecutorTest extends BasicExecutorBaseTest{
    
    
    public NoCDIExecutorTest() {
    }


    @Before
    public void setUp() {
        executor = ExecutorModule.getInstance().getExecutorServiceEntryPoint();
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

   
}
