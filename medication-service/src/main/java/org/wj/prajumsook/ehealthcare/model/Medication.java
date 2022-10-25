package org.wj.prajumsook.ehealthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Medication {

  private Long id;
  private Long doctorId;
  private Long patientId;
  private String medication;
  private String status;
  private String intent;
  private String priority;
  private String value;
  private String unit;
  private String notes;

}
