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

import controllers.S3CatsBucket;
import javax.inject.Inject;
import play.Configuration;

public class Application extends Controller {
	S3CatsBucket aws;

	@Inject
	public Application(Configuration config) {
		this.aws = new S3CatsBucket(config);
	}

	public Result root() {
		return ok(this.aws.bucket_name);
	}

	public Result cat() {
		return redirect(this.aws.randomCatURL());
	}

}
