package com.mango.school.rest;

import com.mango.school.model.PersonEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonRestTest {
	
	@Autowired
	private DataInitializer dataInitializer;
	
	@Autowired
	private PersonRest personRest;
		
	@Before
	public void before() {
		dataInitializer.initData();
	}
	
	@Test
	public void shouldReturnPersonList() {
		Response response = personRest.getAll();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertTrue(response.hasEntity());
		List<PersonEntity> people = (List<PersonEntity>) response.getEntity();
		assertNotNull(people);		
		assertEquals(DataInitializer.PERSON_COUNT,people.size());		
	}

	@Test
	public void shouldReturnPersonNotFound() {
		Response response = personRest.getPerson(new Long(100));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertTrue(response.hasEntity());
		assertEquals("Person with id 100 not found", response.getHeaderString("errormessage"));
	}
	
	@Test
	public void shouldReturnSecondPerson() {
		Long template = dataInitializer.people.get(1);
		Response response = personRest.getPerson(template);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertTrue(response.hasEntity());
		PersonEntity personEntity = (PersonEntity) response.getEntity();
		assertEquals(template, personEntity.getId());
	}
	
}
