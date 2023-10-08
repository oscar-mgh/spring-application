package com.omgcoding.dao;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
