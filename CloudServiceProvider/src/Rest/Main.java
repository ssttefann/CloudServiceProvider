package Rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.IOException;
import java.util.List;

import Model.Entities.Category;
import Model.Entities.Disc;
import Model.Entities.Organization;
import Model.Entities.VirtualMachine;
import Model.Repositories.*;
import Rest.Controlers.UserControler;
import com.google.gson.Gson;
import org.eclipse.jetty.util.Scanner;

public class Main {

    private static Gson gson = new Gson();
    private static CategoryRepository categoryRepository;
    private static UserRepository userRepository;
    private static DiscRepository discRepository;
    private static VirtualMachineRepository virtualMachineRepository;
    private static OrganizationRepository organizationRepository;

    private static void asd() throws IOException {
        OrganizationRepository organizationRepository = OrganizationRepository.getInstance();
        Organization organization = organizationRepository.getOrganizationByName("Organization 2");
        List<Disc> diskovi = organization.getDiscsOfOrganization();
        System.out.println();

    }
    public static void main(String[] args) throws Exception {
        port(8080);
        staticFiles.externalLocation(new File("CloudServiceProvider/WebContent/app/dist").getCanonicalPath());
        asd();
        System.out.println();

        // trenutno vraca ok samo
        get("rest/user/getLoggedUser", UserControler.login);
    }
}
