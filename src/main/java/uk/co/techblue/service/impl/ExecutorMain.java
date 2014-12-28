package uk.co.techblue.service.impl;

import uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint;

public class ExecutorMain {

    public static void main(String[] args) {
        System.out.println("Starting Executor Service ...");
        final ExecutorServiceEntryPoint executorServiceEntryPoint = ExecutorModule.getInstance().getExecutorServiceEntryPoint();
        executorServiceEntryPoint.init();
        System.out.println("Executor Service Started!");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                executorServiceEntryPoint.destroy();
            }
        });
    }
}
