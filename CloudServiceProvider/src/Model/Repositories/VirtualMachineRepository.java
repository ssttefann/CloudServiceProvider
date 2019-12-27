package Model.Repositories;

import Model.Entities.Category;
import Model.Entities.VirtualMachine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VirtualMachineRepository {
    private static Gson gson = new Gson();
    private static final String PATH_TO_FILE = "CloudServiceProvider/data/vm.json";

    private Map<String, VirtualMachine> virtualMachinesIndexedByName;
    private List<VirtualMachine> virtualMachineList;

    /** Singleton */
    private static VirtualMachineRepository instance;
    public static VirtualMachineRepository getInstance() throws IOException {
        if(instance == null){
            instance = new VirtualMachineRepository();
        }
        return instance;
    }

    private VirtualMachineRepository() throws IOException {
        virtualMachineList = new ArrayList<>();
        virtualMachinesIndexedByName = new HashMap<>();
        loadVirtualMachines();
        initializeDiscList();
        connectVirtualMachinesAndDiscs();
        connectVirtualMachinesAndCategory();
    }


    private void loadVirtualMachines() throws FileNotFoundException {
        Reader reader = new FileReader(PATH_TO_FILE);
        Type listType = new TypeToken<ArrayList<VirtualMachine>>() {}.getType();
        virtualMachineList = gson.fromJson(reader, listType);
        virtualMachinesIndexedByName = virtualMachineList.stream()
                .collect(Collectors.toMap(VirtualMachine::getName, virtualMachine -> virtualMachine, (oldValue, newValue) -> newValue));
    }

    private void saveVirtualMachines() throws IOException {
        Writer writer = new FileWriter(PATH_TO_FILE);
        gson.toJson(virtualMachineList, writer);
        writer.flush();
        writer.close();
    }

    /**
     * Mora da se inicijalizuje ova lista ovde
     * a ne u konstruktoru jer je gson postavi na null
     */
    private void initializeDiscList(){
        virtualMachineList.forEach(virtualMachine -> {
            virtualMachine.setDiscList(new ArrayList<>());
        });
    }


    private void connectVirtualMachinesAndDiscs() throws IOException {
        DiscRepository discRepository = DiscRepository.getInstance();
        discRepository.getDiscList().forEach(disc -> {
            String virtualMachineName = disc.getVirtualMachineName();
            VirtualMachine virtualMachine = virtualMachinesIndexedByName.get(virtualMachineName);
            virtualMachine.addDiscIfNotInVirtualMachine(disc);
        });
    }

    private void connectVirtualMachinesAndCategory() throws IOException {
        CategoryRepository categoryRepository = CategoryRepository.getInstance();
        virtualMachineList.forEach(virtualMachine -> {
            Category category = categoryRepository
                    .getCategoryByName(virtualMachine.getCategoryName());
            virtualMachine.setCategory(category);
        });
    }

    public Map<String, VirtualMachine> getVirtualMachinesIndexedByName() {
        return virtualMachinesIndexedByName;
    }

    public List<VirtualMachine> getVirtualMachineList() {
        return virtualMachineList;
    }

    public VirtualMachine getVirtualMachineByName(String virtualMachineName) {
        return virtualMachinesIndexedByName.get(virtualMachineName);
    }
}
