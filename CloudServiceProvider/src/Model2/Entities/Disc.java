package Model2.Entities;

enum DiscType{
    SSD, HDD
}

public class Disc {
    private String name;
    private DiscType type;
    private float capacity;
    private String virtualMachineName;

    public Disc(String name, DiscType type, float capacity, String virtualMachineName) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.virtualMachineName = virtualMachineName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscType getType() {
        return type;
    }

    public void setType(DiscType type) {
        this.type = type;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public String getVirtualMachineName() {
        return virtualMachineName;
    }

    public void setVirtualMachineName(String virtualMachineName) {
        this.virtualMachineName = virtualMachineName;
    }
}
