package org.bachelorprojekt.util;

import org.bachelorprojekt.inventory.Inventory;
import org.bachelorprojekt.inventory.Item;
import org.bachelorprojekt.character.Character;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DB {
	private static SessionFactory factory = new Configuration().configure("config/hibernate.xml")
		.addAnnotatedClass(Item.class)
		.addAnnotatedClass(Inventory.class)
		.addAnnotatedClass(Character.class)
		.buildSessionFactory();

	public static <T> void add(T obj) {
		Session session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.persist(obj);
		transaction.commit();
	}

	public static <T> T get(Class<T> type, Object id) {
		Session session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		T read = session.get(type, id);
		transaction.commit();
		return read;
	}

	public static <T> void update(T obj) {
		Session session = factory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.merge(obj);
		transaction.commit();
	}
}
