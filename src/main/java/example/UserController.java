package example;

import annonations.Endpoint;
import annonations.Get;

@Endpoint(route = "/user")
public class UserController {

    @Get
    public void get() {
        System.out.println("I'm in method with reflection!!!");
    }


    public void post() {
        System.out.println("I'm in method with reflection!!!");
    }

}
