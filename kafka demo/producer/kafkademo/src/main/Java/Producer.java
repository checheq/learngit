import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        Properties props = new Properties();
        //Map<String, Object> props = new HashMap<String, Object>();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        int messageNo = 1;
        while (true) {
            String messageStr = "你好，这是第" + messageNo + "条数据";
            System.out.println(messageStr);
            producer.send(new ProducerRecord<String, String>("demo", String.valueOf(messageNo), messageStr));
            System.out.println(messageStr);
            if (messageNo % 100 == 0) {
                System.out.println("发送的消息：" + messageStr);

            }
            if (messageNo % 1000 == 0) {
                System.out.println("成功发送了" + messageStr + "条");
                break;
            }

            messageNo++;
            Utils.sleep(1);

        }
    }

}
