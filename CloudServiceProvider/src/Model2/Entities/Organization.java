package Model2.Entities;

import java.util.List;

public class Organization {
    private String name;
    private String description;
    private String pathToLogo;
    private List<String> usersNamesList;
    private List<String> virtualMachinesNamesList;

    public Organization(String name, String description, String pathToLogo, List<String> usersNamesList, List<String> virtualMachinesNamesList) {
        this.name = name;
        this.description = description;
        this.pathToLogo = pathToLogo;
        this.usersNamesList = usersNamesList;
        this.virtualMachinesNamesList = virtualMachinesNamesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathToLogo() {
        return pathToLogo;
    }

    public void setPathToLogo(String pathToLogo) {
        this.pathToLogo = pathToLogo;
    }

    public List<String> getUsersNamesList() {
        return usersNamesList;
    }

    public void setUsersNamesList(List<String> usersNamesList) {
        this.usersNamesList = usersNamesList;
    }

    public List<String> getVirtualMachinesNamesList() {
        return virtualMachinesNamesList;
    }

    public void setVirtualMachinesNamesList(List<String> virtualMachinesNamesList) {
        this.virtualMachinesNamesList = virtualMachinesNamesList;
    }
}
