package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

public class TestContainerResource implements QuarkusTestResourceLifecycleManager {

  private static final PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:13")
      .withDatabaseName("labs_db")
      .withUsername("quarkus_user")
      .withPassword("quarkus_pass");

  @Override
  public Map<String, String> start() {
    db.start();
    return Collections.singletonMap("quarkus.datasource.jdbc.url", db.getJdbcUrl());
  }

  @Override
  public void stop() {
    db.stop();
  }
}
