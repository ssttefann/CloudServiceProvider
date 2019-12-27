package Model.Entities;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
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
