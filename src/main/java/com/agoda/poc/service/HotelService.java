package com.agoda.poc.service;

import com.agoda.poc.model.Hotel;
import com.agoda.poc.model.RoomType;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class HotelService {

    Logger logger = LoggerFactory.getLogger(HotelService.class);


    ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
    {
        hotelList.add(new Hotel(generateHotelId(),"Bangkok", RoomType.Deluxe,1000));
        hotelList.add(new Hotel(generateHotelId(),"Amsterdam", RoomType.Superior,2000));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn", RoomType.Sweet_Suite,1300));
        hotelList.add(new Hotel(generateHotelId(),"Amsterdam",RoomType.Deluxe,2200));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Sweet_Suite,1200));
        hotelList.add(new Hotel(generateHotelId(),"Bangkok", RoomType.Superior,2000));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Deluxe,1600));
        hotelList.add(new Hotel(generateHotelId(),"Bangkok", RoomType.Superior,2400));
        hotelList.add(new Hotel(generateHotelId(),"Amsterdam",RoomType.Sweet_Suite,30000));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Superior,1100));
        hotelList.add(new Hotel(generateHotelId(),"Bangkok", RoomType.Deluxe,60));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Deluxe,1800));
        hotelList.add(new Hotel(generateHotelId(),"Amsterdam",RoomType.Superior,1000));
        hotelList.add(new Hotel(generateHotelId(),"Bangkok",RoomType.Sweet_Suite,25000));
        hotelList.add(new Hotel(generateHotelId(),"Bangkok", RoomType.Deluxe,900));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Superior,800));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Deluxe,2800));
        hotelList.add(new Hotel(generateHotelId(),"Bangkok",RoomType.Sweet_Suite,5300));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Superior,1000));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Superior,4444));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Deluxe,7000));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn", RoomType.Sweet_Suite,14000));
        hotelList.add(new Hotel(generateHotelId(),"Amsterdam",RoomType.Deluxe,5000));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Superior,1400));
        hotelList.add(new Hotel(generateHotelId(),"Ashburn",RoomType.Deluxe,1900));
        hotelList.add(new Hotel(generateHotelId(),"Amsterdam",RoomType.Deluxe,2300));




    }


    public List<Hotel> getbyCity(String cityName, int sortBy) {


        List<Hotel> hotelListbycity =  hotelList.stream().filter(hotel -> hotel.getCity().toLowerCase().equals(cityName.toLowerCase()))
                .collect(Collectors.toList());

        if(sortBy == 0){
            return hotelListbycity.stream().sorted((h1,h2) -> h1.getPrice().compareTo(h2.getPrice())).collect(Collectors.toList());
        }else if(sortBy == 1){
            return hotelListbycity.stream().sorted((h1,h2) -> h2.getPrice().compareTo(h1.getPrice())).collect(Collectors.toList());

        }

        return hotelListbycity;
    }

    public List<Hotel> getbyroomType(String roomType, int sortBy) {

        List<Hotel> hotelListbyroom = hotelList.stream().filter(hotel -> hotel.getRoomType().toLowerCase().equals(roomType.toLowerCase()))
                .collect(Collectors.toList());


            if(sortBy == 0){
                return hotelListbyroom.stream().sorted((h1,h2) -> h1.getPrice().compareTo(h2.getPrice())).collect(Collectors.toList());
            }else if(sortBy == 1){
                return hotelListbyroom.stream().sorted((h1,h2) -> h2.getPrice().compareTo(h1.getPrice())).collect(Collectors.toList());

            }

            return hotelListbyroom;
    }


/*    public boolean addHotel(String name, String roomType, String price, String cityName){

        long hotelId = generateHotelId();
        if(hotelId == Long.MIN_VALUE){
            return false;
        }
        Hotel hotel = new Hotel(hotelId,cityName,RoomType.Deluxe,new Integer(price));
        hotelList.add(hotel);
        return true;
    }
*/

    private synchronized long generateHotelId(){
        Random rand = new Random();
        long id = Long.MIN_VALUE;
        int count = 1;
        while(count < 11) {
            id = Math.abs(rand.nextLong());
            if(!hotelList.contains(id)){
                return id;
            }
            count++;
        }

        return id;
    }
}

