package org.wj.prajumsook.ehealthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wj.prajumsook.ehealthcare.entity.Intent;
import org.wj.prajumsook.ehealthcare.entity.MedicationEntity;
import org.wj.prajumsook.ehealthcare.entity.Priority;
import org.wj.prajumsook.ehealthcare.entity.Status;
import org.wj.prajumsook.ehealthcare.model.Medication;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class MedicationService {

  public List<Medication> findAll() {
    List<MedicationEntity> entities = MedicationEntity.listAll();

    return entities.stream()
        .map(this::mapToDomain).toList();
  }

  public List<Medication> findByDoctorId(Long id) {
    return findList("doctorId", id);
  }

  public List<Medication> findByPatientId(Long id) {
    return findList("patientId", id);
  }

  public Medication findById(Long id) {
    return mapToDomain(findEntity(id));
  }

  public Medication create(Medication medication) {
    MedicationEntity entity = mapToEntity(medication);
    entity.persist();

    return mapToDomain(entity);
  }

  public Medication delete(Long id) {
    MedicationEntity entity = findEntity(id);
    entity.delete();

    return mapToDomain(entity);
  }

  public Medication update(Medication medication) {
    MedicationEntity entity = findEntity(medication.getId());
    entity.setPatientId(medication.getPatientId());
    entity.setDoctorId(medication.getDoctorId());
    entity.setMedication(medication.getMedication());
    entity.setStatus(Status.valueOf(medication.getStatus()));
    entity.setIntent(Intent.valueOf(medication.getIntent()));
    entity.setPriority(Priority.valueOf(medication.getPriority()));
    entity.setValue(medication.getValue());
    entity.setUnit(medication.getUnit());
    entity.setNotes(medication.getNotes());

    return mapToDomain(entity);
  }

  private List<Medication> findList(String type, Long id) {
    List<MedicationEntity> entities = MedicationEntity.list(type, id);
    return entities.stream()
        .map(this::mapToDomain).toList();
  }

  private MedicationEntity findEntity(Long id) {
    Optional<MedicationEntity> entity = MedicationEntity.findByIdOptional(id);

    return entity.orElseThrow(
        () -> new WebApplicationException("Medication id " + id + " not found", 404)
    );
  }

  private Medication mapToDomain(MedicationEntity entity) {
    Medication medication = new ObjectMapper().convertValue(entity, Medication.class);
    medication.setStatus(entity.getStatus().name());
    medication.setIntent(entity.getIntent().name());
    medication.setPriority(entity.getPriority().name());

    return medication;
  }

  private MedicationEntity mapToEntity(Medication medication) {
    return new ObjectMapper().convertValue(medication, MedicationEntity.class);
  }
}
