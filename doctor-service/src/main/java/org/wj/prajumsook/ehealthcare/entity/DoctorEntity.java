package org.wj.prajumsook.ehealthcare.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorEntity extends AbstractEntity{

  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;

}
