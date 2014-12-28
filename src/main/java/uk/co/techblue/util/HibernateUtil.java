package uk.co.techblue.util;

import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.event.EJB3SaveEventListener;
import org.hibernate.ejb.event.EntityCallbackHandler;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * The Class HibernateUtil.
 */
public class HibernateUtil {

    /** The Constant sessionFactory. */
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Builds the session factory.
     * 
     * @return the session factory
     */
    private static SessionFactory buildSessionFactory() {
        final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().buildServiceRegistry();
        final Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        final SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        final ReflectionManager reflectionManager = configuration.getReflectionManager();

        final Iterator<PersistentClass> classMappings = configuration.getClassMappings();
        final EntityCallbackHandler entityCallbackHandler = new EntityCallbackHandler();

        while (classMappings.hasNext()) {
            try {
                entityCallbackHandler.add(
                        reflectionManager.classForName(classMappings.next().getClassName(), HibernateUtil.class),
                        reflectionManager);
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        final EventListenerRegistry eventListenerRegistry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry()
                .getService(EventListenerRegistry.class);
        eventListenerRegistry.prependListeners(EventType.SAVE, new EJB3SaveEventListener(entityCallbackHandler));
        return sessionFactory;
    }

    /**
     * Shutdown session factory.
     */
    public static void shutdownSessionFactory() {
        getSessionFactory().close();
    }

    /**
     * Gets the session factory.
     * 
     * @return the session factory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Gets the hibernate session.
     * 
     * @return the hibernate session
     */
    public static Session getHibernateSession() {
        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return sessionFactory.openSession();
    }

}
