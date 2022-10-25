package org.wj.prajumsook.ehealthcare.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaboratoryEntity extends AbstractEntity {

  private Long testTypeId;
  private Long doctorId;
  private Long patientId;
  @Enumerated(EnumType.STRING)
  private LabStatus status;
  private String notes;

}
