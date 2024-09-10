package me.wcquino.tests;

import me.wcquino.core.BaseTest;
import me.wcquino.pojos.Movimentacoes;
import me.wcquino.utils.DateTimeUtil;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MovimentacoesTest extends BaseTest {

    private static Movimentacoes setMovimentacoes() {
        Movimentacoes movimentacoes = Movimentacoes.builder().build();
        movimentacoes.setType("REC");
        movimentacoes.setDescription("Movimentação Teste");
        movimentacoes.setInvolved("Interessado");
        movimentacoes.setValue(100.99f);
        movimentacoes.setStatus(true);
        return movimentacoes;
    }

    @Test
    public void deveIncluirUmaMovimentacaoComSucesso() {
        Integer idAccount =
        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .queryParam("nome", "Conta para movimentacoes")
        .when()
            .get("/contas")
        .then()
            .statusCode(200)
            .extract().path("id[0]");

        Movimentacoes movimentacoes = setMovimentacoes();
        movimentacoes.setAccountId(idAccount);

        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body(movimentacoes)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(201)
            .body("id", is(notNullValue()))
            .body("descricao", is(movimentacoes.getDescription()))
            .body("envolvido", is(movimentacoes.getInvolved()))
            .body("valor", is(movimentacoes.getValue().toString()))
            .body("tipo", is(movimentacoes.getType()))
            .body("status", is(movimentacoes.getStatus()))
            .body("conta_id", is(movimentacoes.getAccountId()));
    }

    @Test
    public void deveValidarCamposObrigatoriosParaMovimentacao() {
        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body("{}")
        .when()
            .post("/transacoes")
        .then()
            .statusCode(400)
            .body("$", hasSize(8))
            .body("msg", hasItems(
                    "Data da Movimentação é obrigatório",
                    "Data do pagamento é obrigatório",
                    "Descrição é obrigatório",
                    "Interessado é obrigatório",
                    "Valor é obrigatório",
                    "Valor deve ser um número",
                    "Conta é obrigatório",
                    "Situação é obrigatório"));
    }

    @Test
    public void naoDeveCadastrarMovimentacaoFutura() {
        Movimentacoes movimentacoes = setMovimentacoes();
        movimentacoes.setTransactionDate(DateTimeUtil.getFutureDate());

        given()
            .header("Authorization", "JWT " + getTokenLogin())
            .body(movimentacoes)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(400)
            .body("msg", hasItems("Data da Movimentação deve ser menor ou igual à data atual"));
    }

    @Test
    public void deveRemoverMovimentacaoComSucesso() {
        Integer id =
        given()
            .header("Authorization", "JWT " + getTokenLogin())
        .when()
            .get("/transacoes")
        .then()
            .statusCode(200)
            .extract().path("find{it.descricao.contains('exclusao')}.id");

        given()
            .header("Authorization", "JWT " + getTokenLogin())
        .when()
            .delete("/transacoes/" + id)
        .then()
            .statusCode(204);
    }
}
