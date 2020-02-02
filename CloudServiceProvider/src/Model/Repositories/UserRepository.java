package Model.Repositories;

import Model.Entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepository {

    private static final String PATH_TO_FILE = "CloudServiceProvider/data/users.json";
    private Map<String, User> usersIndexedByEmail;
    private static Gson gson = new Gson();
    private static UserRepository instance = null;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        usersIndexedByEmail = new LinkedHashMap<>();
        loadUsers();
    }

    private void loadUsers() {
        try {
            FileReader reader = new FileReader(PATH_TO_FILE);
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> usersList = gson.fromJson(reader, listType);
            usersIndexedByEmail = usersList
                    .stream()
                    .collect(Collectors.toMap(User::getId, user -> user, (oldVal, newVal) -> newVal));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        try {
            Writer writer = new FileWriter(PATH_TO_FILE);
            gson.toJson(getUsersList(), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String id) {
        return usersIndexedByEmail.get(id);
    }

    public User getUserByEmail(String email) {
        for(User user : usersIndexedByEmail.values()){
            if (user.getEmail().equals(email)){
                return user;
            }
        }

        return null;
    }

    public Map<String, User> getUsersIndexedByEmail() {
        return usersIndexedByEmail;
    }

    public Collection<User> getUsersList() {
        return usersIndexedByEmail.values();
    }

    public boolean addUser(User user) {
        String email = user.getEmail();
        // ako user nije null znaci da je email zauzet
        if(getUserByEmail(email) != null){
            return false;
        }

        user.setId(UUID.randomUUID());
        usersIndexedByEmail.put(user.getId(), user);
        saveUsers();
        return true;
    }

    public boolean removeUser(String email) {

        User user = getUserByEmail(email);
        // je user null znaci da ne postoji taj email u sistemu
        if (user != null){
            user.getOrganization().getUsersList().remove(user);
            usersIndexedByEmail.remove(user.getId());

            saveUsers();
            return true;
        }

        return false;
    }

    public boolean editUser(User editedUser) {

        User user = getUserByEmail(editedUser.getEmail());
        //ako postoji user sa emailom koji nije ovaj po id-u
        if (user != null && !user.getId().equals(editedUser.getId())){
            return false;
        }

        String id = editedUser.getId();
        if (usersIndexedByEmail.containsKey(id)) {
            User oldUser = usersIndexedByEmail.get(id);
            oldUser.setEmail(editedUser.getEmail());
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
