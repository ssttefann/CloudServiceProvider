package Rest.Controlers;

import Model.Database;
import Model.Entities.*;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;

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
            return gson.toJson(db.getUsersOfOrganization(user));
        }

        response.status(401);
        return "Unauthorized";
    };

    public static Route addUser = (request, response) -> {
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
        if(!db.addUserToOrganization(newUser)){
            response.status(400);
            return "Email vec posotji";
        }

        return "OK";
    };

    public static Route removeUser = (request, response) -> {
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

        if(!db.removeUser(user.getEmail())){
            response.status(400);
            return "Korisnik sa tim emailom ne postoji";
        }

        return "OK";
    };

//    public static Route editUser = (request, response) -> {
//        response.type("application/json");
//        User user = request.session().attribute("user");
//        if (user == null) {
//            response.status(401);
//            return "Unauthorized";
//        }
//
//        if (user.getRole().equals(UserRole.User)) {
//            response.status(401);
//            return "Unauthorized";
//        }
//
//        if(!db.editUserIfExists(user)){
//            response.status(400);
//            return "Korisnik sa tim emailom ne postoji.";
//        }
//
//        return "OK";
//    };

}
