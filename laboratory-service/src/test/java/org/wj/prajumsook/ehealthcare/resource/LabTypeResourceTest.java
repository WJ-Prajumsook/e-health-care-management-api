package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wj.prajumsook.ehealthcare.model.LabType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
public class LabTypeResourceTest {

  private Long id;

  @BeforeEach
  public void setup() {
    LabType labType = new LabType()
        .setName("Complete blood count")
        .setDescription("Complete blood count description");

    labType = given().when()
        .contentType(ContentType.JSON)
        .body(labType)
        .post("/ehealthcare/v1/labtypes")
        .then()
        .statusCode(200)
        .extract()
        .as(LabType.class);

    id = labType.getId();
  }

  @Test
  public void testFindById() {
    given().when()
        .get("/ehealthcare/v1/labtypes/" + id)
        .then()
        .statusCode(200)
        .body(containsString("Complete blood count"));
  }

  @Test
  public void testFindByIdNotFound() {
    given().when()
        .get("/ehealthcare/v1/labtypes/12")
        .then()
        .statusCode(404)
        .body(containsString("Laboratory type id 12 not found"));
  }

  @Test
  public void findAll() {
    given().when()
        .get("/ehealthcare/v1/labtypes")
        .then()
        .statusCode(200)
        .body(containsString("Complete blood count"));
  }

  @Test
  public void testUpdate() {
    LabType labType = given().when().get("/ehealthcare/v1/labtypes/"+id)
        .then()
        .statusCode(200)
        .body(containsString("Complete blood count"))
        .extract()
        .as(LabType.class);
    labType.setName("Endocrinology");

    given().when()
        .contentType(ContentType.JSON)
        .body(labType)
        .put("/ehealthcare/v1/labtypes")
        .then()
        .statusCode(200)
        .body(containsString("Endocrinology"));
  }
}
