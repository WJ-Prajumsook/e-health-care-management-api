package org.wj.prajumsook.ehealthcare.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.wj.prajumsook.ehealthcare.entity.Specialization;

import java.util.List;

@Data
@Accessors(chain = true)
public class SpecializationResponse {

  private List<Specialization> result;
  private int count;

}
