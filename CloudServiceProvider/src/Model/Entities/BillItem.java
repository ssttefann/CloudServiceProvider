package Model.Entities;

public class BillItem {
    private String type;
    private String name;
    private long activeHours;
    private double price;

    public BillItem(String type, String name, long activeHours, double price) {
        this.type = type;
        this.name = name;
        this.activeHours = activeHours;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getActiveHours() {
        return activeHours;
    }

    public void setActiveHours(long activeHours) {
        this.activeHours = activeHours;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
