package com.hedza06.spring.jpa.locking.services;

import com.hedza06.spring.jpa.locking.entities.Item;
import com.hedza06.spring.jpa.locking.repositories.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatePrice(Integer id, Double price)
    {
        log.info("Updating price {} EUR for item with id: {}", price, id);

        Item item = itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        item.setPrice(item.getPrice() + price);
    }
}
