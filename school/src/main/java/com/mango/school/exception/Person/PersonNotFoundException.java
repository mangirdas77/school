package com.mango.school.exception.Person;

import com.mango.school.exception.SchoolBusinessException;

import javax.ws.rs.core.Response;
import java.text.MessageFormat;

public class PersonNotFoundException extends SchoolBusinessException {

    private final static Response.Status RESPONSE_STATUS_CODE = Response.Status.NOT_FOUND;
    private final static String PERSON_NOT_FOUND_MSG = "Person with id {0} not found";

    public PersonNotFoundException(Long personId) {
        super(RESPONSE_STATUS_CODE, MessageFormat.format(PERSON_NOT_FOUND_MSG, personId));
    }
}
