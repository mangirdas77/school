package com.mango.school.exception.Person;

import com.mango.school.exception.SchoolBusinessException;

import javax.ws.rs.core.Response;
import java.text.MessageFormat;

public class PersonAlreadyExistsException extends SchoolBusinessException {

    private final static Response.Status HTTP_STATUS_CODE = Response.Status.CONFLICT;
    private final static String PERSON_WITH_ID_ALREADY_EXIST_MSG = "Person with id {0} already exists.";
    private final static String PERSON_WITH_NAME_SURNAME_ALREADY_EXIST_MSG = "Person with name {0} and surname {1} already exists.";

    public PersonAlreadyExistsException(Long id) {
        super(HTTP_STATUS_CODE, MessageFormat.format(PERSON_WITH_ID_ALREADY_EXIST_MSG, id));
    }

    public PersonAlreadyExistsException(String name, String surname) {
        super(HTTP_STATUS_CODE, MessageFormat.format(PERSON_WITH_NAME_SURNAME_ALREADY_EXIST_MSG, name, surname));
    }

}
