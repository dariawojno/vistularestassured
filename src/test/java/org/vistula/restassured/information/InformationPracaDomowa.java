package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Information;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;

public class InformationPracaDomowa extends RestAssuredTest {

    @Test
    public void shouldAddNewPlayer() {
        // POST

        JSONObject requestParams = new JSONObject();

        String myNationality = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("nationality", myNationality);
        String myName = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("name", myName);
        requestParams.put("salary", 50000);

        int id = given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .extract().path("id");
        // PUT

        JSONObject requestParams2 = new JSONObject();
        String myNewNationality = RandomStringUtils.randomAlphabetic(10);
        String myNewName = RandomStringUtils.randomAlphabetic(10);
        requestParams2.put("name", myNewName);
        requestParams2.put("salary", 80000);
        requestParams2.put("nationality", myNewNationality);

        given().header("Content-Type", "application/json")
                .body(requestParams2.toString())
                .put("/information/" + id)
                .then()
                .log().all()
                .statusCode(200)

                .body("name", equalTo(myNewName))
                .body("nationality", equalTo(myNewNationality))
                .body("salary", equalTo(80000))
                .body("id", greaterThan(0));

        given().delete("/information/" + id)
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void shouldChangeSalary() {
        // POST

        JSONObject requestParams = new JSONObject();

        String myNationality = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("nationality", myNationality);
        String myName = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("name", myName);
        requestParams.put("salary", 75000);

        int id = given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .extract().path("id");
        // PATCH

        JSONObject requestParams3 = new JSONObject();
        requestParams3.put("name", myName);
        requestParams3.put("salary", 90000);
        requestParams3.put("nationality", myNationality);

        given().header("Content-Type", "application/json")
                .body(requestParams3.toString())
                .patch("/information/" + id)
                .then()
                .log().all()
                .statusCode(200)

                .body("name", equalTo(myName))
                .body("nationality", equalTo(myNationality))
                .body("salary", equalTo(90000))
                .body("id", greaterThan(0));

        given().delete("/information/" + id)
                .then()
                .log().all()
                .statusCode(204);
    }
}

        /*Information newSalary = given().header("Content-Type", "application/json")
                .body(requestParams3.toString())
                .patch("/informaion/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("salary", equalTo(salary().getsalary()))
                .extract().as(Information.class);

        assertThat(newSalary.getSalary()).isEqualTo(salary.getSalary()); */









