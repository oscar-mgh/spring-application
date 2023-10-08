package com.omgcoding.dao;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
