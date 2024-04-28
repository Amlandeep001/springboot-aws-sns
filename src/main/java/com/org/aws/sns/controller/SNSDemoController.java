package com.org.aws.sns.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

@RestController
public class SNSDemoController
{
	private final AmazonSNSClient snsClient;
	private final String TOPIC_ARN;

	public SNSDemoController(AmazonSNSClient snsClient, @Value("${sns.arn.topic}") String TOPIC_ARN)
	{
		this.snsClient = snsClient;
		this.TOPIC_ARN = TOPIC_ARN;
	}

	@GetMapping("/addSubscription/{email}")
	public String addSubscription(@PathVariable String email)
	{
		SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, "email", email);
		snsClient.subscribe(request);
		return "Subscription request is pending. To confirm the subscription, check your email : " + email;
	}

	@GetMapping("/sendNotification")
	public String publishMessageToTopic()
	{
		PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, buildEmailBody(), "Notification: Network connectivity issue");
		snsClient.publish(publishRequest);
		return "Notification sent successfully !!";
	}

	private String buildEmailBody()
	{
		return "Dear Employee ,\n" +
				"\n" +
				"\n" +
				"Connection down in Kolkata." + "\n" +
				"All the servers in Kolkata Data center are not accessible. We are working on it ! \n" +
				"Notification will be sent out as soon as the issue is resolved. For any questions regarding this message please feel free to contact IT Service Support team";
	}
}
