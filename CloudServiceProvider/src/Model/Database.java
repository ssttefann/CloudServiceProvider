package Model;

import Model.Entities.*;
import Model.Repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private DiskRepository diskRepository;
    private VirtualMachineRepository virtualMachineRepository;
    private OrganizationRepository organizationRepository;

    private static Database instance;

    public static Database getInstance() {
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

    public Collection<User> getAllUsers() {
        return userRepository.getUsersList();
    }

    public List<User> getUsersOfOrganization(User user) {
        return user.getOrganization().getUsersList();
    }

    public User getUser(String email) {
        return userRepository.getUser(email);
    }

    public boolean addUser(User user) {
        Organization organization = organizationRepository.getOrganization(user.getOrganizationName());
        user.setOrganization(organization);
        if (userRepository.addUser(user)) {
            organization.addUser(user);
            return true;
        }
        return false;
    }

    public boolean editUser(User user) {
        Organization org = organizationRepository.getOrganization(user.getOrganizationName());
        user.setOrganization(org);
        return userRepository.editUser(user);
    }

    public boolean removeUser(String email) {
        return userRepository.removeUser(email);
    }


    public Collection<Category> getAllCategories() {
        return categoryRepository.getCategoryList();
    }

    public boolean addCategory(Category category) {
        return categoryRepository.addCategory(category);
    }

    public boolean editCategory(Category editedCategory) {
        return categoryRepository.editCategory(editedCategory);
    }


    /**
     * Proverava da li neka vm ima trazenu kategoriju.
     * Poziva se pri brisanju kategorije, ako neka vm ima tu kategoriju
     * onda nemere da se brise ta kategorija
     *
     * @param categoryName
     * @return
     */
    public boolean vmHasCategory(String categoryName) {
        return virtualMachineRepository.getVirtualMachineList()
                .stream()
                .anyMatch(vm -> vm.getCategoryName().equals(categoryName));
    }

    public boolean removeCategory(String categoryName) {
        return categoryRepository.removeIfExists(categoryName);
    }


    public Collection<Disk> getAllDiscs() {
        return diskRepository.getDiskList()
                .stream()
                .filter(disk -> !disk.isDeleted())
                .collect(Collectors.toList());
    }

    public List<Disk> getDiscsOfOrganization(String organizationName) {
        Organization organization = organizationRepository.getOrganization(organizationName);
        return organization.getDisks()
                .stream()
                .filter(disk -> !disk.isDeleted())
                .collect(Collectors.toList());
    }

    public boolean addDisc(Disk disk) {
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
            if (oldVm != null) {
                oldVm.removeDisc(disk);
            }

        } else {
            changeVmIfVmWasChanged(disk);
        }

        return diskRepository.editDisc(disk);
    }

    public boolean removeDisc(String discName) {
        Disk disk = diskRepository.getDisk(discName);
        if (disk != null) {
            String virtualMachineName = disk.getVirtualMachineName();

            //ako postoji ta vm
            if (virtualMachineRepository.getVirtualMachineByDiskName(virtualMachineName) != null) {
                VirtualMachine virtualMachine = virtualMachineRepository
                        .getVirtualMachine(virtualMachineName);
                virtualMachine.removeDisc(disk);
            }

            diskRepository.removeDisk(discName);
            return true;
        }

        return false;
    }

    private void changeVmIfVmWasChanged(Disk disk) {
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

    public VirtualMachine getVM(String vmName) {
        return virtualMachineRepository.getVirtualMachine(vmName);
    }

    public Collection<VirtualMachine> getAllVirtualMachines() {
        return virtualMachineRepository.getVirtualMachineList()
                .stream()
                .filter(vm -> !vm.isDeleted())
                .collect(Collectors.toList());
    }

    public Collection<VirtualMachine> getOrganizationVirtualMachines(String organizationName) {
        Organization organization = organizationRepository.getOrganization(organizationName);
        if (organization == null){
            return new ArrayList<>();
        }

        return organization.getVirtualMachinesList()
                .stream()
                .filter(vm -> !vm.isDeleted())
                .collect(Collectors.toList());
    }

    public boolean addVirtualMachine(VirtualMachine vm) {
        String categoryName = vm.getCategoryName();
        Category category = categoryRepository.getCategory(categoryName);
        vm.setCategory(category);
        vm.setActivities(new ArrayList<>());
        vm.setDiskList(new ArrayList<>());
        vm.setCategoryName(category.getName());
        Organization organization = organizationRepository.getOrganization(vm.getOrganizationName());
        organization.addVirtualMachine(vm);
        if(virtualMachineRepository.addVirtualMachine(vm)){
            turnOnVm(vm.getName());
            return true;
        }
        return false;
    }

    public boolean editVirtualMachine(VirtualMachine editedVm) {
        return virtualMachineRepository.editVirtualMachine(editedVm);
    }

    public boolean removeVirtualMachine(String vmName) {
        VirtualMachine vm = virtualMachineRepository.getVirtualMachine(vmName);
        Organization organization = organizationRepository.getOrganization(vm.getOrganizationName());
        deleteVMNameFormDiscs(vmName);
        // ako nije ukljucena onda se nista nece desiti
        turnOffVm(vmName);
        return virtualMachineRepository.removeVirtualMachine(vmName);
    }

    private void deleteVMNameFormDiscs(String vmName) {
        diskRepository.getDiskList().forEach(disc -> {
            if (disc.getVirtualMachineName().equals(vmName)) {
                disc.setVirtualMachineName("");
            }
        });

        diskRepository.saveDisks();
    }

    public List<VirtualMachineActivity> getActivitiesForVm(String vmName) {
        VirtualMachine vm = virtualMachineRepository.getVirtualMachine(vmName);
        return vm.getActivities();
    }

    public boolean turnOnVm(String vmName) {
        VirtualMachine vm = virtualMachineRepository.getVirtualMachine(vmName);
        if (vm == null || vm.isActive()) {
            return false;
        }

        vm.setActive(true);
        LocalDateTime startTime = LocalDateTime.now();
        String id = vm.getName() + startTime.toString();
        VirtualMachineActivity activity = new VirtualMachineActivity(startTime, null, id);
        vm.addActivity(activity);
        virtualMachineRepository.saveVirtualMachines();
        return true;
    }

    public boolean turnOffVm(String vmName) {
        VirtualMachine vm = virtualMachineRepository.getVirtualMachine(vmName);
        if (vm == null || !vm.isActive()) {
            return false;
        }

        vm.setActive(false);
        List<VirtualMachineActivity> activities = vm.getActivities();
        VirtualMachineActivity activity = activities.get(activities.size() - 1);
        LocalDateTime endTime = LocalDateTime.now();
        activity.setEndTime(endTime);
        virtualMachineRepository.saveVirtualMachines();
        return true;
    }

    public Collection<Organization> getAllOrganizations() {
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
