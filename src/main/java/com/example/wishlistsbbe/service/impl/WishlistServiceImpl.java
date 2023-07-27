package com.example.wishlistsbbe.service.impl;

import com.example.wishlistsbbe.dto.ResponseDTO;
import com.example.wishlistsbbe.dto.WishlistDTO;
import com.example.wishlistsbbe.entity.Produtos;
import com.example.wishlistsbbe.entity.Wishlist;
import com.example.wishlistsbbe.repository.ClienteRepository;
import com.example.wishlistsbbe.repository.ProdutosRepository;
import com.example.wishlistsbbe.repository.WishlistRepository;
import com.example.wishlistsbbe.service.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutosRepository produtosRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, ClienteRepository clienteRepository, ProdutosRepository produtosRepository) {
        this.wishlistRepository = wishlistRepository;
        this.clienteRepository = clienteRepository;
        this.produtosRepository = produtosRepository;
    }

    @Override
    public ResponseDTO findWishlistByClienteDocumento(String documento) {
        log.info("=> buscando Wishlist do cliente {}", documento);

        ResponseDTO response;
        try {
            Wishlist wishlist = this.wishlistRepository.findByDocumento(documento);

            if (Objects.nonNull(wishlist)) {
                response = ResponseDTO.builder()
                        .message(ResponseDTO.Message.builder()
                                .code(HttpStatus.OK.value())
                                .message("Consultando Wishlist Cliente Documento:".concat(documento))
                                .build())
                        .body(wishlist)
                        .build();
            } else {
                response = ResponseDTO.builder()
                        .message(ResponseDTO.Message.builder()
                                .code(HttpStatus.NOT_FOUND.value())
                                .message("Não foi encontrado Cliente Documento:".concat(documento))
                                .build())
                        .body(null)
                        .build();
            }

        } catch (Exception e) {
            log.error("=> erro ao consultar Cliente Documento:{}", documento);
            response = ResponseDTO.builder()
                    .message(ResponseDTO.Message.builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Erro ao consultar Cliente Documento:".concat(documento))
                            .build())
                    .body(e.getMessage())
                    .build();
        }
        return response;
    }

    @Override
    public ResponseDTO createOrUpdateClientWishlist(WishlistDTO wishlistDTO) {
        log.debug("=> recebendo Wishlist: {}", wishlistDTO);
        int returnCode = 0;
        String returnMessage = null;
        Object returnBody = null;
        Wishlist wishlist = null;
        ResponseDTO response;

        try {
            boolean clientExists = this.clienteRepository.existsByCpf(wishlistDTO.getClientDocument().trim());

            wishlist = this.wishlistRepository.findByDocumento(wishlistDTO.getClientDocument().trim());
            if (clientExists && Objects.nonNull(wishlist)) {
                if (wishlist.getProdutosList().size() < 20) {
                    log.info("=> Cliente Documento:{} possui Wishlist, atualizando.", wishlistDTO.getClientDocument().trim());

                    List<Produtos> finalList = new ArrayList<>(
                            Stream.of(wishlistDTO.getProdutosList(),
                                            wishlist.getProdutosList().stream().map(Produtos::getCodbarra).toList())
                                    .flatMap(List::stream)
                                    .collect(Collectors.toSet())
                                    .stream()
                                    .map(this::getProdutoByCodbarra)
                                    .toList());

                    wishlist.setProdutosList(finalList);
                    wishlist.setDataAtualizacao(LocalDateTime.now());
                    this.wishlistRepository.save(wishlist);

                    returnCode = HttpStatus.NO_CONTENT.value();
                    returnMessage = "Wishlist do Cliente ".concat(wishlistDTO.getClientDocument()).concat(" atualizada com sucesso");
                } else {
                    returnCode = HttpStatus.BAD_REQUEST.value();
                    returnMessage = "Wishlist do Cliente ".concat(wishlist.getDocumento().trim()).concat(" possui o limite de 20 itens");
                }
            } else if (clientExists) {
                log.info("=> Cliente Documento:{} não possui Wishlist, criando.", wishlistDTO.getClientDocument().trim());

                List<Produtos> produtosList = wishlistDTO.getProdutosList().stream()
                        .map(this::getProdutoByCodbarra)
                        .collect(Collectors.toList());

                LocalDateTime now = LocalDateTime.now();
                wishlist = Wishlist.builder()
                        .documento(wishlistDTO.getClientDocument().trim())
                        .produtosList(produtosList)
                        .dataCriacao(now)
                        .dataAtualizacao(now)
                        .build();
                this.wishlistRepository.save(wishlist);
                returnCode = HttpStatus.CREATED.value();
                returnMessage = "Wishlist do Cliente ".concat(wishlistDTO.getClientDocument().trim()).concat(" criada com sucesso");
            }
            returnBody = wishlist;
        } catch (DataAccessException dae) {
            log.debug(Arrays.toString(dae.getStackTrace()));
            log.error("=> erro ao salvar dados no banco de dados");
            returnCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            returnMessage = dae.getMessage();
            returnBody = Arrays.stream(dae.getStackTrace())
                    .filter(ste -> !ste.getClassName().startsWith("com.sun.proxy"))
                    .toList()
                    .toArray(new StackTraceElement[0]);
        } catch (Exception e) {
            log.debug(Arrays.toString(e.getStackTrace()));
            log.error("=> erro ao processar Wishlist do Cliente {}", wishlistDTO.getClientDocument().trim());
            returnCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            returnMessage = e.getMessage();
            returnBody = Arrays.stream(e.getStackTrace())
                    .filter(ste -> !ste.getClassName().startsWith("com.sun.proxy"))
                    .toList()
                    .toArray(new StackTraceElement[0]);
        } finally {
            response = ResponseDTO.builder()
                    .message(ResponseDTO.Message.builder()
                            .code(returnCode)
                            .message(returnMessage)
                            .build())
                    .body(returnBody)
                    .build();
        }
        return response;
    }

    @Override
    public ResponseDTO deleteItemFromWishlist(String documento, String codbarra) {
        log.debug("=> recebendo documento:{}, codbarra:{}", documento, codbarra);
        int returnCode = 0;
        String returnMessage = null;
        Object returnBody = null;
        Wishlist wishlist = null;
        ResponseDTO response;

        try {
            boolean clientExists = this.clienteRepository.existsByCpf(documento);
            wishlist = this.wishlistRepository.findByDocumento(documento);
            if (clientExists && Objects.nonNull(wishlist)) {

                if (wishlist.getProdutosList().isEmpty()) {
                    returnCode = HttpStatus.BAD_REQUEST.value();
                    returnMessage = "Cliente ".concat(documento).concat(" não possui itens na Wishlist");
                } else {
                    wishlist.setProdutosList(wishlist.getProdutosList().stream()
                            .filter(produto -> !produto.getCodbarra().equals(codbarra))
                            .toList()
                    );

                    wishlist.setDataAtualizacao(LocalDateTime.now());
                    this.wishlistRepository.save(wishlist);

                    returnCode = HttpStatus.NO_CONTENT.value();
                    returnMessage = "Wishlist do Cliente ".concat(documento).concat(" atualizada com sucesso");
                }
            } else if (clientExists) {
                log.info("=> Cliente Documento:{} não possui Wishlist.", documento);
                returnCode = HttpStatus.BAD_REQUEST.value();
                returnMessage = "Cliente ".concat(documento).concat(" não possui Wishlist");
            }
            returnBody = wishlist;
        } catch (Exception e) {
            log.debug(Arrays.toString(e.getStackTrace()));
            log.error("=> erro ao deletar item da Wishlist do Cliente {}", documento);
            returnCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            returnMessage = e.getMessage();
            returnBody = Arrays.stream(e.getStackTrace())
                    .filter(ste -> !ste.getClassName().startsWith("com.sun.proxy"))
                    .toList()
                    .toArray(new StackTraceElement[0]);
        } finally {
            response = ResponseDTO.builder()
                    .message(ResponseDTO.Message.builder()
                            .code(returnCode)
                            .message(returnMessage)
                            .build())
                    .body(returnBody)
                    .build();
        }
        return response;
    }

    @Override
    public ResponseDTO existsCodbarraOnClientWishList(String documento, String codbarra) {
        log.debug("=> recebendo documento:{}, codbarra:{}", documento, codbarra);
        int returnCode = 0;
        String returnMessage = null;
        Object returnBody = null;
        ResponseDTO response;

        try {
            Wishlist wishlist = this.wishlistRepository.findByDocumento(documento);
            if (Objects.nonNull(wishlist)) {
                boolean exists = wishlist.getProdutosList().stream().anyMatch(produto -> produto.getCodbarra().equals(codbarra));
                if (exists) {
                    returnCode = HttpStatus.OK.value();
                    returnMessage = "Cliente ".concat(documento).concat(" possui produto ").concat(codbarra).concat(" na Wishlist");
                } else {
                    returnCode = HttpStatus.NOT_FOUND.value();
                    returnMessage = "Cliente ".concat(documento).concat(" não possui produto ").concat(codbarra).concat(" na Wishlist");
                }
            } else {
                returnCode = HttpStatus.NOT_FOUND.value();
                returnMessage = "Cliente ".concat(documento).concat(" não possui Wishlist ").concat(codbarra).concat(" na Wishlist");
            }
        } catch (Exception e) {
            log.debug(Arrays.toString(e.getStackTrace()));
            log.error("=> erro ao verificar item da Wishlist do Cliente {}", documento);
            returnCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            returnMessage = e.getMessage();
            returnBody = Arrays.stream(e.getStackTrace())
                    .filter(ste -> !ste.getClassName().startsWith("com.sun.proxy"))
                    .toList()
                    .toArray(new StackTraceElement[0]);
        } finally {
            response = ResponseDTO.builder()
                    .message(ResponseDTO.Message.builder()
                            .code(returnCode)
                            .message(returnMessage)
                            .build())
                    .body(returnBody)
                    .build();
        }

        return response;
    }

    private Produtos getProdutoByCodbarra(String codbarra) {
        try {
            return this.produtosRepository.findByCodbarra(codbarra);
        } catch (Exception e) {
            log.error("=> erro ao buscar produto CODBARRA:{}", codbarra);
            throw new RuntimeException("Erro ao buscar produto CODBARRA:".concat(codbarra));
        }
    }
}
