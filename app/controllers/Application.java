package controllers;

import java.util.List;
import java.util.Random;
import play.mvc.Controller;
import play.mvc.Result;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import javax.inject.Inject;
import play.Configuration;

public class Application extends Controller {
	AmazonS3 s3Client;
	List<S3ObjectSummary> photos;
	Configuration configuration;
	String bucket_name = "cescocats";

	@Inject
	public Application(Configuration config) {
		this.configuration = config;
		BasicAWSCredentials creds = new BasicAWSCredentials(configuration.getString("aws.access.key"),
				configuration.getString("aws.secret.key"));
		this.s3Client = AmazonS3ClientBuilder.standard().withRegion("sa-east-1")
				.withCredentials(new AWSStaticCredentialsProvider(creds)).build();
		this.photos = s3Client.listObjects(this.bucket_name).getObjectSummaries();
	}

	public Result root() {
		return ok(this.bucket_name);
	}

	public Result cat() {
		System.out.println("sdkjfnskdf");
		return redirect(this.s3Client.generatePresignedUrl(this.bucket_name,
				this.photos.get(new Random().nextInt(this.photos.size())).getKey(), null).toString());
	}

}
