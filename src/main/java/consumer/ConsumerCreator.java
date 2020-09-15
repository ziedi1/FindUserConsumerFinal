/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumer;

import general.IKafkaConstants;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import kafka.serializer.StringDecoder;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

/**
 *
 * @author saif8
 */
public class ConsumerCreator implements IKafkaConstants{
    public  Map<String, String> createConsumer() {
     Map<String, String> kafkaParams = new HashMap<String, String>();
        kafkaParams.put("bootstrap.servers", IKafkaConstants.KAFKA_BROKERS);
        return kafkaParams;
        
}}
