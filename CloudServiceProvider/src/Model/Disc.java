package Model;

enum DiskType {
    SSD, HDD
}

public class Disc extends Resource {

    private int capacity;
    private DiskType type;

    public Disc(String name, int capacity, DiskType type) {
        super(name);
        this.capacity = capacity;
        this.type = type;
    }

    @Override
    public double getMonthlyPrice() {
        return super.getMonthlyPrice();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public DiskType getType() {
        return type;
    }

    public void setType(DiskType type) {
        this.type = type;
    }
}
