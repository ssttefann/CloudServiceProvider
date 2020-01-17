package Rest;

import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.before;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.staticFiles;

import java.io.*;

import Model.Repositories.*;
import Rest.Controlers.*;
import com.google.gson.Gson;

public class Main {

    private static Gson gson = new Gson();
    private static CategoryRepository categoryRepository;
    private static UserRepository userRepository;
    private static DiskRepository diskRepository;
    private static VirtualMachineRepository virtualMachineRepository;
    private static OrganizationRepository organizationRepository;

    public static void main(String[] args) throws Exception {
        port(8080);
        staticFiles.externalLocation(new File("CloudServiceProvider/WebContent/app/dist").getCanonicalPath());
        System.out.println();

        post("/rest/uploadIcon/:fileName", (request, response) -> {
            String s = request.body();
            String tupan = request.params("fileName");
            byte[] b = request.bodyAsBytes();
            InputStream is = new ByteArrayInputStream(b);
            try (FileOutputStream fos = new FileOutputStream("CloudServiceProvider/data/images/" + tupan)) {
                fos.write(b);
            }
            return "";
        });

        get("rest/getIcon/:fileName", (request, response) -> {
            String fileName = request.params("fileName");
            File file = new File("CloudServiceProvider/data/images/" + fileName);
            try(FileInputStream ins = new FileInputStream(file)){
                long kurac = file.length();
                byte[] res = new byte[(int) kurac];
                int size = ins.read(res);
                response.type("image/image/jpeg");
                return res;
            } catch(Exception e){
                response.status(400);
                return "No file with that name";
            }
        });

        // Login
        post("/rest/login/", AuthenticationController.login);
        get("/rest/loggedUser/", AuthenticationController.getLoggedUser);
        post("/rest/logout/", AuthenticationController.logOut);

        //Korisnici
        get("/rest/users/getAll/", UserController.getAllUsers);
        post("/rest/users/add/", UserController.addUser);
        post("/rest/users/edit/", UserController.editUser);
        post("/rest/users/update/", UserController.updateUserAccount);
        delete("/rest/users/delete/:email", UserController.deleteUser);

        //Virtuelne masine
        get("/rest/VMs/getAll/", VirtualMachineController.getVirtualMachines);
        post("/rest/VMs/add/", VirtualMachineController.addVirtualMachine);
        post("/rest/VMs/edit/", VirtualMachineController.editVirtualMachines);
        delete("/rest/VMs/delete/:vmName", VirtualMachineController.deleteVirtualMachines);


        //Diskovi
        get("/rest/discs/getDiscs/", DiskController.getDiscs);
        post("/rest/discs/add/", DiskController.addDisc);
        post("/rest/discs/edit/", DiskController.editDisc);
        delete("/rest/discs/delete/:discname", DiskController.deleteDisc);

        //Kategorije
        get("/rest/categories/getCategories/", CategoryController.getAllCategories);
        post("/rest/categories/add/", CategoryController.addCategory);
        post("/rest/categories/edit/", CategoryController.editCategory);
        delete("/rest/categories/delete/:categoryName", CategoryController.deleteCategory);

        //Organizacije
        get("/rest/organizations/getOrganizations/", OrganizationController.getAllOrganizations);
        post("/rest/organizations/addOrganization", OrganizationController.addOrganization);
        post("/rest/organizations/edit", OrganizationController.editOrganization);

        //redirect
        get("/*", (req, res) -> {
            res.redirect("/#/" + req.splat()[0]);
            return "OK";
        });


    }
}
