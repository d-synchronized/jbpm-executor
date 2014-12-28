package uk.co.techblue.service.impl;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import uk.co.techblue.service.interfaces.ExecutorServiceEntryPoint;

public class ExecutorModule {
    private static ExecutorModule instance;
    private ExecutorServiceEntryPoint executorService;
    private WeldContainer container;
    private Weld weld;

    public static ExecutorModule getInstance() {
        if (instance == null) {
            instance = new ExecutorModule();
        }
        return instance;
    }

    private ExecutorModule() {
        weld = new Weld();
        this.container = weld.initialize();

        this.executorService = this.container.instance().select(ExecutorServiceEntryPointImpl.class).get();
        // Singleton.. that we need to instantiate
        // this.container.instance().select(TaskLifeCycleEventListener.class).get();
    }

    public ExecutorServiceEntryPoint getExecutorServiceEntryPoint() {
        return this.executorService;
    }

    public WeldContainer getContainer() {
        return container;
    }

    public void dispose() {
        instance = null;
        weld.shutdown();
    }

}
