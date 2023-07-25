package com.example.wishlistsbbe.service;

import com.example.wishlistsbbe.dto.ResponseDTO;
import com.example.wishlistsbbe.dto.WishlistDTO;

public interface WishlistService {

    /**
     *
     * @param documento
     * @return
     */
    ResponseDTO findWishlistByClientDocumento(String documento);

    /**
     *
     * @param wishlistDTO
     * @return
     */
    ResponseDTO createOrUpdateClientWishlist(WishlistDTO wishlistDTO);

    /**
     *
     * @param documento
     * @param codbarra
     * @return
     */
    ResponseDTO deleteItemFromWishlist(String documento, String codbarra);

    /**
     *
     * @param documento
     * @param codbarra
     * @return
     */
    ResponseDTO existsCodbarraOnClientWishList(String documento, String codbarra);
}
