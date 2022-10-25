package org.wj.prajumsook.ehealthcare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wj.prajumsook.ehealthcare.entity.AppointmentEntity;
import org.wj.prajumsook.ehealthcare.entity.AppointmentType;
import org.wj.prajumsook.ehealthcare.model.Appointment;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class AppointmentService {

  public List<Appointment> findAll() {
    List<AppointmentEntity> entities = AppointmentEntity.listAll();
    return entities.stream()
        .map(this::mapToDomain).toList();
  }

  public List<Appointment> findByDoctorId(Long id) {
    return getAppointmentEntities("doctorId", id)
        .stream().map(this::mapToDomain).toList();
  }

  public List<Appointment> findByPatientId(Long id) {
    return getAppointmentEntities("patientId", id)
        .stream().map(this::mapToDomain).toList();
  }

  public Appointment findById(Long id) {
    return mapToDomain(findEntity(id));
  }

  public Appointment create(Appointment appointment) {
    AppointmentEntity entity = mapToEntity(appointment);
    entity.setType(AppointmentType.valueOf(appointment.getType()));
    entity.persist();

    return mapToDomain(entity);
  }

  public Appointment delete(Long id) {
    var entity = findEntity(id);
    entity.delete();

    return mapToDomain(entity);
  }

  public Appointment update(Appointment appointment) {
    var entity = findEntity(appointment.getId());
    entity.setDoctorId(appointment.getDoctorId());
    entity.setPatientId(appointment.getPatientId());
    entity.setType(AppointmentType.valueOf(appointment.getType()));
    entity.setStartDate(appointment.getStartDate());
    entity.setEndDate(appointment.getEndDate());

    return mapToDomain(entity);
  }

  private AppointmentEntity findEntity(Long id) {
    Optional<AppointmentEntity> entity = AppointmentEntity.findByIdOptional(id);
    return entity.orElseThrow(
        () -> new WebApplicationException("Appointment id " + id + " not found", 404)
    );
  }

  private static List<AppointmentEntity> getAppointmentEntities(String patientId, Long id) {
    return AppointmentEntity.list(patientId, id);
  }

  private Appointment mapToDomain(AppointmentEntity entity) {
    Appointment appointment = new ObjectMapper().convertValue(entity, Appointment.class);
    appointment.setCreatedDate(entity.getCreatedDate());
    appointment.setUpdatedDate(entity.getUpdatedDate());
    appointment.setType(entity.getType().name());

    return appointment;
  }

  private AppointmentEntity mapToEntity(Appointment appointment) {
    return new ObjectMapper().convertValue(appointment, AppointmentEntity.class);
  }

}
