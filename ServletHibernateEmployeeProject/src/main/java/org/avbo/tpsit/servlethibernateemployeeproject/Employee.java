/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.avbo.tpsit.servlethibernateemployeeproject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author andre
 */
@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    //Si assicura che generi gli ID in maniera incrementale quando
    //	aggiunge qualcosa al DB
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
	//Tabella su cui fare il JOIN
        name = "Employee_Project",
	//Nome della colonna con cui fare il join del proprio ID
        joinColumns = { @JoinColumn(name = "employee_id") }, 
	//Nome della colonna con cui fare il join dell'ID della tabella
	//  definita dalla classe Poject
        inverseJoinColumns = { @JoinColumn(name = "project_id") }
    )
    Set<Project> projects = new HashSet<>();

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public Set<Project> getProjects() {
	return projects;
    }

    public void setProjects(Set<Project> projects) {
	this.projects = projects;
    }

    public Employee() {
    }

    public Employee(String firstName, String lastName) {
	this.firstName = firstName;
	this.lastName = lastName;
    }
}
