package com.digital.DigitaBooking.models;

import lombok.*;

public class UserDTO {

    @Getter
    @Setter
    private Long idUser;
    private String userName;
    private String lastName;
    private String email;
    private String password;
}
