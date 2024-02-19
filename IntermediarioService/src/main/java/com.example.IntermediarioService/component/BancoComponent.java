package com.example.IntermediarioService.component;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BancoComponent {
    Map<String, Integer> bancoComponent;
    public BancoComponent() {
        bancoComponent = new HashMap<>();
    }
    public Map<String, Integer> geBancoComponent() {
        return bancoComponent;
    }
    public void setBancoComponent(Map<String, Integer> bancoComponent) {
        this.bancoComponent = bancoComponent;
    }
}
