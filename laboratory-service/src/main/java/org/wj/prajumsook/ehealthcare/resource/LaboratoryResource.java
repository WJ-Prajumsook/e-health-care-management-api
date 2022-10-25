package org.wj.prajumsook.ehealthcare.resource;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.ehealthcare.model.Laboratory;
import org.wj.prajumsook.ehealthcare.model.LaboratoryResponse;
import org.wj.prajumsook.ehealthcare.service.LaboratoryService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/labs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LaboratoryResource {

    @Inject
    LaboratoryService laboratoryService;

    @GET
    public LaboratoryResponse findAll() {
        List<Laboratory> laboratories = laboratoryService.findAll();
        return new LaboratoryResponse()
            .setResult(laboratories)
            .setCount(laboratories.size());
    }

    @GET
    @Path("/{id}")
    public Laboratory findById(@RestPath Long id) {
        return laboratoryService.findById(id);
    }

    @GET
    @Path("/doctor/{id}")
    public List<Laboratory> findByDoctorId(@RestPath Long id) {
        return laboratoryService.findByDoctorId(id);
    }

    @GET
    @Path("/patient/{id}")
    public List<Laboratory> findByPatientId(@RestPath Long id) {
        return laboratoryService.findByPatientId(id);
    }

    @POST
    public Laboratory create(Laboratory laboratory) {
        return laboratoryService.create(laboratory);
    }

    @PUT
    public Laboratory update(Laboratory laboratory) {
        return laboratoryService.update(laboratory);
    }

    @DELETE
    @Path("/{id}")
    public Laboratory delete(@RestPath Long id) {
        return laboratoryService.delete(id);
    }

}