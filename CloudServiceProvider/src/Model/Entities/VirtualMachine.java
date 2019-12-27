package Model.Entities;

import com.google.gson.annotations.Expose;

import java.util.List;

public class VirtualMachine {
    private String name;
    private String categoryName;
    private String organizationName;
    private List<VirtualMachineActivity> activities;

    @Expose
    private Category category;
    @Expose
    private List<Disc> discList;

    public VirtualMachine(String name, String categoryName, String organizationName, List<VirtualMachineActivity> activities, Category category, List<Disc> discList) {
        this.name = name;
        this.categoryName = categoryName;
        this.organizationName = organizationName;
        this.activities = activities;
        this.category = category;
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
}
