/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.avbo.tpsit.servlethibernateemployeeproject.hibernate;

import java.util.ArrayList;
import java.util.List;
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
		//Da qui il codice passa una sola volta quando viene creata la
		//  factory all'inizio, quindi allo stesso modo i dati di esempio
		//  verranno creati un'unica volta.
		buildTestData();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	//Restituisce la factory in memoria
	return sessionFactory;
    }
    /**
    * Metodo che crea dei dati di esempio aggiungendoli nel database
    */
    private static void buildTestData() {
	//---------------------------------CREAZIONE DEI DATI DI TEST-------------------------------------
	
	//Crea 5 progetti da inserire nel DB
	Project apolloRedesign = new Project();
	apolloRedesign.setTitle("Apollo Redesign");

	var neptuneExpansion = new Project();
	neptuneExpansion.setTitle("Neptune Expansion");

	var mercuryMigration = new Project();
	mercuryMigration.setTitle("Mercury Migration");

	var plutoAnalytics = new Project();
	plutoAnalytics.setTitle("Pluto Analytics");

	var jupiterDashboard = new Project();
	jupiterDashboard.setTitle("Jupiter Dashboard");
	//Crea una lista di impiegati
	List<Employee> employees = new ArrayList<>();

	//Non serve dare un ID, perché è stato scelto il metodo di generazione automatico
	Employee aliceJohnson = new Employee("Alice", "Johnson");
	//Aggiunge i relativi progetti
	aliceJohnson.getProjects().add(apolloRedesign);
	aliceJohnson.getProjects().add(neptuneExpansion);
	//Inserisce l'impiegata nella lista
	employees.add(aliceJohnson);
	//Continua così per altri 4 impiegati
	Employee bobSmith = new Employee("Bob", "Smith");
	bobSmith.getProjects().add(neptuneExpansion);
	bobSmith.getProjects().add(mercuryMigration);
	employees.add(bobSmith);

	Employee carolDavis = new Employee("Carol", "Davis");
	carolDavis.getProjects().add(mercuryMigration);
	carolDavis.getProjects().add(plutoAnalytics);
	employees.add(carolDavis);

	Employee davidLee = new Employee("David", "Lee");
	davidLee.getProjects().add(plutoAnalytics);
	davidLee.getProjects().add(jupiterDashboard);
	employees.add(davidLee);

	Employee eveMartinez = new Employee("eve", "Martinez");
	eveMartinez.getProjects().add(apolloRedesign);
	eveMartinez.getProjects().add(jupiterDashboard);
	employees.add(eveMartinez);

	//Crea il dao per poter scrivere sul DB
	EmployeeDAO dao = new EmployeeDAO();
	//Inserisce tutti e 5 gli impiegati contemporaneamente per evitare
	//  eventuali problemi dovuti alla Sessione che si chiude e si apre.
	//  Inoltre i progetti verranno inseriti automaticamente perché è
	//  presente il riferimento dentro gli impiegati e la loro relazione
	//  è stata impostata su Cascade.All, verrà anche popolata la relativa
	//  tabella che descrive la relazione in automatico.
	//  Appunto perché si hanno diverse relazioni è necessario fare questa
	//  operazione in un'unica sessione, altrimenti sarà necessario usare
	//  istanze diverse dei progetti per ogni utente per evitare di avere
	//  l'errore di sessione chiusa
	dao.addEmployees(employees);
    }
}
