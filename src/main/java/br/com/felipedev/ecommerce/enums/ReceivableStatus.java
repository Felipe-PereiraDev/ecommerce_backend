package br.com.felipedev.ecommerce.enums;

public enum ReceivableStatus {
    PENDING("Pendente"),
    PAID("Pago"),
    OVERDUE("Vencido"),
    CANCELLED("Cancelado");

    private String description;

    ReceivableStatus(String description) {
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
