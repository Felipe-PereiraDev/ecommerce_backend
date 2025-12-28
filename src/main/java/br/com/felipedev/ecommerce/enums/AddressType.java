package br.com.felipedev.ecommerce.enums;

public enum AddressType {
    BILLING("Cobrança"),
    DELIVERY("Entrega");

    private final String description;

    AddressType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static AddressType getAddressType(String addressType) {
        for (AddressType value : AddressType.values()) {
            if (value.description.equals(addressType)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.description;
    }
}