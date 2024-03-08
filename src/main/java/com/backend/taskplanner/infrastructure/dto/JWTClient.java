package com.backend.taskplanner.infrastructure.dto;

public record JWTClient(String token, String name, String email) {}