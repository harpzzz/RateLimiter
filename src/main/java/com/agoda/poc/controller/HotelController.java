package com.agoda.poc.controller;

import com.agoda.poc.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.agoda.poc.service.HotelService;

import java.util.List;

import static com.agoda.poc.contstant.Constant.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class HotelController {


    @Autowired
    HotelService hotelServices;

    @GetMapping("/city/{cityName}")
    public List<Hotel> getHotelbyCity(@PathVariable String cityName, @RequestParam(defaultValue = "0") int  sortBy){
        return hotelServices.getbyCity(cityName,sortBy);
    }

    @GetMapping("/room/{roomType}")
    public List<Hotel> getHotelByRoomType(@PathVariable String roomType,@RequestParam(defaultValue = "0") int  sortBy){
        return hotelServices.getbyroomType(roomType,sortBy);
    }


   /* @GetMapping("/add")
    public boolean getHotelByRoomType(@RequestParam("name") String name, @RequestParam("roomType") String roomType, @RequestParam("price") String price,@RequestParam("cityName") String cityName){
        return hotelServices.addHotel(name,roomType,price,cityName);
    }

    */
}