package com.example.wishlistsbbe.entity;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "enderecos")
public class Endereco {

    private String rua;
    private String bairro;
    private String quadra;
    private String lote;
    private String numero;
    private String cidade;
    private String complemento;

    @Size(max = 2)
    private String estado;

    @Size(max = 8)
    private String cep;
}
