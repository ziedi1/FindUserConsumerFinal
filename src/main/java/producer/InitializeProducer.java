package producer;

import general.IKafkaConstants;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

public class InitializeProducer implements IKafkaConstants {

    public static Producer<String, String> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", IKafkaConstants.KAFKA_BROKERS);
        props.put("acks", IKafkaConstants.ACKS);
        props.put("compression.type", IKafkaConstants.COMPRESSION_TYPE);
        props.put("retries", IKafkaConstants.RETRIES);
        props.put("batch.size", IKafkaConstants.BATCH_SIZE);
        props.put("buffer.memory", IKafkaConstants.BUFFER_MEMORY);
        props.put("key.serializer", IKafkaConstants.KEY_SERIALIZER);
        props.put("value.serializer", IKafkaConstants.VALUE_SERIALIZER);
        return new KafkaProducer(props);
    }
}
