package com.example.IntermediarioService.utils.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransferenciaPOJOEmisResposta {

    @JsonProperty("transferenciaPOJOEmisList")
    private List<TransferenciaPOJOEmis> lista;

    public List<TransferenciaPOJOEmis> getLista() {
        return lista;
    }

    public void setLista(List<TransferenciaPOJOEmis> lista) {
        this.lista = lista;
    }
    // Getters

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"transferenciaPOJOEmisList\": \"").append(lista).append("\"");
        sb.append("}");
        return sb.toString();
    }
    
}
