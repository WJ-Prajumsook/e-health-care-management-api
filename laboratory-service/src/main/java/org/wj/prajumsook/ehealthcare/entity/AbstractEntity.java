package org.wj.prajumsook.ehealthcare.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(EntityListener.class)
public class AbstractEntity extends PanacheEntity {

  private String createdDate;
  private String updatedDate;

}
