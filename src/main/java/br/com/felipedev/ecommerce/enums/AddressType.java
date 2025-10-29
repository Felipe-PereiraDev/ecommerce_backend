package br.com.felipedev.ecommerce.enums;

public enum AddressType {
    BILLING("Cobrança"),
    DELIVERY("Entrega");

    private String description;

    AddressType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}