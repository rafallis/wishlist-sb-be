package com.example.wishlistsbbe.service;

import com.example.wishlistsbbe.dto.ResponseDTO;
import com.example.wishlistsbbe.dto.WishlistDTO;

public interface WishlistService {

    /**
     * Busca Wishlist do Cliente recebendo o documento (CPF).
     * <p>
     * Caso o cliente possua Wishlist, retornará o objeto Wishlist.
     * Caso o cliente não possua Wishlist, será retornado um corpo vazio na requisicão.
     *
     * @param documento documento do cliente (CPF)
     * @return          resposta padronizada com ResponseDTO
     */
    ResponseDTO findWishlistByClienteDocumento(String documento);

    /**
     * Cria ou atualiza a Wishlist do cliente através do WishlistDTO passado.
     * <p>
     * Caso o cliente nao possua nenhuma Wishlist cadastrada, a api ira criar uma nova.
     * Caso o cliente possua Wishlist, os itens da mesma serao atualizados.
     * A Wishlist nunca contera itens repetidos.
     * O Cliente não poderá adicionar mais do que 20 itens à sua Wishlist.
     *
     * @param wishlistDTO DTO que recebe as informacoes para criacao ou atualizacão de Wishlists.
     * @return resposta padronizada com ResponseDTO
     */
    ResponseDTO createOrUpdateClientWishlist(WishlistDTO wishlistDTO);

    /**
     * Apaga item da Wishlist do Cliente recebendo o documento do Cliente para encontrar a Wishlist e o codbarra do Produto.
     *
     * @param documento documento do Cliente (CPF).
     * @param codbarra  código de barras do produto.
     * @return resposta padronizada com ResponseDTO.
     */
    ResponseDTO deleteItemFromWishlist(String documento, String codbarra);

    /**
     * Verifica se um item está presente na Wishlist do Cliente recebendo o documento (CPF) e o codbarra do Produto.
     *
     * @param documento documento do Cliente (CPF).
     * @param codbarra  código de barras do produto.
     * @return resposta padronizada com ResponseDTO
     */
    ResponseDTO existsCodbarraOnClientWishList(String documento, String codbarra);
}
