package com.aot.invoice.topic;

import com.aot.invoice.model.Invoice;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class InvoiceSender {
    private final static String TOPIC = "InvoiceTopic";
    private final static String BOOTSTRAP_SERVER = "localhost:9092";
    private static Producer<String, String> producer;

    private InvoiceSender() {
        producer= getProducer();
    }

    private static Producer<String, String> getProducer() {
        Properties settings = new Properties();
        settings.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        settings.put(ProducerConfig.CLIENT_ID_CONFIG, "OrderProducerGroup");
        settings.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        settings.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        settings.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        settings.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "C:\\codes\\programs\\Kafka\\ssl\\client.truststore.jks");
        settings.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "test1234");
        settings.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "C:\\codes\\programs\\Kafka\\ssl\\server.keystore.jks");
        settings.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "test1234");
        settings.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "test1234");
        return new KafkaProducer<>(settings);
    }

    public static void sendToPayment(final String status, final Invoice invoice) throws ExecutionException, InterruptedException {
        System.out.println("Invoice - " + invoice.toString());
        producer= getProducer();
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







