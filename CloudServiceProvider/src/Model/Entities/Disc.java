package Model.Entities;

public class Disc {
    private String name;
    private DiscType type;
    private int capacity;
    private String virtualMachineName;

    public Disc(String name, DiscType type, int capacity, String virtualMachineName) {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getVirtualMachineName() {
        return virtualMachineName;
    }

    public void setVirtualMachineName(String virtualMachineName) {
        this.virtualMachineName = virtualMachineName;
    }
}
