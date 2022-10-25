package org.wj.prajumsook.ehealthcare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicationEntity extends AbstractEntity {

  private Long doctorId;
  private Long patientId;
  private String medication;
  @Enumerated(EnumType.STRING)
  private Status status;
  @Enumerated(EnumType.STRING)
  private Intent intent;
  @Enumerated(EnumType.STRING)
  private Priority priority;
  private String value;
  private String unit;
  private String notes;

}
