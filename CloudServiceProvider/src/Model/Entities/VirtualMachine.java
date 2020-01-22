package Model.Entities;

import java.util.List;
import java.util.Objects;

public class VirtualMachine {
    private String name;
    private String categoryName;
    private String organizationName;
    private boolean active;
    private List<VirtualMachineActivity> activities;

    private transient Category category;
    private transient List<Disk> diskList;

    public VirtualMachine(String name, String categoryName, String organizationName, List<VirtualMachineActivity> activities, Category category, List<Disk> diskList) {
        this.name = name;
        this.categoryName = categoryName;
        this.organizationName = organizationName;
        this.activities = activities;
        this.category = category;
        this.active = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirtualMachine that = (VirtualMachine) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<VirtualMachineActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<VirtualMachineActivity> activities) {
        this.activities = activities;
    }

    public boolean isActive() { return this.active;}

    public void setActive(boolean active) {this.active = active;}

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Disk> getDiskList() {
        return diskList;
    }

    public void setDiskList(List<Disk> diskList) {
        this.diskList = diskList;
    }

    public boolean addDiscIfNotInVirtualMachine(Disk disk) {
        if (!diskList.contains(disk)) {
            diskList.add(disk);
            return true;
        }

        return false;
    }

    public boolean addDisc(Disk disk) {
        if(!diskList.contains(disk)){
            diskList.add(disk);
            return true;
        }

        return false;
    }

    public void removeDisc(Disk disk) {
        if(diskList.contains(disk)){
            diskList.remove(disk);
        }
    }

    public void addActivity(VirtualMachineActivity vac) {
        activities.add(vac);
    }
}
