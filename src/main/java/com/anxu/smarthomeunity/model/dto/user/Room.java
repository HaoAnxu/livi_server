package com.anxu.smarthomeunity.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private int roomId;//用户房间id
    private String roomName;//房间名称
}
