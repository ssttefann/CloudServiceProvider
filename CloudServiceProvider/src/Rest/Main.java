package Rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;

import Model.Entities.Category;
import Model.Entities.VirtualMachine;
import Model.Repositories.*;
import com.google.gson.Gson;
import org.eclipse.jetty.util.Scanner;

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

        get("/rest/test", (request, response) -> "OK");
    }
}
