package org.wj.prajumsook.ehealthcare.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wj.prajumsook.ehealthcare.model.Doctor;

import javax.print.Doc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DoctorResourceTest {
    private Long id;

    @BeforeEach
    public void setup() {
        Doctor doctor = new Doctor()
            .setFirstName("Firstname")
            .setLastName("Lastname")
            .setEmail("email@amail.com")
            .setPhoneNumber("1234567890");

        doctor = given().when()
            .contentType(ContentType.JSON)
            .body(doctor)
            .post("/ehealthcare/v1/doctors")
            .then()
            .statusCode(200)
            .extract()
            .as(Doctor.class);

        id = doctor.getId();
    }

    @Test
    public void testFindById() {
        given().when()
            .get("/ehealthcare/v1/doctors/"+id)
            .then()
            .statusCode(200)
            .body(containsString("email@amail.com"));
    }

    @Test
    public void testFindAll() {
        given().when()
            .get("/ehealthcare/v1/doctors")
            .then()
            .statusCode(200);
    }

    @Test
    public void testUpdate() {
        Doctor doctor = given().when()
            .get("/ehealthcare/v1/doctors/"+id)
            .then()
            .statusCode(200)
            .extract()
            .as(Doctor.class);
        doctor.setEmail("updated");
        doctor = given().when()
            .contentType(ContentType.JSON)
            .body(doctor)
            .put("/ehealthcare/v1/doctors")
            .then()
            .statusCode(200)
            .body(containsString("updated"))
            .extract()
            .as(Doctor.class);

        assertEquals("updated", doctor.getEmail());
    }

}