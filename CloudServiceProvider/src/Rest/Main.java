package Rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import spark.Request;
import spark.Session;


import Model.*;
import spark.Spark;

public class Main {

    private static Gson g = new Gson();
    private static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) throws Exception {
        port(8080);

        init();

        //mapiramo na vuejs public folder
        staticFiles.externalLocation(new File("CloudServiceProvider/WebContent/app/dist").getCanonicalPath());


        get("/test", (req, res) -> {
            return "Works";
        });

        get("/rest/getUsers/", (req, res) -> {
            res.type("application/json");
            return g.toJson(users);
        });


        get("/rest/LoggedUser/", (req, res) -> {
            res.type("text/plain");

            if(req.session().attribute("user") != null){
                User user = req.session().attribute("user");
                return user.getRole();
            }
            return "ERR";
        });


        get("/rest/logout/", (req, res) -> {
            res.type("text/plain");

            req.session().invalidate();

            return "OK";
        });


        post("/rest/proizvodi/add", (req, res) -> {
            res.type("application/json");
            String payload = req.body();
            //User user = g.fromJson(payload, )
            return ("OK");
        });


        post("/rest/login", (req, res) -> {
            res.type("text/plain");
            String password = req.queryParams("password");
            String email = req.queryParams("email");

            if (!users.containsKey(email)) {
                return "ERR";
            }

            // ako je prosla autentikacija zakaci ga na sesiju i vrati njegovu ulogu
            if (users.get(email).getPassword().equals(password)){
                req.session().attribute("user", users.get(email));
                return users.get(email).getRole();
            }


            return "ERR";
        });

    }


    public static void init() {
        User user = new User("Admin", "Admin", "admin", "admin", Role.Admin);
        users.put(user.getEmail(), user);


        user = new User("a", "a", "a", "a", Role.Admin);
        users.put(user.getEmail(), user);


        user = new User("123", "123", "123", "123", Role.User);
        users.put(user.getEmail(), user);

    }

}
