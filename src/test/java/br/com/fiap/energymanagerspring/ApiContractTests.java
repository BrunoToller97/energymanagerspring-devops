
package br.com.fiap.energymanagerspring;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ApiContractTests {

    @LocalServerPort
    private int port;

    private String adminToken;

    private final String senha = "SenhaSegura123";

    @BeforeEach
    void configurar() {
        RestAssured.reset();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String email = novoEmail();

        Response cadastro = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "nome", "Administrador de Teste",
                        "email", email,
                        "senha", senha,
                        "role", "ADMIN"
                ))
                .post("/auth/register");

        assertEquals(200, cadastro.statusCode(), cadastro.asString());

        Response login = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email", email,
                        "senha", senha
                ))
                .post("/auth/login");

        validarSchema(login, 200, "token.schema.json");
        adminToken = login.jsonPath().getString("token");
    }

    private String novoEmail() {
        return "teste-" + UUID.randomUUID() + "@example.com";
    }

    private RequestSpecification autenticado() {
        return RestAssured.given()
                .header("Authorization", "Bearer " + adminToken);
    }

    private void validarSchema(
            Response resposta,
            int statusEsperado,
            String schema
    ) {
        assertEquals(
                statusEsperado,
                resposta.statusCode(),
                resposta.asString()
        );

        resposta.then().body(
                matchesJsonSchemaInClasspath("schemas/" + schema)
        );
    }

    private long idDaResposta(Response resposta, String campo) {
        return ((Number) resposta.jsonPath().get(campo)).longValue();
    }

    @Test
    void deveValidarCadastroELogin() {
        String email = novoEmail();

        Response cadastro = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "nome", "Usuario de Teste",
                        "email", email,
                        "senha", senha,
                        "role", "USER"
                ))
                .post("/auth/register");

        validarSchema(cadastro, 200, "usuario.schema.json");

        Response login = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "email", email,
                        "senha", senha
                ))
                .post("/auth/login");

        validarSchema(login, 200, "token.schema.json");
    }

    @Test
    void deveTestarEndpointsDeSetor() {
        String nome = "Setor-" + UUID.randomUUID().toString().substring(0, 8);

        Response criado = autenticado()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "nome", nome,
                        "metaReducaoPercentual", 10.0,
                        "responsavel", "Responsavel BDD"
                ))
                .post("/api/setores");

        validarSchema(criado, 201, "setor.schema.json");
        long id = idDaResposta(criado, "id");

        Response lista = autenticado().get("/api/setores");
        validarSchema(lista, 200, "page.schema.json");
        assertTrue(lista.jsonPath().getList("content").size() > 0);

        Response porId = autenticado().get("/api/setores/" + id);
        validarSchema(porId, 200, "setor.schema.json");

        Response porNome = autenticado()
                .queryParam("nome", nome)
                .get("/api/setores");

        validarSchema(porNome, 200, "setor.schema.json");

        String nomeAtualizado = nome + "-Atualizado";

        Response atualizado = autenticado()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "id", id,
                        "nome", nomeAtualizado,
                        "metaReducaoPercentual", 15.0,
                        "responsavel", "Responsavel Atualizado"
                ))
                .put("/api/setores");

        validarSchema(atualizado, 200, "setor.schema.json");

        Response excluido = autenticado()
                .delete("/api/setores/" + id);

        assertEquals(204, excluido.statusCode());
    }

    @Test
    void deveTestarEndpointsDeFuncionario() {
        String sufixo = UUID.randomUUID().toString().substring(0, 8);
        String nomeSetor = "SetorFuncionario-" + sufixo;

        Response setor = autenticado()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "nome", nomeSetor,
                        "metaReducaoPercentual", 12.0,
                        "responsavel", "Responsavel Funcionario"
                ))
                .post("/api/setores");

        validarSchema(setor, 201, "setor.schema.json");
        long setorId = idDaResposta(setor, "id");

        String nome = "Funcionario-" + sufixo;

        Response criado = autenticado()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "nome", nome,
                        "cargo", "Analista ESG",
                        "salario", 4500.0,
                        "ativo", "S",
                        "setorId", setorId
                ))
                .post("/api/funcionarios");

        validarSchema(criado, 201, "funcionario.schema.json");
        long id = idDaResposta(criado, "id");

        Response lista = autenticado().get("/api/funcionarios");
        validarSchema(lista, 200, "page.schema.json");
        assertTrue(lista.jsonPath().getList("content").size() > 0);

        Response porId = autenticado().get("/api/funcionarios/" + id);
        validarSchema(porId, 200, "funcionario.schema.json");

        Response porNome = autenticado()
                .queryParam("nome", nome)
                .get("/api/funcionarios");

        validarSchema(porNome, 200, "funcionario.schema.json");

        Response atualizado = autenticado()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "id", id,
                        "nome", nome + "-Atualizado",
                        "cargo", "Coordenador ESG",
                        "salario", 5000.0,
                        "ativo", "S",
                        "setor", Map.of(
                                "id", setorId,
                                "nome", nomeSetor,
                                "metaReducaoPercentual", 12.0,
                                "responsavel", "Responsavel Funcionario"
                        )
                ))
                .put("/api/funcionarios");

        validarSchema(atualizado, 200, "funcionario-model.schema.json");

        Response excluido = autenticado()
                .delete("/api/funcionarios/" + id);

        assertEquals(204, excluido.statusCode());
    }

    @Test
    void deveTestarEndpointsDeUsuario() {
        String email = novoEmail();
        String nome = "UsuarioAPI-" + UUID.randomUUID().toString().substring(0, 8);

        Response criado = autenticado()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "nome", nome,
                        "email", email,
                        "senha", senha,
                        "role", "USER"
                ))
                .post("/api/usuarios");

        validarSchema(criado, 201, "usuario.schema.json");
        long id = idDaResposta(criado, "usuarioId");

        Response lista = autenticado().get("/api/usuarios");
        validarSchema(lista, 200, "page.schema.json");
        assertTrue(lista.jsonPath().getList("content").size() > 0);

        Response porId = autenticado().get("/api/usuarios/" + id);
        validarSchema(porId, 200, "usuario.schema.json");

        Response porNome = autenticado()
                .queryParam("nome", nome)
                .get("/api/usuarios");

        validarSchema(porNome, 200, "usuario.schema.json");

        Response atualizado = autenticado()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "usuarioId", id,
                        "nome", nome + "-Atualizado",
                        "email", email,
                        "senha", "NovaSenha123",
                        "role", "USER"
                ))
                .put("/api/usuarios");

        validarSchema(atualizado, 200, "usuario.schema.json");

        Response excluido = autenticado()
                .delete("/api/usuarios/" + id);

        assertEquals(204, excluido.statusCode());
    }

    @Test
    void deveTestarEndpointsDeDemonstracao() {
        Response hello = autenticado().get("/api/hello");

        assertEquals(200, hello.statusCode());
        assertEquals("<h1>Hello World!</h1>", hello.asString());

        Response ola = autenticado().get("/api/ola");

        assertEquals(200, ola.statusCode());
        assertEquals("Olá mundo docker!", ola.asString());
    }
}
