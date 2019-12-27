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
import Rest.Controlers.LoginController;
import Rest.Controlers.UserController;
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

        post("/rest/login", LoginController.login);
        get("/rest/getLoggedIn", LoginController.getLoggedInUser);

        get("/rest/users/getAll", UserController.getAllUsers);
        post("/rest/users/add", UserController.addUser);

    }
}
