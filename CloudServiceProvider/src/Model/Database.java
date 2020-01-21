package Model;

import Model.Entities.*;
import Model.Repositories.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Database {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private DiskRepository diskRepository;
    private VirtualMachineRepository virtualMachineRepository;
    private OrganizationRepository organizationRepository;

    private static Database instance;

    public static Database getInstance(){
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    private Database() {
        userRepository = UserRepository.getInstance();
        categoryRepository = CategoryRepository.getInstance();
        diskRepository = DiskRepository.getInstance();
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

    public boolean addUser(User user){
        Organization organization = organizationRepository.getOrganization(user.getOrganizationName());
        user.setOrganization(organization);
        if (userRepository.addUser(user)) {
            organization.addUser(user);
            return true;
        }
        return false;
    }

    public boolean editUser(User user){
        Organization org = organizationRepository.getOrganization(user.getOrganizationName());
        user.setOrganization(org);
        return userRepository.editUser(user);
    }

    public boolean removeUser(String email){
        return userRepository.removeUser(email);
    }


    public List<Category> getAllCategories() {
        return categoryRepository.getCategoryList();
    }

    public boolean addCategory(Category category){
        return categoryRepository.addCategory(category);
    }

    public boolean editCategory(Category editedCategory){
        return categoryRepository.editCategory(editedCategory);
    }


    /**
     * Proverava da li neka vm ima trazenu kategoriju.
     * Poziva se pri brisanju kategorije, ako neka vm ima tu kategoriju
     * onda nemere da se brise ta kategorija
     * @param categoryName
     * @return
     */
    public boolean vmHasCategory(String categoryName) {
        return virtualMachineRepository.getVirtualMachineList()
                .stream()
                .anyMatch(vm -> vm.getCategoryName().equals(categoryName));
    }

    public boolean removeCategory(String categoryName){
        return categoryRepository.removeIfExists(categoryName);
    }


    public List<Disk> getAllDiscs() {
        return diskRepository.getDiskList();
    }

    public List<Disk> getDiscsOfOrganization(String organizationName) {
        Organization organization = organizationRepository.getOrganization(organizationName);
        return organization.getDisks();
    }

    public boolean addDisc(Disk disk){
        if (diskRepository.addDisc(disk)) {
            String virtualMachineName = disk.getVirtualMachineName();
            VirtualMachine virtualMachine = virtualMachineRepository
                    .getVirtualMachine(virtualMachineName);

            // ne mora da izabere vm
            if (virtualMachine != null) {
                virtualMachine.addDisc(disk);
            }
            Organization org = organizationRepository.getOrganization(disk.getOrganizationName());
            org.getDisks().add(disk);
            return true;
        }

        return false;
    }


    public boolean editDisk(Disk disk) {
        // ako je ime prazno znaci da je izbacio disk iz vma
        if (disk.getVirtualMachineName().isEmpty()) {
            VirtualMachine oldVm = virtualMachineRepository.getVirtualMachineByDiskName(disk.getName());

            //samo ako disk ima vm
            if(oldVm != null) {
                oldVm.removeDisc(disk);
            }

        }else{
            changeVmIfVmWasChanged(disk);
        }

        return diskRepository.editDisc(disk);
    }

    public boolean removeDisc(String discName) {
        Disk disk = diskRepository.getDisk(discName);
        if (disk != null) {
            String virtualMachineName = disk.getVirtualMachineName();

            //ako postoji ta vm
            if(virtualMachineRepository.getVirtualMachineByDiskName(virtualMachineName) != null){
                VirtualMachine virtualMachine = virtualMachineRepository
                        .getVirtualMachine(virtualMachineName);
                virtualMachine.removeDisc(disk);
            }

            diskRepository.removeDisk(discName);
            return true;
        }

        return false;
    }

    private void changeVmIfVmWasChanged(Disk disk){
        VirtualMachine vm = virtualMachineRepository.getVirtualMachine(disk.getVirtualMachineName());
        // ako disk ni je u vm to znaci da je promenio vm disku
        boolean diskInVm = vm.getDiskList()
                .stream()
                .anyMatch(d -> d.getName().equals(disk.getName()));

        if (diskInVm) {
            vm.addDisc(disk);
            VirtualMachine oldVm = virtualMachineRepository.getVirtualMachineByDiskName(disk.getName());
            oldVm.removeDisc(disk);
        }
    }

    public List<VirtualMachine> getAllVirtualMachines() {
        return virtualMachineRepository.getVirtualMachineList();
    }

    public boolean addVirtualMachine(VirtualMachine vm){
        String categoryName = vm.getCategoryName();
        Category category = categoryRepository.getCategory(categoryName);
        vm.setCategory(category);
        vm.setActivities(new ArrayList<>());
        vm.setDiskList(new ArrayList<>());
        vm.setCategoryName(category.getName());
        Organization organization = organizationRepository.getOrganization(vm.getOrganizationName());
        organization.addVirtualMachine(vm);
        return virtualMachineRepository.addVirtualMachine(vm);
    }

    public boolean editVirtualMachine(VirtualMachine editedVm) {
        return virtualMachineRepository.editVirtualMachine(editedVm);
    }

    public boolean removeVirtualMachine(String vmName) {
        VirtualMachine vm = virtualMachineRepository.getVirtualMachine(vmName);
        Organization organization = organizationRepository.getOrganization(vm.getOrganizationName());
        deleteVMNameFormDiscs(vmName);
        organization.removeVirtualMachine(vm);
        return virtualMachineRepository.removeVirtualMachine(vmName);
    }

    private void deleteVMNameFormDiscs(String vmName){
        diskRepository.getDiskList().forEach(disc -> {
            if (disc.getVirtualMachineName().equals(vmName)) {
                disc.setVirtualMachineName("");
            }
        });

        diskRepository.saveDisks();
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.getOrganizationsList();
    }

    public List<Organization> getOrganization(String name) {
        List<Organization> list = new ArrayList<Organization>();
        list.add(organizationRepository.getOrganization(name));
        return list;
    }

    public boolean addOrganization(Organization organization) {
        return organizationRepository.addOrganization(organization);
    }

    public boolean editOrganization(Organization newOrganization) {
        return organizationRepository.editOrganization(newOrganization);
    }



}
