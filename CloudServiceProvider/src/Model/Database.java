package Model;

import Model.Entities.Category;
import Model.Entities.Organization;
import Model.Entities.User;
import Model.Repositories.*;

import java.io.IOException;
import java.util.List;

public class Database {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private DiscRepository discRepository;
    private VirtualMachineRepository virtualMachineRepository;
    private OrganizationRepository organizationRepository;

    private static Database instance;

    public static Database getInstance() throws IOException {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    private Database() throws IOException {
        userRepository = UserRepository.getInstance();
        categoryRepository = CategoryRepository.getInstance();
        discRepository = DiscRepository.getInstance();
        virtualMachineRepository = VirtualMachineRepository.getInstance();
        organizationRepository = OrganizationRepository.getInstance();
    }

    public List<User> getUsers() {
        return userRepository.getUsersList();
    }


    public List<User> getUsersOfOrganization(User user) {
        return user.getOrganization().getUsersList();
    }

    public boolean addUserToOrganizationIfEmailUnique(User user) throws IOException {
        Organization organization = organizationRepository.getOrganizationByName(user.getOrganizationName());
        user.setOrganization(organization);
        if (userRepository.addUser(user)) {
            organization.addUser(user);
            return true;
        }
        return false;
    }

    public boolean removeUserIfExists(User user) throws IOException {
        return userRepository.removeUser(user);
    }

    public boolean editUserIfExists(User user) throws IOException {
        return userRepository.editUser(user);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getCategoryList();
    }

    public boolean addCategory(Category category) {
        return false;
    }

    public boolean removeCategory(Category category) {
        return false;
    }

    public boolean editCategory(Category category) {
        return false;
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.getOrganizationsList();
    }

    public User getUser(String email) {
        return userRepository.getUser(email);
    }
}
