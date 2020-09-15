/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producer;

import general.IKafkaConstants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import static producer.InitializeProducer.createProducer;

/**
 *
 * @author saif8
 */
public class UserResultProducer implements IKafkaConstants {
    private Producer<String, String> producer;
    private InitializeProducer iniProducer;
    
    public void sendUserResult(String name,int status) {
     this.producer = iniProducer.createProducer();
     ProducerRecord<String, String> record;
     String line=name+" "+ String.valueOf(status);
     record = new ProducerRecord<String, String>(IKafkaConstants.USER_RESULT_TOPIC_NAME, line);
     this.producer.send(record);
        
}
}
