package Model.Repositories;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Model.Entities.Category;

public class CategoryRepository {
    private static Gson gson = new Gson();
    private static final String PATH_TO_FILE = "CloudServiceProvider/data/category.json";

    private Map<String, Category> categoriesIndexedByName;
    private static CategoryRepository instance;

    public static CategoryRepository getInstance(){
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

    private CategoryRepository(){
        categoriesIndexedByName = new LinkedHashMap<>();
        loadCategories();
    }

    private void loadCategories(){
        try {
            FileReader reader = new FileReader(PATH_TO_FILE);
            Type listType = new TypeToken<ArrayList<Category>>() {
            }.getType();
            List<Category> categoryList = gson.fromJson(reader, listType);
            categoriesIndexedByName = categoryList.stream()
                    .collect(Collectors.toMap(Category::getName, category -> category, (oldValue, newValue) -> newValue));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void saveCategories(){
        try {
            Writer writer = new FileWriter(PATH_TO_FILE);
            gson.toJson(getCategoryList(), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Category getCategory(String categoryName) {
        return categoriesIndexedByName.get(categoryName);
    }

    public Collection<Category> getCategoryList() {
        return categoriesIndexedByName.values();
    }

    public boolean addCategory(Category category){
        String categoryName = category.getName();
        if (!categoriesIndexedByName.containsKey(categoryName)) {
            categoriesIndexedByName.put(categoryName, category);
            saveCategories();
            return true;

        }

        return false;
    }

    public boolean removeIfExists(String categoryName){
        if (categoriesIndexedByName.containsKey(categoryName)) {
            categoriesIndexedByName.remove(categoryName);
            saveCategories();
            return true;
        }

        return false;
    }

    public boolean editCategory(Category editedCategory){
        String categoryName = editedCategory.getName();
        if(categoriesIndexedByName.containsKey(categoryName)){
            Category oldCategory = categoriesIndexedByName.get(categoryName);
            oldCategory.setCores(editedCategory.getCores());
            oldCategory.setGPU(editedCategory.getGPU());
            oldCategory.setRAM(editedCategory.getRAM());
            saveCategories();
            return true;
        }

        return false;
    }
}
