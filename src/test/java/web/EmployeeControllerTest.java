package web;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

class EmployeeControllerTest {

    @Test
    void getAllEmployees() {
        given()
                .when()
                .get("/employees")
                .then()
                .body("_embedded.employees.name", hasItems("John Doo", "Bob Boo", "Sam Boo"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getEmployeeById() {
        given().config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE)))
                .when()
                .get("/employees/2")
                .then()
                .body("id", equalTo(2))
                .body("name", equalTo("John Doo"))
                .body("position", equalTo("QA engineer"))
                .body("salary", equalTo(1500.0))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getEmployeesByDepartmentId() {
        given()
                .when()
                .get("/departments/1/employees")
                .then()
                .body("_embedded.employees.name", hasItems("John Doo", "Bob Boo"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getDetailedEmployees() {
        given()
                .when()
                .get("/employees/detailed")
                .then()
                .body("_embedded.employeeWithDepartments.name", hasItems("John Doo", "Bob Boo", "Sam Boo"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getDetailedEmployeeById() {
        given().config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE)))
                .when()
                .get("/employees/2/detailed")
                .then()
                .body("id", equalTo(2))
                .body("name", equalTo("John Doo"))
                .body("position", equalTo("QA engineer"))
                .body("department", equalTo("Testing"))
                .body("salary", equalTo(1500.0))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getEmployeeSalary() {
        given().config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE)))
                .when()
                .get("/employees/2/salary")
                .then()
                .body("salary", equalTo(1500.0))
                .statusCode(HttpStatus.SC_OK);
    }


    @Test
    void createNewEmployee() {

        JSONObject person = new JSONObject();
        person.put("name", "Terry");
        person.put("position", "QA manager");
        person.put("department", "Testing");
        person.put("salary", 5000);

        String jsonString = person.toString();

        given().contentType(JSON).body(jsonString)
                .when()
                .post("/employees")
                .then()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    void updateEmployee() {

        JSONObject employee = new JSONObject();
        employee.put("name", "New Name");
        employee.put("position", "New position");
        employee.put("salary", 2000);

        String jsonString = employee.toString();

        given().contentType(JSON).body(jsonString)
                .when()
                .put("/employees/2")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void deleteEmployee() {
        given()
                .when()
                .delete("/employees/3")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}