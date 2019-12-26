package Model2.Entities;

import java.time.LocalDateTime;

public class VirtualMachineActivity {
    private LocalDateTime virtualMachineTurnedOnAt;
    private LocalDateTime virtualMachineTurnedOfAt;

    public VirtualMachineActivity(LocalDateTime virtualMachineTurnedOnAt, LocalDateTime virtualMachineTurnedOfAt) {
        this.virtualMachineTurnedOnAt = virtualMachineTurnedOnAt;
        this.virtualMachineTurnedOfAt = virtualMachineTurnedOfAt;
    }

    public LocalDateTime getVirtualMachineTurnedOnAt() {
        return virtualMachineTurnedOnAt;
    }

    public void setVirtualMachineTurnedOnAt(LocalDateTime virtualMachineTurnedOnAt) {
        this.virtualMachineTurnedOnAt = virtualMachineTurnedOnAt;
    }

    public LocalDateTime getVirtualMachineTurnedOfAt() {
        return virtualMachineTurnedOfAt;
    }

    public void setVirtualMachineTurnedOfAt(LocalDateTime virtualMachineTurnedOfAt) {
        this.virtualMachineTurnedOfAt = virtualMachineTurnedOfAt;
    }
}
