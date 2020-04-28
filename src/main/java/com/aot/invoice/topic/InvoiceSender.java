package com.aot.invoice.topic;

import com.aot.invoice.model.Invoice;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class InvoiceSender {
    private final static String TOPIC ="InvoiceTopic";
    private final static String BOOTSTRAP_SERVER = "kafka:9092";
    private static Producer<String, String> producer;

    private InvoiceSender() {
        producer= getProducer();
    }

    private static Producer<String, String> getProducer() {
        Properties settings = new Properties();
        settings.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        settings.put(ProducerConfig.CLIENT_ID_CONFIG, "com.aot.orders.topic.OrderStatusProducer");
        settings.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        settings.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        settings.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        settings.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/app/apps/AOTVendorInvoicing/src/main/java/com/aot/invoice/jks/kafka.truststore.jks");
        //settings.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "C:\\Users\\vignesh vellingiri\\Downloads\\docker\\kafka.truststore.jks");
        settings.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "test123");
        settings.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "/app/apps/AOTVendorInvoicing/src/main/java/com/aot/invoice/jks/kafka.keystore.jks");
        //settings.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "C:\\Users\\vignesh vellingiri\\Downloads\\docker\\kafka.keystore.jks");
        settings.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "test123");
        settings.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "test123");
        settings.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        settings.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + "user" + "\" password=\"" + "password" + "\";");
        KafkaProducer<String, String> t = new KafkaProducer<String, String>(settings);
        System.out.println("after getProdcuer");
        //return new KafkaProducer<>(settings);
        return t;
    }

    public static void sendToPayment(final String status, final Invoice invoice) throws ExecutionException, InterruptedException {
        System.out.println("Invoice - " + invoice.toString());
        producer= getProducer();
        System.out.println("after getProdcuer");
        long time = System.currentTimeMillis();
        try {
                final ProducerRecord<String, String> record =
                        new ProducerRecord<>(TOPIC, invoice.getInvoiceNumber()+"-"+status,
                                invoice.toString());
                RecordMetadata metadata = producer.send(record).get();
                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " +
                                "meta(partition=%d, offset=%d) time=%d\n",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime);


        } finally {
            producer.flush();
            producer.close();
        }
    }


}







