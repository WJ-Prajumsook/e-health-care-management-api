package org.wj.prajumsook.ehealthcare.resource;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.ehealthcare.model.Patient;
import org.wj.prajumsook.ehealthcare.model.PatientResponse;
import org.wj.prajumsook.ehealthcare.service.PatientService;

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

@Path("/v1/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @Inject
    PatientService patientService;

    @GET
    public PatientResponse findAll() {
        List<Patient> patients = patientService.findAll();
        return new PatientResponse()
            .setResult(patients)
            .setCount(patients.size());
    }

    @GET
    @Path("/{id}")
    public Patient findById(@RestPath Long id) {
        return patientService.findById(id);
    }

    @POST
    public Patient create(Patient patient) {
        return patientService.create(patient);
    }

    @PUT
    public Patient update(Patient patient) {
        return patientService.update(patient);
    }

    @DELETE
    @Path("/{id}")
    public Patient delete(@RestPath Long id) {
        return patientService.delete(id);
    }

}