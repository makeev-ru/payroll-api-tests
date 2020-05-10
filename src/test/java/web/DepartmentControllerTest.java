package web;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

class DepartmentControllerTest {

    @Test
    void getAllDepartments() {
        given()
                .when()
                .get("/departments")
                .then().log().ifError()
                .body("_embedded.departments.name", hasItems("Testing", "Development"))
                .statusCode(200);
    }

    @Test
    void getOneDepartmentById() {
        given()
                .when()
                .get("/departments/1")
                .then().log().ifError()
                .body("id", equalTo(1))
                .body("name", equalTo("Testing"))
                .statusCode(200);
    }

    @Test
    void getDepartmentForEmployee() {
        given()
                .when()
                .get("/employees/2/department")
                .then().log().ifError()
                .body("id", equalTo(1))
                .body("name", equalTo("Testing"))
                .statusCode(200);
    }

}