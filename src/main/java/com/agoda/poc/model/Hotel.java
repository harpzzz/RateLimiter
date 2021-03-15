package com.agoda.poc.model;

public class Hotel {

    private long hotelId;
    private String city;
    private RoomType roomType;
    private Integer price;


    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoomType() {
        return roomType.getroomTypeName();
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Hotel(long hotelId, String city, RoomType roomType, Integer price) {
        this.hotelId = hotelId;
        this.city = city;
        this.roomType = roomType;
        this.price = price;
    }
}