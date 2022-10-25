package org.wj.prajumsook.ehealthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment {

  private Long id;
  private Long doctorId;
  private Long patientId;
  private String type;
  private String startDate;
  private String endDate;
  private String createdDate;
  private String updatedDate;

}
