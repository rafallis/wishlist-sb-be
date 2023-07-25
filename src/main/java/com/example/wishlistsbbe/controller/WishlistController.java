package com.example.wishlistsbbe.controller;

import com.example.wishlistsbbe.dto.ResponseDTO;
import com.example.wishlistsbbe.dto.WishlistDTO;
import com.example.wishlistsbbe.service.impl.WishlistServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "CRUD de Wishlist E-Commerce", description = "CRUD de Wishlist E-Commerce")
public class WishlistController {

    private final WishlistServiceImpl wishlistService;

    public WishlistController(WishlistServiceImpl wishlistService) {
        this.wishlistService = wishlistService;
    }

    @Operation(summary = "Busca de Wishlist pelo documento do cliente")
    @GetMapping("{documento}")
    public ResponseEntity<ResponseDTO> findWishlistByClientDocument(
            @PathVariable String documento,
            @RequestParam(value = "codbarra", required = false, defaultValue = "") String codbarra) {
        try {
            ResponseDTO response;
            if (codbarra.isBlank()) {
                response = this.wishlistService.findWishlistByClientDocumento(documento.trim());
            } else {
                response = this.wishlistService.existsCodbarraOnClientWishList(documento.trim(), codbarra.trim());
            }
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getMessage().getCode()));
        } catch (Exception e) {
            log.error("=> erro ao processar requisicao");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Criação ou atualiação da Wishlist do Cliente recebendo WishlistDTO como corpo da requisição")
    @PostMapping
    public ResponseEntity<ResponseDTO> createOrUpdateClientWishlist(@RequestBody WishlistDTO wishlistDTO) {
        try {
            ResponseDTO response = this.wishlistService.createOrUpdateClientWishlist(wishlistDTO);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getMessage().getCode()));
        } catch (Exception e) {
            log.error("=> erro ao adicionar item(s) a Wishlist do cliente".concat(wishlistDTO.getClientDocument()));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Remoção de item da Wishlist do Cliente")
    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteItemFromWishlist(
            @PathParam("documento") String documento,
            @PathParam("codbarra") String codbarra) {
        try {
            ResponseDTO response = this.wishlistService.deleteItemFromWishlist(documento.trim(), codbarra.trim());
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getMessage().getCode()));
        } catch (Exception e) {
            log.error("=> erro ao remover iten(s) da Wishlist do cliente".concat(documento.trim()));
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
