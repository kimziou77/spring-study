package com.example.restaurant.wishlist.entity.repository;

import com.example.restaurant.db.AMemoryDbRepository;
import com.example.restaurant.wishlist.entity.entity.WishListEntity;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository extends AMemoryDbRepository<WishListEntity> {

}
