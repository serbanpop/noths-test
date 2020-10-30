package com.notonthehighstreet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Collections.unmodifiableMap;

public class Order implements Cloneable {

    private final Map<Item, Integer> entries = new HashMap<>();

    public void add(Item item) {
        entries.put(item, entries.getOrDefault(item, 0) + 1);
    }

    public BigDecimal total() {
        return entries.entrySet().stream()
                .map(entry -> BigDecimal.valueOf(entry.getValue()).multiply(entry.getKey().getPrice()))
                .reduce(ZERO, BigDecimal::add)
                .setScale(2, HALF_UP);
    }

    public Map<Item, Integer> getEntries() {
        return unmodifiableMap(entries);
    }

    @Override
    protected Order clone() throws CloneNotSupportedException {
        final Order clone = (Order) super.clone();
        clone.entries.putAll(this.entries);
        return clone;
    }
}
