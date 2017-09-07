package com.mango.school.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name="Person.findAll", query = "select p from PersonEntity p"),
		@NamedQuery(name="Person.findByNameSurname", query = "select p from PersonEntity p where p.firstName=:name and p.lastName=:surname")
})
public class PersonEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NAME")
	private String firstName;

	@Column(name = "SECOND_NAME")
	private String secondName;

	@Column(name = "SURNAME")
	private String lastName;

	public PersonEntity() {
	}

	public PersonEntity(String firstName, String secondName, String lastName) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "PersonEntity{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", secondName='" + secondName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}
