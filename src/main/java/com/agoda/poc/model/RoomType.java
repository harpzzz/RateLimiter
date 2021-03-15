package com.agoda.poc.model;

public enum RoomType {

    Superior("Superior"),
    Deluxe("Deluxe"),
    Sweet_Suite("Sweet Suite");


    private String roomTypeName;

    RoomType(String roomName) {
        this.roomTypeName = roomName;
    }

    public String getroomTypeName() {
        return roomTypeName;
    }

    @Override
    public String toString()
    {
        return this.roomTypeName;
    }
}
