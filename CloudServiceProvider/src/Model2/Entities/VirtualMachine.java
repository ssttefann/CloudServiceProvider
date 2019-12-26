package Model2.Entities;

import java.util.List;

public class VirtualMachine {
    private String name;
    private Category category;
    private List<String> discsNamesList;
    private List<VirtualMachineActivity> activities;

    public VirtualMachine(String name, Category category, List<String> discsNamesList, List<VirtualMachineActivity> activities) {
        this.name = name;
        this.category = category;
        this.discsNamesList = discsNamesList;
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getDiscsNamesList() {
        return discsNamesList;
    }

    public void setDiscsNamesList(List<String> discsNamesList) {
        this.discsNamesList = discsNamesList;
    }

    public List<VirtualMachineActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<VirtualMachineActivity> activities) {
        this.activities = activities;
    }
}
