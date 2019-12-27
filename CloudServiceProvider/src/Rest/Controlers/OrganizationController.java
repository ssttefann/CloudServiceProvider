package Rest.Controlers;

import Model.DatabaseOrWhatEverIDontCareItsSoStupid;
import Model.Entities.User;
import Model.Entities.UserRole;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;

public class OrganizationController {
    private static Gson gson = new Gson();
    private static DatabaseOrWhatEverIDontCareItsSoStupid db;

    static {
        try {
            db = DatabaseOrWhatEverIDontCareItsSoStupid.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Route getAllOrganizations = (request, response) -> {
        User user = request.session().attribute("user");
        if(user == null){
            // TODO vrati 400 nesto
            throw new Exception("nisi ulogovan");
        }

        if(user.getRole().equals(UserRole.SuperAdmin)){
            return gson.toJson(db.getAllOrganizations());
        }

        // TODO : vrati neki 400
        throw new Exception("Nema prava da to uradis");
    };

    public static Route addOrganization = (request, response) -> {
        return "addOrganization not yet impolemented";
    };

    public static Route removeOrganization = (request, response) -> {
        return "removeOrganization not yet impolemented";
    };

    public static Route editOrganization = (request, response) -> {
        return "editOrganization not yet impolemented";
    };






}
