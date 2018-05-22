package database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateSingleton {

    private static SessionFactory INSTANCE = null;

    private HibernateSingleton() {}

    public static synchronized SessionFactory getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return INSTANCE;
    }

}
