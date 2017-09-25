package controllers;

import java.util.List;
import java.util.Random;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import play.Configuration;
import play.mvc.Result;


public class S3CatsBucket {
	public AmazonS3 s3Client;
	public List<S3ObjectSummary> photos;
    Configuration configuration;
	public String bucket_name = "cescocats";
	
	public S3CatsBucket(Configuration configuration){
		String AS = configuration.getString("aws.access.key");
		String AK = configuration.getString("aws.secret.key");
		BasicAWSCredentials creds = new BasicAWSCredentials(AS, AK); 
		this.s3Client = AmazonS3ClientBuilder.standard()
			.withRegion("sa-east-1") 
			.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
		this.photos = s3Client.listObjects(this.bucket_name).getObjectSummaries();
	}
	
	public S3ObjectSummary random(){
		return this.photos.get(new Random().nextInt(this.photos.size()));
	}

	public String randomCatURL(){
		return this.s3Client
			.generatePresignedUrl(this.bucket_name, this.random().getKey(), null).toString();

	}
}
