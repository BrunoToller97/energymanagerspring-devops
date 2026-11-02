
package br.com.fiap.energymanagerspring.bdd;

import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class AutenticacaoSteps {

    @LocalServerPort
    private int port;

    private String email;
    private String senha;
    private Map<String, Object> corpoCadastro;
    private Response resposta;

    @Before
    public void configurar() {
        RestAssured.reset();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        email = "bdd-" + UUID.randomUUID() + "@example.com";
        senha = "SenhaSegura123";
        corpoCadastro = null;
        resposta = null;
    }

    @Dado("que informo dados válidos para cadastro de usuário")
    public void informarDadosValidos() {
        corpoCadastro = Map.of(
                "nome", "Usuario BDD",
                "email", email,
                "senha", senha,
                "role", "USER"
        );
    }

    @Dado("que informo dados inválidos para cadastro de usuário")
    public void informarDadosInvalidos() {
        corpoCadastro = Map.of(
                "nome", "",
                "email", "email-invalido",
                "senha", "123",
                "role", "USER"
        );
    }

    @Quando("envio a requisição de cadastro")
    public void enviarCadastro() {
        resposta = given()
                .contentType(ContentType.JSON)
                .body(corpoCadastro)
                .when()
                .post("/auth/register");
    }

    @Dado("que existe um usuário registrado")
    public void registrarUsuarioParaLogin() {
        Map<String, Object> usuario = Map.of(
                "nome", "Usuario BDD",
                "email", email,
                "senha", senha,
                "role", "USER"
        );

        Response cadastro = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/auth/register");

        assertEquals(
                200,
                cadastro.statusCode(),
                "Falha ao preparar o usuário para o teste de login: "
                        + cadastro.asString()
        );
    }

    @Dado("que informo um email inválido para login")
    public void informarEmailInvalido() {
        email = "email-invalido";
    }

    @Quando("envio a requisição de login")
    public void enviarLogin() {
        resposta = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email", email,
                        "senha", senha
                ))
                .when()
                .post("/auth/login");
    }

    @Entao("a API deve responder com status {int}")
    public void verificarStatus(int statusEsperado) {
        assertNotNull(resposta, "Nenhuma resposta foi recebida");

        assertEquals(
                statusEsperado,
                resposta.statusCode(),
                "Corpo da resposta: " + resposta.asString()
        );
    }

    @Entao("a resposta deve conter o email cadastrado")
    public void verificarEmailCadastrado() {
        assertEquals(
                email,
                resposta.jsonPath().getString("email")
        );

        assertTrue(
                resposta.jsonPath().getLong("usuarioId") > 0,
                "O ID do usuário deve ser positivo"
        );
    }

    @Entao("a resposta deve indicar erros no cadastro")
    public void verificarErrosCadastro() {
        Map<String, Object> erros = resposta.jsonPath().getMap("$");

        assertNotNull(erros);
        assertTrue(erros.containsKey("nome"));
        assertTrue(erros.containsKey("email"));
        assertTrue(erros.containsKey("senha"));
    }

    @Entao("a resposta deve conter um token JWT")
    public void verificarTokenJwt() {
        String token = resposta.jsonPath().getString("token");

        assertNotNull(token, "A resposta não contém o token");
        assertFalse(token.isBlank(), "O token não pode estar vazio");
    }

    @Entao("a resposta deve indicar o email inválido")
    public void verificarEmailInvalido() {
        Map<String, Object> erros = resposta.jsonPath().getMap("$");

        assertNotNull(erros);
        assertTrue(
                erros.containsKey("email"),
                "A resposta deve indicar o erro no campo email"
        );
    }
}
