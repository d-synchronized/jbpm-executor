/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.techblue.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.solder.core.ExtensionManaged;

public class ExecutorDatabaseProducer {

    @PersistenceUnit(unitName = "jbpm-executor")
    @ExtensionManaged
    @ApplicationScoped
    @Produces
    private EntityManagerFactory emf;
}
