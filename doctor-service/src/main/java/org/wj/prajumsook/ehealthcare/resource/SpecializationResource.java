package org.wj.prajumsook.ehealthcare.resource;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.ehealthcare.entity.Specialization;
import org.wj.prajumsook.ehealthcare.model.SpecializationResponse;
import org.wj.prajumsook.ehealthcare.service.SpecializationService;

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

@Path("/v1/specs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpecializationResource {

  @Inject
  SpecializationService service;

  @GET
  public SpecializationResponse findAll() {
    List<Specialization> specs = service.findAll();
    return new SpecializationResponse()
        .setResult(specs)
        .setCount(specs.size());
  }

  @GET
  @Path("/{id}")
  public Specialization findById(@RestPath Long id) {
    return service.findById(id);
  }

  @GET
  @Path("/doctor/{id}")
  public List<Specialization> findByDoctorId(@RestPath Long id) {
    return service.findByDoctorId(id);
  }

  @POST
  public Specialization create(Specialization spec) {
    return service.create(spec);
  }

  @PUT
  public Specialization update(Specialization spec) {
    return service.update(spec);
  }

  @DELETE
  @Path("/{id}")
  public Specialization delete(@RestPath Long id) {
    return service.delete(id);
  }
}
