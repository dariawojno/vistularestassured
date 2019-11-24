package org.vistula.restassured.pet;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class PetControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    public void shouldGetFirstPet() {
        given().get("/pet/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", equalTo("Cow"));
    }

    @Test
    public void shouldCreateNewPet() {
        JSONObject requestParams = new JSONObject();                      /*żeby stworzyć jsona w klasie ocject */
        int value = ThreadLocalRandom.current().nextInt(20,Integer.MAX_VALUE);
        requestParams.put("id", value);                                       /*korzystamy z metody put*/
        requestParams.put("name", RandomStringUtils.randomAlphabetic(10));

        given().header("Content-Type", "application/json")          /*w jednym teście stworzył i usunął nowe zwierzątko*/
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
        given().delete("/pet/"+value)
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void shouldGetNotExistPet() {
        int statusCode = given().get("/pet/7")
                .then()
                .log().all()
                .statusCode(404)
                .body(equalTo("There is no Pet with such id"))
                .extract().statusCode();

        assertThat(statusCode).isEqualTo(404);
    }

    @Test
    public void shouldGetSecondPet() {
        Object name = given().get("/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(2))
                .body("name", equalTo("Dog"))
                .extract().path("name");

        assertThat(name).isEqualTo("Dog");


    }

    @Test
    public void shouldGetSecondPetWithName() {
        Pet pet = given().get("/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(2))
                .body("name", equalTo("Dog"))
                .extract().body().as(Pet.class);

        assertThat(pet.getId()).isEqualTo(2);
        assertThat(pet.getName()).isEqualTo("Dog");
    }

    @Test
    public void shouldDeleteNewPet() {

        given().delete("/pet/4")
                .then()
                .log().all()
                .statusCode(204);
    }

}