package com.yahya.brewmorning;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final int[] DRINK_NAMES = {
        R.string.drink_tea, R.string.drink_coffee,
        R.string.drink_cappuccino, R.string.drink_latte
    };
    private static final int[] DRINK_PRICES = {10, 15, 20, 25};
    private final Button[] drinkButtons = new Button[4];
    private int selectedDrink = -1;
    private int quantity = 1;
    private TextView quantityText;
    private TextView totalText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (view, insets) -> {
            androidx.core.graphics.Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
        bindViews();
        configureDrinks();
        configureActions();
        updateTotal();
    }

    private void bindViews() {
        drinkButtons[0] = findViewById(R.id.btnTea);
        drinkButtons[1] = findViewById(R.id.btnCoffee);
        drinkButtons[2] = findViewById(R.id.btnCappuccino);
        drinkButtons[3] = findViewById(R.id.btnLatte);
        quantityText = findViewById(R.id.txtQuantity);
        totalText = findViewById(R.id.txtTotal);
    }

    private void configureDrinks() {
        for (int index = 0; index < drinkButtons.length; index++) {
            final int drinkIndex = index;
            drinkButtons[index].setText(getString(R.string.drink_format,
                    getString(DRINK_NAMES[index]), DRINK_PRICES[index]));
            drinkButtons[index].setOnClickListener(view -> selectDrink(drinkIndex));
        }
    }

    private void selectDrink(int drinkIndex) {
        selectedDrink = drinkIndex;
        for (int index = 0; index < drinkButtons.length; index++) {
            drinkButtons[index].setSelected(index == selectedDrink);
        }
        updateTotal();
    }

    private void configureActions() {
        findViewById(R.id.btnPlus).setOnClickListener(view -> {
            if (quantity < Integer.MAX_VALUE) quantity++;
            updateTotal();
        });
        findViewById(R.id.btnMinus).setOnClickListener(view -> {
            if (quantity > 1) quantity--;
            updateTotal();
        });
        findViewById(R.id.btnPlaceOrder).setOnClickListener(view -> placeOrder());
    }

    private void updateTotal() {
        long total = selectedDrink < 0 ? 0 : (long) DRINK_PRICES[selectedDrink] * quantity;
        quantityText.setText(getString(R.string.quantity_format, quantity));
        totalText.setText(getString(R.string.total_format, total));
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
        quantity = 1;
        selectDrink(-1);
    }
}
