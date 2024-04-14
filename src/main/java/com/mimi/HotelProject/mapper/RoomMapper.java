package com.mimi.HotelProject.mapper;

import com.mimi.HotelProject.dtos.RoomDTO;
import com.mimi.HotelProject.entity.Room;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class RoomMapper {
    public Room toRoom(RoomDTO roomDTO) {
        Room room = new Room();
        BeanUtils.copyProperties(roomDTO, room);
        return room;
    }
    public RoomDTO toRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        BeanUtils.copyProperties(room, roomDTO);
        return roomDTO;
    }
}
