package org.wj.prajumsook.ehealthcare.resource;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.ehealthcare.model.Doctor;
import org.wj.prajumsook.ehealthcare.model.DoctorResponse;
import org.wj.prajumsook.ehealthcare.service.DoctorService;

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

@Path("/v1/doctors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

  @Inject
  DoctorService doctorService;

  @GET
  public DoctorResponse findAll() {
    List<Doctor> doctors = doctorService.findAll();
    return new DoctorResponse()
        .setResult(doctors)
        .setCount(doctors.size());
  }

  @GET
  @Path("/{id}")
  public Doctor findById(@RestPath Long id) {
    return doctorService.findById(id);
  }

  @POST
  public Doctor create(Doctor doc) {
    return doctorService.create(doc);
  }

  @PUT
  public Doctor update(Doctor doc) {
    return doctorService.update(doc);
  }

  @DELETE
  @Path("/{id}")
  public Doctor delete(@RestPath Long id) {
    return doctorService.delete(id);
  }

}