package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
    public static Result index() {
        return ok("Hello world");
    }

    public static Result Cesco() {
		System.out.println(45444);
        return ok("33");
    }

}
