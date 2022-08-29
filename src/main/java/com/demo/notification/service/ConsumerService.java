package com.demo.notification.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.notification.entity.ApiOutput;
import com.demo.notification.entity.Channels;
import com.demo.notification.entity.Destination;
import com.demo.notification.entity.Sms;
import com.demo.notification.entity.SmsApi;
import com.demo.notification.entity.SmsRequest;
import com.demo.notification.model.MessageElasticsearch;
import com.demo.notification.response.GetSmsDetailsSuccess;

@Service
public class ConsumerService {


private BlacklistRedisService blacklistRedisService;
private MessageElasticsearchService messageElasticsearchService;

@Autowired
SmsRequestService smsRequestService;
@Autowired
	public ConsumerService(BlacklistRedisService blacklistRedisService,MessageElasticsearchService messageElasticsearchService) {
	
	this.blacklistRedisService = blacklistRedisService;
	this.messageElasticsearchService=messageElasticsearchService;
}

private SmsRequest getSmsDetailsApi(String message) {
	String id=message;
    String uri="http://localhost:9000/v1/sms/"+id;

    
    String authStr = "user:password";
    String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
    
    
    HttpHeaders firstHeaders = new HttpHeaders();
    firstHeaders.add("Authorization", "Basic " + base64Creds);
    
    HttpEntity request = new HttpEntity(firstHeaders);


    ResponseEntity<GetSmsDetailsSuccess> response = new RestTemplate().exchange(uri, HttpMethod.GET, request, GetSmsDetailsSuccess.class);

            GetSmsDetailsSuccess temp = response.getBody();
    
    SmsRequest result=temp.getData();
	return result;
}


private ApiOutput callThirdPartyApi(SmsRequest result) {
	String smsApiUrl="https://api.imiconnect.in/resources/v1/messaging?key=93ceffda-5941-11ea-9da9-025282c394f2";
	
	RestTemplate template = new RestTemplate();
	
	
	String deliverychannel="sms";
	
	String text=result.getMessage();
	Sms smsObject=new Sms(text);
	Channels channelsObject=new Channels(smsObject);
	
	
	
	
	List<String> msisdn =new ArrayList<>();
	msisdn.add(result.getPhoneNumber());
	 String correlationid=Integer.toString(result.getId());
	
	 
	 Destination destination=new Destination(msisdn,correlationid);
	 List<Destination> listDestination=new ArrayList<>();
	 listDestination.add(destination);
			 
	
	SmsApi payload = new SmsApi(deliverychannel,channelsObject,listDestination);
	
	
	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	headers.set("key", "93ceffda-5941-11ea-9da9-025282c394f2");
	
	headers.setContentType(MediaType.APPLICATION_JSON);
	
	
	HttpEntity<SmsApi> requestEntity = new HttpEntity<SmsApi>(payload, headers);
	
	
	ApiOutput newResponse = template.exchange(smsApiUrl, HttpMethod.POST, requestEntity, 
				   ApiOutput.class).getBody();
	return newResponse;
}

	private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @KafkaListener(topics = "notification.send_sms", groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("#### -> Consumed message -> %s", message));
        
        SmsRequest result = getSmsDetailsApi(message);
    	boolean isBlacklisted=blacklistRedisService.findById(result.getPhoneNumber());
        
    	
    	
    	if(isBlacklisted)
    	{
    		System.out.println("BLACKLIST HAI YEH");
    		
    	}
    	else
    	{
    		System.out.println("NAHI HAI YEH");
    		
    		
    		ApiOutput newResponse = callThirdPartyApi(result);
    		
    		String transid=newResponse.getResponse().get(0).getTransid();
    		String description=newResponse.getResponse().get(0).getDescription();
    		String code=newResponse.getResponse().get(0).getCode();
    		int codeInt=Integer.parseInt(code);
    		System.out.println(description);
    		System.out.println(code);
    		System.out.println(transid);

    		Date currentTime=new Date();
    		SmsRequest smsrequestToBeUpdated=null;
    			if(codeInt==1001)
    			{
            smsrequestToBeUpdated=new SmsRequest(result.getId(),result.getPhoneNumber(),result.getMessage(),description,result.getCreatedAt(),currentTime);}
    			else
    			{
    				
    				smsrequestToBeUpdated=new SmsRequest(result.getId(),result.getPhoneNumber(),result.getMessage(),description,codeInt,result.getCreatedAt(),currentTime);
    			}
            

        	smsRequestService.save(smsrequestToBeUpdated);
            
            
            
    		
    		long unixTime=currentTime.getTime()/1L;
    		
MessageElasticsearch theMessageElasticsearch=new MessageElasticsearch(result.getPhoneNumber(),result.getMessage(),unixTime);
    		
    		messageElasticsearchService.save(theMessageElasticsearch);
    	}
    	
    }

	

	
}
