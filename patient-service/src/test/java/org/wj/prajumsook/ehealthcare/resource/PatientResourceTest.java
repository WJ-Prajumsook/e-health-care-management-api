package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wj.prajumsook.ehealthcare.entity.Gender;
import org.wj.prajumsook.ehealthcare.entity.PatientType;
import org.wj.prajumsook.ehealthcare.model.Patient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class PatientResourceTest {

  private Long id;

  @BeforeEach
  public void setup() {
    Patient patient = new Patient()
        .setFirstName("John")
        .setLastName("Bradley")
        .setDateOfBirth("1998-02-22")
        .setEmail("john@gmail.com")
        .setPhoneNumber("202-505-0122")
        .setAddress("3933 Cheshire Road")
        .setCity("New Haven")
        .setGender(Gender.MALE.name())
        .setPatientType(PatientType.PRIVATE.name());

    patient = given().when()
        .contentType(ContentType.JSON)
        .body(patient)
        .post("/ehealthcare/v1/patients")
        .then()
        .statusCode(200)
        .extract()
        .as(Patient.class);

    id = patient.getId();
  }

  @Test
  public void testFindById() {
    given().when()
        .get("/ehealthcare/v1/patients/"+id)
        .then()
        .statusCode(200)
        .body(containsString("New Haven"));
  }

  @Test
  public void testUpdate() {
    Patient patient = given().when()
        .get("/ehealthcare/v1/patients/"+id)
        .then()
        .statusCode(200)
        .extract()
        .as(Patient.class);
    patient.setEmail("updated@gmail.com");
    given().when()
        .contentType(ContentType.JSON)
        .body(patient)
        .put("/ehealthcare/v1/patients")
        .then()
        .statusCode(200)
        .body(containsString("updated@gmail.com"));
  }


}