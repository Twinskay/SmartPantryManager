package com.example.smartpantrymanager;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_PANTRY = "pantry";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY_DATE = "expiry_date";
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "recipe_id";
    public static final String COL_RECIPE_NAME = "recipe_name";
    public static final String COL_RECIPE_STEPS = "recipe_steps";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COL_RI_ID = "ri_id";
    public static final String COL_RI_RECIPE_ID = "recipe_id";
    public static final String COL_RI_INGREDIENT_NAME = "ingredient_name";
    public static final String COL_RI_QUANTITY = "required_quantity";
    public static final String COL_RI_UNIT = "required_unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_QUANTITY + " REAL NOT NULL, " +
                COL_UNIT + " TEXT NOT NULL, " +
                COL_EXPIRY_DATE + " TEXT)";



        db.execSQL(createPantryTable);

        String createRecipeTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT NOT NULL, " +
                COL_RECIPE_STEPS + " TEXT NOT NULL)";

        db.execSQL(createRecipeTable);
        String createRecipeIngredientsTable = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COL_RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RI_RECIPE_ID + " INTEGER NOT NULL, " +
                COL_RI_INGREDIENT_NAME + " TEXT NOT NULL, " +
                COL_RI_QUANTITY + " REAL NOT NULL, " +
                COL_RI_UNIT + " TEXT NOT NULL)";

        db.execSQL(createRecipeIngredientsTable);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        onCreate(db);
    }

    public boolean addIngredient(String name, double quantity, String unit, String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY_DATE, expiryDate);
        long result = db.insert(TABLE_PANTRY, null, values);
        return result != -1;
    }

    public ArrayList<PantryItem> getAllIngredients() {
        ArrayList<PantryItem> ingredientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PANTRY, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(COL_UNIT));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPIRY_DATE));
                PantryItem item = new PantryItem(id, name, quantity, unit, expiryDate);
                ingredientList.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return ingredientList;
    }
    public boolean deleteIngredient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_PANTRY,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }
    public boolean updateIngredient(int id, String name, double quantity,
                                    String unit, String expiryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY_DATE, expiryDate);

        int result = db.update(
                TABLE_PANTRY,
                values,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }
    private long addRecipe(SQLiteDatabase db, String name, String steps) {
        ContentValues values = new ContentValues();

        values.put(COL_RECIPE_NAME, name);
        values.put(COL_RECIPE_STEPS, steps);

        return db.insert(TABLE_RECIPES, null, values);
    }

