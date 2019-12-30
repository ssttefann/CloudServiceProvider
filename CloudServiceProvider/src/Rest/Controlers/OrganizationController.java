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

        if(user.getRole().equals(UserRole.SuperAdmin)){
            return gson.toJson(db.getAllOrganizations());
        }

        response.status(401);
        return "Unauthorized";
    };

    public static Route addOrganization = (request, response) -> {
        response.type("application/type");
        Organization newOrganization = gson.fromJson(request.body(), Organization.class);

        if(db.addOrganization(newOrganization)){
            return "Success";
        }

        response.status(403);
        return "Organization name already exists";
    };

    public static Route removeOrganization = (request, response) -> {
        response.type("application/type");

        String organizationName = request.queryParams("organizationName");

        if(organizationName == null){
            response.status(403);
            return "Organization name doesn't exist";
        }

        if (db.removeOrganization(organizationName)) {
            return "Success";
        }

        response.status(403);
        return "Organization name doesn't exist";
    };

    public static Route editOrganization = (request, response) -> {
        return "editOrganization not yet impolemented";
    };






}
