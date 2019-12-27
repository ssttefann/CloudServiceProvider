package Rest.Controlers;

import Model.DatabaseOrWhatEverIDontCareItsSoStupid;
import Model.Entities.*;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;

/**
 * Sadrzi metode koje vracaju podatke na osnovu toga koji je korisnik ulogovan
 */
public class UserController {
    private static Gson gson = new Gson();
    private static DatabaseOrWhatEverIDontCareItsSoStupid db;

    static {
        try {
            db = DatabaseOrWhatEverIDontCareItsSoStupid.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Route getAllUsers = (request, response) -> {
        response.type("application/json");
        User user = request.session().attribute("user");
        if (user == null) {
            // TODO vrati 400 nesto
            throw new Exception("nisi ulogovan");
        }

        if (user.getRole().equals(UserRole.SuperAdmin)) {
            return gson.toJson(db.getUsers());
        } else if(user.getRole().equals(UserRole.Admin)){
            return gson.toJson(db.getUsersOfOrganization(user));
        }

        throw new Exception("nemas prava da to uradis");
    };

    public static Route addUser = (request, response) -> {
        response.type("application/json");
        User user = request.session().attribute("user");
        if (user == null) {
            // TODO vrati 400 nesto
            return "nisi ulogovan";
        }

        if (user.getRole().equals(UserRole.User)) {
            // TODO vrati 400 nesto
            return "nemas prava to da radis";
        }

        String userJson = request.body();
        User newUser = gson.fromJson(userJson, User.class);
        if(!db.addUserToOrganizationIfEmailUnique(newUser)){
            // TODO vrati 400 nesto
            return "email vec postoji";
        }

        return "OK";
    };

    public static Route removeUser = (request, response) -> {
        response.type("application/json");
        User user = request.session().attribute("user");
        if (user == null) {
            // TODO vrati 400 nesto
            throw new Exception("nisi ulogovan");
        }

        if (user.getRole().equals(UserRole.User)) {
            // TODO vrati 400 nesto
            throw new Exception("nemas prava to da radis");
        }

        if(!db.removeUserIfExists(user)){
            // TODO vrati 400 nesto
            throw new Exception("user ne postoji");
        }

        return "OK";
    };

    public static Route editUser = (request, response) -> {
        response.type("application/json");
        User user = request.session().attribute("user");
        if (user == null) {
            // TODO vrati 400 nesto
            throw new Exception("nisi ulogovan");
        }

        if (user.getRole().equals(UserRole.User)) {
            // TODO vrati 400 nesto
            throw new Exception("nemas prava to da radis");
        }

        if(!db.editUserIfExists(user)){
            // TODO vrati 400 nesto
            throw new Exception("user ne postoji");
        }

        return "OK";
    };


}
