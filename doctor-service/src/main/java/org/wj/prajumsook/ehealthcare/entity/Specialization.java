package org.wj.prajumsook.ehealthcare.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Specialization extends PanacheEntity {

  private Long doctorId;
  private String name;
  private String description;

}
