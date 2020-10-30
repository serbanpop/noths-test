package com.notonthehighstreet.promotions;

import com.notonthehighstreet.Item;
import com.notonthehighstreet.Order;

import java.math.BigDecimal;

import static com.notonthehighstreet.promotions.PromotionalRule.Type.PRODUCT;

public class XOrMoreItemsReducedRule extends PromotionalRule {

    private final Item item;
    private final Integer threshold;
    private final BigDecimal discountedPrice;

    public XOrMoreItemsReducedRule(Item item, Integer threshold, BigDecimal discountedPrice) {
        super(PRODUCT);
        this.item = item;
        this.threshold = threshold;
        this.discountedPrice = discountedPrice;
    }

    @Override
    public boolean applies(Order order) {
        return threshold <= order.getEntries().getOrDefault(item, 0);
    }

    @Override
    public Item apply(Order order) {
        // discount = quantity * (price - discounted price)
        Integer quantity = order.getEntries().get(item);
        BigDecimal discount = BigDecimal.valueOf(quantity)
                .multiply(item.getPrice().subtract(discountedPrice));
        return Item.builder()
                .name(threshold + " or more " + item.getName() + " at " + discountedPrice)
                .price(discount.negate())
                .build();
    }
}
