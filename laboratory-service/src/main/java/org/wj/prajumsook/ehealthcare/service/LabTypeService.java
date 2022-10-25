package org.wj.prajumsook.ehealthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wj.prajumsook.ehealthcare.entity.LabTypeEntity;
import org.wj.prajumsook.ehealthcare.model.LabType;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class LabTypeService {

  public List<LabType> findAll() {
    List<LabTypeEntity> entities = LabTypeEntity.listAll();
    return entities.stream()
        .map(this::mapToDomain).toList();
  }

  public LabType findById(Long id) {
    return mapToDomain(findEntity(id));
  }

  public LabType create(LabType labType) {
    LabTypeEntity entity = mapToEntity(labType);
    entity.persist();

    return mapToDomain(entity);
  }

  public LabType update(LabType labType) {
    LabTypeEntity entity = findEntity(labType.getId());
    entity.setName(labType.getName());
    entity.setDescription(labType.getDescription());

    return mapToDomain(entity);
  }

  public LabType delete(Long id) {
    LabTypeEntity entity = findEntity(id);
    entity.delete();

    return mapToDomain(entity);
  }

  private LabTypeEntity findEntity(Long id) {
    Optional<LabTypeEntity> entity = LabTypeEntity.findByIdOptional(id);

    return entity.orElseThrow(
        () -> new WebApplicationException("Laboratory type id " + id + " not found", 404)
    );
  }

  private LabType mapToDomain(LabTypeEntity entity) {
    return new ObjectMapper().convertValue(entity, LabType.class);
  }

  private LabTypeEntity mapToEntity(LabType labType) {
    return new ObjectMapper().convertValue(labType, LabTypeEntity.class);
  }
}
