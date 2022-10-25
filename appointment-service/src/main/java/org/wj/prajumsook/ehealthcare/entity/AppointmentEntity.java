package org.wj.prajumsook.ehealthcare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentEntity extends AbstractEntity {

  private Long doctorId;
  private Long patientId;
  @Enumerated(EnumType.STRING)
  private AppointmentType type;
  private String startDate;
  private String endDate;

}
