package Model.Entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Disk {
    private String name;
    private DiscType type;
    private int capacity;
    private String organizationName;
    private LocalDateTime timeCreated;
    private LocalDateTime timeDeleted;
    private String virtualMachineName;
    private boolean deleted;


    public Disk(String name, DiscType type, int capacity, String virtualMachineName, boolean deleted) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.virtualMachineName = virtualMachineName;
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disk disk = (Disk) o;
        return Objects.equals(name, disk.name);
    }

    public LocalDateTime getTimeDeleted() {
        return timeDeleted;
    }

    public void setTimeDeleted(LocalDateTime timeDeleted) {
        this.timeDeleted = timeDeleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
