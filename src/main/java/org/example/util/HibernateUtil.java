package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.security.auth.login.AppConfigurationEntry;


public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration();
            SessionFactory sessionFactory = cfg.configure().buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            System.out.println("Session factory oluşturulurken hata meydana geldi" + e);
            throw new ExceptionInInitializerError(e);
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
