package org.wj.prajumsook.ehealthcare.resource;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.ehealthcare.model.Medication;
import org.wj.prajumsook.ehealthcare.model.MedicationResponse;
import org.wj.prajumsook.ehealthcare.service.MedicationService;

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

@Path("/v1/medications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicationResource {

    @Inject
    MedicationService service;

    @GET
    public MedicationResponse findAll() {
        List<Medication> medications = service.findAll();
        return new MedicationResponse()
            .setResult(medications)
            .setCount(medications.size());
    }

    @GET
    @Path("/{id}")
    public Medication findById(@RestPath Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/doctor/{id}")
    public List<Medication> findByDoctorId(@RestPath Long id) {
        return service.findByDoctorId(id);
    }

    @GET
    @Path("/patient/{id}")
    public List<Medication> findByPatientId(@RestPath Long id) {
        return service.findByPatientId(id);
    }

    @POST
    public Medication create(Medication medication) {
        return service.create(medication);
    }

    @PUT
    public Medication update(Medication medication) {
        return service.update(medication);
    }

    @DELETE
    @Path("/{id}")
    public Medication delete(@RestPath Long id) {
        return service.delete(id);
    }

}