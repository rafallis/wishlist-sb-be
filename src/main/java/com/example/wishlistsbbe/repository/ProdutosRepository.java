package com.example.wishlistsbbe.repository;

import com.example.wishlistsbbe.entity.Produtos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository extends MongoRepository<Produtos, String> {

    Produtos findByCodbarra(String codbarra);
}
