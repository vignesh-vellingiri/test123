package com.aot.invoice.model;

public class Payment {
    private String InvoiceNumber;
    private String paidAmount;
    private String balanceAmount;
    private String paidDate;
    private String paidAccount;
    private String paymentMode;
    private String paymentNote;
    private String status;

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaidAccount() {
        return paidAccount;
    }

    public void setPaidAccount(String paidAccount) {
        this.paidAccount = paidAccount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentNote() {
        return paymentNote;
    }

    public void setPaymentNote(String paymentNote) {
        this.paymentNote = paymentNote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "InvoiceNumber:'" + InvoiceNumber + '\'' +
                ", paidAmount:'" + paidAmount + '\'' +
                ", balanceAmount:'" + balanceAmount + '\'' +
                ", paidDate:'" + paidDate + '\'' +
                ", paidAccount:'" + paidAccount + '\'' +
                ", paymentMode:'" + paymentMode + '\'' +
                ", paymentNote:'" + paymentNote + '\'' +
                ", status:'" + status + '\'' +
                '}';
    }
}
