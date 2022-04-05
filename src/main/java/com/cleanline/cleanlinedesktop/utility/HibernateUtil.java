package com.cleanline.cleanlinedesktop.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static Session getSession(){
        SessionFactory sf;
        sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        Session s;
        s = sf.openSession();

        return s;
    }
}
