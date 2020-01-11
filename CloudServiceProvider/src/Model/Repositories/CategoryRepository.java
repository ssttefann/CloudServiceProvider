package Model.Repositories;

import Model.Entities.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryRepository {
    private static Gson gson = new Gson();
    private static final String PATH_TO_FILE = "CloudServiceProvider/data/category.json";

    private Map<String, Category> categoriesIndexedByName;
    private List<Category> categoryList;

    private static CategoryRepository instance;

    public static CategoryRepository getInstance() throws IOException {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

    private CategoryRepository() throws IOException {
        categoriesIndexedByName = new HashMap<>();
        categoryList = new ArrayList<>();

        loadCategories();
    }

    private void loadCategories() throws FileNotFoundException {
        FileReader reader = new FileReader(PATH_TO_FILE);
        Type listType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        categoryList = gson.fromJson(reader, listType);
        categoriesIndexedByName = categoryList.stream()
                .collect(Collectors.toMap(Category::getName, category -> category, (oldValue, newValue) -> newValue));

    }

    private void saveCategories() throws IOException {
        Writer writer = new FileWriter(PATH_TO_FILE);
        gson.toJson(categoryList, writer);
        writer.flush();
        writer.close();
    }

    public Category getCategoryByName(String categoryName) {
        return categoriesIndexedByName.get(categoryName);
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public boolean addCategory(Category category) throws IOException {
        String categoryName = category.getName();
        if (!categoriesIndexedByName.containsKey(categoryName)) {
            categoryList.add(category);
            categoriesIndexedByName.put(categoryName, category);
            saveCategories();
            return true;

        }

        return false;
    }

    public boolean removeIfExists(String categoryName) throws IOException {
        if (categoriesIndexedByName.containsKey(categoryName)) {
            categoryList.remove(categoriesIndexedByName.get(categoryName));
            categoriesIndexedByName.remove(categoryName);
            saveCategories();
            return true;
        }

        return false;
    }

    public boolean editCategory(Category editedCategory) throws IOException {
        String categoryName = editedCategory.getName();
        if(categoriesIndexedByName.containsKey(categoryName)){
            categoryList.remove(editedCategory);
            categoryList.add(editedCategory);
            categoriesIndexedByName.put(categoryName, editedCategory);
            saveCategories();
            return true;
        }

        return false;
    }
}
