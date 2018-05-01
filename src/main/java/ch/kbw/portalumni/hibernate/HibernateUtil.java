/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.kbw.portalumni.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author 5im15faulrich    
 */
public class HibernateUtil {
    
    private static HibernateUtil instance;
    private SessionFactory factory;
    
    public static HibernateUtil getInstance(){
        if (HibernateUtil.instance == null) {
            HibernateUtil.instance = new HibernateUtil();
        }
        return HibernateUtil.instance;
    }
    
    public Session openSession(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        factory = configuration.buildSessionFactory(ssrb.build());
        return factory.openSession();
    }
    
    public void cleanUp(){
        factory.close();
    }
}
