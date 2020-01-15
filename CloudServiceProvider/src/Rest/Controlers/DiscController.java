package Rest.Controlers;

import Model.Database;
import Model.Entities.Disc;
import Model.Entities.User;
import Model.Entities.UserRole;
import Model.Repositories.DiscRepository;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;
import java.util.List;

public class DiscController {
    private static Gson gson = new Gson();
    private static Database db;

    static {
        try {
            db = Database.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Route getDiscs = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null) {
            return null;
        }

        List<Disc> discs;
        if (user.isSuperAdmin()) {
            discs = db.getAllDiscs();
        } else {
            discs = db.getDiscsOfOrganization(user.getOrganizationName());
        }

        return gson.toJson(discs);
    };

    public static Route addDisc = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String discJson = request.body();
        Disc newDisc = gson.fromJson(discJson, Disc.class);
        if (!db.addDisc(newDisc)){
            response.status(400);
            return "Disc name already exists";
        }

        return gson.toJson(newDisc);
    };

    public static Route editDisc = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String discJson = request.body();
        Disc editedDisc = gson.fromJson(discJson, Disc.class);
        if(!db.editDisc(editedDisc)){
            response.status(400);
            return "Disc name doesn't exist";
        }

        return gson.toJson(editedDisc);
    };

    public static Route deleteDisc = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String discName = request.params("discname");
        if(!db.removeDisc(discName)){
            response.status(400);
            return "Disc name doesn't exist";
        }

        return "Disc deleted";
    };
}
