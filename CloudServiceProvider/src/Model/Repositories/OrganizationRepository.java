package Model.Repositories;

import Model.Entities.Organization;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class OrganizationRepository {
    private static final String PATH_TO_FILE = "CloudServiceProvider/data/organizations.json";
    private static Gson gson = new Gson();

    private Map<String, Organization> organizationsIndexedByName;

    /**
     * singleton
     */
    private static OrganizationRepository instance;

    public static OrganizationRepository getInstance(){
        if (instance == null) {
            instance = new OrganizationRepository();
        }

        return instance;
    }

    private OrganizationRepository() {
        organizationsIndexedByName = new LinkedHashMap<>();
        loadOrganizations();
        initializeUserAndVmLists();
        connectOrganizationsWithUsers();
        connectOrganizationsWithDiscs();
        connectOrganizationsWithVirtualMachines();
    }

    private void loadOrganizations(){
        try {
            FileReader reader = new FileReader(PATH_TO_FILE);
            Type listType = new TypeToken<ArrayList<Organization>>() {
            }.getType();
            List<Organization> organizationsList = gson.fromJson(reader, listType);
            organizationsIndexedByName = organizationsList.stream()
                    .collect(Collectors.toMap(Organization::getName, org -> org, (oldValue, newValue) -> newValue));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveOrganizations(){
        try {
            Writer writer = new FileWriter(PATH_TO_FILE);
            gson.toJson(getOrganizationsList(), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mora da se inicijalizuju ove dve liste ovde
     * a ne u konstruktoru jer ih gson postavi na null
     */
    private void initializeUserAndVmLists() {
        getOrganizationsList().forEach(organization -> {
            organization.setVirtualMachinesList(new ArrayList<>());
            organization.setUsersList(new ArrayList<>());
            organization.setDisks(new ArrayList<>());
        });
    }

    private void connectOrganizationsWithUsers() {
        UserRepository userRepository = UserRepository.getInstance();
        userRepository.getUsersList().forEach(user -> {
            // preskace samo jednu iteraciju, ne izlazi iz fje
            if (user.isSuperAdmin()) return;
            String organizationName = user.getOrganizationName();
            Organization organization = organizationsIndexedByName.get(organizationName);
            organization.addUser(user);
            user.setOrganization(organization);
        });
    }

    private void connectOrganizationsWithDiscs() {
        DiskRepository disceRepository = DiskRepository.getInstance();
        disceRepository.getDiskList().forEach(disc -> {
            String organizationName = disc.getOrganizationName();
            Organization organization = organizationsIndexedByName.get(organizationName);
            organization.addDisc(disc);
        });
    }

    private void connectOrganizationsWithVirtualMachines(){
        VirtualMachineRepository virtualMachineRepository = VirtualMachineRepository.getInstance();
        virtualMachineRepository.getVirtualMachineList().forEach(virtualMachine -> {
            String organizationName = virtualMachine.getOrganizationName();
            Organization organization = organizationsIndexedByName.get(organizationName);
            organization.addVirtualMachine(virtualMachine);
        });
    }

    public Organization getOrganization(String organizationName) {
        return organizationsIndexedByName.get(organizationName);
    }

    public Collection<Organization> getOrganizationsList() {
        return organizationsIndexedByName.values();
    }

    public void setOrganizationsIndexedByName(Map<String, Organization> organizationsIndexedByName) {
        this.organizationsIndexedByName = organizationsIndexedByName;
    }

    public boolean addOrganization(Organization organization) {
        String organizationName = organization.getName();
        if (!organizationsIndexedByName.containsKey(organizationName)) {
            organizationsIndexedByName.put(organizationName, organization);
            organization.setDisks(new ArrayList<>());
            organization.setUsersList(new ArrayList<>());
            organization.setVirtualMachinesList(new ArrayList<>());
            saveOrganizations();
            return true;
        }

        return false;
    }

    public boolean removeOrganization(String organizationName) {
        if (organizationsIndexedByName.containsKey(organizationName)) {
            organizationsIndexedByName.remove(organizationName);
            saveOrganizations();
            return true;
        }

        return false;
    }

    public boolean editOrganization(Organization newOrganization) {
        String organizationName = newOrganization.getName();
        if (organizationsIndexedByName.containsKey(organizationName)) {
            Organization oldOrganization = organizationsIndexedByName.get(organizationName);
            oldOrganization.setDescription(newOrganization.getDescription());
            oldOrganization.setLogo(newOrganization.getLogo());
            saveOrganizations();
            return true;
        }

        return false;
    }
}
