/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.avbo.tpsit.servlethibernateemployeeproject.hibernate;

import org.avbo.tpsit.servlethibernateemployeeproject.Employee;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andrea Iannì
 */
public class EmployeeDAO {
    /**
     * Get all Employees
     *
     * @return
     */
    public List<Employee> getAllEmployees() {

	Transaction transaction = null;
	List<Employee> listOfEmployees = null;
	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	    // start a transaction
	    transaction = session.beginTransaction();
	    // get an Employee object

	    // Get Criteria Builder
	    CriteriaBuilder builder = session.getCriteriaBuilder();

	    // Create Criteria
	    CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
	    Root<Employee> contactRoot = criteria.from(Employee.class);

	    criteria.select(contactRoot);

	    // Use criteria to query with session to fetch all contacts
	    listOfEmployees = session.createQuery(criteria).getResultList();

	    // commit transaction
	    transaction.commit();
	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }
	    e.printStackTrace();
	}
	return listOfEmployees;
    }
    /**
     * Aggiunge un impiegato alla tabella
     * 
     * @param employee Impiegato da aggiungere alla tabella.
     */
    public void addEmployee(Employee employee) {
	Transaction transaction = null;
	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	    // start a transaction
	    transaction = session.beginTransaction();

	    session.persist(employee);
	    // commit transaction
	    transaction.commit();
	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }
	    e.printStackTrace();
	}
    }
    /**
     * Aggiunge più impiegati alla tabella con un'unica sessione.
     * 
     * @param employees Impiegati da aggiungere alla tabella.
     */
    public void addEmployees(List<Employee> employees) {
	Transaction transaction = null;
	try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	    // start a transaction
	    transaction = session.beginTransaction();
	    
	    for (Employee employee : employees) {
		session.persist(employee);
	    }
	    // commit transaction
	    transaction.commit();
	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }
	    e.printStackTrace();
	}
    }
}
