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
        if (user.getRole().equals(UserRole.SuperAdmin)) {
            discs = db.getAllDiscs();
        } else {
            discs = db.getDiscsOfOrganization(user.getOrganizationName());
        }

        return gson.toJson(discs);
    };

    public static Route addDisc = (request, response) -> {
        User user = request.session().attribute("user");

        if (user == null || user.getRole().equals(UserRole.User)) {
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
}
