# <u>E-Health Care Management API</u>
Build E-Health Care Management API with Quarkus and PostgreSQL on Kubernets.
## Technologies
- Java
- Quarkus and Microprofile
- PostgreSQL
- Minikube and Kubernetes
- Hibernate-ORM

## Structure
- Create Quarkus Project
- Project configuration
- Database source configuration
- Define database entities
- Define domain model
- Define service layer
- Define JAX-RS resource layer
- Define exception mappers
- Test

## API Architecture
- Appointment Service
- Doctor Service
- Laboratory Service
- Medication Service
- Patient Service

![img.00]

## Appointment Service
The scheduling api for appointment and events.
### Endpoints
- findAll - GET `/api/v1/appointments`
- findById - GET `/api/v1/appointments/{id}`
- findByDoctorId - GET `/api/v1/appointments/doctor/{id}`
- findByPatientId - GET `/api/v1/appointments/patient/{id}`
- create - POST `/api/v1/appointments`
- update - PUT `/api/v1/appointments`
- delete - DELETE `/api/v1/appointments/{id}`

### Create Quarkus Project
To create a new Quarkus project, open a terminal and run the following command:
```shell script
mvn io.quarkus:quarkus-maven-plugin:2.13.2.Final:create \
    -DprojectGroupId=prajumsook \
    -DprojectArtifactId=appointment-service \
    -DclassName="org.wj.prajumsook.ehealthcare.resource.AppointmentResource" \
    -Dpath="/v1/appointments" 
```
Add project extensions
```shell script
mvn quarkus:add-extension -Dextensions="hibernate-orm-panache,jdbc-postgresql,smallrye-openapi,kubernetes,jib,minikube"
```
#### Project configuration
Add `lombok` to `pom.xml`
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version>
</dependency>
```
Add `testcontainers` to `pom.xml`
```xml
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>1.15.3</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>1.15.3</version>
    <scope>test</scope>
</dependency>
```
`Testcontainers is a Java library that supports JUnit tests, providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a container.` 

### Datasource Configuration
Open `application.properties` file and add following properties
```java
quarkus.http.cors=true

quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=quarkus_user
quarkus.datasource.password=quarkus_pass
quarkus.datasource.jdbc.url=jdbc:postgresql://192.168.64.13:30831/quarkusdb

quarkus.hibernate-orm.database.generation=update
```
Database type: `postgresql`<br/>
Database username: `quarkus_user`<br/>
Database password: `quarkus_pass`<br/>
Database url: `jdbc:postgresql://192.168.64.13:30831/quarkusdb`
Database name: `quarkusdb`<br/>
Database running on host: `192.168.64.13`<br/>
Port: `30831`

### Define Database Entities
This service has only one entity and an enum:
- AppointmentEntity
- AppointmentType

Define entity listeners to handle create and update callback.
```java
package org.wj.prajumsook.ehealthcare.entity;

import java.time.Instant;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EntityListener {

  @PrePersist
  public void preCreate(AbstractEntity entity) {
    Instant now = Instant.now();
    entity.setCreatedDate(now.toString());
    entity.setUpdatedDate(now.toString());
  }

  @PreUpdate
  public void preUpdate(AbstractEntity entity) {
    Instant now = Instant.now();
    entity.setUpdatedDate(now.toString());
  }
}
```
Use `@EntityListeners` to apply these method to all class that extends it.
```java
package org.wj.prajumsook.ehealthcare.entity;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(EntityListener.class)
public abstract class AbstractEntity extends PanacheEntity {

  private String createdDate;
  private String updatedDate;

}
```
And now extends the AbstractEntity:
```java
package org.wj.prajumsook.ehealthcare.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentEntity extends AbstractEntity {

  private Long doctorId;
  private Long patientId;
  @Enumerated(EnumType.STRING)
  private AppointmentType type;
  private String startDate;
  private String endDate;

}
```
And the enum
```java
package org.wj.prajumsook.ehealthcare.entity;

public enum AppointmentType {
  CHECKUP,
  EMERGENCY,
  FOLLOWUP,
  ROUTINE,
  WALKIN
}
```

### Define Domain Model
Domain model is a DTO that we will share with the client that are using the service.
```java
package org.wj.prajumsook.ehealthcare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment {

  private Long id;
  private Long doctorId;
  private Long patientId;
  private String type;
  private String startDate;
  private String endDate;
  private String createdDate;
  private String updatedDate;

}
```

