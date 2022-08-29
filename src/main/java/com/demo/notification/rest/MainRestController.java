package com.demo.notification.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.notification.entity.Blacklist;
import com.demo.notification.entity.BlacklistRedis;
import com.demo.notification.entity.SmsRequest;
import com.demo.notification.exception.IdNotFoundException;
import com.demo.notification.exception.NoBlacklistNumberFound;
import com.demo.notification.exception.PhoneNumberBlacklistedException;
import com.demo.notification.exception.PhoneNumberInvalidException;
import com.demo.notification.exception.PhoneNumberNullException;
import com.demo.notification.model.MessageElasticsearch;
import com.demo.notification.response.AddRemoveBlacklistNumberSuccess;
import com.demo.notification.response.GetBlacklistNumberSuccess;
import com.demo.notification.response.GetSmsDetailsError;
import com.demo.notification.response.GetSmsDetailsSuccess;
import com.demo.notification.response.InnerSmsRequestError;
import com.demo.notification.response.InnerSmsRequestSuccess;
import com.demo.notification.response.SmsRequestError;
import com.demo.notification.response.ElasticsearchSuccess;
import com.demo.notification.response.SmsRequestSuccess;
import com.demo.notification.service.BlacklistRedisService;
import com.demo.notification.service.BlacklistService;
import com.demo.notification.service.MessageElasticsearchService;
import com.demo.notification.service.ProducerService;
import com.demo.notification.service.SmsRequestService;
import com.demo.notification.utils.NumberDetails;
import com.demo.notification.utils.SmsDetails;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;





@RestController
@RequestMapping("/v1")
public class MainRestController {

private SmsRequestService smsRequestService;
private BlacklistService blacklistService;
private BlacklistRedisService blacklistRedisService;
private final ProducerService producer;
MessageElasticsearchService messageElasticsearchService;
	@Autowired
	public MainRestController(SmsRequestService theSmsRequestService, BlacklistService theBlacklistService ,ProducerService theProducerService,BlacklistRedisService theBlacklistRedisService,MessageElasticsearchService theMessageElasticsearchService) {
		smsRequestService = theSmsRequestService;
		blacklistService = theBlacklistService;
		producer=theProducerService;
		blacklistRedisService=theBlacklistRedisService;
		messageElasticsearchService=theMessageElasticsearchService;
		
	}
	
	
	
