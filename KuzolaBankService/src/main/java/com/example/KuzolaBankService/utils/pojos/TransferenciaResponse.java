package com.example.KuzolaBankService.utils.pojos;

public class TransferenciaResponse {
    private String descricao;
    private Boolean status;

    public TransferenciaResponse() {
    }

    public TransferenciaResponse(String descricao, Boolean status) {
        this.descricao = descricao;
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
