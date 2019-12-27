package Model.Entities;

public class Category {
    private String name;
    private int cores;
    private int RAM;
    private int GPU;

    public Category(String name, int cores, int RAM, int GPU) {
        this.name = name;
        this.cores = cores;
        this.RAM = RAM;
        this.GPU = GPU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public int getRAM() {
        return RAM;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    public int getGPU() {
        return GPU;
    }

    public void setGPU(int GPU) {
        this.GPU = GPU;
    }
}
