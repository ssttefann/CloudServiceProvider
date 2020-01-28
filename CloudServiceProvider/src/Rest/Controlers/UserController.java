package Rest.Controlers;

import Model.Database;
import Model.Entities.*;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;
import java.util.List;

public class UserController {
    private static Gson gson = new Gson();
    private static Database db = Database.getInstance();

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
            return "User with that email already exists!";
        }

        return "New User succesfully added";
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
            return "User with that email doesn't exists!";
        }

        return "User succesfully deleted";
    };

    public static Route updateUserAccount = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null) {
            response.status(401);
            return "Unauthorized";
        }

        String userJson = request.body();
        User editedUser = gson.fromJson(userJson, User.class);
        // ako nije menjao sifru
        if (editedUser.getPassword().isEmpty()){
            editedUser.setPassword(user.getPassword());
        }

        request.session().attribute("user", editedUser);

        if(!db.editUser(editedUser)){
            response.status(400);
            return "User with that email doesn't exists!";
        }

        return gson.toJson(editedUser);
    };

    public static Route editUser = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null) {
            response.status(401);
            return "Unauthorized";
        }

        String userJson = request.body();
        User editedUser = gson.fromJson(userJson, User.class);
        if(!db.editUser(editedUser)){
            response.status(400);
            return "User with that email doesn't exists!";
        }

        return "User succesfully edited!";
    };

}
