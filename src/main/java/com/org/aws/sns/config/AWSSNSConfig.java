package com.org.aws.sns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class AWSSNSConfig
{
	private final String accessKey;
	private final String secretKey;

	public AWSSNSConfig(@Value("${sns.access.key}") String accessKey, @Value("${sns.secret.key}") String secretKey)
	{
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	@Primary
	@Bean
	AmazonSNSClient getSnsClient()
	{
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard()
				.withRegion(Regions.AP_SOUTH_1)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
	}
}
