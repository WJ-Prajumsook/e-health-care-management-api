package org.wj.prajumsook.ehealthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wj.prajumsook.ehealthcare.entity.DoctorEntity;
import org.wj.prajumsook.ehealthcare.model.Doctor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class DoctorService {

  @Inject
  SpecializationService specializationService;

  public List<Doctor> findAll() {
    List<DoctorEntity> entities = DoctorEntity.listAll();
    return entities.stream()
        .map(this::mapToDomain).toList();
  }

  public Doctor findById(Long id) {
    Optional<DoctorEntity> opt = DoctorEntity.findByIdOptional(id);
    return opt.map(this::mapToDomain)
        .orElseThrow(
            () -> new WebApplicationException("Doctor id " + id + " not found", 404)
        );
  }

  public Doctor create(Doctor doctor) {
    DoctorEntity entity = mapToEntity(doctor);
    entity.persist();

    return mapToDomain(entity);
  }

  public Doctor update(Doctor doctor) {
    Doctor doc = findById(doctor.getId());
    DoctorEntity entity = DoctorEntity.findById(doc.getId());
    entity.setFirstName(doctor.getFirstName());
    entity.setLastName(doctor.getLastName());
    entity.setEmail(doctor.getEmail());
    entity.setPhoneNumber(doctor.getPhoneNumber());

    return mapToDomain(entity);
  }

  public Doctor delete(Long id) {
    Doctor doc = findById(id);
    DoctorEntity.deleteById(doc.getId());

    return doc;
  }

  private Doctor mapToDomain(DoctorEntity entity) {
    return new ObjectMapper().convertValue(entity, Doctor.class)
        .setSpecializations(specializationService.findByDoctorId(entity.id))
        .setCreatedDate(entity.getCreatedDate())
        .setUpdatedDate(entity.getUpdatedDate());
  }

  private DoctorEntity mapToEntity(Doctor doctor) {
    return new ObjectMapper().convertValue(doctor, DoctorEntity.class);
  }
}
