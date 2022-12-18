package com.hedza06.spring.jpa.locking.services;

import com.hedza06.spring.jpa.locking.Application;
import com.hedza06.spring.jpa.locking.entities.Item;
import com.hedza06.spring.jpa.locking.repositories.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class ItemServiceOrchestratorTest {

    @Autowired
    private ItemServiceOrchestrator itemServiceOrchestrator;

    @Autowired
    private ItemRepository itemRepository;


    @Test
    void shouldUpdateItemPriceWithoutConcurrency()
    {
        Item item = Item.builder()
            .name("Item 1")
            .description("Item 1 Description")
            .price(0.00D)
            .build();

        itemRepository.save(item);
        assertEquals(0, item.getVersion());

        List<Double> itemPrices = List.of(10.00D, 12.15D);
        for (Double price : itemPrices) {
            itemServiceOrchestrator.updateItemPrice(item.getId(), price);
        }

        Item updatedItem = itemRepository.findById(item.getId()).orElseThrow(EntityNotFoundException::new);
        assertAll(
            () -> assertEquals(2, updatedItem.getVersion()),
            () -> assertEquals(22.15D, updatedItem.getPrice())
        );
    }

    @Test
    void shouldUpdateItemWithConcurrency() throws InterruptedException
    {
        Item item = Item.builder()
            .name("Item 2")
            .description("Item 2 Description")
            .price(100.00D)
            .build();

        itemRepository.save(item);
        assertEquals(0, item.getVersion());

        List<Double> itemPrices = List.of(50.00D, 10.00D);
        final ExecutorService executor = Executors.newFixedThreadPool(itemPrices.size());

        for (Double itemPrice : itemPrices) {
            executor.execute(() -> itemServiceOrchestrator.updateItemPrice(item.getId(), itemPrice));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        Item updatedItem = itemRepository.findById(item.getId()).orElseThrow(EntityNotFoundException::new);

        assertAll(
            () -> assertEquals(2, updatedItem.getVersion()),
            () -> assertEquals(160.00D, updatedItem.getPrice())
        );
    }
}