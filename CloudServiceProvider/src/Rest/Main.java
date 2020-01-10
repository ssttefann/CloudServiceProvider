package Rest;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.before;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Model.Entities.Disc;
import Model.Entities.Organization;
import Model.Repositories.*;
import Rest.Controlers.*;
import com.google.gson.Gson;

public class Main {

    private static Gson gson = new Gson();
    private static CategoryRepository categoryRepository;
    private static UserRepository userRepository;
    private static DiscRepository discRepository;
    private static VirtualMachineRepository virtualMachineRepository;
    private static OrganizationRepository organizationRepository;

    public static void main(String[] args) throws Exception {
        port(8080);
        staticFiles.externalLocation(new File("CloudServiceProvider/WebContent/app/dist").getCanonicalPath());
        System.out.println();

        // Login
        post("/rest/login/", LoginController.login);
        get("/rest/loggedUser/", LoginController.getLoggedUser);
        get("/rest/logout/", LoginController.logOut);

        //Korisnici
        get("/rest/users/getAll/", UserController.getAllUsers);
        post("/rest/users/add/", UserController.addUser);
        post("/rest/users/edit/", UserController.editUser);
        post("/rest/users/delete/", UserController.deleteUser);

        //Virtuelne masine
        get("/rest/VMs/getVMs/", VirtualMachineController.getVirtualMachines);
        post("/rest/VMs/addVM/", VirtualMachineController.addVirtualMachine);
        post("/rest/VMs/editVM/", VirtualMachineController.getVirtualMachines);
        post("/rest/VMs/deleteVM/", VirtualMachineController.getVirtualMachines);


        //Diskovi
        get("/rest/discs/getDiscs/", DiscController.getDiscs);
        post("/rest/discs/add/", DiscController.addDisc);
        post("/rest/discs/edit/", DiscController.editDisc);
        delete("/rest/discs/delete/:discname", DiscController.deleteDisc);

        //Kategorije
        get("/rest/categories/getCategories/", CategoryController.getAllCategories);

        //Organizacije
        get("/rest/organizations/getOrganizations/", OrganizationController.getAllOrganizations);
        post("/rest/organizations/addOrganization", OrganizationController.addOrganization);
        post("/rest/organizations/removeOrganization", OrganizationController.removeOrganization);

        //redirect
        get("/*", (req, res) -> {
            res.redirect("/#/" + req.splat()[0]);
            return "OK";
        });




    }
}
