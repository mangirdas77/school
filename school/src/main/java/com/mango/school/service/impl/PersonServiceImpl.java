package com.mango.school.service.impl;

import com.mango.school.dao.PersonDao;
import com.mango.school.exception.Person.PersonAlreadyExistsException;
import com.mango.school.exception.Person.PersonNotFoundException;
import com.mango.school.exception.RequiredValueException;
import com.mango.school.model.PersonEntity;
import com.mango.school.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonDao personDao;

    @Override
    @Transactional(readOnly = true)
    public List<PersonEntity> getAll() {
        List<PersonEntity> people = personDao.findAll();
        return people;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonEntity getPerson(Long id) {
        PersonEntity pe = personDao.find(id);
        if (pe == null) {
            throw  new PersonNotFoundException(id);
        }
        return pe;
    }

    @Override
    public PersonEntity savePerson(PersonEntity personEntity) {
        validateSave(personEntity);
        return personDao.saveOrUpdate(personEntity);
    }

    @Override
    public PersonEntity updatePerson(Long id, PersonEntity person) {
        validateMandatoryFields(person);
        PersonEntity personInDb = personDao.find(id);
        if (personInDb == null) {
            throw  new PersonNotFoundException(id);
        }
        updatePersonWithNewValues(personInDb, person);
        return personDao.saveOrUpdate(personInDb);
    }

    @Override
    public void deletePerson(Long id) {
        PersonEntity personInDb = personDao.find(id);
        if (personInDb == null) {
            throw  new PersonNotFoundException(id);
        }
        personDao.delete(personInDb);
        LOGGER.info("Removed person " + personInDb);
    }

    // TODO MANGO: There can be created dedicated class PersonValidator for Person validation purpose and below methods can be added there
    private void validateSave(PersonEntity pe) {
        validateMandatoryFields(pe);
        validatePersonAlreadyExists(pe);
    }

    // TODO MANGO: Can be also considered to use Hibernate Validator on an Entity, annotation like @NotNull, @Min(3), etc.
    private void validateMandatoryFields(PersonEntity pe) {
        validatePropertyNotEmpty(pe.getFirstName(), "Person.firstName");
        validatePropertyNotEmpty(pe.getLastName(), "Person.lastName");
    }

    private void validatePropertyNotEmpty(String value, String propertyName) {
        if (null == value || value.trim().isEmpty()) {
            throw new RequiredValueException(propertyName);
        }
    }

    private void validatePersonAlreadyExists(PersonEntity pe) {
        if (null != pe.getId()) {
            PersonEntity personInDb = personDao.find(pe.getId());
            if (personInDb != null) {
                throw  new PersonAlreadyExistsException(pe.getId());
            }
        } else {
            // assume that there is requirement that firstName and lastName of the person are unique (we can also have a uniquness constraint on database side) or as below
            List<PersonEntity> personInDbList = personDao.findByNameAndSurname(pe.getFirstName(), pe.getLastName());
            if (personInDbList != null && !personInDbList.isEmpty()) {
                throw  new PersonAlreadyExistsException(pe.getFirstName(), pe.getLastName());
            }
        }
    }

    private void updatePersonWithNewValues(PersonEntity personInDb, PersonEntity personFromUi) {
        personInDb.setFirstName(personFromUi.getFirstName());
        personInDb.setLastName(personFromUi.getLastName());
        personInDb.setSecondName(personFromUi.getSecondName());
    }
}
