package Model;

import java.util.HashMap;
import java.util.Objects;

enum Role {
    User, Admin, SuperAdmin
}

public class User {

    private String fname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private HashMap<String, Resource> resources;


    public User(String fname, String lastname, String email, String password, Role role) {
        this.fname = fname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.resources = new HashMap<>();
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


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Role getRole() {
        return role;
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
