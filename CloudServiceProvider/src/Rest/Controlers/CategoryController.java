package Rest.Controlers;

import Model.Database;
import Model.Entities.Category;
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
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        return gson.toJson(db.getAllCategories());
    };

    public static Route addCategory = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        Category newCategory = gson.fromJson(request.body(), Category.class);
        if(!db.addCategory(newCategory)){
            response.status(400);
            return "Category name already exists";
        }

        return "Category added";
    };

    public static Route editCategory;
    public static Route deleteCategory;
}
