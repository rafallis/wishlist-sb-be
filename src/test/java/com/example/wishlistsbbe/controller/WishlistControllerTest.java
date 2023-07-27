package com.example.wishlistsbbe.controller;

import com.example.wishlistsbbe.dto.ResponseDTO;
import com.example.wishlistsbbe.dto.WishlistDTO;
import com.example.wishlistsbbe.service.impl.WishlistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

    @Mock
    private WishlistServiceImpl wishlistService;
    @InjectMocks
    private WishlistController wishlistController;
    private WishlistDTO wishlistDTO;

    @BeforeEach
    public void setup() {
        wishlistDTO = new WishlistDTO();
        wishlistDTO.setClientDocument("12345678900");
        wishlistDTO.setProdutosList(Arrays.asList("7894900011517", "7894900015195", "7894900015270"));
    }

    @Test
    public void testFindWishlistByClientDocument() {
        ResponseDTO responseDTO = new ResponseDTO(new ResponseDTO.Message(HttpStatus.OK.value(), "Wishlist Found"), wishlistDTO);
        when(wishlistService.findWishlistByClienteDocumento(anyString())).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = wishlistController.findWishlistByClientDocument("12345678900", "");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateOrUpdateClientWishlist() {
        ResponseDTO responseDTO = new ResponseDTO(new ResponseDTO.Message(HttpStatus.CREATED.value(), "Wishlist Created"), wishlistDTO);
        when(wishlistService.createOrUpdateClientWishlist(wishlistDTO)).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = wishlistController.createOrUpdateClientWishlist(wishlistDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}