package Rest.Controlers;

import Model.Database;
import Model.Entities.User;
import Model.Entities.UserRole;
import com.google.gson.Gson;
import spark.Request;
import spark.Route;

import java.io.IOException;

public class AuthenticationController {
    private static Gson gson = new Gson();
    private static Database db = Database.getInstance();

    public static User authenticateUser(Request request, UserRole... allowedRoles) {
        User user = request.session().attribute("user");
        if (user == null) {
            return null;
        }

        if(allowedRoles.length == 0){
            return user;
        }

        for (UserRole userRole : allowedRoles) {
            if (user.getRole().equals(userRole)){
                return user;
            }
        }

        return null;
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

        if (req.session().attribute("user") != null) {
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

}
