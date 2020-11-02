
package com.aitemconnect.driverapp.pojo.order;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderPojo implements Serializable {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("destination")
    @Expose
    private Destination destination;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("modifiedAt")
    @Expose
    private String modifiedAt;
    @SerializedName("modifiedBy")
    @Expose
    private String modifiedBy;
    @SerializedName("orderExternalReferenceId")
    @Expose
    private String orderExternalReferenceId;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("origin")
    @Expose
    private Origin origin;

    @SerializedName("contact")
    @Expose
    private Contact contact;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOrderExternalReferenceId() {
        return orderExternalReferenceId;
    }

    public void setOrderExternalReferenceId(String orderExternalReferenceId) {
        this.orderExternalReferenceId = orderExternalReferenceId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

}
