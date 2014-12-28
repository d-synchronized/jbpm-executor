package uk.co.techblue.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.AvailableSettings;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.DuplicationStrategy;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.secure.internal.JACCSecurityListener;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class JaccEventListenerIntegrator implements Integrator {

    private static final DuplicationStrategy JACC_DUPLICATION_STRATEGY = new DuplicationStrategy() {
        @Override
        public boolean areMatch(Object listener, Object original) {
            return listener.getClass().equals(original.getClass()) && JACCSecurityListener.class.isInstance(original);
        }

        @Override
        public Action getAction() {
            return Action.KEEP_ORIGINAL;
        }
    };

    @Override
    public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
        final boolean isSecurityEnabled = configuration.getProperties().containsKey(AvailableSettings.JACC_ENABLED);
        if (!isSecurityEnabled) {
            return;
        }

        final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);
        eventListenerRegistry.addDuplicationStrategy(JACC_DUPLICATION_STRATEGY);

//        final String jaccContextId = configuration.getProperty(Environment.JACC_CONTEXTID);
//        eventListenerRegistry.appendListeners(EventType.PRE_INSERT, new PreInsertListener());
//        eventListenerRegistry.prependListeners(EventType.SAVE_UPDATE, new SaveUpdateEventListener());
    }

    @Override
    public void integrate(MetadataImplementor metadata, SessionFactoryImplementor sessionFactory,
            SessionFactoryServiceRegistry serviceRegistry) {
        // TODO Auto-generated method stub

    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        // TODO Auto-generated method stub

    }
}
