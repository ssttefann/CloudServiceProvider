package Model;

public class VirtualMachine extends Resource{

    private VMCategory category;

    public VirtualMachine(String name, VMCategory category) {
        super(name);
        this.category = category;
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


    public VMCategory getCategory() {
        return category;
    }

    public void setCategory(VMCategory category) {
        this.category = category;
    }
}
