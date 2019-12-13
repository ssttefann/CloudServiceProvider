package Model;

import java.util.Objects;

public class VMCategory {

    private String name;
    private int cores;
    private int RAM;
    private String GPU;

    public VMCategory(String name, int cores, int RAM, String GPU) {
        this.name = name;
        this.cores = cores;
        this.RAM = RAM;
        this.GPU = GPU;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VMCategory that = (VMCategory) o;
        return name.equals(that.name);
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

    public String getGPU() {
        return GPU;
    }

    public void setGPU(String GPU) {
        this.GPU = GPU;
    }
}
