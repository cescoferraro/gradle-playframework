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
import play.Logger;

public class Application extends Controller {


    private Configuration configuration;

    @Inject
    public Application(Configuration config) {
        this.configuration = config;
    }

	public Result root() {
		System.out.println(configuration.getString("aws.access.key"));
		return ok( configuration.getString("aws.access.key"));
	}

    public Result kitty() {
		String AK = configuration.getString("aws.access.key");
		String AS =		configuration.getString("aws.secret.key");
		System.out.println(AK);
		System.out.println(AS);
		BasicAWSCredentials creds = new BasicAWSCredentials(AK, AS); 
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
			.withRegion("sa-east-1") .withCredentials(new AWSStaticCredentialsProvider(creds)).build();
        return redirect(Hey(s3Client));
    }

	private String Hey(AmazonS3 s3client){
    	String bucket_name = "cescocats";
        List<S3ObjectSummary> objects = s3client.listObjects(bucket_name).getObjectSummaries();
		return s3client.generatePresignedUrl(bucket_name, objects.get(new Random().nextInt(objects.size())).getKey(), null).toString();
	}
}
