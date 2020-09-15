/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import consumer.ConsumerCreator;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kafka.serializer.StringDecoder;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import producer.UserResultProducer;
import scala.Tuple2;

/**
 *
 * @author saif8
 */
public class UserProcessing implements IKafkaConstants,Runnable{
    
   private ConsumerCreator consumerCreator;
   private String topic=IKafkaConstants.LOGS_TOPIC_NAME;
   private UserResultProducer userResultProducer;
   
    public void userProcessing() throws InterruptedException{
    SparkConf sparkConf = new SparkConf().setAppName("kafkaSparkStream").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaStreamingContext ssc = new JavaStreamingContext(sc, new       Duration(5000));
       Map<String, String> kafkaParams=consumerCreator.createConsumer();
        Set<String> topicName = Collections.singleton(this.topic);

        JavaPairInputDStream<String, String> kafkaSparkPairInputDStream = KafkaUtils
                                            .createDirectStream(ssc, String.class, String.class,
                                                StringDecoder.class, StringDecoder.class, kafkaParams,
                                                topicName);
                                       
        JavaDStream<String> kafkaSparkInputDStream = kafkaSparkPairInputDStream
                        .map(new Function<Tuple2<String, String>, String>() {
                            private static final long serialVersionUID = 1L;
                            public String call(Tuple2<String, String> tuple2) {
                return tuple2._2();
        }
});
  /*JavaDStream<String> words = kafkaSparkInputDStream.flatMap(new FlatMapFunction<String, String>() {
                public Iterable<String> call(String x) {
                    return Arrays.asList(x.split(" "));
                }
            });*/
  
  /*JavaPairDStream<String, Integer> wordCounts = words.mapToPair(new PairFunction<String, String, Integer>() {
    @Override
    public Tuple2<String, Integer> call(String s) {
      return new Tuple2<String, Integer>(s, 1);
    }
  });*/
 
        JavaDStream<String> lines=kafkaSparkInputDStream.filter (new Function<String,Boolean>() 
        {
            public Boolean call(String s){
                return s.contains("pam_unix(xrdp-sesman:session): session");
            }
        });
  /*JavaDStream<String> words = kafkaSparkInputDStream.flatMap(new FlatMapFunction<String, String>() {
                public Iterable<String> call(String x) {
                    return Arrays.stream(x.split(" "))
                        .skip(1)
                        .limit(4)
                        .collect(Collectors.toList());
                }
            });*/
            lines.foreachRDD(rdd -> {
          if(rdd!=null)
            {
                List<String> result = rdd.collect();
                


                for (String temp : result) {
                    String[] words=temp.split(" ");
                    String name1=words[8];
                    String name = StringUtils.chomp(name1);
                    String statusInWord=words[5];
                    int status=0;
                    if (statusInWord.equals("opened")){
                        status=1;
                    }
                    else{
                        status=0;
                    }
                    System.out.println("user: "+name+" status: "+statusInWord);
                    userResultProducer.sendUserResult(name, status);
                }
            }
      });
            /*lines.foreachRDD(new Function<JavaRDD<String>, Void>(){
            public Void call(JavaRDD<String> rdd) throws Exception {
                if(rdd!=null)
            {
                List<String> result = rdd.collect();
                


                for (String temp : result) {
                    String[] words=temp.split(" ");
                    String name1=words[8];
                    String name = StringUtils.chomp(name1);
                    String statusInWord=words[5];
                    int status=0;
                    if (statusInWord.equals("opened")){
                        status=1;
                    }
                    else{
                        status=0;
                    }
                    System.out.println("user: "+name+" status: "+statusInWord);
                    SendToFirebase.sendToFB(name, status);
                }
            }
            return null;
        }});*/
        
     
        //wordCounts.print();
  //kafkaSparkInputDStream.print();
        ssc.start();
        ssc.awaitTermination();
}

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
