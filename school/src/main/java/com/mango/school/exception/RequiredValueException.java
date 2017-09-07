package com.mango.school.exception;

import javax.ws.rs.core.Response;
import java.text.MessageFormat;

public class RequiredValueException extends SchoolBusinessException {

    private final static Response.Status HTTP_STATUS_CODE = Response.Status.CONFLICT;
    private final static String REQUIRED_VALUE_MSG = "Missing required value for {0}.";

    public RequiredValueException(String propertyName) {
        super(HTTP_STATUS_CODE, MessageFormat.format(REQUIRED_VALUE_MSG, propertyName));
    }
}
