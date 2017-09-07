package com.mango.school.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mango.school.model.PersonEntity;
import com.mango.school.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

//	private static final String FIND_ALL = "select p from PersonEntity p";
//	private static final String FIND_BY_NAME_SURNAME = "select p from PersonEntity p where p.firstName=:name and p.lastName=:surname";

	@PersistenceContext
	private EntityManager entityManager;
	
	public PersonEntity find(Long id) {
		return entityManager.find(PersonEntity.class, id);
	}
	
	public List<PersonEntity> findAll() {
		return entityManager.createNamedQuery("Person.findAll").getResultList();
	}

	public List<PersonEntity> findByNameAndSurname(String name, String surname) {
		return entityManager.createNamedQuery("Person.findByNameSurname")
				.setParameter("name", name)
				.setParameter("surname", surname)
				.getResultList();
	}

	public PersonEntity saveOrUpdate(PersonEntity personEntity) {
		if (personEntity.getId() == null) {
			entityManager.persist(personEntity);
			return personEntity;
		} else {
			return entityManager.merge(personEntity);
		}		
	}

	public void delete(PersonEntity personEntity) {
		entityManager.remove(personEntity);
	}

}
