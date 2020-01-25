package Model.Repositories;

import Model.Entities.Category;
import Model.Entities.Disk;
import Model.Entities.VirtualMachine;
import Model.Entities.VirtualMachineActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class VirtualMachineRepository {
    private static Gson gson = new Gson();
    private static final String PATH_TO_FILE = "CloudServiceProvider/data/vm.json";

    private Map<String, VirtualMachine> virtualMachinesIndexedByName;
    private static VirtualMachineRepository instance;

    public static VirtualMachineRepository getInstance() {
        if (instance == null) {
            instance = new VirtualMachineRepository();
        }
        return instance;
    }

    private VirtualMachineRepository() {
        virtualMachinesIndexedByName = new LinkedHashMap<>();
        loadVirtualMachines();
        initializeDiscList();
        connectVirtualMachinesAndDiscs();
        connectVirtualMachinesAndCategory();
    }

    private void loadVirtualMachines() {
        try {
            Reader reader = new FileReader(PATH_TO_FILE);
            Type listType = new TypeToken<ArrayList<VirtualMachine>>() {
            }.getType();
            List<VirtualMachine> virtualMachineList = gson.fromJson(reader, listType);
            virtualMachinesIndexedByName = virtualMachineList.stream()
                    .collect(Collectors.toMap(VirtualMachine::getName, virtualMachine -> virtualMachine, (oldValue, newValue) -> newValue));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveVirtualMachines() {
        try {
            Writer writer = new FileWriter(PATH_TO_FILE);
            gson.toJson(getVirtualMachineList(), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mora da se inicijalizuje ova lista ovde
     * a ne u konstruktoru jer je gson postavi na null
     */
    private void initializeDiscList() {
        getVirtualMachineList().forEach(virtualMachine -> {
            virtualMachine.setDiskList(new ArrayList<>());
        });
    }


    private void connectVirtualMachinesAndDiscs() {
        DiskRepository diskRepository = DiskRepository.getInstance();
        diskRepository.getDiskList().forEach(disc -> {
            String virtualMachineName = disc.getVirtualMachineName();
            if (!virtualMachineName.equals("")) {
                VirtualMachine virtualMachine = virtualMachinesIndexedByName.get(virtualMachineName);
                virtualMachine.addDiscIfNotInVirtualMachine(disc);
            }
        });
    }

    private void connectVirtualMachinesAndCategory() {
        CategoryRepository categoryRepository = CategoryRepository.getInstance();

        getVirtualMachineList().forEach(virtualMachine -> {
            Category category = categoryRepository
                    .getCategory(virtualMachine.getCategoryName());
            virtualMachine.setCategory(category);
        });
    }

    public Map<String, VirtualMachine> getVirtualMachinesIndexedByName() {
        return virtualMachinesIndexedByName;
    }

    public Collection<VirtualMachine> getVirtualMachineList() {
        return virtualMachinesIndexedByName.values();
    }

    public VirtualMachine getVirtualMachine(String virtualMachineName) {
        return virtualMachinesIndexedByName.get(virtualMachineName);
    }

    public boolean addVirtualMachine(VirtualMachine virtualMachine) {
        String virtualMachineName = virtualMachine.getName();
        if (!virtualMachinesIndexedByName.containsKey(virtualMachineName)) {
            virtualMachinesIndexedByName.put(virtualMachineName, virtualMachine);
            saveVirtualMachines();
            return true;
        }

        return false;
    }

    public boolean removeVirtualMachine(String virtualMachineName) {
        if (virtualMachinesIndexedByName.containsKey(virtualMachineName)) {
            VirtualMachine vm = virtualMachinesIndexedByName.get(virtualMachineName);
            vm.setDeleted(true);
            saveVirtualMachines();
            return true;
        }

        return false;
    }

    public boolean editVirtualMachine(VirtualMachine editedVm) {
        String vmName = editedVm.getName();
        if (virtualMachinesIndexedByName.containsKey(vmName)) {
            VirtualMachine vm = virtualMachinesIndexedByName.get(vmName);
            String newCategoryName = editedVm.getCategoryName();
            vm.setCategoryName(newCategoryName);
            vm.setCategory(editedVm.getCategory());
            vm.setActive(editedVm.isActive());

            LocalDateTime now = LocalDateTime.now();
            //ako je sad aktivna znaci da pravimo novi activity
            if(vm.isActive()){
                String id = vm.getName() + now.toString();
                VirtualMachineActivity vac = new VirtualMachineActivity(now, null, id);
                vm.addActivity(vac);
            }
            //ako nije znaci da zavrsavamo poslednji activity
            else{
                int lastElem = vm.getActivities().size() - 1;
                VirtualMachineActivity vac = vm.getActivities().get(lastElem);
                vac.setEndTime(now);
            }

            saveVirtualMachines();
            return true;
        }

        return false;
    }

    public VirtualMachine getVirtualMachineByDiskName(String diskName) {
        Optional<VirtualMachine> result = getVirtualMachineList()
                .stream()
                .filter(vm -> vm.getDiskList()
                        .stream()
                        .anyMatch(disk -> disk.getName().equals(diskName)))
                .findAny();

        return result.orElse(null);
    }
}
