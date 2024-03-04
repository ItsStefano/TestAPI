package com.stefano.parktestapi;

import com.stefano.parktestapi.web.dto.UsuarioCreateDto;
import com.stefano.parktestapi.web.dto.UsuarioResponseDto;
import com.stefano.parktestapi.web.dto.UsuarioSenhaDto;
import com.stefano.parktestapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUsuario_UsernameEPasswordValidos_RetornarUsuarioCriadoStatus201() {
        UsuarioResponseDto responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("ozzy@test.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getUsername()).isEqualTo("ozzy@test.com");
        assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public void createUsuario_UsernameInvalido_RetornarErrorMassageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("romeo@", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("romeo@test", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("@test", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_PasswordInvalido_RetornarErrorMassageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("romeo@test.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("romeo@test.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("romeo@test.com", "1234567890"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_UsernameRepetido_RetornarErrorMassageStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("romeo@test.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void buscarUsuario_IdExistente_RetornarUsuarioStatus200() {
        UsuarioResponseDto responseBody = testClient
                .get()
                .uri("api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isEqualTo(100);
        assertThat(responseBody.getUsername()).isEqualTo("romeo@test.com");
        assertThat(responseBody.getRole()).isEqualTo("ADMIN");

        responseBody = testClient
                .get()
                .uri("api/v1/usuarios/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isEqualTo(101);
        assertThat(responseBody.getUsername()).isEqualTo("peto@test.com");
        assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        responseBody = testClient
                .get()
                .uri("api/v1/usuarios/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "peto@test.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isEqualTo(101);
        assertThat(responseBody.getUsername()).isEqualTo("peto@test.com");
        assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public void buscarUsuario_IdInexistente_RetornarUsuarioStatus404() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/v1/usuarios/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void buscarUsuario_UsuarioClienteBuscaOutroUsuarioCliente_RetornarErrorMassageStatus403() {
        ErrorMessage responseBody = testClient
                .get()
                .uri("api/v1/usuarios/102")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "peto@test.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void editarSenha_UsernameEPasswordValidos_RetornarUsuarioCriadoStatus204() {
        testClient
                .patch()
                .uri("api/v1/usuarios/101")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "peto@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isNoContent();

        testClient
                .patch()
                .uri("api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void editarSenha_UsuariosDiferentes_RetornarUsuarioStatus403() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("api/v1/usuarios/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "654321", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);

        responseBody = testClient
                .patch()
                .uri("api/v1/usuarios/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "riri@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "654321", "654321"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void editarSenha_CamposInvalidos_RetornarUsuarioStatus422() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .patch()
                .uri("api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "12345678", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .patch()
                .uri("api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("12345", "12345", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void editarSenha_SenhaInvalida_RetornarUsuarioStatus400() {
        ErrorMessage responseBody = testClient
                .patch()
                .uri("api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("111111", "123456", "123456"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .patch()
                .uri("api/v1/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123455"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void buscarTodosUsuarios_Existentes_RetornarStatus200() {
        List<UsuarioResponseDto> responseBody = testClient
                .get()
                .uri("api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "romeo@test.com", "123456"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.size()).isEqualTo(3);

        assertThat(responseBody.get(0).getId()).isEqualTo(100);
        assertThat(responseBody.get(0).getUsername()).isEqualTo("romeo@test.com");
        assertThat(responseBody.get(0).getRole()).isEqualTo("ADMIN");

        assertThat(responseBody.get(1).getId()).isEqualTo(101);
        assertThat(responseBody.get(1).getUsername()).isEqualTo("peto@test.com");
        assertThat(responseBody.get(1).getRole()).isEqualTo("CLIENTE");

        assertThat(responseBody.get(2).getId()).isEqualTo(102);
        assertThat(responseBody.get(2).getUsername()).isEqualTo("riri@test.com");
        assertThat(responseBody.get(2).getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public void listarUsuarios_ComUsuarioSemPermissao_RetornarErrorMessageComStatus403() {
        List<ErrorMessage> responseBody = testClient
                .get()
                .uri("api/v1/usuarios")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "peto@test.com", "123456"))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isForbidden()
                .expectBodyList(ErrorMessage.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        for (ErrorMessage errorMessage : responseBody) {
            assertThat(errorMessage.getStatus()).isEqualTo(403);
        }
    }
}