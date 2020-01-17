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

    private List<Organization> organizationsList;
    private Map<String, Organization> organizationsIndexedByName;

    /**
     * singleton
     */
    private static OrganizationRepository instance;

    public static OrganizationRepository getInstance() throws IOException {
        if (instance == null) {
            instance = new OrganizationRepository();
        }

        return instance;
    }

    private OrganizationRepository() throws IOException {
        organizationsIndexedByName = new HashMap<>();
        organizationsList = new ArrayList<>();
        loadOrganizations();
        initializeUserAndVmLists();
        connectOrganizationsWithUsers();
        connectOrganizationsWithDiscs();
        connectOrganizationsWithVirtualMachines();
    }

    private void loadOrganizations() throws FileNotFoundException {
        FileReader reader = new FileReader(PATH_TO_FILE);
        Type listType = new TypeToken<ArrayList<Organization>>() {
        }.getType();
        organizationsList = gson.fromJson(reader, listType);
        organizationsIndexedByName = organizationsList.stream()
                .collect(Collectors.toMap(Organization::getName, org -> org, (oldValue, newValue) -> newValue));
    }

    private void saveOrganizations() throws IOException {
        Writer writer = new FileWriter(PATH_TO_FILE);
        gson.toJson(organizationsList, writer);
        writer.flush();
        writer.close();
    }

    /**
     * Mora da se inicijalizuju ove dve liste ovde
     * a ne u konstruktoru jer ih gson postavi na null
     */
    private void initializeUserAndVmLists() {
        organizationsList.forEach(organization -> {
            organization.setVirtualMachinesList(new ArrayList<>());
            organization.setUsersList(new ArrayList<>());
            organization.setDisks(new ArrayList<>());
        });
    }

    private void connectOrganizationsWithUsers() throws IOException {
        UserRepository userRepository = UserRepository.getInstance();
        userRepository.getUsersList().forEach(user -> {
            String organizationName = user.getOrganizationName();
            Organization organization = organizationsIndexedByName.get(organizationName);
            organization.addUser(user);
            user.setOrganization(organization);
        });
    }

    private void connectOrganizationsWithDiscs() throws IOException {
        DiskRepository disceRepository = DiskRepository.getInstance();
        disceRepository.getDiskList().forEach(disc -> {
            String organizationName = disc.getOrganizationName();
            Organization organization = organizationsIndexedByName.get(organizationName);
            organization.addDisc(disc);
        });
    }

    private void connectOrganizationsWithVirtualMachines() throws IOException {
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

    public List<Organization> getOrganizationsList() {
        return organizationsList;
    }

    public void setOrganizationsList(List<Organization> organizationsList) {
        this.organizationsList = organizationsList;
    }

    public Map<String, Organization> getOrganizationsIndexedByName() {
        return organizationsIndexedByName;
    }

    public void setOrganizationsIndexedByName(Map<String, Organization> organizationsIndexedByName) {
        this.organizationsIndexedByName = organizationsIndexedByName;
    }

    public boolean addOrganization(Organization organization) throws IOException {
        String organizationName = organization.getName();
        if (!organizationsIndexedByName.containsKey(organizationName)) {
            organizationsIndexedByName.put(organizationName, organization);
            organizationsList.add(organization);
            saveOrganizations();
            return true;
        }

        return false;
    }

    public boolean removeOrganization(String organizationName) throws IOException {
        if (organizationsIndexedByName.containsKey(organizationName)) {
            organizationsList.remove(organizationsIndexedByName.get(organizationName));
            organizationsIndexedByName.remove(organizationName);
            saveOrganizations();
            return true;
        }

        return false;
    }

    public boolean editOrganization(Organization newOrganization) throws IOException {
        String organizationName = newOrganization.getName();
        if (organizationsIndexedByName.containsKey(organizationName)) {
            organizationsIndexedByName.put(organizationName, newOrganization);
            organizationsList.remove(newOrganization);
            organizationsList.add(newOrganization);
            saveOrganizations();
            return true;
        }

        return false;
    }
}
