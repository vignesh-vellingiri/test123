package com.aot.invoice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Invoice {
        private String InvoiceNumber;
        private String ItemName;
        private String ItemCode;
        private String InvoiceDate;
        private String Status;
        private String Quantity;
        private String Cost;
        private String FullName;
        private String Company;
        private Address address;
        private String Phone;
        private String Email;

        public String getInvoiceNumber() {
                return InvoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
                InvoiceNumber = invoiceNumber;
        }

        public String getItemName() {
                return ItemName;
        }

        public void setItemName(String itemName) {
                ItemName = itemName;
        }

        public String getItemCode() {
                return ItemCode;
        }

        public void setItemCode(String itemCode) {
                ItemCode = itemCode;
        }

        public String getInvoiceDate() {
                return InvoiceDate;
        }

        public void setInvoiceDate(String invoiceDate) {
                InvoiceDate = invoiceDate;
        }

        public String getStatus() {
                return Status;
        }

        public void setStatus(String status) {
                Status = status;
        }

        public String getQuantity() {
                return Quantity;
        }

        public void setQuantity(String quantity) {
                Quantity = quantity;
        }

        public String getCost() {
                return Cost;
        }

        public void setCost(String cost) {
                Cost = cost;
        }

        public String getFullName() {
                return FullName;
        }

        public void setFullName(String fullName) {
                FullName = fullName;
        }

        public String getCompany() {
                return Company;
        }

        public void setCompany(String company) {
                Company = company;
        }

        public Address getAddress() {
                return address;
        }

        public void setAddress(Address address) {
                this.address = address;
        }

        public String getPhone() {
                return Phone;
        }

        public void setPhone(String phone) {
                Phone = phone;
        }

        public String getEmail() {
                return Email;
        }

        public void setEmail(String email) {
                Email = email;
        }

        @Override
        public String toString() {
                return "{" +
                        "InvoiceNumber:'" + InvoiceNumber + '\'' +
                        ", ItemName:'" + ItemName + '\'' +
                        ", ItemCode:'" + ItemCode + '\'' +
                        ", InvoiceDate:'" + InvoiceDate + '\'' +
                        ", Status:'" + Status + '\'' +
                        ", Quantity:'" + Quantity + '\'' +
                        ", Cost:'" + Cost + '\'' +
                        ", FullName:'" + FullName + '\'' +
                        ", Company:'" + Company + '\'' +
                        ", address:" + address +
                        ", Phone:'" + Phone + '\'' +
                        ", Email:'" + Email + '\'' +
                        '}';
        }
}