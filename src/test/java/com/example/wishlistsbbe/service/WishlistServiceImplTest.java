package com.example.wishlistsbbe.service;

import com.example.wishlistsbbe.dto.ResponseDTO;
import com.example.wishlistsbbe.dto.WishlistDTO;
import com.example.wishlistsbbe.entity.Produtos;
import com.example.wishlistsbbe.entity.Wishlist;
import com.example.wishlistsbbe.repository.ClienteRepository;
import com.example.wishlistsbbe.repository.ProdutosRepository;
import com.example.wishlistsbbe.repository.WishlistRepository;
import com.example.wishlistsbbe.service.impl.WishlistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceImplTest {

    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ProdutosRepository produtosRepository;
    @InjectMocks
    private WishlistServiceImpl wishlistService;

    private WishlistDTO wishlistDTO;
    private Wishlist wishlist;
    private Produtos produto;

    @BeforeEach
    void setUp() {
        produto = new Produtos();
        produto.setCodbarra("prod1");

        wishlistDTO = new WishlistDTO();
        wishlistDTO.setClientDocument("client1");
        wishlistDTO.setProdutosList(Collections.singletonList("prod1"));

        wishlist = new Wishlist();
        wishlist.setDocumento("client1");
        wishlist.setProdutosList(Collections.singletonList(produto));
        wishlist.setDataCriacao(LocalDateTime.now());
        wishlist.setDataAtualizacao(LocalDateTime.now());
    }

    @Test
    void shouldFindWishlistByClientDocumento() {
        when(wishlistRepository.findByDocumento(anyString())).thenReturn(wishlist);
        ResponseDTO responseDTO = wishlistService.findWishlistByClienteDocumento("client1");
        assertEquals(wishlist, responseDTO.getBody());
    }

    @Test
    void shouldCreateOrUpdateClientWishlist() {
        when(clienteRepository.existsByCpf(anyString())).thenReturn(true);
        when(wishlistRepository.findByDocumento(anyString())).thenReturn(wishlist);
        when(produtosRepository.findByCodbarra(anyString())).thenReturn(produto);
        when(wishlistRepository.save(any())).thenReturn(wishlist);

        ResponseDTO responseDTO = wishlistService.createOrUpdateClientWishlist(wishlistDTO);
        assertEquals(wishlist, responseDTO.getBody());
    }

    @Test
    void shouldDeleteItemFromWishlist() {
        when(clienteRepository.existsByCpf(anyString())).thenReturn(true);
        when(wishlistRepository.findByDocumento(anyString())).thenReturn(wishlist);
        when(wishlistRepository.save(any())).thenReturn(wishlist);

        ResponseDTO responseDTO = wishlistService.deleteItemFromWishlist("client1", "prod1");
        assertEquals(wishlist, responseDTO.getBody());
    }
}