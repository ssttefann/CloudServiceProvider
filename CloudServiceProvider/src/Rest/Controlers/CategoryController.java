package Rest.Controlers;

import Model.Database;
import Model.Entities.Category;
import Model.Entities.User;
import Model.Entities.UserRole;
import Model.Entities.VirtualMachine;
import Model.Repositories.CategoryRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Route;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        if (user == null || !user.isSuperAdmin()) {
            response.status(401);
            return "Unauthorized";
        }

        Category newCategory = gson.fromJson(request.body(), Category.class);
        if (!db.addCategory(newCategory)) {
            response.status(400);
            return "Category name already exists";
        }

        return "Category added";
    };

    public static Route editCategory = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || !user.isSuperAdmin()) {
            response.status(401);
            return "Unauthorized";
        }

        Category editedCategory = gson.fromJson(request.body(), Category.class);
        if (!db.editCategory(editedCategory)) {
            response.status(400);
            return "Category name doesn't exists";
        }

        return "Category eddited";
    };
    public static Route deleteCategory = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || !user.isSuperAdmin()) {
            response.status(401);
            return "Unauthorized";
        }

        String categoryName = request.params("categoryName");
        if (!db.removeCategory(categoryName)) {
            response.status(400);
            return "Category name doesn't exists";
        }

        return "Category deleted";
    };

    public static Route getCategoriesForVms = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null) {
            response.status(401);
            return "Unauthorized";
        }
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> categoryNameList = gson.fromJson(request.body(), listType);
        return gson.toJson(db.getCategoriesForVms(categoryNameList));
    };
}
