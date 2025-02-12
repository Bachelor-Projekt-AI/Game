package org.bachelorprojekt.util;

import org.bachelorprojekt.character.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * A utility class for database operations using Hibernate.
 * This class provides methods for adding, retrieving, and updating objects in the database.
 * The database connection is configured using Hibernate's SessionFactory.
 */
public class DB {
    // Hibernate SessionFactory initialized with the configuration file and annotated classes
    private SessionFactory factory;

    /**
     * Constructs a DB object with a Hibernate SessionFactory configured using the specified SQLite file.
     * 
     * @param filename The SQLite database file path.
     */
    public DB(String filename) {
        this.factory = new Configuration()
            .configure("config/hibernate.xml") // Path to Hibernate configuration
            .setProperty("hibernate.connection.url", "jdbc:sqlite:" + filename)
            .addAnnotatedClass(Player.class) // Maps the Character entity
            .buildSessionFactory();
    }

    /**
     * Adds an object to the database.
     * 
     * @param <T> The type of the object to be added.
     * @param obj The object to be persisted in the database.
     */
    public <T> void add(T obj) {
        Session session = this.factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(obj); // Persist the object
        transaction.commit(); // Commit the transaction
    }

    /**
     * Retrieves an object from the database by its ID.
     * 
     * @param <T> The type of the object to be retrieved.
     * @param type The class of the object to be retrieved.
     * @param id The ID of the object to be retrieved.
     * @return The object with the specified ID, or null if not found.
     */
    public <T> T get(Class<T> type, Object id) {
        Session session = this.factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        T read = session.get(type, id); // Fetch the object by its ID
        transaction.commit(); // Commit the transaction
        return read;
    }

    /**
     * Updates an object in the database.
     * 
     * @param <T> The type of the object to be updated.
     * @param obj The object with updated values.
     */
    public <T> void update(T obj) {
        Session session = this.factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(obj); // Merge the updated object
        transaction.commit(); // Commit the transaction
    }
}
