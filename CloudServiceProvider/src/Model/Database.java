package Model;

import Model.Entities.*;
import Model.Repositories.*;

import java.io.IOException;
import java.util.ArrayList;
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

    public List<User> getAllUsers() {
        return userRepository.getUsersList();
    }

    public List<User> getUsersOfOrganization(User user) {
        return user.getOrganization().getUsersList();
    }

    public User getUser(String email) {
        return userRepository.getUser(email);
    }

    public boolean addUserToOrganization(User user) throws IOException {
        Organization organization = organizationRepository.getOrganization(user.getOrganizationName());
        user.setOrganization(organization);
        if (userRepository.addUser(user)) {
            organization.addUser(user);
            return true;
        }
        return false;
    }

    public boolean editUser(User user) throws IOException {

        if(userRepository.getUsersIndexedByEmail().containsKey(user.getEmail())){

            User stari = userRepository.getUsersIndexedByEmail().get(user.getEmail());
            stari.setFirstName(user.getFirstName());
            stari.setLastName(user.getLastName());
            stari.setPassword(user.getPassword());
            stari.setRole(user.getRole());
            userRepository.saveUsers();
            return true;
        }

        return false;
    }

    public boolean removeUser(String email) throws IOException {
        return userRepository.removeUser(email);
    }


    public List<Category> getAllCategories() {
        return categoryRepository.getCategoryList();
    }

    public boolean addCategory(Category category) throws IOException {
        return categoryRepository.addIfNameIsUnique(category);
    }

    public boolean removeCategory(String categoryName) throws IOException {
        return categoryRepository.removeIfExists(categoryName);
    }



    public List<Disc> getAllDiscs() {
        return discRepository.getDiscList();
    }

    public List<Disc> getDiscsOfOrganization(String organizationName) {
        Organization organization = organizationRepository.getOrganization(organizationName);
        return organization.getDiscs();
    }

    public boolean addDisc(Disc disc) throws IOException {
        if (discRepository.addDisc(disc)) {
            String virtualMachineName = disc.getVirtualMachineName();
            VirtualMachine virtualMachine = virtualMachineRepository
                    .getVirtualMachine(virtualMachineName);

            virtualMachine.addDisc(disc);
            return true;
        }

        return false;
    }

    public boolean removeDisc(String discName) throws IOException {
        Disc disc = discRepository.getDisc(discName);
        if (disc != null) {
            String virtualMachineName = disc.getVirtualMachineName();
            VirtualMachine virtualMachine = virtualMachineRepository
                    .getVirtualMachine(virtualMachineName);
            virtualMachine.removeDisc(disc);
            discRepository.removeDisc(discName);
            return true;
        }

        return false;
    }

    public List<VirtualMachine> getAllVirtualMachines(){
        return virtualMachineRepository.getVirtualMachineList();
    }

    public List<VirtualMachine> getAllVirtualMachinesOfOrganization(String organizationName){
        Organization organization = organizationRepository.getOrganization(organizationName);
        return organization.getVirtualMachinesList();
    }

    public boolean addVirtualMachine(VirtualMachine virtualMachine) throws IOException {
        Category cat = categoryRepository.getCategoryByName(virtualMachine.getCategory().getName());
        virtualMachine.setCategory(cat);
        virtualMachine.setActivities(new ArrayList<>());
        virtualMachine.setCategoryName(cat.getName());
        return virtualMachineRepository.addVirtualMachine(virtualMachine);
    }

    public boolean removeVirtualMachine(String virtualMachineName) throws IOException {
        return virtualMachineRepository.removeVirtualMachine(virtualMachineName);
    }

    public List<Organization> getAllOrganizations(){
        return organizationRepository.getOrganizationsList();
    }

    public List<Organization> getOrganization(String name) {
        List<Organization> list = new ArrayList<Organization>();
        list.add(organizationRepository.getOrganization(name));
        return list;
    }

    public boolean addOrganization(Organization organization) throws IOException {
        return organizationRepository.addOrganization(organization);
    }

    public boolean removeOrganization(String organizationName) throws IOException {
        return organizationRepository.removeOrganization(organizationName);
    }


}
