package Model.Entities;


import java.util.List;
import java.util.Objects;

public class Organization {
    private String name;
    private String description;
    private String logo;
    private transient List<User> usersList;
    private transient List<VirtualMachine> virtualMachinesList;
    private transient  List<Disk> disks;

    public Organization(String name, String description, String pathToLogo) {
        this.name = name;
        this.description = description;
        this.logo = pathToLogo;
    }

    public List<Disk> getDisks() { return disks; }

    public void setDisks(List<Disk> disks) {this.disks = disks;}

    public void addDisc(Disk disk) {this.disks.add(disk);}

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

    public void removeVirtualMachine(VirtualMachine vm) {
        virtualMachinesList.remove(vm);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name);
    }


}
