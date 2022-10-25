package org.wj.prajumsook.ehealthcare.service;

import org.wj.prajumsook.ehealthcare.entity.Specialization;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class SpecializationService {

  public List<Specialization> findAll() {
    return Specialization.listAll();
  }

  public Specialization findById(Long id) {
    Optional<Specialization> opt = Specialization.findByIdOptional(id);
    return opt.orElseThrow(
        () -> new WebApplicationException("Specialization id " + id + " not found", 404)
    );
  }

  public List<Specialization> findByDoctorId(Long id) {
    return Specialization.list("doctorId", id);
  }

  public Specialization create(Specialization spec) {
    spec.persist();

    return spec;
  }

  public Specialization update(Specialization spec) {
    Specialization entity = findById(spec.id);
    entity.setName(spec.getName());
    entity.setDescription(spec.getDescription());
    entity.setDoctorId(spec.getDoctorId());

    return entity;
  }

  public Specialization delete(Long id) {
    Specialization spec = findById(id);
    spec.delete();

    return spec;
  }
}
