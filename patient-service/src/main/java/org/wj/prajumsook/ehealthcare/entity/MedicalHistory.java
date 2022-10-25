package org.wj.prajumsook.ehealthcare.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class MedicalHistory extends AbstractEntity {

  private Long patientId;
  private String bloodType;
  private String bloodSugar;
  private String bloodPressure;
  private String weight;
  private String notes;

}
