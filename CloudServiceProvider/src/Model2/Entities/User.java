package Model2.Entities;

enum UserRole{
    User, Admin, SuperAdmin
}

public class User {
    private String email;
    private String firstName;
    private String secondName;
    private String organizationName;
    private UserRole role;

    public User(String email, String firstName, String secondName, String organizationName, UserRole role) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.organizationName = organizationName;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
