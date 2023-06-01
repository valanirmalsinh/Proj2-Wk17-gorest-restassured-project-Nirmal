package com.gorest.crudtest;

import com.gorest.model.UserPojo;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UserCRUDTest extends TestBase {


    static String name =  "Shubh" + TestUtils.getRandomValue();
    static String email = "Shubh" + TestUtils.getRandomValue() + "@gmail.com";
    static String gender = "Male";
    static String status = "Active";
    static  int id;


    @Test
    public void verifyUserCreatedSuccessfully() {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer 57a081dd4669814ea0f6e6681c3e28bfa86977769ec6d47a5673c55835839316")
                .when()
                .body(userPojo)
                .post()
                .then().log().body().statusCode(201);
        id= response.extract().path("id");
    }
    @Test
    public  void test002Get(){
        int uId=given()
                .header("Authorization", "Bearer 57a081dd4669814ea0f6e6681c3e28bfa86977769ec6d47a5673c55835839316")
                .pathParams("id",id)
                .when()
                .get("/{id}")
                .then().statusCode(200)
                .extract()
                .path("id");
      Assert.assertEquals(uId,id);

    }


    @Test
    public void verifyUserUpdateSuccessfully() {
        UserPojo userPojo = new UserPojo();
        userPojo.setName("Kiara");
        userPojo.setEmail("Kiara"+ TestUtils.getRandomValue()+"@gmail.com");
        userPojo.setGender("Female");
        userPojo.setStatus("inactive");
        Response response= given()
                .header("Content-Type", "application/json")
                .header("Authorization","Bearer 57a081dd4669814ea0f6e6681c3e28bfa86977769ec6d47a5673c55835839316")
                .pathParams("id", id)
                .when()
                .body(userPojo)
                .put("/{id}");
        response.then().log().body().statusCode(200);
    }
    @Test
    public  void VerifyUserDeleteSuccessfully() {
        given()
                .header("Authorization","Bearer 57a081dd4669814ea0f6e6681c3e28bfa86977769ec6d47a5673c55835839316")
                .pathParam("id", id)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);

        given()
                .header("Authorization","Bearer 57a081dd4669814ea0f6e6681c3e28bfa86977769ec6d47a5673c55835839316")
                .pathParam("id", id)
                .when()
                .get("/{id}")
                .then().statusCode(404);

    }
}
