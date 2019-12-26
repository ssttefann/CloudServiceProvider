package Model;

import java.util.HashMap;
import java.util.Objects;



public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private HashMap<String, Resource> resources;


    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.resources = new HashMap<>();
    }

    public User() {
        this.firstName = "";
        this.lastName = "";

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {

        if (role == Role.Admin || role == Role.SuperAdmin)
            return "ADMIN";

        return "USER";
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public HashMap<String, Resource> getResources() {
        return resources;
    }

    public void setResources(HashMap<String, Resource> resources) {
        this.resources = resources;
    }
}
