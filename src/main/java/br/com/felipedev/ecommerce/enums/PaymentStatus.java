package br.com.felipedev.ecommerce.enums;

public enum PaymentStatus {
    PENDING("Pendente"),
    PAID("Pago"),
    OVERDUE("Vencido"),
    CANCELLED("Cancelado"),
    NEGOTIATED("Renegociado"),
    OPEN("Aberto"),
    SETTLED("Quitado");

    private String description;

    PaymentStatus(String description) {
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
