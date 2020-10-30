package com.notonthehighstreet.promotions;

import com.notonthehighstreet.Item;
import com.notonthehighstreet.Order;

import java.math.BigDecimal;

import static com.notonthehighstreet.promotions.PromotionalRule.Type.ORDER;
import static java.math.RoundingMode.HALF_DOWN;

public class XPercentOffPurchaseRule extends PromotionalRule {

    private final BigDecimal threshold;
    private final BigDecimal discountPercentage;

    public XPercentOffPurchaseRule(BigDecimal threshold, BigDecimal discountPercentage) {
        super(ORDER);
        this.threshold = threshold;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public boolean applies(Order order) {
        return order.total().compareTo(threshold) >= 0;
    }

    @Override
    public Item apply(Order order) {
        BigDecimal discount = order.total()
                .multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), HALF_DOWN);
        return Item.builder()
                .name(discountPercentage + "% off for orders over £" + threshold)
                .price(discount.negate())
                .build();
    }
}
