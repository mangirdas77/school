package com.mango.school.mapper;

import com.mango.school.exception.SchoolBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class ResponseExceptionMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseExceptionMapper.class);

    public Response toResponse(Exception e) {

        Response.Status status = null;
        String errorMsg = null;

        if (e instanceof SchoolBusinessException) {
            SchoolBusinessException sbe = (SchoolBusinessException) e;
            status = sbe.getResponseStatus();
            errorMsg = sbe.getMessage();
        } else {
            final long timestamp = new Timestamp(new Date().getTime()).getTime();
            status = Response.Status.INTERNAL_SERVER_ERROR;
            errorMsg = "There was unpredicted error. Please contact with the helpdesk and provide the error code: [" + timestamp + "].";
            LOGGER.error("[{}] ", timestamp, e);
        }

        ResponseExceptionEntity entity = new ResponseExceptionEntity(status.getStatusCode(), errorMsg);

        return Response
                .status(status)
                .type(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .entity(entity)
                .header("errormessage", errorMsg)
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .build();
    }

    private class ResponseExceptionEntity {

        private Integer code;
        private String message;

        public ResponseExceptionEntity(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
