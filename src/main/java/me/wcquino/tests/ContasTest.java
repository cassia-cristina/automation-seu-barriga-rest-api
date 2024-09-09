package me.wcquino.tests;

import me.wcquino.core.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ContasTest extends BaseTest {

    @Test
    public void naoDevePermitirAcessarRecursoContasSemToken() {
        given()
        .when()
            .get("/contas")
        .then()
            .statusCode(401)
            .body(is("Unauthorized"));
    }

    @Test
    public void deveIncluirUmaContaComSucesso() {
        Map<String, String> accountData = new HashMap<>();
        accountData.put("nome", "TestesNew");

        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body(accountData)
        .when()
            .post("/contas")
        .then()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("nome", is("TestesNew"))
            .body("visivel", is(true));
    }

    @Test
    public void deveAlterarUmaContaComSucesso() {
        Map<String, String> accountData = new HashMap<>();
        accountData.put("nome", "Testes2");

        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body(accountData)
        .when()
            .put("/contas/2244037")
        .then()
            .statusCode(200)
            .body("id", is(notNullValue()))
            .body("nome", is("Testes2"))
            .body("visivel", is(true));;
    }

    @Test
    public void naoDeveInserirContaComMesmoNome() {
        Map<String, String> accountData = new HashMap<>();
        accountData.put("nome", "Testes");

        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body(accountData)
        .when()
            .post("/contas")
        .then()
            .statusCode(400)
            .body("error", is("Já existe uma conta com esse nome!"));
    }
}
