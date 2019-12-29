package Model.Entities;

import java.util.List;
import java.util.Objects;

public class VirtualMachine {
    private String name;
    private String categoryName;
    private String organizationName;
    private List<VirtualMachineActivity> activities;

    private Category category;
    private transient List<Disc> discList;

    public VirtualMachine(String name, String categoryName, String organizationName, List<VirtualMachineActivity> activities, Category category, List<Disc> discList) {
        this.name = name;
        this.categoryName = categoryName;
        this.organizationName = organizationName;
        this.activities = activities;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Disc> getDiscList() {
        return discList;
    }

    public void setDiscList(List<Disc> discList) {
        this.discList = discList;
    }

    public boolean addDiscIfNotInVirtualMachine(Disc disc) {
        if (!discList.contains(disc)) {
            discList.add(disc);
            return true;
        }

        return false;
    }

    public boolean addDisc(Disc disc) {
        if(!discList.contains(disc)){
            discList.add(disc);
            return true;
        }

        return false;
    }

    public void removeDisc(Disc disc) {
        if(discList.contains(disc)){
            discList.remove(disc);
        }
    }
}
