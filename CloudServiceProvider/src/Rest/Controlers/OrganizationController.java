package Rest.Controlers;

import Model.Database;
import Model.Entities.Organization;
import Model.Entities.User;
import Model.Entities.UserRole;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;

public class OrganizationController {
    private static Gson gson = new Gson();
    private static Database db;

    static {
        try {
            db = Database.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Route getAllOrganizations = (request, response) -> {
        User user = request.session().attribute("user");
        if(user == null){
            response.status(401);
            return "Unauthorized";
        }

        if(user.isSuperAdmin()){
            return gson.toJson(db.getAllOrganizations());
        }
        else if(user.isAdmin()){
            return gson.toJson(db.getOrganization(user.getOrganizationName()));
        }

        return "[]";
    };

    public static Route addOrganization = (request, response) -> {
        User user = request.session().attribute("user");
        if(user == null || !user.isSuperAdmin()){
            response.status(401);
            return "Unauthorized";
        }

        Organization newOrganization = gson.fromJson(request.body(), Organization.class);
        if(!db.addOrganization(newOrganization)){
            response.status(400);
            return "Name taken";
        }

        return "org added";
    };


    public static Route editOrganization = (request, response) -> {
        User user = request.session().attribute("user");
        if(user == null || user.isUser()){
            response.status(401);
            return "Unauthorized";
        }

        Organization newOrganization = gson.fromJson(request.body(), Organization.class);
        if(!db.editOrganization(newOrganization)){
            response.status(400);
            return "Name doesn't exist.";
        }

        return "Org edited";
    };






}
