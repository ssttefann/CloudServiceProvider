package Rest.Controlers;

import Model.Database;
import Model.Entities.Disk;
import Model.Entities.User;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class DiskController {
    private static Gson gson = new Gson();
    private static Database db = Database.getInstance();
    public static Route getDiscs = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null) {
            return null;
        }

        Collection<Disk> disks;
        if (user.isSuperAdmin()) {
            disks = db.getAllDiscs();
        } else {
            disks = db.getDiscsOfOrganization(user.getOrganizationName());
        }

        return gson.toJson(disks);
    };

    public static Route addDisc = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String discJson = request.body();
        Disk newDisk = gson.fromJson(discJson, Disk.class);
        if (!db.addDisc(newDisk)){
            response.status(400);
            return "Disc with that name already exists";
        }

        return gson.toJson(newDisk);
    };

    public static Route editDisc = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String discJson = request.body();
        Disk editedDisk = gson.fromJson(discJson, Disk.class);
        if(!db.editDisk(editedDisk)){
            response.status(400);
            return "Disc with that name doesn't exist";
        }

        return gson.toJson(editedDisk);
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
            return "Disc with that name doesn't exist";
        }

        return "Disc successfully deleted";
    };
}
