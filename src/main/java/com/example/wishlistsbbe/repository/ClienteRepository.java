package com.example.wishlistsbbe.repository;

import com.example.wishlistsbbe.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {

    Boolean existsByCpf(String cpf);
}
