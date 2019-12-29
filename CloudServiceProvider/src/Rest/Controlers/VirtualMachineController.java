package Rest.Controlers;

import Model.Entities.User;
import Model.Entities.UserRole;
import Model.Entities.VirtualMachine;
import Model.Repositories.VirtualMachineRepository;
import com.google.gson.Gson;
import spark.Route;

import java.util.List;

public class VirtualMachineController {
    private static Gson gson = new Gson();

    public static Route getVirtualMachines = (request, response) -> {
        User user = request.session().attribute("user");
        if(user == null){
            // TODO vrati 400 nesto
//            throw new Exception("nisi ulogovan");
            return null;
        }

        List<VirtualMachine> virtualMachines;
        if(user.getRole().equals(UserRole.SuperAdmin)){
            VirtualMachineRepository virtualMachineRepository = VirtualMachineRepository.getInstance();
            virtualMachines = virtualMachineRepository.getVirtualMachineList();
        } else {
            virtualMachines = user.getOrganization().getVirtualMachinesList();
        }

        return gson.toJson(virtualMachines);
    };
}
