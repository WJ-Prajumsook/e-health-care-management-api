package org.wj.prajumsook.ehealthcare.resource;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.ehealthcare.entity.MedicalHistory;
import org.wj.prajumsook.ehealthcare.model.MedicalHistoryResponse;
import org.wj.prajumsook.ehealthcare.service.MedicalHistoryService;

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

@Path("/v1/medicshistory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicalHistoryResource {

  @Inject
  MedicalHistoryService service;

  @GET
  public MedicalHistoryResponse findAll() {
    List<MedicalHistory> medicalHistories = service.findAll();
    return new MedicalHistoryResponse()
        .setResult(medicalHistories)
        .setCount(medicalHistories.size());
  }

  @GET
  @Path("/{id}")
  public MedicalHistory findById(@RestPath Long id) {
    return service.findById(id);
  }

  @GET
  @Path("/patient/{id}")
  public List<MedicalHistory> findByPatientId(@RestPath Long id) {
    return service.findByPatientId(id);
  }

  @POST
  public MedicalHistory create(MedicalHistory medicalHistory) {
    return service.create(medicalHistory);
  }

  @PUT
  public MedicalHistory update(MedicalHistory medicalHistory) {
    return service.update(medicalHistory);
  }

  @DELETE
  @Path("/{id}")
  public MedicalHistory delete(@RestPath Long id) {
    return service.delete(id);
  }

}