### Define Service Layer
To separate business logic out of the database layer, so we will implement the service layer that will take care of out business logic.
```java
package org.wj.prajumsook.ehealthcare.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.wj.prajumsook.ehealthcare.entity.AppointmentEntity;
import org.wj.prajumsook.ehealthcare.entity.AppointmentType;
import org.wj.prajumsook.ehealthcare.model.Appointment;
import org.wj.prajumsook.ehealthcare.model.AppointmentResponse;

@ApplicationScoped
@Transactional
public class AppointmentService {

  public AppointmentResponse findAll() {
    List<AppointmentEntity> entities = AppointmentEntity.listAll();
    var list = entities.stream().map(this::mapToDomain).collect(Collectors.toList());
    return new AppointmentResponse().setResult(list).setCount(list.size());
  }

  public Appointment findById(Long id) {
    return mapToDomain(findEntity(id));
  }

  public List<Appointment> findByDoctorId(Long id) {
    List<AppointmentEntity> entities = AppointmentEntity.list("doctorId", id);
    return entities.stream().map(this::mapToDomain).collect(Collectors.toList());
  }

  public List<Appointment> findByPatientId(Long id) {
    List<AppointmentEntity> entities = AppointmentEntity.list("patientId", id);
    return entities.stream().map(this::mapToDomain).collect(Collectors.toList());
  }

  public Appointment create(Appointment appointment) {
    var entity = mapToEntity(appointment);
    entity.setType(AppointmentType.valueOf(appointment.getType()));

    entity.persist();

    return mapToDomain(entity);
  }

  public Appointment update(Appointment appointment) {
    var entity = findEntity(appointment.getId());
    entity.setDoctorId(appointment.getDoctorId());
    entity.setPatientId(appointment.getPatientId());
    entity.setType(AppointmentType.valueOf(appointment.getType()));
    entity.setStartDate(appointment.getStartDate());
    entity.setEndDate(appointment.getEndDate());

    return mapToDomain(entity);
  }

  public Appointment delete(Long id) {
    var entity = findEntity(id);
    entity.delete();

    return mapToDomain(entity);
  }

  private AppointmentEntity findEntity(Long id) {
    Optional<AppointmentEntity> entity = AppointmentEntity.findByIdOptional(id);

    return entity.orElseThrow(() -> new WebApplicationException("Appointment id " + id + " not found", 404));
  }

  private Appointment mapToDomain(AppointmentEntity entity) {
    return new ObjectMapper().convertValue(entity, Appointment.class);
  }

  private AppointmentEntity mapToEntity(Appointment appointment) {
    return new ObjectMapper().convertValue(appointment, AppointmentEntity.class);
  }
}
```

### Define JAX-RS Resource layer
This layer will implement ur endpoints using JAX-RS
```java
@Path("/v1/appointments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointResource {

  @Inject
  AppointmentService appointmentService;

  @GET
  public AppointmentResponse findAll() {
    return appointmentService.findAll();
  }

  @GET
  @Path("/{id}")
  public Appointment findById(@RestPath Long id) {
    return appointmentService.findById(id);
  }

  @GET
  @Path("/doctor/{id}")
  public List<Appointment> findByDoctorId(@RestPath Long id) {
    return appointmentService.findByDoctorId(id);
  }

  @GET
  @Path("/patient/{id}")
  public List<Appointment> findByPatientId(@RestPath Long id) {
    return appointmentService.findByPatientId(id);
  }

  @POST
  public Appointment create(Appointment appointment) {
    return appointmentService.create(appointment);
  }

  @PUT
  public Appointment update(Appointment appointment) {
    return appointmentService.update(appointment);
  }

  @DELETE
  @Path("/{id}")
  public Appointment delete(@RestPath Long id) {
    return appointmentService.delete(id);
  }
}
```

### Define Exception Mappers
Create an implementation of `ExceptionMapper` to map all throw application exception to a `Response` object.
```java
package org.wj.prajumsook.ehealthcare.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ErrorMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception exe) {
    int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
    if (exe instanceof WebApplicationException) {
      statusCode = ((WebApplicationException) exe).getResponse().getStatus();
    }

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode error = mapper.createObjectNode();
    error.put("exceptionType", exe.getClass().getName());
    error.put("statusCode", statusCode);
    error.put("error", (exe.getMessage() != null) ? exe.getMessage() : "Unknown error");

    return Response.status(statusCode).entity(error).build();
  }
}
```

### Test our Service
Using `testcontainer` to test the service running on `Kubernetes`<br/>
Create test container resource:
```java
package org.wj.prajumsook.ehealthcare.resource;

import java.util.Collections;
import java.util.Map;
import org.testcontainers.containers.PostgreSQLContainer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class TestContainerResource implements QuarkusTestResourceLifecycleManager {

  private static final PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:13")
      .withDatabaseName("appointment_db")
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
```
And then in our test class
```java
@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class AppointResourceTest {
  private Long id;

  @BeforeEach
  public void setup() {
    Appointment appointment = new Appointment()
        .setDoctorId(11L)
        .setPatientId(22L)
        .setType("CHECKUP")
        .setStartDate("")
        .setEndDate("");

    appointment = given().when()
        .contentType(ContentType.JSON)
        .body(appointment)
        .post("/api/v1/appointments")
        .then()
        .statusCode(200)
        .extract()
        .as(Appointment.class);

    id = appointment.getId();
  }

  @Test
  public void testFindById() {
    given()
        .when().get("/api/v1/appointments/" + id)
        .then()
        .statusCode(200)
        .body(containsString("CHECKUP"));
  }

  @Test
  public void testFindAll() {
    given().when()
        .get("/api/v1/appointments")
        .then()
        .statusCode(200)
        .body(containsString("CHECKUP"));
  }

  @Test
  public void testUpdate() {
    Appointment appointment = given().when()
        .get("/api/v1/appointments/" + id)
        .then()
        .statusCode(200)
        .extract()
        .as(Appointment.class);

    appointment.setStartDate("startdate");
    appointment.setEndDate("enddate");

    given().when()
        .contentType(ContentType.JSON)
        .body(appointment)
        .put("/api/v1/appointments")
        .then()
        .statusCode(200)
        .body(containsString("startdate"))
        .body(containsString("enddate"))
        .body(containsString("CHECKUP"));
  }
}
```

And the application resource
```java
package org.wj.prajumsook.ehealthcare;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class AppointmentServiceApplication extends Application {
}
```

Deploy to Kubernetes
```shell script
mvn clean package -Dquarkus.native.container-build=true l-Dquarkus.kubernetes.deploy=true
```
Delete from Kubernetes
```shell script
kubectl delete -f target/kubernetes/minikube.yaml
```



[img.00]: assets/e-health-care-system-api.png
