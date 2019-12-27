package Model.Entities;

public class Category {
    private String name;
    private int numberOfCPUCores;
    private int ramInGb;
    private int numberOfGPUCores;

    public Category(String name, int numberOfCPUCores, int ramInGb, int numberOfGPUCores) {
        this.name = name;
        this.numberOfCPUCores = numberOfCPUCores;
        this.ramInGb = ramInGb;
        this.numberOfGPUCores = numberOfGPUCores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfCPUCores() {
        return numberOfCPUCores;
    }

    public void setNumberOfCPUCores(int numberOfCPUCores) {
        this.numberOfCPUCores = numberOfCPUCores;
    }

    public int getRamInGb() {
        return ramInGb;
    }

    public void setRamInGb(int ramInGb) {
        this.ramInGb = ramInGb;
    }

    public int getNumberOfGPUCores() {
        return numberOfGPUCores;
    }

    public void setNumberOfGPUCores(int numberOfGPUCores) {
        this.numberOfGPUCores = numberOfGPUCores;
    }
}
