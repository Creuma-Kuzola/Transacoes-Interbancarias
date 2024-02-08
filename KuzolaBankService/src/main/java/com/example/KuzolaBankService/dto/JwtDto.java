package com.example.KuzolaBankService.dto;

import java.math.BigInteger;

public record JwtDto(
    String accessToken,
        String iban,
        BigInteger numeroDaConta
        ) {
}