	private void checkValidityAndThrowException(SmsDetails theSmsDetail) {
		if(theSmsDetail.getPhoneNumber()=="")
	    {
	    	throw new PhoneNumberNullException("Phone Number Field is Empty");
	    	
	    }
	PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();  
	    
	    PhoneNumber phoneNumber;
		try {
			phoneNumber = numberUtil.parse(theSmsDetail.getPhoneNumber(),"IN");
		} catch (NumberParseException e) {
			

			   throw new PhoneNumberInvalidException("Phone Number is INVALID");
		}
	    
	    boolean isValid = numberUtil.isValidNumber(phoneNumber);  
	    
	    
	   if(isValid==false)
	    {
		   throw new PhoneNumberInvalidException("Phone Number is INVALID");
	    }
		
		
	   boolean isBlacklisted=blacklistRedisService.findById(theSmsDetail.getPhoneNumber());
	   
	   if(isBlacklisted)
	   {
		   throw new PhoneNumberBlacklistedException("Number Is Blacklisted");
	   }
	}
		
	
	public SmsRequest getSmsDetailsApi(String id) {
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

	public RestHighLevelClient initialiseRestHighLevelClient()
	{

		RestHighLevelClient client = new RestHighLevelClient(
	            RestClient.builder(
	                    new HttpHost("localhost", 9200, "http")
	            )
	    );
		return client;
		

		
		
	}
	
	
@PostMapping("sms/send")
public ResponseEntity<SmsRequestSuccess> sendSms(@RequestBody SmsDetails theSmsDetail) 
{	

	
	checkValidityAndThrowException(theSmsDetail);
	
	

	SmsRequest theSmsRequest=new SmsRequest(theSmsDetail.getPhoneNumber(),theSmsDetail.getMessage(),"pending",new Date(),new Date());
	

	theSmsRequest.setId(0);
	smsRequestService.save(theSmsRequest);
	
	System.out.println(theSmsRequest.getId());
	

    this.producer.sendMessage(Integer.toString(theSmsRequest.getId()));
     
    try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
    SmsRequest result = getSmsDetailsApi(Integer.toString(theSmsRequest.getId()));
    
    
    
	
    
    InnerSmsRequestSuccess responseData=new InnerSmsRequestSuccess(theSmsRequest.getId(),result.getStatus());
    SmsRequestSuccess newResponse=new SmsRequestSuccess(responseData);
    
    return new ResponseEntity<>(newResponse, HttpStatus.OK);
    
}












@GetMapping("sms/{smsRequestId}")
public ResponseEntity<GetSmsDetailsSuccess> GetSmsDetails(@PathVariable int smsRequestId)
{
	
	SmsRequest theSmsRequest=smsRequestService.findById(smsRequestId);
	
	if(theSmsRequest==null)
	{	
		throw new IdNotFoundException("Id not found "+ smsRequestId);
		
		
	}
	GetSmsDetailsSuccess response=new GetSmsDetailsSuccess(theSmsRequest);
	
	
	return new ResponseEntity<>(response, HttpStatus.OK);
}







@PostMapping("blacklist")
public ResponseEntity<AddRemoveBlacklistNumberSuccess> addToBlacklist(@RequestBody NumberDetails theNumberDetails)
{	
	List<String> numbers=theNumberDetails.getNumbers();
	
	for(String number:numbers) {
		
		if(number=="")
	    {
	    	throw new PhoneNumberNullException("Phone Number Field is Empty");
	    	
	    }
	PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();  
	    
	    PhoneNumber phoneNumber;
		try {
			phoneNumber = numberUtil.parse(number,"IN");
		} catch (NumberParseException e) {
			

			   throw new PhoneNumberInvalidException("Phone Number is INVALID " +number);
		}
	    
	    boolean isValid = numberUtil.isValidNumber(phoneNumber);  
	    
	    
	   if(isValid==false)
	    {
		   throw new PhoneNumberInvalidException("Phone Number is INVALID "+ number);
	    }
	   
	   
	   boolean isBlacklisted=blacklistRedisService.findById(number);
	   
	   if(isBlacklisted)
	   {
		   continue;
	   }
	   
		
	Blacklist theBlacklist=new Blacklist(number);
	theBlacklist.setId(0);
	blacklistService.save(theBlacklist);
	BlacklistRedis theBlacklistRedis=new BlacklistRedis(number);
	blacklistRedisService.save(theBlacklistRedis);
	}
	
	AddRemoveBlacklistNumberSuccess response=new AddRemoveBlacklistNumberSuccess("Successfully blacklisted");
	
	return new ResponseEntity<>(response, HttpStatus.OK);

}
	


@GetMapping("blacklist")
public ResponseEntity<GetBlacklistNumberSuccess> GetBlacklistedNumbers() {
		
		List<BlacklistRedis> result= blacklistRedisService.findAll();
		List<String> temp=new ArrayList<>();
		for(BlacklistRedis e:result)
		{
			temp.add(e.getId());
		}
		GetBlacklistNumberSuccess response=new GetBlacklistNumberSuccess(temp);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
}

	



@DeleteMapping("/blacklist/{number}")
public ResponseEntity<AddRemoveBlacklistNumberSuccess> deleteBlacklistNumber(@PathVariable String number) {
	
	
	List<Blacklist> tempBlacklist=blacklistService.findByNumber(number);
	
	  boolean isBlacklisted=blacklistRedisService.findById(number);
	   
	   if(!isBlacklisted)
	   {

		   throw new NoBlacklistNumberFound("NO Number found");
	   }
	   
	
	if (tempBlacklist == null) {
		throw new RuntimeException("");
	}
	
	Blacklist deletionBlacklist=tempBlacklist.get(0);
	

	blacklistService.deleteById(deletionBlacklist.getId());
	
	blacklistRedisService.deleteById(deletionBlacklist.getBlacklistNumber());
	

	AddRemoveBlacklistNumberSuccess response=new AddRemoveBlacklistNumberSuccess("Removed from blacklist succesfully");
	
	return new ResponseEntity<>(response, HttpStatus.OK);

}


@GetMapping("findEverything")
public Iterable<MessageElasticsearch> findAll() {
	
	return messageElasticsearchService.findAll();
    
}





@GetMapping("SmsContainingGivenText")
public ResponseEntity<ElasticsearchSuccess> SmsContainingGivenText(
		
		@RequestParam(name="text") String text,
		@RequestParam(name="page") int page,
		@RequestParam(name="perPage") int perPage

		
		
		) throws IOException
{

	RestHighLevelClient client=initialiseRestHighLevelClient();
	SearchRequest searchRequest = new SearchRequest("message-details"); 
	SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
	MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("message", text); 
	matchQueryBuilder.fuzziness(Fuzziness.AUTO);;
	sourceBuilder.query(matchQueryBuilder); 
	sourceBuilder.from((page-1)*perPage); 
	sourceBuilder.size(perPage); 
	searchRequest.source(sourceBuilder);
	SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
	
	
	
	
SearchHits hits = searchResponse.getHits();
	SearchHit[] searchHits = hits.getHits();
	List<String> temp=new ArrayList<>();
	for (SearchHit hit : searchHits) {
		Map<String, Object> sourceAsMap = hit.getSourceAsMap();
		String message = (String) sourceAsMap.get("message");
		temp.add(message);
	}


	
	ElasticsearchSuccess response=new ElasticsearchSuccess(temp);
	return new ResponseEntity<>(response, HttpStatus.OK);
}






@GetMapping("SmsSentToNumberBetweenGivenStartAndEndTime")
public ResponseEntity<ElasticsearchSuccess>  smsSentToNumberBetweenGivenStartAndEndTime(@RequestParam(name="number") String number,
		@RequestParam(name="start") String start,
		@RequestParam(name="end") String end,
		
		@RequestParam(name="page") int page,
		@RequestParam(name="perPage") int perPage
		
		
		) throws IOException, ParseException
{
	SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); 
	 long unixStart=formatter.parse(start).getTime();
	 long unixEnd=formatter.parse(end).getTime();
	 
	 
	 
	 
	RestHighLevelClient client=initialiseRestHighLevelClient();
	SearchRequest searchRequest = new SearchRequest("message-details"); 
	SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	boolQueryBuilder
	    .filter(QueryBuilders.termQuery("number", number))
	    .filter(QueryBuilders.rangeQuery("sentAt").gte(unixStart))
	    .filter(QueryBuilders.rangeQuery("sentAt").lte(unixEnd));


	sourceBuilder.query(boolQueryBuilder); 
	sourceBuilder.from((page-1)*perPage); 
	sourceBuilder.size(perPage); 
	searchRequest.source(sourceBuilder);
	SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
	
	
	
	SearchHits hits = searchResponse.getHits();
	SearchHit[] searchHits = hits.getHits();
	List<String> temp=new ArrayList<>();
	for (SearchHit hit : searchHits) {
		Map<String, Object> sourceAsMap = hit.getSourceAsMap();
		String message = (String) sourceAsMap.get("message");
		temp.add(message);
		
	}
	
	
	
	ElasticsearchSuccess response=new ElasticsearchSuccess(temp);
	return new ResponseEntity<>(response, HttpStatus.OK);
}



@ExceptionHandler
public ResponseEntity<SmsRequestError> handleException(PhoneNumberNullException exc) {
	
	InnerSmsRequestError temp=new InnerSmsRequestError();
	
	
	temp.setCode("BAD REQUEST");
	
	temp.setMessage(exc.getMessage());

	SmsRequestError error = new SmsRequestError(temp);
	
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
 }

@ExceptionHandler
public ResponseEntity<SmsRequestError> handleException(PhoneNumberInvalidException exc) {
	
	InnerSmsRequestError temp=new InnerSmsRequestError();
	
	
	temp.setCode("BAD REQUEST");
	
	temp.setMessage(exc.getMessage());

	SmsRequestError error = new SmsRequestError(temp);
	
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
 }

@ExceptionHandler
public ResponseEntity<SmsRequestError> handleException(PhoneNumberBlacklistedException exc) {
	
	InnerSmsRequestError temp=new InnerSmsRequestError();
	
	
	temp.setCode("BAD REQUEST");
	
	temp.setMessage(exc.getMessage());

	SmsRequestError error = new SmsRequestError(temp);
	
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
 }

@ExceptionHandler
public ResponseEntity<GetSmsDetailsError> handleException(IdNotFoundException exc) {
	
	GetSmsDetailsError error = new GetSmsDetailsError();
	
	error.setStatus(HttpStatus.NOT_FOUND.value());
	error.setMessage(exc.getMessage());
	error.setTimeStamp(System.currentTimeMillis());
	
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
 }

@ExceptionHandler
public ResponseEntity<String> handleException(NoBlacklistNumberFound exc) {
	
	String error;
	error=exc.getMessage();
	
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
 }


}
