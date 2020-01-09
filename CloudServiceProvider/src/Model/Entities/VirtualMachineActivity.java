package Model.Entities;

import java.time.LocalDateTime;

public class VirtualMachineActivity {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public VirtualMachineActivity(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public VirtualMachineActivity(LocalDateTime startTime) {
        this.startTime = startTime;
        this.endTime = null;
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
}
