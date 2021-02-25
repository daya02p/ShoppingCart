package com.hackerrank.eshopping.product.dashboard.pojo;

public class ProductDetailsRevised {

    private Double retail_price;
    private Double discounted_price;
    private boolean availability;

    public ProductDetailsRevised() { }

    public ProductDetailsRevised(Double retail_price, Double discounted_price, boolean availability) {
        this.retail_price = retail_price;
        this.discounted_price = discounted_price;
        this.availability = availability;
    }

    public Double getRetail_price() { return retail_price; }

    public void setRetail_price(Double retail_price) { this.retail_price = retail_price; }

    public Double getDiscounted_price() { return discounted_price; }

    public void setDiscounted_price(Double discounted_price) { this.discounted_price = discounted_price; }

    public boolean isAvailability() { return availability; }

    public void setAvailability(boolean availability) { this.availability = availability; }
}
