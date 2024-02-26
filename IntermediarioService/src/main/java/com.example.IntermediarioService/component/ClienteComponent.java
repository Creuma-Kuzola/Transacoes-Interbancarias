package com.example.IntermediarioService.component;

import java.util.Map;

public class ClienteComponent {

    Map<String, String> clienteComponent;
    public ClienteComponent(Map<String, String> clienteComponent) {
        this.clienteComponent = clienteComponent;
    }

    public Map<String, String> getClienteComponent() {
        return clienteComponent;
    }

    public void setClienteComponent(Map<String, String> clienteComponent) {
        this.clienteComponent = clienteComponent;
    }
}
