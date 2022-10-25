package org.wj.prajumsook.ehealthcare.resource;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.ehealthcare.model.LabType;
import org.wj.prajumsook.ehealthcare.model.LabTypeResponse;
import org.wj.prajumsook.ehealthcare.model.LaboratoryResponse;
import org.wj.prajumsook.ehealthcare.service.LabTypeService;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Path("/v1/labtypes")
public class LabTypeResource {

  @Inject
  LabTypeService labTypeService;

  @GET
  public LabTypeResponse findAll() {
    List<LabType> labTypes = labTypeService.findAll();
    return new LabTypeResponse()
        .setResult(labTypes)
        .setCount(labTypes.size());
  }

  @GET
  @Path("/{id}")
  public LabType findById(@RestPath Long id) {
    return labTypeService.findById(id);
  }

  @POST
  public LabType create(LabType labType) {
    return labTypeService.create(labType);
  }

  @PUT
  public LabType update(LabType labType) {
    return labTypeService.update(labType);
  }

  @DELETE
  @Path("/{id}")
  public LabType delete(@RestPath Long id) {
    return labTypeService.delete(id);
  }
}
