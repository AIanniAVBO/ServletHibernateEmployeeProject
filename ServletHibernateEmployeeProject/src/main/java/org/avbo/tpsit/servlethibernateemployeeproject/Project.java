/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.avbo.tpsit.servlethibernateemployeeproject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrea Iannì
 */
@Entity
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer id;
    @Column(name = "title")
    private String title;
    //Il fetch EAGER indica che la lista deve essere popolata sin da subito,
    //	a differenza di LAZY che permette di fare la query per popolarla solo
    //	quando i dati vengono chiesti
    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private Set<Employee> employees = new HashSet<>();

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public Set<Employee> getEmployees() {
	return employees;
    }

    public void setEmployees(Set<Employee> employees) {
	this.employees = employees;
    }

}
