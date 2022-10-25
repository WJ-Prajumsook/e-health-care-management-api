package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wj.prajumsook.ehealthcare.entity.Specialization;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class SpecializationResourceTest {

  private Long id;

  @BeforeEach
  public void setup() {
    Specialization spec = new Specialization()
        .setDoctorId(1L)
        .setName("General Physician")
        .setDescription("This is description");
    spec = given().when()
        .contentType(ContentType.JSON)
        .body(spec)
        .post("/ehealthcare/v1/specs")
        .then()
        .extract()
        .as(Specialization.class);

    id = spec.id;
  }

  @Test
  public void testFindById() {
    given().when()
        .get("/ehealthcare/v1/specs/"+id)
        .then()
        .statusCode(200)
        .body(containsString("General Physician"));
  }

  @Test
  public void testUpdate() {
    Specialization spec = given().when()
        .get("/ehealthcare/v1/specs/"+id)
        .then()
        .statusCode(200)
        .extract()
        .as(Specialization.class);
    spec.setDescription(spec.getDescription() + " updated");
    given().when()
        .contentType(ContentType.JSON)
        .body(spec)
        .put("/ehealthcare/v1/specs")
        .then()
        .statusCode(200)
        .body(containsString("This is description updated"));
  }
}
