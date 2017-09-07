package com.mango.school.rest;

import com.mango.school.mapper.ResponseExceptionMapper;
import com.mango.school.model.PersonEntity;
import com.mango.school.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Controller
@Path("/person")
@Consumes({"application/json;charset=UTF-8", "application/json;charset=utf-8"})
@Produces({"application/json;charset=UTF-8"})
public class PersonRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRest.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private ResponseExceptionMapper responseExceptionMapper;

    /*------------------- GET ALL --------------------------*/
    @GET
    @Path("/")
    public Response getAll() {
        try {
            LOGGER.debug("Received request to list all persons");
            List<PersonEntity> people = personService.getAll();
            LOGGER.debug("PersonEntity Listing count = " + people.size());
            return Response.ok(people).build();
        } catch (Exception e) {
            return responseExceptionMapper.toResponse(e);
        }
    }

    /*------------------- GET SINGLE (BY ID) -----------------*/
    @GET
    @Path("/{personId}")
    public Response getPerson(@PathParam(value="personId") Long id) {
        try {
            LOGGER.debug("Received get request to get personEntity id : " + id);
            PersonEntity personEntity = personService.getPerson(id);
            return Response.ok(personEntity).build();
        } catch (Exception e) {
            return responseExceptionMapper.toResponse(e);
        }
    }

    /*------------------- CREATE --------------------------*/
    @POST
    @Path("/")
    public Response createPerson(PersonEntity personEntity) {
        try {
            LOGGER.debug("Received post request on personEntity " + personEntity);
            PersonEntity createdPerson = personService.savePerson(personEntity);
            return Response.status(Response.Status.CREATED).entity(createdPerson).build();
        } catch (Exception e) {
            return responseExceptionMapper.toResponse(e);
        }
    }

    /*------------------- UPDATE --------------------------*/
    @PUT
    @Path("/{personId}")
    public Response updatePerson(@PathParam(value="personId") Long id, PersonEntity personEntity) {
        try {
            LOGGER.debug("Received put request for personId" + "id, personEntity " +   personEntity);
            PersonEntity updatedPerson = personService.updatePerson(id, personEntity);
            return Response.status(Response.Status.OK).entity(updatedPerson).build();
        } catch (Exception e) {
            return responseExceptionMapper.toResponse(e);
        }
    }

    /*------------------- DELETE --------------------------*/
    @DELETE
    @Path("/{personId}")
    public Response deletePerson(@PathParam(value="personId") Long id) {
        try {
            LOGGER.debug("Received delete request for personId " + id);
            personService.deletePerson(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return responseExceptionMapper.toResponse(e);
        }
    }

}
