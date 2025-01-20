package org.bachelorprojekt.util;

import org.bachelorprojekt.inventory.Inventory;
import org.bachelorprojekt.inventory.Item;
import org.bachelorprojekt.character.Character;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

// Utility class for database operations using Hibernate
public class DB {

    // Hibernate SessionFactory initialized with the configuration file and annotated classes
    private static SessionFactory factory = new Configuration()
        .configure("config/hibernate.xml") // Path to Hibernate configuration
        .addAnnotatedClass(Item.class) // Maps the Item entity
        .addAnnotatedClass(Inventory.class) // Maps the Inventory entity
        .addAnnotatedClass(Character.class) // Maps the Character entity
        .buildSessionFactory();

    // Generic method to add an object to the database
    public static <T> void add(T obj) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(obj); // Persist the object
        transaction.commit(); // Commit the transaction
    }

    // Generic method to retrieve an object from the database by its ID
    public static <T> T get(Class<T> type, Object id) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        T read = session.get(type, id); // Fetch the object by its ID
        transaction.commit(); // Commit the transaction
        return read;
    }

    // Generic method to update an object in the database
    public static <T> void update(T obj) {
        Session session = factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(obj); // Merge the updated object
        transaction.commit(); // Commit the transaction
    }
}
