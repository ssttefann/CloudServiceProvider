package Model2.Repositories;

import Model2.Entities.Organization;
import Model2.Entities.User;
import Model2.Entities.VirtualMachine;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizationRepository {
    private static Gson gson = new Gson();

    private List<Organization> organizationsList;
    private Map<String, Organization> organizationsIndexedByName;

    public OrganizationRepository(){
        organizationsIndexedByName = new HashMap<>();
        organizationsList = new ArrayList<>();
    }

    public Organization getOrganizationByName(String name){
        return organizationsIndexedByName.get(name);
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
}
