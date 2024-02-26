package ucan.edu.config.component;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
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
