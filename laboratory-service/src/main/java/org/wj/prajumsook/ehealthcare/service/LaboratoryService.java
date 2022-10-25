package org.wj.prajumsook.ehealthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wj.prajumsook.ehealthcare.entity.LabStatus;
import org.wj.prajumsook.ehealthcare.entity.LaboratoryEntity;
import org.wj.prajumsook.ehealthcare.model.Laboratory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class LaboratoryService {

  @Inject
  LabTypeService labTypeService;

  public List<Laboratory> findAll() {
    List<LaboratoryEntity> entities = LaboratoryEntity.listAll();
    return entities.stream()
        .map(this::mapToDomain).toList();
  }

  public Laboratory findById(Long id) {
    return mapToDomain(findEntity(id));
  }

  public List<Laboratory> findByDoctorId(Long id) {
    return findList("doctorId", id);
  }

  public List<Laboratory> findByPatientId(Long id) {
    return findList("patientId", id);
  }

  public Laboratory create(Laboratory laboratory) {
    LaboratoryEntity entity = mapToEntity(laboratory);
    entity.persist();

    return mapToDomain(entity);
  }

  public Laboratory delete(Long id) {
    LaboratoryEntity entity = findEntity(id);
    entity.delete();

    return mapToDomain(entity);
  }

  public Laboratory update(Laboratory laboratory) {
    LaboratoryEntity entity = findEntity(laboratory.getId());
    if(laboratory.getLabType() != null) {
      entity.setTestTypeId(laboratory.getLabType().getId());
    }
    entity.setDoctorId(laboratory.getDoctorId());
    entity.setPatientId(laboratory.getPatientId());
    entity.setStatus(LabStatus.valueOf(laboratory.getStatus()));
    entity.setNotes(laboratory.getNotes());

    return mapToDomain(entity);
  }

  private List<Laboratory> findList(String type, Long id) {
    List<LaboratoryEntity> entities = LaboratoryEntity.list(type, id);
    return entities.stream()
        .map(this::mapToDomain).toList();
  }

  private LaboratoryEntity findEntity(Long id) {
    Optional<LaboratoryEntity> entity = LaboratoryEntity.findByIdOptional(id);

    return entity.orElseThrow(
        () -> new WebApplicationException("Laboratory id " + id + " not found", 404)
    );
  }

  private Laboratory mapToDomain(LaboratoryEntity entity) {
    Laboratory laboratory = new ObjectMapper().convertValue(entity, Laboratory.class);
    laboratory.setCreatedDate(entity.getCreatedDate());
    laboratory.setUpdatedDate(entity.getUpdatedDate());
    if(entity.getTestTypeId() != null) {
      laboratory.setLabType(labTypeService.findById(entity.getTestTypeId()));
    }

    return laboratory;
  }

  public LaboratoryEntity mapToEntity(Laboratory laboratory) {
    return new ObjectMapper().convertValue(laboratory, LaboratoryEntity.class);
  }

}
