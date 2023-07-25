package com.example.wishlistsbbe.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseDTO {

    private Message message;
    private Object body;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Message {
        private int code;
        private String message;
    }
}
