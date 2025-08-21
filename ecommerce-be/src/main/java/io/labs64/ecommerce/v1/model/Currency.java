package io.labs64.ecommerce.v1.model;

public enum Currency {
    USD, EUR;

    public static Currency fromString(String currency) {
        if (currency == null) {
            return null;
        }
        for (Currency c : values()) {
            if (c.name().equalsIgnoreCase(currency)) {
                return c;
            }
        }

        throw new IllegalArgumentException("Invalid currency value: " + currency);
    }
}
