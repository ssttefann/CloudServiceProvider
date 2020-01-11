package Rest.Controlers;

import Model.Database;
import Model.Entities.User;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;

public class AuthenticationController {
    private static Gson gson = new Gson();
    private static Database db;

    static {
        try {
            db = Database.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Route login = (request, response) -> {
        response.type("application/json");
        String password = request.queryParams("password");
        String email = request.queryParams("email");

        User user = db.getUser(email);
        if (user == null) {
            return gson.toJson(new User());
        }

        if (user.getPassword().equals(password)) {
            request.session().attribute("user", user);
            return gson.toJson(user);
        }

        return gson.toJson(new User());
    };

    public static Route getLoggedUser = (req, res) -> {
        res.type("application/json");

        if(req.session().attribute("user") != null){
            User user = req.session().attribute("user");
            return gson.toJson(user);
        }
        return gson.toJson(new User());
    };


    public static Route logOut = (req, res) -> {
        res.type("text/plain");
        req.session().invalidate();
        return gson.toJson(new User());
    };


//    public static Route register = (req, res) -> {
//        res.type("text/plain");
//        String password = req.queryParams("password");
//        String email = req.queryParams("email");
//        String name = req.queryParams("name");
//        String lastname = req.queryParams("lastname");
//
//
////        if (users.containsKey(email)) {
////            return "EMAIL_ERR";
////        }
////
////        User user = new User(name, lastname, email, password, Role.User);
////        users.put(user.getEmail(), user);
////        req.session().attribute("user", user);
////        System.out.println(name+" "+lastname+" "+email+" "+password);
////        return "OK";
//    };
}
