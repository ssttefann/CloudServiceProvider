package Rest.Controlers;

import Model.Database;
import Model.Entities.User;
import Model.Entities.UserRole;
import Model.Entities.VirtualMachine;
import Model.Repositories.VirtualMachineRepository;
import com.google.gson.Gson;
import spark.Route;

import java.io.IOException;
import java.util.List;

public class VirtualMachineController {
    private static Gson gson = new Gson();

    private static Database db = Database.getInstance();

    public static Route getVirtualMachines = (request, response) -> {
        User user = request.session().attribute("user");
        if(user == null){
            response.status(401);
            return "Unauthorized";
        }

        List<VirtualMachine> virtualMachines;
        if(user.isSuperAdmin()){
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
        if(!db.addVirtualMachine(newVM)){
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
        if(!db.editVirtualMachine(editedVm)){
            response.status(400);
            return "Vm name doesn't exist";
        }
        return "SUCCESS";
    };

    public static Route deleteVirtualMachines = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || user.isUser()) {
            response.status(401);
            return "Unauthorized";
        }

        String vmName = request.params("vmName");
        if(!db.removeVirtualMachine(vmName)){
            response.status(400);
            return "Vm with that name doesn't exist";
        }

        return "Vm deleted";

    };

}


