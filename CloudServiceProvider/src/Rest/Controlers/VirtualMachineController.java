package Rest.Controlers;

import Model.Database;
import Model.Entities.User;
import Model.Entities.UserRole;
import Model.Entities.VirtualMachine;
import Model.Entities.VirtualMachineActivity;
import Model.Repositories.VirtualMachineRepository;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class VirtualMachineController {
    private static Gson gson = new Gson();

    private static Database db = Database.getInstance();

    public static Route getVirtualMachines = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null) {
            response.status(401);
            return "Unauthorized";
        }

        Collection<VirtualMachine> virtualMachines;
        if (user.isSuperAdmin()) {
            virtualMachines = db.getAllVirtualMachines();
        } else {
            virtualMachines = user.getOrganization().getVirtualMachinesList();
        }

        return gson.toJson(virtualMachines);
    };


    public static Route addVirtualMachine = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String vmJson = request.body();
        VirtualMachine newVM = gson.fromJson(vmJson, VirtualMachine.class);
        if (!db.addVirtualMachine(newVM)) {
            response.status(400);
            return "ERR";
        }

        return "SUCCESS";
    };

    public static Route editVirtualMachines = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String vmJson = request.body();
        VirtualMachine editedVm = gson.fromJson(vmJson, VirtualMachine.class);
        if (!db.editVirtualMachine(editedVm)) {
            response.status(400);
            return "Vm name doesn't exist";
        }
        return "SUCCESS";
    };

    public static Route deleteVirtualMachine = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String vmName = request.params("vmName");
        if (!db.removeVirtualMachine(vmName)) {
            response.status(400);
            return "Vm with that name doesn't exist";
        }

        return "Vm deleted";

    };

    public static Route getActivitiesForVm = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String vmName = request.params("vmName");
        List<VirtualMachineActivity> res = db.getActivitiesForVm(vmName);
        return gson.toJson(res);
    };

    public static Route turnOnVirtualMachine = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || !user.isAdmin()) {
            response.status(403);
            return "Unauthorized";
        }

        String vmName = request.params("vmName");
        if(!db.turnOnVm(vmName)){
            response.status(400);
            return "Vm already on";
        }

        return gson.toJson(db.getVM(vmName));
    };

    public static Route turnOffVirtualMachines = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || !user.isAdmin()) {
            response.status(403);
            return "Unauthorized";
        }

        String vmName = request.params("vmName");
        if(!db.turnOffVm(vmName)){
            response.status(400);
            return "Vm already off";
        }

        return gson.toJson(db.getVM(vmName));
    };

}


