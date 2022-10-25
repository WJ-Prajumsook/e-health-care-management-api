package org.wj.prajumsook.ehealthcare.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@RegisterForReflection
public class EntityListener {

  @PrePersist
  public void preCreate(AbstractEntity entity) {
    Instant now = Instant.now();
    entity.setCreatedDate(now.toString());
    entity.setUpdateDate(now.toString());
  }

  @PreUpdate
  public void preUpdate(AbstractEntity entity) {
    Instant now = Instant.now();
    entity.setUpdateDate(now.toString());
  }

}
