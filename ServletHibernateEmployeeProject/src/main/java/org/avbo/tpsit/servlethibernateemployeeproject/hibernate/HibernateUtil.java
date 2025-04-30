/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.avbo.tpsit.servlethibernateemployeeproject.hibernate;

import java.util.Properties;
import org.avbo.tpsit.servlethibernateemployeeproject.Employee;
import org.avbo.tpsit.servlethibernateemployeeproject.Project;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Andrea Iannì
 */
public class HibernateUtil {
    //Si tratta di un Singleton, cioé viene inizializzato una sola volta
    //	e dopo resta in vita per tutta la vita del processo
    private static SessionFactory sessionFactory;

    private static String filePath = "sakila_master.db";

    public static void SetFilePath(String path) {
	filePath = path;
    }

    public static SessionFactory getSessionFactory() {
	//Se non è ancora stato inizializzato
	if (sessionFactory == null) {
	    //Lo fa
	    try {
		Configuration configuration = new Configuration();

		// Hibernate settings equivalent to hibernate.cfg.xml's properties
		Properties settings = new Properties();
		//Indica che vuole usare il JDBC di SQLite
		settings.put(Environment.JAKARTA_JDBC_DRIVER, "org.sqlite.JDBC");
		//Indica la stringa di connessione
		settings.put(Environment.JAKARTA_JDBC_URL, "jdbc:sqlite:" + filePath);
		//Indica che deve usare il dialetto di SQLite
		settings.put(Environment.DIALECT, "org.hibernate.community.dialect.SQLiteDialect");
		//Indica di mostrare le Query generate nel LOG
		settings.put(Environment.SHOW_SQL, "true");

		settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
		//ATTENZIONE: solo a fine di test!!! Il create-drop cancellerà tutte le tabelle
		//  mappate e ne inserirà di nuove!!!
		settings.put(Environment.HBM2DDL_AUTO, "create-drop");

		configuration.setProperties(settings);
		configuration.addAnnotatedClass(Employee.class);
		configuration.addAnnotatedClass(Project.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
			.applySettings(configuration.getProperties()).build();
		System.out.println("Hibernate Java Config serviceRegistry created");
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	//Restituisce la factory in memoria
	return sessionFactory;
    }
}
