package org.bachelorprojekt.util;

import org.bachelorprojekt.character.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

// Utility class for database operations using Hibernate
public class DB {
	// Hibernate SessionFactory initialized with the configuration file and annotated classes
	private SessionFactory factory;

	// Hibernate SessionFactory initialized with the configuration file and annotated classes
	public DB(String filename) {
		this.factory = new Configuration()
			.configure("config/hibernate.xml") // Path to Hibernate configuration
			.setProperty("hibernate.connection.url", "jdbc:sqlite:" + filename)
			.addAnnotatedClass(Player.class) // Maps the Character entity
			.buildSessionFactory();
	}

    // Generic method to add an object to the database
    public <T> void add(T obj) {
        Session session = this.factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(obj); // Persist the object
        transaction.commit(); // Commit the transaction
    }

    // Generic method to retrieve an object from the database by its ID
    public <T> T get(Class<T> type, Object id) {
        Session session = this.factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        T read = session.get(type, id); // Fetch the object by its ID
        transaction.commit(); // Commit the transaction
        return read;
    }

    // Generic method to update an object in the database
    public <T> void update(T obj) {
        Session session = this.factory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(obj); // Merge the updated object
        transaction.commit(); // Commit the transaction
    }
}
