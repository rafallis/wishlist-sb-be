package com.example.wishlistsbbe.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "wishlist")
public class Wishlist {

    @Id
    private String id;

    @Indexed
    private String documento;

    @DBRef
    private List<Produtos> produtosList;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
