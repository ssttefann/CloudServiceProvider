package Model.Repositories;

import Model.Entities.Disc;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscRepository {
    private static Gson gson = new Gson();
    private static final String PATH_TO_FILE = "CloudServiceProvider/data/disc.json";

    private Map<String, Disc> discsIndexedByName;
    private List<Disc> discList;

    private static DiscRepository instance;
    public static DiscRepository getInstance() throws FileNotFoundException {
        if(instance == null){
            instance = new DiscRepository();
        }
        return instance;
    }

    private DiscRepository() throws FileNotFoundException {
        discList = new ArrayList<>();
        discsIndexedByName = new HashMap<>();
        loadDiscs();
    }

    private void loadDiscs() throws FileNotFoundException {
        FileReader reader = new FileReader(PATH_TO_FILE);
        Type listType = new TypeToken<ArrayList<Disc>>() {}.getType();
        discList = gson.fromJson(reader, listType);
        discsIndexedByName = discList.stream()
                .collect(Collectors.toMap(Disc::getName, disc -> disc, (oldValue, newValue) -> newValue));
    }

    private void saveDiscs(){

    }

    public Disc getDiscByName(String discName) {
        return discsIndexedByName.get(discName);
    }

    public Map<String, Disc> getDiscsIndexedByName() {
        return discsIndexedByName;
    }

    public List<Disc> getDiscList() {
        return discList;
    }
}
