package com.hedza06.spring.jpa.locking.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceOrchestrator {

    private final ItemService itemService;


    @Transactional
    public void updateItemPrice(Integer itemId, Double itemPrice)
    {
        try
        {
            log.info("Preparing item update from orchestrator...");
            itemService.updatePrice(itemId, itemPrice);
        }
        catch (ObjectOptimisticLockingFailureException e)
        {
            log.warn("Some other transaction is trying to update the price of same item. Message: {}", e.getMessage());
            log.warn("Retrying update after optimistic locking failure exception...");
            itemService.updatePrice(itemId, itemPrice);
        }
    }
}
