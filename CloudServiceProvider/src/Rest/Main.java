package Rest;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;

import spark.Request;
import spark.Session;


import Model.*;

public class Main {

    private static Gson g = new Gson();
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        port(8080);

        //mapiramo na vuejs public folder
        staticFiles.externalLocation(new File("CloudServiceProvider/WebContent/app/dist").getCanonicalPath());


        get("/test", (req, res) -> {
            return "Works";
        });

        get("/rest/getUsers/", (req, res) -> {
            res.type("application/json");
            return g.toJson(users);
        });

    }

}
