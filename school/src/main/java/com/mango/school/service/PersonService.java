package com.mango.school.service;

import com.mango.school.model.PersonEntity;

import java.util.List;

public interface PersonService {

    List<PersonEntity> getAll();

    PersonEntity getPerson(Long id);

    PersonEntity savePerson(PersonEntity personEntity);

    PersonEntity updatePerson(Long id, PersonEntity personEntity);

    void deletePerson(Long id);

}
