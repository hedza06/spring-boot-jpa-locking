package com.hedza06.spring.jpa.locking.repositories;

import com.hedza06.spring.jpa.locking.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> { }
