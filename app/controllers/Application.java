package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import com.amazonaws.*;

public class Application extends Controller {

    public static Result index() {

        return ok("Hello");
    }


}
