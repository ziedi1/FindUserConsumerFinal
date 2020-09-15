package general;

public interface IKafkaConstants {

    public String KAFKA_BROKERS = "localhost:9092";

    public String LOGS_TOPIC_NAME = "logfile";

    public String USER_RESULT_TOPIC_NAME = "userResult";

    public String TRAFFIC_TOPIC_NAME = "traffic";

    public String BOTNET_RESULT_TOPIC_NAME = "botnetResult";

    public String ACKS = "all";

    public String COMPRESSION_TYPE = "snappy";

    public Integer RETRIES = 1;

    public Integer BATCH_SIZE = 16384;

    public Integer BUFFER_MEMORY = 33554432;

    public String KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";

    public String VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
}
