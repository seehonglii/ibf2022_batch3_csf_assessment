package ibf2022.batch3.assessment.csf.orderbackend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
    @Value("${mongo.url}")
    private String url;

    @Bean
    @Qualifier("my-mongo")
    public MongoTemplate createMongoTemplate() {
        
        MongoClient client = MongoClients.create(url);

        // provide your database here
        return new MongoTemplate(client, "orders");
    }
}
