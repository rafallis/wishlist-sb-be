package com.example.wishlistsbbe.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "clientes")
public class Cliente {

    @Id
    private String id;

    @NotBlank
    @CPF
    @Field
    @Indexed(unique = true)
    @Size(max = 11)
    private String cpf;

    @NotBlank
    @Indexed
    @Size(max = 50)
    private String nome;

    @NotBlank
    @Indexed
    @Size(max = 150)
    private String sobrenome;

    private LocalDate dataNascimento;
    private Endereco endereco;

    @Email
    @Size(max = 50)
    private String email;

    @Size(max = 15)
    private String telefone;

    private Comunicacoes comunicacoes;
}
