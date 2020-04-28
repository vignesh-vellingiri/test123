package com.aot.invoice.dto;

import com.aot.invoice.model.Address;
import com.aot.invoice.model.Invoice;
import com.aot.invoice.model.Payment;
import com.google.gson.Gson;

public class LineToObjectConverter {
    public static <T extends Payment> T convertJsonToInvoiceObject(String line, T t) {
        return (T) new Gson().fromJson(line, t.getClass());
    }

    public static <T extends Invoice> T convertToInvoiceObject(String line, T t) {
        String objValues[] = line.split(",");
        t.setInvoiceDate(objValues[0]);
        t.setInvoiceNumber(objValues[1]);
        t.setItemCode(objValues[2]);
        t.setItemName(objValues[3]);
        t.setQuantity(objValues[4]);
        t.setCost(objValues[5]);
        Address address = new Address();
        address.setAddressLine1(objValues[6]);
        address.setAddressLine2(objValues[7]);
        address.setCity(objValues[8]);
        address.setState(objValues[9]);
        address.setZip(objValues[10]);
        address.setCountry(objValues[11]);
        t.setAddress(address);
        t.setStatus(objValues[12]);
        t.setFullName(objValues[13]);
        t.setCompany(objValues[14]);
        t.setEmail(objValues[15]);
        t.setPhone(objValues[16]);
        return t;
    }

    public static <T extends Payment> String convertObjectToLine(T t) {
        return t.getInvoiceNumber() + "," +
                t.getPaidAmount()+ "," +
                t.getBalanceAmount()+ "," +
                t.getPaidDate()+ "," +
                t.getPaidAccount()+ "," +
                t.getPaymentMode()+ "," +
                t.getStatus()+ "," +
                t.getPaymentNote()+ ",";
    }
}
