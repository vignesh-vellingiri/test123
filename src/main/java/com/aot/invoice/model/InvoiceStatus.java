package com.aot.invoice.model;

public enum InvoiceStatus {
    INVOICED("Invoiced"),
    COMPLETED("Payment Completed"),
    PARTIAL("Partial Payment Completed"),
    DECLINED("Payment Declined");

    String status;
    InvoiceStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
