package Model;

import Model.Entities.*;
import Model.Repositories.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        return userRepository.editUser(user);
    }

    public boolean removeUser(String email) throws IOException {
        return userRepository.removeUser(email);
    }


    public List<Category> getAllCategories() {
        return categoryRepository.getCategoryList();
    }

    public List<Category> getCategoriesForVms(List<String> categoryNameList) {
        Set<Category> result = categoryNameList
                .stream()
                .map(cat -> categoryRepository.getCategory(cat))
                .collect(Collectors.toSet());

        return new ArrayList<>(result);
    }

    public boolean addCategory(Category category) throws IOException {
        return categoryRepository.addCategory(category);
    }

    public boolean editCategory(Category editedCategory) throws IOException {
        return categoryRepository.editCategory(editedCategory);
    }


    /**
     * Proverava da li neka vm ima trazenu kategoriju.
     * @param categoryName
     * @return
     */
    public boolean vmHasCategory(String categoryName) {
        return virtualMachineRepository.getVirtualMachineList()
                .stream()
                .anyMatch(vm -> vm.getCategoryName().equals(categoryName));
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

    public boolean editDisc(Disc disc) throws IOException {
        return discRepository.editDisc(disc);
    }

    public List<VirtualMachine> getAllVirtualMachines() {
        return virtualMachineRepository.getVirtualMachineList();
    }

    public List<VirtualMachine> getAllVirtualMachinesOfOrganization(String organizationName) {
        Organization organization = organizationRepository.getOrganization(organizationName);
        return organization.getVirtualMachinesList();
    }

    public boolean addVirtualMachine(VirtualMachine vm) throws IOException {
        String categoryName = vm.getCategory().getName();
        Category category = categoryRepository.getCategory(categoryName);
        vm.setCategory(category);
        vm.setActivities(new ArrayList<>());
        vm.setDiscList(new ArrayList<>());
        vm.setCategoryName(category.getName());
        Organization organization = organizationRepository.getOrganization(vm.getOrganizationName());
        organization.addVirtualMachine(vm);
        return virtualMachineRepository.addVirtualMachine(vm);
    }

    public boolean removeVirtualMachine(String vmName) throws IOException {
        VirtualMachine vm = virtualMachineRepository.getVirtualMachine(vmName);
        Organization organization = organizationRepository.getOrganization(vm.getOrganizationName());
        deleteVMNameFormDiscs(vmName);
        organization.removeVirtualMachine(vm);
        return virtualMachineRepository.removeVirtualMachine(vmName);
    }

    private void deleteVMNameFormDiscs(String vmName){
        discRepository.getDiscList().forEach(disc -> {
            if (disc.getVirtualMachineName().equals(vmName)){
                disc.setVirtualMachineName("");
            }
        });
    }

    public List<Organization> getAllOrganizations() {
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

    public boolean editOrganization(Organization newOrganization) throws IOException {
        return organizationRepository.editOrganization(newOrganization);
    }


    public boolean editVirtualMachine(VirtualMachine editedVm) throws IOException {
        return virtualMachineRepository.editVirtualMachine(editedVm);
    }



}
