package Model.Entities;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String organizationName;
    private UserRole role;

    private transient Organization organization;

    public User(String email, String firstName, String lastName, String organizationName, UserRole role, Organization organization) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organizationName = organizationName;
        this.role = role;
        this.organization = organization;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
