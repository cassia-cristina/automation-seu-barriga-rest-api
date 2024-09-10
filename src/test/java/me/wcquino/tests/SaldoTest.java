package me.wcquino.tests;

import me.wcquino.core.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SaldoTest extends BaseTest {

    @Test
    public void naoDevePermitirAcessarSaldoSemToken() {
        given()
        .when()
            .get("/saldo")
        .then()
            .statusCode(401)
            .body(is("Unauthorized"));
    }

    @Test
    public void deveCalcularSaldoDasContas() {
        given()
            .header("Authorization", "JWT " + getTokenLogin())
        .when()
            .get("/saldo")
        .then()
            .statusCode(200)
            .body("find{it.conta.contains('saldo')}.saldo", is("534.00"));
    }
}
