package com.luan.javaApi.domain;

import com.luan.javaApi.enumx.MessageDefault;

public class Message {

    private String mensagem;

    public Message(String mensagem) {
        this.mensagem = mensagem;
    }

    public Message(MessageDefault mensagem) {
        this.mensagem = mensagem.getMensagem();
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
