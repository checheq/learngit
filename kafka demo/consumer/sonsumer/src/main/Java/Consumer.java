import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class Consumer {

    private final KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> msgList;
    private final String topic;
    private static final String GROUPID = "groupA";

    public Consumer(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", GROUPID);
        props.put("enable.auto.commit", "true"); //是否自动提交
        props.put("auto.commit.interval.ms", "1000");
        props.put("max.poll.records", "1000");  //一次最大拉取条数
        props.put("session.timeout.ms", "30000"); //超时时间
        props.put("auto.offset.reset", "earliest");   //消费规则
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer" );

        this.consumer = new KafkaConsumer<String, String>(props);
        this.topic = topicName;
        this.consumer.subscribe(Arrays.asList(topic));
}

    public void test() {
        System.out.println("---------开始消费---------");
        try {
            for (;;) {
                int messageNo=1;
                msgList = consumer.poll(1000);
                if(null!=msgList&&msgList.count()>0){
                    for (ConsumerRecord<String, String> record : msgList) {
                        //消费100条就打印 ,但打印的数据不一定是这个规律的
                        if(messageNo%100==0){
                            System.out.println(messageNo+"=======receive: key = " + record.key() + ", value = " + record.value()+" offset==="+record.offset());
                        }
                        //当消费了1000条就退出
                        if(messageNo%1000==0){
                            break;
                        }
                        messageNo++;
                    }
                }else{
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
    public static void main(String args[]) {
        Consumer test1 = new Consumer("demo");
            test1.test();

    }

}
