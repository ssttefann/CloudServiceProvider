package Rest.Controlers;

import Model.DatabaseOrWhatEverIDontCareItsSoStupid;
import Model.Entities.User;
import Model.Entities.UserRole;
import Model.Repositories.CategoryRepository;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;

public class CategoryController {
    private static Gson gson = new Gson();
    private static DatabaseOrWhatEverIDontCareItsSoStupid db;

    static {
        try {
            db = DatabaseOrWhatEverIDontCareItsSoStupid.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Route getAllCategories = (request, response) -> {
        User user = request.session().attribute("user");
        if(user == null){
            response.status(401);
            return "Unauthorized";
        }

        if(user.getRole().equals(UserRole.SuperAdmin)){
            return gson.toJson(db.getAllCategories());
        }

        response.status(401);
        return "Unauthorized";
    };
}
