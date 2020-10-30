package com.notonthehighstreet;

import com.notonthehighstreet.promotions.PromotionalRule;
import lombok.Getter;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Comparator.comparing;

@Getter
public class Checkout {

    private final Order order = new Order();
    private final Set<PromotionalRule> rules = new TreeSet<>(comparing(PromotionalRule::getType));

    public Checkout(Collection<PromotionalRule> rules) {
        this.rules.addAll(rules);
    }

    public void scan(Item item) {
        order.add(item);
    }

    @SneakyThrows
    public BigDecimal total() {
        final Order orderWithDiscounts = this.order.clone();
        rules.stream()
                .filter(rule -> rule.applies(orderWithDiscounts))
                .forEach(rule -> orderWithDiscounts.add(rule.apply(orderWithDiscounts)));
        return orderWithDiscounts.total();
    }

}
