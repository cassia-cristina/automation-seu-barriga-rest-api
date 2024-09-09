package me.wcquino.tests;

import me.wcquino.core.BaseTest;
import me.wcquino.pojos.Contas;
import org.junit.jupiter.api.Test;

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
        Contas account = new Contas();
        account.setName("TestesNew");

        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body(account)
        .when()
            .post("/contas")
        .then()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("nome", is(account.getName()))
            .body("visivel", is(true));
    }

    @Test
    public void deveAlterarUmaContaComSucesso() {
        Contas account = new Contas();
        account.setName("Testes2");

        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body(account)
        .when()
            .put("/contas/2244037")
        .then()
            .statusCode(200)
            .body("id", is(notNullValue()))
            .body("nome", is(account.getName()))
            .body("visivel", is(true));;
    }

    @Test
    public void naoDeveInserirContaComMesmoNome() {
        Contas account = new Contas();
        account.setName("Testes");

        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body(account)
        .when()
            .post("/contas")
        .then()
            .statusCode(400)
            .body("error", is("Já existe uma conta com esse nome!"));
    }
}