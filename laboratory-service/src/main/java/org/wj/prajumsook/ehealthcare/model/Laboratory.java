package org.wj.prajumsook.ehealthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Laboratory {

  private Long id;
  private LabType labType;
  private Long doctorId;
  private Long patientId;
  private String status;
  private String notes;
  private String createdDate;
  private String updatedDate;

}
