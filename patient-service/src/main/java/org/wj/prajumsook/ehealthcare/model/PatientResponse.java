package org.wj.prajumsook.ehealthcare.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PatientResponse {

  private List<Patient> result;
  private int count;

}