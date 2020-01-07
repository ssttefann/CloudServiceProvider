package Rest.Controlers;

import Model.Database;
import Model.Entities.*;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static Gson gson = new Gson();
    private static Database db;

    static {
        try {
            db = Database.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Route getAllUsers = (request, response) -> {
        response.type("application/json");
        User user = request.session().attribute("user");
        if (user == null) {
            response.status(401);
            return "Unauthorized";
        }

        if (user.getRole().equals(UserRole.SuperAdmin)) {
            return gson.toJson(db.getAllUsers());
        } else if(user.getRole().equals(UserRole.Admin)){
            List<User> var = db.getUsersOfOrganization(user);
            return gson.toJson(db.getUsersOfOrganization(user));
        }

        response.status(401);
        return "Unauthorized";
    };

    public static Route addUser = (request, response) -> {
        response.type("text/plain");
        User user = request.session().attribute("user");
        if (user == null) {
            response.status(401);
            return "Unauthorized";
        }

        if (user.getRole().equals(UserRole.User)) {
            response.status(401);
            return "Unauthorized";
        }

        String userJson = request.body();
        User newUser = gson.fromJson(userJson, User.class);
        if(!db.addUserToOrganization(newUser)){
            return "EMAIL_ERR";
        }

        return "SUCCESS";
    };

    public static Route deleteUser = (request, response) -> {
        response.type("application/json");
        User user = request.session().attribute("user");
        if (user == null) {
            response.status(401);
            return "Unauthorized";
        }

        if (user.getRole().equals(UserRole.User)) {
            response.status(401);
            return "Unauthorized";
        }

        String userJson = request.body();
        User newUser = gson.fromJson(userJson, User.class);
        if(!db.removeUser(newUser.getEmail())){
            response.status(400);
            return "EMAIL_ERR";
        }

        return "SUCCESS";
    };

    public static Route editUser = (request, response) -> {
        response.type("text/plain");
        User user = request.session().attribute("user");
        if (user == null) {
            response.status(401);
            return "Unauthorized";
        }

        if (user.getRole().equals(UserRole.User)) {
            response.status(401);
            return "Unauthorized";
        }

        String userJson = request.body();
        User newUser = gson.fromJson(userJson, User.class);
        if(!db.editUser(newUser)){
            return "EMAIL_ERR";
        }

        return "SUCCESS";
    };

}
