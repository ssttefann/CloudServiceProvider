package Model.Repositories;

import Model.Entities.Disk;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiskRepository {
    private static Gson gson = new Gson();
    private static final String PATH_TO_FILE = "CloudServiceProvider/data/disc.json";

    private Map<String, Disk> disksIndexedByName;
    private List<Disk> diskList;

    private static DiskRepository instance;
    public static DiskRepository getInstance(){
        if(instance == null){
            instance = new DiskRepository();
        }
        return instance;
    }

    private DiskRepository(){
        diskList = new ArrayList<>();
        disksIndexedByName = new HashMap<>();
        loadDisck();
    }

    private void loadDisck(){
        try {
            FileReader reader = new FileReader(PATH_TO_FILE);
            Type listType = new TypeToken<ArrayList<Disk>>() {}.getType();
            diskList = gson.fromJson(reader, listType);
            disksIndexedByName = diskList.stream()
                    .collect(Collectors.toMap(Disk::getName, disc -> disc, (oldValue, newValue) -> newValue));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveDisks() {
        try {
            Writer writer = new FileWriter(PATH_TO_FILE);
            gson.toJson(diskList, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Disk getDisk(String discName) {
        return disksIndexedByName.get(discName);
    }

    public Map<String, Disk> getDisksIndexedByName() {
        return disksIndexedByName;
    }

    public List<Disk> getDiskList() {
        return diskList;
    }

    public boolean addDisc(Disk disk){
        String discName = disk.getName();
        if(!disksIndexedByName.containsKey(discName)){
            disk.setTimeCreated(LocalDateTime.now());
            disksIndexedByName.put(discName, disk);
            diskList.add(disk);

            saveDisks();
            return true;
        }

        return false;
    }

    public boolean removeDisk(String diskName) {
        if (disksIndexedByName.containsKey(diskName)) {
            diskList.remove(disksIndexedByName.get(diskName));
            disksIndexedByName.remove(diskName);
            saveDisks();
            return true;
        }

        return false;
    }

    public boolean editDisc(Disk editedDisk){
        String diskName = editedDisk.getName();
        if(disksIndexedByName.containsKey(diskName)){
            Disk oldDisk = disksIndexedByName.get(diskName);
            oldDisk.setCapacity(editedDisk.getCapacity());
            oldDisk.setType(editedDisk.getType());
            oldDisk.setVirtualMachineName(editedDisk.getVirtualMachineName());
            saveDisks();
            return true;
        }

        return false;
    }
}
