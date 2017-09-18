package controllers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import play.Configuration;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {


    private Configuration configuration;

    @Inject
    public Application(Configuration config) {
        this.configuration = config;
    }

	public Result root() {
		return ok( configuration.getString("cesco"));
	}

    public Result kitty() {
		BasicAWSCredentials creds = new BasicAWSCredentials("AKIAJ7OWUPJ7ANREIBVA", "th8UJkoydxYnCtX/fqh8TdPLU7EOtIIykQ8OKNLb"); 
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
