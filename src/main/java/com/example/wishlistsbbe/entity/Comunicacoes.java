package com.example.wishlistsbbe.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comunicacoes {

    private Boolean email;
    private Boolean sms;
    private Boolean whatsapp;
    private Boolean telegram;
}
