package org.wj.prajumsook.ehealthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wj.prajumsook.ehealthcare.entity.Gender;
import org.wj.prajumsook.ehealthcare.entity.PatientEntity;
import org.wj.prajumsook.ehealthcare.entity.PatientType;
import org.wj.prajumsook.ehealthcare.model.Patient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class PatientService {

  @Inject
  MedicalHistoryService medicalHistoryService;

  public List<Patient> findAll() {
    List<PatientEntity> entities = PatientEntity.listAll();
    return entities.stream()
        .map(this::mapToDomain).toList();
  }

  public Patient findById(Long id) {
    Optional<PatientEntity> entity = PatientEntity.findByIdOptional(id);
    return entity.map(this::mapToDomain).orElseThrow(
        () -> new WebApplicationException("Patient id " + id + " not found", 404)
    );
  }

  public Patient create(Patient patient) {
    PatientEntity entity = mapToEntity(patient);
    entity.persist();

    return mapToDomain(entity);
  }

  public Patient update(Patient patient) {
    PatientEntity entity = PatientEntity.findById(patient.getId());
    entity.setFirstName(patient.getFirstName());
    entity.setLastName(patient.getLastName());
    entity.setDateOfBirth(patient.getDateOfBirth());
    entity.setEmail(patient.getEmail());
    entity.setPhoneNumber(patient.getPhoneNumber());
    entity.setAddress(patient.getAddress());
    entity.setCity(patient.getCity());
    entity.setGender(Gender.valueOf(patient.getGender()));
    entity.setPatientType(PatientType.valueOf(patient.getPatientType()));

    return mapToDomain(entity);
  }

  public Patient delete(Long id) {
    Patient patient = findById(id);
    PatientEntity.deleteById(patient.getId());

    return patient;
  }

  private Patient mapToDomain(PatientEntity entity) {
    return new ObjectMapper().convertValue(entity, Patient.class)
        .setMedicalHistories(medicalHistoryService.findByPatientId(entity.id))
        .setCreateDate(entity.getCreatedDate())
        .setLastUpdatedDate(entity.getUpdateDate())
        .setGender(entity.getGender().name())
        .setPatientType(entity.getPatientType().name());
  }

  private PatientEntity mapToEntity(Patient patient) {
    return new ObjectMapper().convertValue(patient, PatientEntity.class);
  }

}
