package me.wcquino.core;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class BaseTest {
    static Properties properties = ConfigFactory.create(Properties.class);

    @BeforeAll
    public static void setup() {
        baseURI = properties.baseURI();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.addFilter(new AllureRestAssured());
        requestSpecification = requestSpecBuilder.build();

        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected static String getTokenLogin() {
        Map<String, String> userData = new HashMap<>();
        userData.put("email", properties.email());
        userData.put("senha", properties.password());

        return given()
            .body(userData)
        .when()
            .post("/signin")
        .then()
            .statusCode(200)
            .extract().path("token");
    }

    @BeforeEach
    public void resetData() {
        given()
            .header("Authorization", "JWT " + getTokenLogin())
        .when()
            .get("/reset")
        .then()
            .statusCode(200);
    }
}
