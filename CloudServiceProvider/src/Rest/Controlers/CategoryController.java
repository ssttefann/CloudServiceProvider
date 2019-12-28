package Rest.Controlers;

import Model.Database;
import Model.Entities.User;
import Model.Entities.UserRole;
import Model.Repositories.CategoryRepository;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;

public class CategoryController {
    private static Gson gson = new Gson();
    private static Database db;

    static {
        try {
            db = Database.getInstance();
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

        if(user.getRole().equals(UserRole.SuperAdmin) || user.getRole().equals(UserRole.Admin)){
            return gson.toJson(db.getAllCategories());
        }

        response.status(401);
        return "Unauthorized";
    };
}
