package br.com.felipedev.ecommerce.enums;

public enum PaymentType {
    CREDIT_CARD("Crédito"),
    DEBIT_CARD("Débito"),
    BANK_SLIP("Boleto Bancário"),
    PIX("Pix");

    private String description;

    PaymentType(String description) {
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
