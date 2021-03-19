package com.luan.javaApi.enumx;

import org.springframework.http.HttpStatus;

public enum MessageDefault {

    INVALID_SESSION("Sessão inválida",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("Não autorizado",HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("Usuario não encontrado",HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("Token invalido",HttpStatus.BAD_REQUEST),
    INVALID_USER_OR_PASSWORD("Usuário e/ou senha inválidos", null),
    MISSING_CORE_ATTRIBUTES("Dados faltantes para a criação", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS("Email ja existente",HttpStatus.CONFLICT);

    final String mensagem;
    HttpStatus status;

    MessageDefault(String mensagem, HttpStatus status) {
        this.mensagem = mensagem;
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
