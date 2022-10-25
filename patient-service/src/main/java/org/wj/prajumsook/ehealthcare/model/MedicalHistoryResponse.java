package org.wj.prajumsook.ehealthcare.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.wj.prajumsook.ehealthcare.entity.MedicalHistory;

import java.util.List;

@Data
@Accessors(chain = true)
public class MedicalHistoryResponse {

  private List<MedicalHistory> result;
  private int count;

}