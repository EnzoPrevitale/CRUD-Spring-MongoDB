package com.enzoprevitale.mongo.dtos;

import java.time.LocalDate;
import java.util.Map;

public record ProductDto(String name, float price, String description, LocalDate releaseDate, Map<String, ?> dataFields) {
}
