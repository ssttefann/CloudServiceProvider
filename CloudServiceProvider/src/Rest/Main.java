package Rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
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
        post("/rest/login", LoginController.login);
        get("/rest/loggedUser/", LoginController.getLoggedUser);
        get("/rest/logout/", LoginController.logOut);

        //Korisnici
        get("/rest/users/getAll/", UserController.getAllUsers);
        post("/rest/users/add/", UserController.addUser);

        //Virtuelne masine
        get("/rest/getVMs/", VirtualMachineController.getVirtualMachines);

        //Diskovi
        get("/rest/getDiscs/", DiscController.getDiscs);

        //Kategorije
        get("/rest/getCategories/", CategoryController.getAllCategories);

        //Organizacije
        get("/rest/getOrganizations/", OrganizationController.getAllOrganizations);
        post("/rest/addOrganization", OrganizationController.addOrganization);
        post("/rest/removeOrganization", OrganizationController.removeOrganization);

        //redirect
        get("/*", (req, res) -> {
            res.redirect("/#/" + req.splat()[0]);
            return "OK";
        });

    }
}
