package com.rab.medicare;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class API_Response
{
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("FirstName")
    private String FirstName;
    @JsonProperty("DOB")
    private String DOB;
    @JsonProperty("notification")
    private static String notification;
    @JsonProperty("LastName")
    private String LastName;
    @JsonProperty("Gender")
    private String Gender;
    @JsonProperty("Contact")
    private String Contact;
    @JsonProperty("Address")
    private String Address;
    @JsonProperty("EmailVerified")
    private String EmailVerified;

    public String getEmailVerified() {
        return EmailVerified;
    }

    public void setEmailVerified(String emailVerified) {
        EmailVerified = emailVerified;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ProductsAPI
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
class ProductDataAPI
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
    @JsonProperty("PrescriptionRequired")
    private String PrescriptionRequired;
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

    public String getPrescriptionRequired() {
        return PrescriptionRequired;
    }

    public void setPrescriptionRequired(String prescriptionRequired) {
        PrescriptionRequired = prescriptionRequired;
    }

    public String[] getStockedIn() {
        return StockedIn;
    }

    public void setStockedIn(String[] stockedIn) {
        StockedIn = stockedIn;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class OrdersAPI
{
    @JsonProperty("OrderID")
    private String[] OrderID;
    @JsonProperty("OrderType")
    private String[] OrderType;
    @JsonProperty("OrderStatus")
    private String[] OrderStatus;
    @JsonProperty("OrderTotal")
    private String[] OrderTotal;

    public String[] getOrderID()
    {
        return OrderID;
    }
    public void setOrderID(String[] OrderID)
    {
        this.OrderID = OrderID;
    }
    public String[] getOrderType()
    {
        return OrderType;
    }
    public void setOrderType(String[] OrderType)
    {
        this.OrderType = OrderType;
    }
    public String[] getOrderStatus()
    {
        return OrderStatus;
    }
    public void setOrderStatus(String[] OrderStatus)
    {
        this.OrderStatus = OrderStatus;
    }
    public String[] getOrderTotal()
    {
        return OrderTotal;
    }
    public void setOrderTotal(String[] OrderTotal)
    {
        this.OrderTotal = OrderTotal;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class OrdersDetailsAPI
{
    @JsonProperty("OrderID")
    private String OrderID;
    @JsonProperty("notification")
    private String notification;
    @JsonProperty("OrderType")
    private String OrderType;
    @JsonProperty("ProductID")
    private String[] ProductID;
    @JsonProperty("Quantity")
    private int[] Quantity;
    @JsonProperty("Price")
    private float[] Price;
    @JsonProperty("PharmacyID")
    private String[] PharmacyID;
    @JsonProperty("ProductStatus")
    private String[] ProductStatus;
    @JsonProperty("ServiceCharge")
    private float ServiceCharge;
    @JsonProperty("DeliveryCharge")
    private float DeliveryCharge;
    @JsonProperty("PrescriptionRequired")
    private int PrescriptionRequired;
    @JsonProperty("PrescriptionImage")
    private String PrescriptionImage;
    @JsonProperty("ReUploadPrescription")
    private int ReUploadPrescription;
    @JsonProperty("PaymentRequired")
    private int PaymentRequired;
    @JsonProperty("SubTotalPrice")
    private float SubTotalPrice;

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String[] getProductID() {
        return ProductID;
    }

    public void setProductID(String[] productID) {
        ProductID = productID;
    }

    public int[] getQuantity() {
        return Quantity;
    }

    public void setQuantity(int[] quantity) {
        Quantity = quantity;
    }

    public float[] getPrice() {
        return Price;
    }

    public void setPrice(float[] price) {
        Price = price;
    }

    public String[] getPharmacyID() {
        return PharmacyID;
    }

    public void setPharmacyID(String[] pharmacyID) {
        PharmacyID = pharmacyID;
    }

    public String[] getProductStatus() {
        return ProductStatus;
    }

    public void setProductStatus(String[] productStatus) {
        ProductStatus = productStatus;
    }

    public float getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(float serviceCharge) {
        ServiceCharge = serviceCharge;
    }

    public float getDeliveryCharge() {
        return DeliveryCharge;
    }

    public void setDeliveryCharge(float deliveryCharge) {
        DeliveryCharge = deliveryCharge;
    }

    public int getPrescriptionRequired() {
        return PrescriptionRequired;
    }

    public void setPrescriptionRequired(int prescriptionRequired) {
        PrescriptionRequired = prescriptionRequired;
    }

    public String getPrescriptionImage() {
        return PrescriptionImage;
    }

    public void setPrescriptionImage(String prescriptionImage) {
        PrescriptionImage = prescriptionImage;
    }

    public int getReUploadPrescription() {
        return ReUploadPrescription;
    }

    public void setReUploadPrescription(int reUploadPrescription) {
        ReUploadPrescription = reUploadPrescription;
    }

    public int getPaymentRequired() {
        return PaymentRequired;
    }

    public void setPaymentRequired(int paymentRequired) {
        PaymentRequired = paymentRequired;
    }

    public float getSubTotalPrice() {
        return SubTotalPrice;
    }

    public void setSubTotalPrice(float subTotalPrice) {
        SubTotalPrice = subTotalPrice;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PharmacyDetailsAPI
{
    @JsonProperty("PharmacyID")
    private String PharmacyID;
    @JsonProperty("PharmacyName")
    private String PharmacyName;
    @JsonProperty("OfficialEmailId")
    private String OfficialEmailId;
    @JsonProperty("OfficialContact")
    private String OfficialContact;
    @JsonProperty("PhysicalAddress")
    private String PhysicalAddress;
    @JsonProperty("Latitude")
    private float Latitude;
    @JsonProperty("Longitude")
    private float Longitude;

    public String getPharmacyID() {
        return PharmacyID;
    }

    public void setPharmacyID(String pharmacyID) {
        PharmacyID = pharmacyID;
    }

    public String getPharmacyName() {
        return PharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        PharmacyName = pharmacyName;
    }

    public String getOfficialEmailId() {
        return OfficialEmailId;
    }

    public void setOfficialEmailId(String officialEmailId) {
        OfficialEmailId = officialEmailId;
    }

    public String getOfficialContact() {
        return OfficialContact;
    }

    public void setOfficialContact(String officialContact) {
        OfficialContact = officialContact;
    }

    public String getPhysicalAddress() {
        return PhysicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        PhysicalAddress = physicalAddress;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }
}