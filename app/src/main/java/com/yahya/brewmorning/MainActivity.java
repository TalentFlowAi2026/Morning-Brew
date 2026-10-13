package com.yahya.brewmorning;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int[] DRINK_NAMES = {R.string.drink_tea, R.string.drink_coffee,
            R.string.drink_cappuccino, R.string.drink_latte};
    private static final int[] DRINK_PRICES = {10, 15, 20, 25};
    private static final int[] SIZE_PERCENTAGES = {100, 150, 200};
    private static final int SUGAR_PRICE = 2;
    private static final int MILK_PRICE = 3;
    private static final String DRAFT = "order_draft";
    private static final String DRINK = "drink";
    private static final String QUANTITY = "quantity";
    private static final String SIZE = "size";
    private static final String SUGAR = "sugar";
    private static final String MILK = "milk";
    private final Button[] drinkButtons = new Button[4];
    private final RadioButton[] sizeButtons = new RadioButton[3];
    private int selectedDrink = -1;
    private int quantity = 1;
    private int selectedSize;
    private boolean sugar;
    private boolean milk;
    private TextView quantityText;
    private TextView totalText;
    private CheckBox sugarCheck;
    private CheckBox milkCheck;
    private SharedPreferences preferences;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applyInsets();
        preferences = getSharedPreferences(DRAFT, MODE_PRIVATE);
        loadOrder();
        bindViews();
        configureDrinks();
        configureOptions();
        configureActions();
        updateScreen();
    }

    private void applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (view, insets) -> {
            androidx.core.graphics.Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private void loadOrder() {
        selectedDrink = preferences.getInt(DRINK, -1);
        quantity = Math.max(1, preferences.getInt(QUANTITY, 1));
        selectedSize = preferences.getInt(SIZE, 0);
        sugar = preferences.getBoolean(SUGAR, false);
        milk = preferences.getBoolean(MILK, false);
        if (selectedDrink < -1 || selectedDrink >= DRINK_NAMES.length) selectedDrink = -1;
        if (selectedSize < 0 || selectedSize >= SIZE_PERCENTAGES.length) selectedSize = 0;
    }

    private void bindViews() {
        drinkButtons[0] = findViewById(R.id.btnTea);
        drinkButtons[1] = findViewById(R.id.btnCoffee);
        drinkButtons[2] = findViewById(R.id.btnCappuccino);
        drinkButtons[3] = findViewById(R.id.btnLatte);
        sizeButtons[0] = findViewById(R.id.sizeSmall);
        sizeButtons[1] = findViewById(R.id.sizeMedium);
        sizeButtons[2] = findViewById(R.id.sizeLarge);
        sugarCheck = findViewById(R.id.checkSugar);
        milkCheck = findViewById(R.id.checkMilk);
        quantityText = findViewById(R.id.txtQuantity);
        totalText = findViewById(R.id.txtTotal);
    }

    private void configureDrinks() {
        for (int index = 0; index < drinkButtons.length; index++) {
            final int drinkIndex = index;
            drinkButtons[index].setText(getString(R.string.drink_format,
                    getString(DRINK_NAMES[index]), DRINK_PRICES[index]));
            drinkButtons[index].setOnClickListener(view -> {
                selectedDrink = drinkIndex;
                orderChanged();
            });
        }
    }

    private void configureOptions() {
        for (int index = 0; index < sizeButtons.length; index++) {
            final int sizeIndex = index;
            sizeButtons[index].setOnClickListener(view -> {
                selectedSize = sizeIndex;
                orderChanged();
            });
        }
        sugarCheck.setText(getString(R.string.extra_sugar, SUGAR_PRICE));
        milkCheck.setText(getString(R.string.extra_milk, MILK_PRICE));
        sugarCheck.setOnClickListener(view -> {
            sugar = sugarCheck.isChecked();
            orderChanged();
        });
        milkCheck.setOnClickListener(view -> {
            milk = milkCheck.isChecked();
            orderChanged();
        });
    }

    private void configureActions() {
        findViewById(R.id.btnPlus).setOnClickListener(view -> {
            if (quantity < Integer.MAX_VALUE) quantity++;
            orderChanged();
        });
        findViewById(R.id.btnMinus).setOnClickListener(view -> {
            if (quantity > 1) quantity--;
            orderChanged();
        });
        findViewById(R.id.btnPlaceOrder).setOnClickListener(view -> placeOrder());
    }

    private void updateScreen() {
        for (int index = 0; index < drinkButtons.length; index++)
            drinkButtons[index].setSelected(index == selectedDrink);
        for (int index = 0; index < sizeButtons.length; index++)
            sizeButtons[index].setChecked(index == selectedSize);
        sugarCheck.setChecked(sugar);
        milkCheck.setChecked(milk);
        updateTotal();
    }

    private void updateTotal() {
        long totalCents = calculateTotalCents();
        quantityText.setText(getString(R.string.quantity_format, quantity));
        totalText.setText(totalCents % 100 == 0
                ? getString(R.string.total_format, totalCents / 100)
                : getString(R.string.total_decimal_format, totalCents / 100, totalCents % 100));
    }

    private long calculateTotalCents() {
        if (selectedDrink < 0) return 0;
        // Multiplying the price by the size percentage gives the price in cents.
        long cupCents = (long) DRINK_PRICES[selectedDrink] * SIZE_PERCENTAGES[selectedSize];
        if (sugar) cupCents += SUGAR_PRICE * 100L;
        if (milk) cupCents += MILK_PRICE * 100L;
        return cupCents * quantity;
    }

    private void orderChanged() {
        updateScreen();
        preferences.edit().putInt(DRINK, selectedDrink).putInt(QUANTITY, quantity)
                .putInt(SIZE, selectedSize).putBoolean(SUGAR, sugar).putBoolean(MILK, milk).apply();
    }

    private void placeOrder() {
        if (selectedDrink < 0) {
            Toast.makeText(this, R.string.select_drink_warning, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, getString(R.string.order_placed,
                getString(DRINK_NAMES[selectedDrink]), quantity), Toast.LENGTH_SHORT).show();
        resetOrder();
    }

    private void resetOrder() {
        selectedDrink = -1;
        quantity = 1;
        selectedSize = 0;
        sugar = false;
        milk = false;
        orderChanged();
    }
}
