package Rest.Controlers;

import Model.Entities.Disc;
import Model.Entities.User;
import Model.Entities.UserRole;
import Model.Repositories.DiscRepository;
import com.google.gson.Gson;
import spark.Route;

import java.util.List;

public class DiscController {
    private static Gson gson = new Gson();

    public static Route getDiscs = (request, response) -> {
        User user = request.session().attribute("user");
        if(user == null){
            // TODO vrati 400 nesto
//            throw new Exception("nisi ulogovan");
            return null;
        }

        List<Disc> discs;
        if(user.getRole().equals(UserRole.SuperAdmin)){
            DiscRepository discRepository = DiscRepository.getInstance();
            discs = discRepository.getDiscList();
        } else {
            discs = user.getOrganization().getDiscsOfOrganization();
        }

        return gson.toJson(discs);
    };
}
