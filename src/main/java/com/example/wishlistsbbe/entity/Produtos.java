package com.example.wishlistsbbe.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "produtos")
public class Produtos {

    @Id
    private String id;
    private String codbarra;
    private String descricao;
    private String fornecedor;
    private Double valor;
    private Integer quantidadeEstoque;
}
