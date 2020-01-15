package Rest.Controlers;

import Model.Database;
import Model.Entities.*;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;
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
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        if (user.isSuperAdmin()) {
            return gson.toJson(db.getAllUsers());
        } else {
            List<User> var = db.getUsersOfOrganization(user);
            return gson.toJson(db.getUsersOfOrganization(user));
        }
    };

    public static Route addUser = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String userJson = request.body();
        User newUser = gson.fromJson(userJson, User.class);
        if(!db.addUser(newUser)){
            response.status(400);
            return "EMAIL_ERR";
        }

        return "SUCCESS";
    };

    public static Route deleteUser = (request, response) -> {
        response.type("application/json");
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String email = request.params("email");
        if(!db.removeUser(email)){
            response.status(400);
            return "EMAIL_ERR";
        }

        return "SUCCESS";
    };

    public static Route editUser = (request, response) -> {
        response.type("text/plain");
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String userJson = request.body();
        User newUser = gson.fromJson(userJson, User.class);
        if(!db.editUser(newUser)){
            response.status(400);
            return "EMAIL_ERR";
        }

        return "SUCCESS";
    };

}
