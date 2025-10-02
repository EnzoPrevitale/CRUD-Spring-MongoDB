package com.enzoprevitale.mongo.dtos;

import java.time.LocalDate;

public record ProductDto(String name, float price, String description, LocalDate releaseDate) {
}
