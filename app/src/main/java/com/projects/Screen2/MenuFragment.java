package com.projects.Screen2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.DataManagement.FoodItem;
import com.projects.DataManagement.FoodType;
import com.projects.DataManagement.MenuAdapter;
import com.projects.DataManagement.MenuHelperDB;
import com.projects.DataManagement.MenuLoadAdapter;
import com.projects.Screen1.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MenuHelperDB helperDB;
    private ArrayList<FoodItem>[] menuItems;

    private String mParam1;
    private String mParam2;

    public MenuFragment(){
        menuItems = new ArrayList[3 /* meals */ * 7 /* days */];
        for (int i = 0; i < 21; i++)
            menuItems[i] = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        helperDB = new MenuHelperDB(getActivity());
        TextView tv = view.findViewById(R.id.day1_meal1_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day2_meal1_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day3_meal1_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day4_meal1_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day5_meal1_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day6_meal1_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day7_meal1_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day1_meal2_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day2_meal2_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day3_meal2_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day4_meal2_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day5_meal2_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day6_meal2_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day7_meal2_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day1_meal3_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day2_meal3_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day3_meal3_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day4_meal3_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day5_meal3_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day6_meal3_tv);
        tv.setOnClickListener(this);
        tv = view.findViewById(R.id.day7_meal3_tv);
        tv.setOnClickListener(this);

        AppCompatButton createMenu = view.findViewById(R.id.menuCreation_btn);
        createMenu.setOnClickListener(v -> {
            for (ArrayList<FoodItem> item : menuItems){
                if (item.isEmpty()){
                    Toast.makeText(getContext(), "מילאת את התפריט באופן חלקי, ולכן לא ניתן ליצור אותו כרגע", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(!helperDB.searchConsumerMenu(getActivity().getIntent().getStringExtra("Username")).isEmpty()) {
                Toast.makeText(getContext(), "כבר יש ברשותך תפריט", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < 21 /* 3 meals * 7 days */; i++){
                for (FoodItem item : menuItems[i]){
                    helperDB.addFoodItem(item, i / 3, i % 3);
                }
            }
            Toast.makeText(getContext(), "התפריט נוצר בהצלחה", Toast.LENGTH_SHORT).show();
        });

        AppCompatButton deleteMenu = view.findViewById(R.id.menuDeletion_btn);
        deleteMenu.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("אישור");
            builder.setMessage("האם ברצונך למחוק את התפריט שלך?");
            builder.setPositiveButton("כן", (dialog, which) -> {
                if(!helperDB.searchConsumerMenu(getActivity().getIntent().getStringExtra("Username")).isEmpty()) {
                    helperDB.deleteConsumerMenu(getActivity().getIntent().getStringExtra("Username"));
                    Toast.makeText(getContext(), "התפריט נמחק בהצלחה", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "אין ברשותך תפריט", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("לא", (dialog, which) -> {});
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        AppCompatButton loadMenu = view.findViewById(R.id.menuLoading_btn);
        loadMenu.setOnClickListener(v -> {
            if(helperDB.searchConsumerMenu(getActivity().getIntent().getStringExtra("Username")).isEmpty()){
                Toast.makeText(getContext(), "אין ברשותך תפריט", Toast.LENGTH_SHORT).show();
                return;
            }
            createMenu.setClickable(false);
            deleteMenu.setClickable(false);
            TextView meal = view.findViewById(R.id.day1_meal1_tv);
            manageMenuLoading(meal, 0, 0);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day2_meal1_tv);
            manageMenuLoading(meal, 1, 0);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day3_meal1_tv);
            manageMenuLoading(meal, 2, 0);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day4_meal1_tv);
            manageMenuLoading(meal, 3, 0);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day5_meal1_tv);
            manageMenuLoading(meal, 4, 0);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day6_meal1_tv);
            manageMenuLoading(meal, 5, 0);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day7_meal1_tv);
            manageMenuLoading(meal, 6, 0);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day1_meal2_tv);
            manageMenuLoading(meal, 0, 1);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day2_meal2_tv);
            manageMenuLoading(meal, 1, 1);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day3_meal2_tv);
            manageMenuLoading(meal, 2, 1);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day4_meal2_tv);
            manageMenuLoading(meal, 3, 1);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day5_meal2_tv);
            manageMenuLoading(meal, 4, 1);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day6_meal2_tv);
            manageMenuLoading(meal, 5, 1);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day7_meal2_tv);
            manageMenuLoading(meal, 6, 1);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day1_meal3_tv);
            manageMenuLoading(meal, 0, 2);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day2_meal3_tv);
            manageMenuLoading(meal, 1, 2);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day3_meal3_tv);
            manageMenuLoading(meal, 2, 2);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day4_meal3_tv);
            manageMenuLoading(meal, 3, 2);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day5_meal3_tv);
            manageMenuLoading(meal, 4, 2);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day6_meal3_tv);
            manageMenuLoading(meal, 5, 2);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            meal = view.findViewById(R.id.day7_meal3_tv);
            manageMenuLoading(meal, 6, 2);
            meal.setText("הצג");
            meal.setTextColor(Color.BLACK);
            Toast.makeText(getContext(), "התפריט נטען בהצלחה", Toast.LENGTH_SHORT).show();
        });
    }

    public void manageMenuLoading(TextView trigger, int day, int meal){
        trigger.setOnClickListener(v -> {
            ArrayList<FoodRecyclerItem> foodItems = new ArrayList<>();
            ArrayList<FoodItem> menu = helperDB.searchConsumerMeal(
                    getActivity().getIntent().getStringExtra("Username"),
                    day,
                    meal
            );
            for (FoodItem item : menu)
                foodItems.add(new FoodRecyclerItem(item.getType(), item));
            Dialog dialog = new Dialog(this.getContext());
            dialog.setContentView(R.layout.food_items_load_dialog);
            DisplayMetrics dm = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            double height = dm.heightPixels;
            dialog.getWindow().setLayout(width, (int) (height * .925));
            recyclerView = dialog.findViewById(R.id.loadRecyclerView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(dialog.getContext());
            adapter = new MenuLoadAdapter(foodItems, dialog.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            Button confirmMenu = dialog.findViewById(R.id.mealDone_btn);
            confirmMenu.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onClick(View v) {
        ArrayList<FoodRecyclerItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodRecyclerItem(
                FoodType.DESSERT,
                new FoodItem("עוגת גבינה", "", 100, 3.7, 8.1, 5.1)));
        foodItems.add(new FoodRecyclerItem(
                FoodType.GRAIN,
                new FoodItem("לחם", "", 30, 2.8, 14.7, 0.4)));
        foodItems.add(new FoodRecyclerItem(
                FoodType.FRUIT,
                new FoodItem("תפוח עץ", "", 150, 0.3, 19.9, 0.4)));
        foodItems.add(new FoodRecyclerItem(
                FoodType.VEGETABLE,
                new FoodItem("מלפפון", "", 225, 1.5, 4.5, 0.2)));
        foodItems.add(new FoodRecyclerItem(
                FoodType.DAIRY,
                new FoodItem("חלב", "", FoodItem.CaloriesScale.כוס, 1, 7.9, 11.7, 7.2)));
        foodItems.add(new FoodRecyclerItem(
                FoodType.JUNK,
                new FoodItem("צ'יפס", "", 100, 2.3, 21.3, 6)));
        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.food_items_selection_dialog);
        DisplayMetrics dm = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        double height = dm.heightPixels;
        dialog.getWindow().setLayout(width, (int) (height * .925));
        recyclerView = dialog.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(dialog.getContext());
        adapter = new MenuAdapter(foodItems, dialog.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        EditText searchBar = dialog.findViewById(R.id.search_bar_et);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString(), foodItems);
            }
        });
        Button confirmMenu = dialog.findViewById(R.id.menuConfirm_btn);
        confirmMenu.setOnClickListener(v1 -> { EditText grams, scale;
            Spinner cs;
            FoodItem foodItem;
            FoodRecyclerItem recyclerItem;
            String id = getResources().getResourceEntryName(v.getId());
            // day1_meal1_tv - day number in index 3, meal number in index 9
            int len = foodItems.size(), index = (Integer.parseInt(String.valueOf(id.charAt(3))) - 1) * 3 + Integer.parseInt(String.valueOf(id.charAt(9))) - 1;
            for (int i = 0; i < len; i++) {
                grams = layoutManager.getChildAt(i).findViewById(R.id.grams_et);
                scale = layoutManager.getChildAt(i).findViewById(R.id.CaloriesScale_et);
                cs = layoutManager.getChildAt(i).findViewById(R.id.CaloriesScale_sp);
                recyclerItem = foodItems.get(i);
                foodItem = new FoodItem(recyclerItem.getFoodItem());
                foodItem.setConsumer(getActivity().getIntent().getStringExtra("Username"));
                switch (recyclerItem.getImageResource()){
                    case R.drawable.ic_dairy:
                        foodItem.setType(FoodType.DAIRY);
                        break;
                    case R.drawable.ic_dessert:
                        foodItem.setType(FoodType.DESSERT);
                        break;
                    case R.drawable.ic_fruit:
                        foodItem.setType(FoodType.FRUIT);
                        break;
                    case R.drawable.ic_grain:
                        foodItem.setType(FoodType.GRAIN);
                        break;
                    case R.drawable.ic_junk_food:
                        foodItem.setType(FoodType.JUNK);
                        break;
                    default:
                        foodItem.setType(FoodType.VEGETABLE);
                }
                if (!(cs.getSelectedItem().toString().equals("כלי מדידה")) &&
                        scale.getText().toString().matches("([\\d]{1,2})") &&
                        !(scale.getText().toString().equals("0"))) {
                    foodItem.setGrams(
                            FoodItem.CaloriesScale.valueOf(cs.getSelectedItem().toString()),
                            Integer.parseInt(scale.getText().toString()));
                    menuItems[index].add(foodItem);
                }
                else if (grams.getText().toString().matches("([\\d]{1,4})") &&
                        !(grams.getText().toString().equals("0"))){
                    foodItem.setGrams(Integer.parseInt(grams.getText().toString()));
                    menuItems[index].add(foodItem);
                }
                else if(!(scale.getText().toString().matches("([\\d]{1,2})")) ||
                        !(grams.getText().toString().matches("([\\d]{1,4})")) ||
                        (!(scale.getText().toString().equals("0")) && cs.getSelectedItem().toString().equals("כלי מדידה"))) {
                    menuItems[index].clear();
                    Toast.makeText(dialog.getContext(), "אותרו תקלות בקלט. נא לבדוק את האינפורציה שהוזנה על '" + foodItem.getFoodName() + "'.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(menuItems[index].isEmpty()){
                Toast.makeText(dialog.getContext(), "התפריט ריק, ולכן לא ניתן ליצור אותו", Toast.LENGTH_SHORT).show();
                return;
            }
            ((TextView)v).setText("בחרת");
            ((TextView)v).setTextColor(Color.GREEN);
            v.setClickable(false);
            dialog.dismiss();
        });
        dialog.show();
    }


    private void filter(String text, ArrayList<FoodRecyclerItem> all) {
        ArrayList<FoodRecyclerItem> filtered = new ArrayList<>();
        for (FoodRecyclerItem item : all) {
            if (item.getFoodItem().getFoodName().contains(text))
                filtered.add(item);
        }
        ((MenuAdapter) adapter).filterList(filtered);
    }
}