package me.wcquino.core;

import static io.restassured.RestAssured.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    static final String BASE_URL = "http://barrigarest.wcaquino.me";

    @BeforeAll
    public static void setup() {
        baseURI = BASE_URL;
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecification = requestSpecBuilder.build();

        enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
