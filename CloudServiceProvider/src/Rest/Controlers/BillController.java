package Rest.Controlers;

import Model.Database;
import Model.Entities.*;
import com.google.gson.Gson;
import spark.Route;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class BillController {
    private static Gson gson = new Gson();
    private static Database db = Database.getInstance();

    public static Route getBill = (request, response) -> {
        User user = request.session().attribute("user");
        if (user == null || !user.isAdmin()) {
            response.status(401);
            return "Unauthorized";
        }

        long timeStamp1 = Long.parseLong(request.params("startDate"));
        long timeStamp2 = Long.parseLong(request.params("endDate"));

        LocalDateTime startDate =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp1),
                        TimeZone.getDefault().toZoneId());
        LocalDateTime endDate =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp2),
                        TimeZone.getDefault().toZoneId());

        Organization org = user.getOrganization();
        List<BillItem> items = calculatePricesForDisks(org.getDisks(), startDate, endDate);
        items.addAll(calculatePricesForVms(org.getVirtualMachinesList(), startDate, endDate));
        return gson.toJson(items);
    };

    private static List<BillItem> calculatePricesForDisks(List<Disk> disks, LocalDateTime startDate, LocalDateTime endDate) {
        List<BillItem> items = new ArrayList<>();
        for (Disk disk : disks) {
            long activeHours = calculateActiveHoursForDisk(disk, startDate, endDate);
            if (activeHours <= 0) {
                continue;
            }

            double price = calculatePriceForDisk(disk, activeHours);

            String type;
            if (disk.getType().equals(DiscType.HDD)) {
                type = "HDD DISK";
            } else {
                type = "SSD DISK";
            }

            BillItem item = new BillItem(type, disk.getName(), activeHours, price);
            items.add(item);
        }

        return items;
    }

    private static double calculatePriceForDisk(Disk disk, long activeHours) {
        double price;
        if (disk.getType().equals(DiscType.HDD)) {
            price = Prices.hourlyPricePerGBForHDD;
        } else {
            price = Prices.hourlyPricePerGBForSSD;
        }

        return Math.round(activeHours * disk.getCapacity() * price);
    }

    private static long calculateActiveHoursForDisk(Disk disk, LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime start = disk.getTimeCreated();
        LocalDateTime end = disk.getTimeDeleted();

        if (start.isAfter(endDate) || (end != null && end.isBefore(startDate))) {
            return 0;
        }

        if (!disk.isDeleted()) {
            // ako je disk aktivan onda se racuna do kraja
            end = endDate;
        } else {
            if (end != null && end.isAfter(endDate)) {
                end = endDate;
            }
        }

        if (disk.getTimeCreated().isBefore(start)) {
            start = startDate;
        }

        return start.until(end, ChronoUnit.HOURS);
    }

    private static List<BillItem> calculatePricesForVms(List<VirtualMachine> vms, LocalDateTime startDate, LocalDateTime endDate) {
        List<BillItem> items = new ArrayList<>();
        for (VirtualMachine vm : vms) {
            long activeHours = calculateActiveTimeForVm(vm.getActivities(), startDate, endDate);
            if (activeHours <= 0) {
                continue;
            }

            double price = calculatePriceForVm(vm.getCategory(), activeHours);
            BillItem item = new BillItem("Virtual machine", vm.getName(), activeHours, price);
            items.add(item);
        }

        return items;
    }

    private static double calculatePriceForVm(Category category, long activeHours) {
        return Math.round(category.getCores() * Prices.hourlyPricePerCpu * activeHours +
                category.getGPU() * Prices.hourlyPricePerGPU * activeHours +
                category.getRAM() * Prices.hourlyPricePerGBOfRam * activeHours);
    }

    private static long calculateActiveTimeForVm(List<VirtualMachineActivity> activities, LocalDateTime startDate, LocalDateTime endDate) {
        long activeHours = 0;

        for (VirtualMachineActivity activity : activities) {
            if (activity.getStartTime().isAfter(endDate) || activity.getEndTime().isBefore(startDate)) {
                continue;
            }

            LocalDateTime start = activity.getStartTime();
            LocalDateTime end = activity.getEndTime();

            // ako je aktivnost pocela pre trazenog datuma
            if (start.isBefore(startDate)) {
                start = startDate;
            }

            // ako se aktivnost zavrsila nakon trazenog datuma
            if (end.isAfter(endDate)) {
                end = endDate;
            }

            activeHours += start.until(end, ChronoUnit.HOURS);
        }

        return activeHours;
    }

}
