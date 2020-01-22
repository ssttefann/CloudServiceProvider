package Model.Entities;

import java.time.LocalDateTime;

public class VirtualMachineActivity {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String id;

    public VirtualMachineActivity(LocalDateTime startTime, LocalDateTime endTime, String id) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