private void addRecipeIngredient(SQLiteDatabase db, long recipeId,
                                 String ingredientName, double quantity, String unit) {

    ContentValues values = new ContentValues();

    values.put(DatabaseHelper.COL_RI_RECIPE_ID, recipeId);
    values.put(DatabaseHelper.COL_RI_INGREDIENT_NAME, ingredientName);
    values.put(DatabaseHelper.COL_RI_QUANTITY, quantity);
    values.put(DatabaseHelper.COL_RI_UNIT, unit);

    db.insert(DatabaseHelper.TABLE_RECIPE_INGREDIENTS, null, values);

}
    private void seedRecipes(SQLiteDatabase db) {
        long recipe1 = addRecipe(db,
                "Tomato Toast",
                "1. Slice the tomato. 2. Toast the bread. 3. Place the tomato on the toast and serve.");

        addRecipeIngredient(db, recipe1, "Tomato", 1, "pieces");
        addRecipeIngredient(db, recipe1, "Bread", 2, "slices");

        long recipe2 = addRecipe(db,
                "Scrambled Eggs",
                "1. Crack the eggs into a bowl. 2. Beat the eggs. 3. Cook in a pan while stirring until fully cooked.");

        addRecipeIngredient(db, recipe2, "Egg", 2, "pieces");

        long recipe3 = addRecipe(db,
                "Tomato and Egg Scramble",
                "1. Chop the tomato. 2. Crack and beat the eggs. 3. Cook the tomato in a pan. 4. Add the eggs and stir until fully cooked.");

        addRecipeIngredient(db, recipe3, "Tomato", 1, "pieces");
        addRecipeIngredient(db, recipe3, "Egg", 2, "pieces");

        long recipe4 = addRecipe(db,
                "Cheese Toast",
                "1. Place the cheese on the bread. 2. Toast until the bread is crisp and the cheese has melted. 3. Serve warm.");

        addRecipeIngredient(db, recipe4, "Bread", 2, "slices");
        addRecipeIngredient(db, recipe4, "Cheese", 2, "slices");

        long recipe5 = addRecipe(db,
                "Egg Sandwich",
                "1. Cook the eggs. 2. Place the cooked eggs between the bread slices. 3. Serve.");

        addRecipeIngredient(db, recipe5, "Egg", 2, "pieces");
        addRecipeIngredient(db, recipe5, "Bread", 2, "slices");

        long recipe6 = addRecipe(db,
                "Tomato and Cheese Sandwich",
                "1. Slice the tomato. 2. Place the tomato and cheese between the bread slices. 3. Serve.");

        addRecipeIngredient(db, recipe6, "Bread", 2, "slices");
        addRecipeIngredient(db, recipe6, "Tomato", 1, "pieces");
        addRecipeIngredient(db, recipe6, "Cheese", 2, "slices");

        long recipe7 = addRecipe(db,
                "Cheese Omelette",
                "1. Crack and beat the eggs. 2. Pour the eggs into a pan. 3. Add the cheese. 4. Fold the omelette and cook until done.");

        addRecipeIngredient(db, recipe7, "Egg", 2, "pieces");
        addRecipeIngredient(db, recipe7, "Cheese", 2, "slices");

        long recipe8 = addRecipe(db,
                "Tomato Cheese Omelette",
                "1. Chop the tomato. 2. Beat the eggs. 3. Pour the eggs into a pan. 4. Add tomato and cheese. 5. Fold and cook until done.");

        addRecipeIngredient(db, recipe8, "Egg", 2, "pieces");
        addRecipeIngredient(db, recipe8, "Tomato", 1, "pieces");
        addRecipeIngredient(db, recipe8, "Cheese", 2, "slices");

        long recipe9 = addRecipe(db,
                "Buttered Toast",
                "1. Toast the bread until crisp. 2. Spread butter over the warm toast. 3. Serve.");

        addRecipeIngredient(db, recipe9, "Bread", 2, "slices");
        addRecipeIngredient(db, recipe9, "Butter", 20, "g");

        long recipe10 = addRecipe(db,
                "Cheese and Egg Toast",
                "1. Toast the bread. 2. Cook the egg in a pan. 3. Place the egg and cheese on the toast. 4. Serve warm.");

        addRecipeIngredient(db, recipe10, "Bread", 2, "slices");
        addRecipeIngredient(db, recipe10, "Egg", 1, "pieces");
        addRecipeIngredient(db, recipe10, "Cheese", 2, "slices");

        long recipe11 = addRecipe(db,
                "Tomato Pasta",
                "1. Cook the pasta until soft. 2. Chop the tomato. 3. Cook the tomato in a pan. 4. Add the cooked pasta and mix well.");

        addRecipeIngredient(db, recipe11, "Pasta", 200, "g");
        addRecipeIngredient(db, recipe11, "Tomato", 2, "pieces");

        long recipe12 = addRecipe(db,
                "Cheesy Pasta",
                "1. Cook the pasta until soft. 2. Drain the pasta. 3. Add the cheese while the pasta is hot. 4. Mix until the cheese melts.");

        addRecipeIngredient(db, recipe12, "Pasta", 200, "g");
        addRecipeIngredient(db, recipe12, "Cheese", 2, "slices");


        long recipe13 = addRecipe(db,
                "Tomato Cheese Pasta",
                "1. Cook the pasta until soft. 2. Chop and cook the tomato. 3. Add the cooked pasta. 4. Add cheese and mix until melted.");

        addRecipeIngredient(db, recipe13, "Pasta", 200, "g");
        addRecipeIngredient(db, recipe13, "Tomato", 2, "pieces");
        addRecipeIngredient(db, recipe13, "Cheese", 2, "slices");


        long recipe14 = addRecipe(db,
                "Egg Pasta",
                "1. Cook the pasta until soft. 2. Beat the eggs. 3. Cook the eggs in a pan. 4. Add the cooked pasta and mix well.");

        addRecipeIngredient(db, recipe14, "Pasta", 200, "g");
        addRecipeIngredient(db, recipe14, "Egg", 2, "pieces");


        long recipe15 = addRecipe(db,
                "Tomato Egg Sandwich",
                "1. Cook the eggs. 2. Slice the tomato. 3. Place the egg and tomato between the bread slices. 4. Serve.");

        addRecipeIngredient(db, recipe15, "Bread", 2, "slices");
        addRecipeIngredient(db, recipe15, "Egg", 2, "pieces");
        addRecipeIngredient(db, recipe15, "Tomato", 1, "pieces");
    }

}


        








