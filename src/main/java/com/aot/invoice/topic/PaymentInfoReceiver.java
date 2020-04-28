package com.aot.invoice.topic;

import com.aot.invoice.model.Payment;
import com.aot.invoice.service.InvoiceFileService;
import com.google.gson.Gson;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class PaymentInfoReceiver {
    private final static String TOPIC = "PaymentTopic";
    private final static String BOOTSTRAP_SERVER = "localhost:9092";


    private static Consumer<String, String> createConsumer() {
        final Properties settings = new Properties();
        settings.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        settings.put(ConsumerConfig.GROUP_ID_CONFIG, "OrderStatusConsumerGroup");
        settings.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        settings.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        settings.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        settings.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "C:\\codes\\programs\\Kafka\\ssl\\client.truststore.jks");
        settings.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "test1234");
        settings.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "C:\\codes\\programs\\Kafka\\ssl\\server.keystore.jks");
        settings.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "test1234");
        settings.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "test1234");
        final Consumer<String, String> consumer = new KafkaConsumer<>(settings);
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }

    public static void getPaymentInfoFromTopic() {
        final Consumer<String, String> consumer = createConsumer();
        final ConsumerRecords<String, String> consumerRecords =
                consumer.poll(1000);
        consumerRecords.forEach(record -> {
            System.out.println(record.key() + ":" + record.value());
            Gson g = new Gson();
            Payment payment = g.fromJson(record.value(), Payment.class);
            System.out.println("From gson:" + payment.getInvoiceNumber());
        });
        new InvoiceFileService().triggerPaymentEvent(consumerRecords);
        consumer.commitAsync();
        consumer.close();
    }
}







