package org.wj.prajumsook.ehealthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.wj.prajumsook.ehealthcare.entity.MedicalHistory;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient {

  private Long id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String email;
  private String phoneNumber;
  private String address;
  private String city;
  private String gender;
  private String patientType;
  private String createDate;
  private String lastUpdatedDate;
  private List<MedicalHistory> medicalHistories;

}
