package com.example.wishlistsbbe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WishlistDTO {

    private String clientDocument;
    private List<String> produtosList;
}
