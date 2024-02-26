package com.example.IntermediarioService.dto;

import java.math.BigInteger;

public record JwtDto(
    String accessToken,
        String iban,
        String numeroDaConta
        ) {
}