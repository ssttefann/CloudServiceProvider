package Model.Entities;

import com.google.gson.annotations.Expose;

import java.beans.Transient;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Organization {
    private String name;
    private String description;
    private String logo;
    private transient List<User> usersList;
    private transient List<VirtualMachine> virtualMachinesList;

    public Organization(String name, String description, String pathToLogo) {
        this.name = name;
        this.description = description;
        this.logo = pathToLogo;
    }

    public List<Disc> getDiscs() {
        return virtualMachinesList
                .stream()
                .flatMap(virtualMachine -> virtualMachine.getDiscList().stream())
                .collect(Collectors.toList());
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String pathToLogo) {
        this.logo = pathToLogo;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }


    public List<VirtualMachine> getVirtualMachinesList() {
        return virtualMachinesList;
    }

    public void setVirtualMachinesList(List<VirtualMachine> virtualMachinesList) {
        this.virtualMachinesList = virtualMachinesList;
    }

    public void addUser(User user) {
        usersList.add(user);
    }

    public void addVirtualMachine(VirtualMachine virtualMachine) {
        virtualMachinesList.add(virtualMachine);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name);
    }

}
