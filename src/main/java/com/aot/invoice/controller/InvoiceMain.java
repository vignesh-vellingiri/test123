package com.aot.invoice.controller;

import com.aot.invoice.properties.InvoiceConstants;
import com.aot.invoice.watcher.FileWatcher;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.aot.invoice.topic.PaymentInfoReceiver.getPaymentInfoFromTopic;

public class InvoiceMain {
    public static void main(String[] args)  {
        final Path dir = Paths.get(InvoiceConstants.invoiceInboundPath);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            try {
                new FileWatcher(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        executor.submit(()-> {
            for(;;) {
                try {
                    getPaymentInfoFromTopic();
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
