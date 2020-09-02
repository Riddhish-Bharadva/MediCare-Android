package com.rab.medicare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsAPI
{
    @JsonProperty("ProductID")
    private String[] ProductID;

    public String[] getProductID()
    {
        return ProductID;
    }
    public void setProductID(String[] productID)
    {
        ProductID = productID;
    }
}
@JsonIgnoreProperties(ignoreUnknown = true)
class ProductData
{
    @JsonProperty("ProductID")
    private String ProductID;
    @JsonProperty("ProductName")
    private String ProductName;
    @JsonProperty("Image")
    private String Image;
    @JsonProperty("Description")
    private String Description;
    @JsonProperty("Dosage")
    private String Dosage;
    @JsonProperty("Category")
    private String Category;
    @JsonProperty("Ingredients")
    private String Ingredients;
    @JsonProperty("Price")
    private String Price;
    @JsonProperty("ProductLife")
    private String ProductLife;
    @JsonProperty("Quantity")
    private String Quantity;
    @JsonProperty("StockedIn")
    private String[] StockedIn;

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getProductLife() {
        return ProductLife;
    }

    public void setProductLife(String productLife) {
        ProductLife = productLife;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String[] getStockedIn() {
        return StockedIn;
    }

    public void setStockedIn(String[] stockedIn) {
        StockedIn = stockedIn;
    }
}