package org.wj.prajumsook.ehealthcare.service;

import org.wj.prajumsook.ehealthcare.entity.MedicalHistory;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class MedicalHistoryService {

  public List<MedicalHistory> findAll() {
    return MedicalHistory.listAll();
  }

  public MedicalHistory findById(Long id) {
    Optional<MedicalHistory> entity = MedicalHistory.findByIdOptional(id);
    return entity.orElseThrow(
        () -> new WebApplicationException("Medical history id " + id + " not found", 404)
    );
  }

  public List<MedicalHistory> findByPatientId(Long id) {
    return MedicalHistory.list("patientId", id);
  }

  public MedicalHistory create(MedicalHistory medicalHistory) {
    medicalHistory.persist();

    return medicalHistory;
  }

  public MedicalHistory update(MedicalHistory medicalHistory) {
    MedicalHistory entity = findById(medicalHistory.id);
    entity.setPatientId(medicalHistory.getPatientId());
    entity.setBloodType(medicalHistory.getBloodType());
    entity.setBloodSugar(medicalHistory.getBloodSugar());
    entity.setBloodPressure(medicalHistory.getBloodPressure());
    entity.setWeight(medicalHistory.getWeight());
    entity.setNotes(medicalHistory.getNotes());

    return entity;
  }

  public MedicalHistory delete(Long id) {
    MedicalHistory mh = findById(id);
    mh.delete();

    return mh;
  }
}
