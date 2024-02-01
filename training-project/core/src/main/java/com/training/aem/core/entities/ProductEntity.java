package com.training.aem.core.entities;

import lombok.Data;

@Data
public class ProductEntity {
    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;

    // Getter and Setter methods

    @Data
    public static class Rating {
        private double rate;
        private int count;

        // Getter and Setter methods
    }
}
