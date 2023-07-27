package com.example.wishlistsbbe.repository;

import com.example.wishlistsbbe.entity.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    Wishlist findByDocumento(String documento);
}
