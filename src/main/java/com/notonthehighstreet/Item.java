package com.notonthehighstreet;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
public class Item {

    private final String code;
    private final String name;
    private final BigDecimal price;

}
