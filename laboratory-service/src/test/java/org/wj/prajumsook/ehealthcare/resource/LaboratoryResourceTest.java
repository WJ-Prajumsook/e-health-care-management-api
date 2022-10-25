package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wj.prajumsook.ehealthcare.model.LabType;
import org.wj.prajumsook.ehealthcare.model.Laboratory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class LaboratoryResourceTest {

  private Long id;

  @BeforeEach
  public void setup() {
    Laboratory laboratory = new Laboratory()
        .setDoctorId(11L)
        .setPatientId(22L)
        .setStatus("ORDERED")
        .setNotes("Lab notes");

    laboratory = given().when()
        .contentType(ContentType.JSON)
        .body(laboratory)
        .post("/ehealthcare/v1/labs")
        .then()
        .statusCode(200)
        .extract()
        .as(Laboratory.class);

    id = laboratory.getId();
  }

  @Test
  public void testFindById() {
    given().when()
        .get("/ehealthcare/v1/labs/" + id)
        .then()
        .statusCode(200)
        .body(containsString("Lab notes"));
  }

  @Test
  public void testFindByIdNotFound() {
    given().when()
        .get("/ehealthcare/v1/labs/12")
        .then()
        .statusCode(404)
        .body(containsString("Laboratory id 12 not found"));
  }

  @Test
  public void testUpdate() {
    LabType type = new LabType()
        .setName("Complete blood count")
        .setDescription("Description");
    type = given().when()
        .contentType(ContentType.JSON)
        .body(type)
        .post("/ehealthcare/v1/labtypes")
        .then()
        .statusCode(200)
        .extract()
        .as(LabType.class);

    Laboratory lab = given().when()
        .get("/ehealthcare/v1/labs/"+ id)
        .then()
        .statusCode(200)
        .extract()
        .as(Laboratory.class);
    lab.setLabType(type);

    Laboratory res = given().when()
        .contentType(ContentType.JSON)
        .body(lab)
        .put("/ehealthcare/v1/labs")
        .then()
        .statusCode(200)
        .extract()
        .as(Laboratory.class);

    Assertions.assertEquals("Complete blood count", res.getLabType().getName());
  }

}