package Rest.Controlers;

import Model.DatabaseOrWhatEverIDontCareItsSoStupid;
import Model.Entities.User;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;

public class LoginController {
    private static Gson gson = new Gson();
    private static DatabaseOrWhatEverIDontCareItsSoStupid db;

    static {
        try {
            db = DatabaseOrWhatEverIDontCareItsSoStupid.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Route login = (request, response) -> {
        response.type("appliacation/json");
        User u = gson.fromJson(request.body(), User.class);
        String email = u.getEmail();
        String password = u.getPassword();

        User user = db.getUser(email);
        if (user == null) {
            // TODO vrati 400 nesto
            throw new Exception("247 365 021");
        }

        if (user.getPassword().equals(password)) {
            request.session().attribute("user", user);
            return "ulogovao si se brao";
        }

        // TODO nek vrati neki 400
        return "pa nisi bas uspeo";
    };

    public static Route getLoggedInUser = (request, response) -> {
        response.type("appliacation/json");
        return gson.toJson(request.session().attribute("user"), User.class);
    };

}
