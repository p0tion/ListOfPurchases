package com.antontulskih.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
    private static final SessionFactory sf = configureSF();

    private static SessionFactory configureSF()
            throws HibernateException {
        Configuration conf = new Configuration().configure();
        ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        return conf.buildSessionFactory(sr);
    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }
}