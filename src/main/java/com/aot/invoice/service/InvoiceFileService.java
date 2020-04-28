package com.aot.invoice.service;

import com.aot.invoice.model.Invoice;
import com.aot.invoice.model.InvoiceStatus;
import com.aot.invoice.model.Payment;
import com.aot.invoice.properties.InvoiceConstants;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static com.aot.invoice.dto.LineToObjectConverter.*;
import static com.aot.invoice.topic.InvoiceSender.sendToPayment;

public class InvoiceFileService {

    public void triggerInvoiceEvent(String filePath) throws IOException, ExecutionException, InterruptedException {
        Scanner sc = new Scanner(new File(filePath));
        int recordCounter=0;
        while (sc.hasNextLine()) {
            if (recordCounter == 0)
                sc.nextLine();
            else {
                String line =sc.nextLine();
                Invoice invoice = convertToInvoiceObject(line, new Invoice());
                System.out.println("Invoice Object created.");
                sendToPayment(InvoiceStatus.INVOICED.getStatus() , invoice);
                System.out.println("Invoice sent.");
                processOutbound("INVOICE", invoice.getInvoiceNumber(), line);
                System.out.println("Invoice Outbound file created.");
            }
            recordCounter++;
        }
        //renameInvoiceFile(filePath);
        System.out.println("Renamed Invoiced file.");

    }

    private void processOutbound(String status, String invoiceNumber, String line) {
        String outboundFile = InvoiceConstants.invoiceOutboundPath + "//" + invoiceNumber +"_" + status + ".txt";
        boolean fileExists = new File(outboundFile).exists();
        if(!fileExists) {
            File f = new File(outboundFile);
            try (FileWriter fileWriter = new FileWriter(outboundFile)) {
                fileWriter.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(outboundFile)) {
                fileWriter.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void renameInvoiceFile(String filePath) throws IOException {
        System.out.println("Rename:" + filePath);
        File f = new File(filePath);
        f.renameTo(new File(filePath+".invoiced"));
        Files.move(Paths.get(filePath),Paths.get(filePath+".invoiced"));
    }

    public void triggerPaymentEvent(ConsumerRecords<String, String> consumerRecords) {
        consumerRecords.forEach(record -> {
            Payment payment = convertJsonToInvoiceObject(record.value(), new Payment());
            System.out.println("Payment received.");
            processOutbound("PAYMENT", payment.getInvoiceNumber(), convertObjectToLine(payment));
            System.out.println("Payment Outbound file created.");
        });
    }
}
