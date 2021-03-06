package Rest.Controlers;

import Model.Database;
import Model.Entities.Organization;
import Model.Entities.User;
import Model.Entities.UserRole;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class OrganizationController {
    private static Gson gson = new Gson();
    private static Database db = Database.getInstance();

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
            return "Organization with that name already exists!";
        }

        return "Organization succesfully added";
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
            return "Organization with that name already exists!";
        }

        return "Organization successfully edited";
    };
}
