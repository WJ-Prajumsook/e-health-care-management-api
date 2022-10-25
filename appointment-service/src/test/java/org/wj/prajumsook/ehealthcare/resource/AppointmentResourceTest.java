package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wj.prajumsook.ehealthcare.model.Appointment;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class AppointmentResourceTest {

  private Long id;

  @BeforeEach
  public void setup() {
    Appointment appointment = new Appointment()
        .setDoctorId(11L)
        .setPatientId(22L)
        .setType("CHECKUP")
        .setStartDate("2022-10-10")
        .setEndDate("2022-11-10");

    appointment = given().when()
        .contentType(ContentType.JSON)
        .body(appointment)
        .post("/ehealthcare/v1/appointments")
        .then()
        .statusCode(200)
        .extract()
        .as(Appointment.class);

    id = appointment.getId();
  }

  @Test
  public void testFindById() {
    given()
        .when().get("/ehealthcare/v1/appointments/" + id)
        .then()
        .statusCode(200)
        .body(containsString("CHECKUP"));
  }

  @Test
  public void testFindAll() {
    given().when()
        .get("/ehealthcare/v1/appointments")
        .then()
        .statusCode(200)
        .body(containsString("CHECKUP"));
  }

  @Test
  public void testUpdate() {
    Appointment appointment = given().when()
        .get("/ehealthcare/v1/appointments/" + id)
        .then()
        .statusCode(200)
        .extract()
        .as(Appointment.class);

    appointment.setStartDate("2022-10-12");
    appointment.setEndDate("2022-10-13");

    given().when()
        .contentType(ContentType.JSON)
        .body(appointment)
        .put("/ehealthcare/v1/appointments")
        .then()
        .statusCode(200)
        .body(containsString("CHECKUP"))
        .body(containsString("2022-10-12"))
        .body(containsString("2022-10-13"));

  }


}