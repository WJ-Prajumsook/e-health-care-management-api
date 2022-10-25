package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wj.prajumsook.ehealthcare.model.Medication;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class MedicationResourceTest {

  private Long id;

  @BeforeEach
  public void setup() {
    Medication medication = new Medication()
        .setPatientId(11L)
        .setDoctorId(22L)
        .setMedication("Medication name")
        .setStatus("ACTIVE")
        .setIntent("ORDER")
        .setPriority("ASAP")
        .setValue("1L")
        .setUnit("1B")
        .setNotes("Medication notes");

    medication = given().when()
        .contentType(ContentType.JSON)
        .body(medication)
        .post("/ehealthcare/v1/medications")
        .then()
        .statusCode(200)
        .extract()
        .as(Medication.class);

    id = medication.getId();
  }

  @Test
  public void testFindById() {
    given().when()
        .get("/ehealthcare/v1/medications/" + id)
        .then()
        .statusCode(200)
        .body(containsString("Medication notes"));
  }

  @Test
  public void testFindAll() {
    given().when()
        .get("/ehealthcare/v1/medications")
        .then()
        .statusCode(200)
        .body(containsString("Medication notes"));
  }

  @Test
  public void testUpdate() {
    Medication medic = given().when()
        .get("/ehealthcare/v1/medications/" + id)
        .then()
        .statusCode(200)
        .extract()
        .as(Medication.class);
    medic.setMedication(medic.getMedication() + " updated");

    given().when()
        .contentType(ContentType.JSON)
        .body(medic)
        .put("/ehealthcare/v1/medications")
        .then()
        .statusCode(200)
        .body(containsString("Medication name updated"));
  }


}