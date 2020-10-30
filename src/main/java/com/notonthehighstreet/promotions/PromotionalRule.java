package com.notonthehighstreet.promotions;

import com.notonthehighstreet.Item;
import com.notonthehighstreet.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class PromotionalRule {

    private final Type type;

    public abstract boolean applies(Order order);

    public abstract Item apply(Order order);

    public enum Type {
        PRODUCT,
        ORDER
    }
}
