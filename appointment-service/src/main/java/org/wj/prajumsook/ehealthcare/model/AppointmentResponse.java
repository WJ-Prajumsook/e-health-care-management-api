package org.wj.prajumsook.ehealthcare.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AppointmentResponse {

  private List<Appointment> result;
  private int count;

}
