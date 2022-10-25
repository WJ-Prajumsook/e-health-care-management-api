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
public class PatientEntity extends AbstractEntity {

  private String firstName;
  private String lastName;
  private String dateOfBirth;
  private String email;
  private String phoneNumber;
  private String address;
  private String city;
  @Enumerated(EnumType.STRING)
  private Gender gender;
  @Enumerated(EnumType.STRING)
  private PatientType patientType;

}
