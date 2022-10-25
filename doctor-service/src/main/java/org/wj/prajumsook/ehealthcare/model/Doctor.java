package org.wj.prajumsook.ehealthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.wj.prajumsook.ehealthcare.entity.Specialization;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Doctor {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private String createdDate;
  private String updatedDate;
  private List<Specialization> specializations;

}
