package Model.Repositories;

import Model.Entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {

    private static final String PATH_TO_USERS = "CloudServiceProvider/data/users.json";
    private Map<String, User> usersIndexedByEmail;
    private List<User> usersList;
    private static Gson gson = new Gson();

    /** singleton */
    private static UserRepository instance = null;
    public static UserRepository getInstance() throws FileNotFoundException {
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() throws FileNotFoundException {
        usersIndexedByEmail = new HashMap<>();
        usersList = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() throws FileNotFoundException {
        FileReader reader = new FileReader(PATH_TO_USERS);
        Type listType = new TypeToken<ArrayList<User>>() {}.getType();
        usersList = gson.fromJson(reader, listType);
        usersIndexedByEmail = usersList
                .stream()
                .collect(Collectors.toMap(User::getEmail, user -> user, (oldVal, newVal) -> newVal));
    }

    private void saveUsers(){

    }

    public User getUserByEmail(String email){
        return usersIndexedByEmail.get(email);
    }

    public Map<String, User> getUsersIndexedByEmail() {
        return usersIndexedByEmail;
    }

    public List<User> getUsersList() {
        return usersList;
    }
}
