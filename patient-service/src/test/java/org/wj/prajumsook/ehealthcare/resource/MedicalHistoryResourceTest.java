package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wj.prajumsook.ehealthcare.entity.MedicalHistory;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class MedicalHistoryResourceTest {

  private Long id;

  @BeforeEach
  public void setup() {
    MedicalHistory mh = new MedicalHistory()
        .setPatientId(100L)
        .setBloodType("A")
        .setBloodSugar("101 deg")
        .setBloodPressure("120/185")
        .setWeight("90 kg")
        .setNotes("History notes");

    mh = given().when()
        .contentType(ContentType.JSON)
        .body(mh)
        .post("/ehealthcare/v1/medicshistory")
        .then()
        .statusCode(200)
        .body(containsString("History notes"))
        .extract()
        .as(MedicalHistory.class);

    id = mh.id;
  }

  @Test
  public void testFindById() {
    MedicalHistory mh = given().when()
        .get("/ehealthcare/v1/medicshistory/" + id)
        .then()
        .statusCode(200)
        .extract()
        .as(MedicalHistory.class);

    assertEquals(100, mh.getPatientId());
    assertEquals("History notes", mh.getNotes());
  }

  @Test
  public void testFindByPatientId() {
    given().when()
        .get("/ehealthcare/v1/medicshistory/patient/100")
        .then()
        .statusCode(200)
        .body(containsString("120/185"));
  }

  @Test
  public void testUpdate() {
    MedicalHistory mh = given().when()
        .get("/ehealthcare/v1/medicshistory/"+id)
        .then()
        .statusCode(200)
        .extract()
        .as(MedicalHistory.class);
    mh.setNotes("updated notes");
    given().when()
        .contentType(ContentType.JSON)
        .body(mh)
        .put("/ehealthcare/v1/medicshistory")
        .then()
        .statusCode(200)
        .body(containsString("updated notes"));
  }

  @Test
  public void testDelete() {
    given().when()
        .delete("/ehealthcare/v1/medicshistory/"+id)
        .then()
        .statusCode(200);
  }
}
