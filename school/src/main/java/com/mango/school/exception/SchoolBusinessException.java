package com.mango.school.exception;

import javax.ws.rs.core.Response;

/**
 * General abstract exception which contains {@link Response.Status} and {@code message}, which is meaningful to user and can be displayed on UI, if there is such a need
 */
public abstract class SchoolBusinessException extends RuntimeException {

    private Response.Status responseStatus;

    public SchoolBusinessException(Response.Status responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public Response.Status getResponseStatus() {
        return responseStatus;
    }

}
