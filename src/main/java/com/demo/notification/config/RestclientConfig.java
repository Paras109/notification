package com.demo.notification.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages="com.example.springelastic.repository")
@ComponentScan(basePackages= {"com.example.springelastic.service"})
public class RestclientConfig {
	
	

	  @Bean
	    RestHighLevelClient client() {

		  RestClient httpClient = RestClient.builder(
				    new HttpHost("localhost", 9200)
				).build();

				// Create the HLRC
				RestHighLevelClient hlrc = new RestHighLevelClientBuilder(httpClient)
				    .setApiCompatibilityMode(true) 
				    .build();

		  
	        return hlrc;
	    }

	    @Bean
	    public ElasticsearchOperations elasticsearchTemplate() {
	        return new ElasticsearchRestTemplate(client());
	    }
	
	
	
}
