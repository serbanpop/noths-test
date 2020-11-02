package com.notonthehighstreet;

import com.notonthehighstreet.promotions.PromotionalRule;
import com.notonthehighstreet.promotions.XOrMoreItemsReducedRule;
import com.notonthehighstreet.promotions.XPercentOffPurchaseRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class CheckoutTest {

    private static final Item PRODUCT_1 = Item.builder()
            .code("001")
            .name("Travel Card Holder")
            .price(new BigDecimal("9.25"))
            .build();

    private static final Item PRODUCT_2 = Item.builder()
            .code("002")
            .name("Personalised cufflinks")
            .price(new BigDecimal("45.00"))
            .build();

    private static final Item PRODUCT_3 = Item.builder()
            .code("003")
            .name("Kids T-shirt")
            .price(new BigDecimal("19.95"))
            .build();

    private static final PromotionalRule PRODUCT_1_PROMOTION = new XOrMoreItemsReducedRule(PRODUCT_1, 2, new BigDecimal("8.50"));
    private static final PromotionalRule ORDER_TOTAL_PROMOTION = new XPercentOffPurchaseRule(new BigDecimal("60.00"), new BigDecimal("10.00"));

    private static final List<PromotionalRule> PROMOTIONAL_RULES = asList(PRODUCT_1_PROMOTION, ORDER_TOTAL_PROMOTION);

    private Checkout checkout;

    @BeforeEach
    void setUp() {
        checkout = new Checkout(PROMOTIONAL_RULES);
    }

    @Test
    void appliesOrderTotalDiscount() {
        checkout.scan(PRODUCT_1);
        checkout.scan(PRODUCT_2);
        checkout.scan(PRODUCT_3);

        BigDecimal price = checkout.total();

        assertThat(price).isEqualTo(new BigDecimal("66.78"));
    }

    @Test
    void appliesDiscountForTwoProducts() {
        checkout.scan(PRODUCT_1);
        checkout.scan(PRODUCT_3);
        checkout.scan(PRODUCT_1);

        BigDecimal price = checkout.total();

        assertThat(price).isEqualTo(new BigDecimal("36.95"));
    }

    @Test
    void appliesDiscountForThreeProducts() {
        checkout.scan(PRODUCT_1);
        checkout.scan(PRODUCT_3);
        checkout.scan(PRODUCT_1);
        checkout.scan(PRODUCT_1);

        BigDecimal price = checkout.total();

        assertThat(price).isEqualTo(new BigDecimal("45.45"));
    }

    @Test
    void appliesDiscountForTwoProductsAndOrderTotal() {
        checkout.scan(PRODUCT_1);
        checkout.scan(PRODUCT_2);
        checkout.scan(PRODUCT_3);
        checkout.scan(PRODUCT_1);

        BigDecimal price = checkout.total();

        assertThat(price).isEqualTo(new BigDecimal("73.76"));
    }

    @Test
    void appliesDiscountForTwoProductsAndOrderTotal_regardlessOfTheProductOrder() {
        checkout.scan(PRODUCT_3);
        checkout.scan(PRODUCT_1);
        checkout.scan(PRODUCT_1);
        checkout.scan(PRODUCT_2);

        BigDecimal price = checkout.total();

        assertThat(price).isEqualTo(new BigDecimal("73.76"));
    }

    @Test
    void doesNotApplyDiscountWhenRulesDoNotApply() {
        checkout.scan(PRODUCT_1);
        checkout.scan(PRODUCT_2);

        BigDecimal price = checkout.total();

        assertThat(price).isEqualTo(new BigDecimal("54.25"));
    }

    @Test
    void doesNotApplyDiscountWhenOrderIsEmpty() {
        BigDecimal price = checkout.total();

        assertThat(price).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void totalIsZeroWhenOderIsEmpty() {
        BigDecimal price = checkout.total();

        assertThat(price).isEqualTo(new BigDecimal("0.00"));
    }
}