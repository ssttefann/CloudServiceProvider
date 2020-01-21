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

    private static final String PATH_TO_FILE = "CloudServiceProvider/data/users.json";
    private Map<String, User> usersIndexedByEmail;
    private List<User> usersList;
    private static Gson gson = new Gson();
    private static UserRepository instance = null;

    public static UserRepository getInstance(){
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        usersIndexedByEmail = new HashMap<>();
        usersList = new ArrayList<>();
        try {
            loadUsers();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() throws FileNotFoundException {
        FileReader reader = new FileReader(PATH_TO_FILE);
        Type listType = new TypeToken<ArrayList<User>>() {}.getType();
        usersList = gson.fromJson(reader, listType);
        usersIndexedByEmail = usersList
                .stream()
                .collect(Collectors.toMap(User::getEmail, user -> user, (oldVal, newVal) -> newVal));
    }

    public void saveUsers() {
        try {
            Writer writer = new FileWriter(PATH_TO_FILE);
            gson.toJson(usersList, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String email) {
        return usersIndexedByEmail.get(email);
    }

    public Map<String, User> getUsersIndexedByEmail() {
        return usersIndexedByEmail;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public boolean addUser(User user) {
        String email = user.getEmail();
        if (usersIndexedByEmail.containsKey(email)) {
            return false;
        }

        usersIndexedByEmail.put(email, user);
        usersList.add(user);
        saveUsers();
        return true;
    }

    public boolean removeUser(String email) {
        if (usersIndexedByEmail.containsKey(email)) {
            User user = usersIndexedByEmail.get(email);
            user.getOrganization().getUsersList().remove(user);
            usersList.remove(usersIndexedByEmail.get(email));
            usersIndexedByEmail.remove(email);

            saveUsers();
            return true;
        }

        return false;
    }

    public boolean editUser(User editedUser) {
        String email = editedUser.getEmail();
        if (usersIndexedByEmail.containsKey(email)) {
            User oldUser = usersIndexedByEmail.get(email);
            oldUser.setPassword(editedUser.getPassword());
            oldUser.setFirstName(editedUser.getFirstName());
            oldUser.setLastName(editedUser.getLastName());
            oldUser.setRole(editedUser.getRole());
            oldUser.setLikesDark(editedUser.getLikesDark());
            saveUsers();
            return true;
        }

        return false;
    }
}
