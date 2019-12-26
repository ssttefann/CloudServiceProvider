package Model2.Repositories;

import Model2.Entities.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {
    private Map<String, User> usersIndexedByEmail;
    private List<User> usersList;
    private static Gson gson = new Gson();

    public UserRepository() {
        usersIndexedByEmail = new HashMap<>();
        usersList = new ArrayList<>();
    }

    public User getUserByEmail(String email){
        return usersIndexedByEmail.get(email);
    }

    public List<User> getUsersByOrganization(String organizationName){
        return usersList.stream()
                .filter(user -> user.getOrganizationName().equals(organizationName))
                .collect(Collectors.toList());
    }

    public Map<String, User> getUsersIndexedByEmail() {
        return usersIndexedByEmail;
    }

    public void setUsersIndexedByEmail(Map<String, User> usersIndexedByEmail) {
        this.usersIndexedByEmail = usersIndexedByEmail;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}
