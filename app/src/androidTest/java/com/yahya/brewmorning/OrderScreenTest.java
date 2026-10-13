package com.yahya.brewmorning;

import android.widget.TextView;
import androidx.test.core.app.ActivityScenario;
import android.widget.CheckBox;
import android.widget.RadioButton;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Before;
import org.junit.After;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import static org.junit.Assert.*;

@SuppressWarnings("deprecation")
public class OrderScreenTest {
    private ActivityScenario<MainActivity> scenario;

    @Before public void startClean() {
        InstrumentationRegistry.getInstrumentation().getTargetContext()
                .getSharedPreferences("order_draft", 0).edit().clear().commit();
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @After public void closeScreen() { scenario.close(); }

    private MainActivity getActivity() {
        AtomicReference<MainActivity> current = new AtomicReference<>();
        scenario.onActivity(current::set);
        return current.get();
    }

    private void tap(int id) throws Throwable {
        scenario.onActivity(activity -> activity.findViewById(id).performClick());
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    private void assertText(int id, String expected) {
        assertEquals(expected, ((TextView) getActivity().findViewById(id)).getText().toString());
    }

    @Test public void testOrderLifecycleAndAllDrinkTotals() throws Throwable {
        MainActivity activity = getActivity();
        int[] ids = {R.id.btnTea, R.id.btnCoffee, R.id.btnCappuccino, R.id.btnLatte};
        int[] prices = {10, 15, 20, 25};
        assertText(R.id.txtQuantity, activity.getString(R.string.quantity_format, 1));
        assertText(R.id.txtTotal, activity.getString(R.string.total_format, 0));
        for (int id : ids) assertFalse(activity.findViewById(id).isSelected());
        tap(R.id.btnPlaceOrder);
        tap(R.id.btnMinus);
        assertTrue(activity.findViewById(R.id.btnMinus).isEnabled());
        assertText(R.id.txtQuantity, activity.getString(R.string.quantity_format, 1));
        for (int index = 0; index < ids.length; index++) {
            tap(ids[index]);
            for (int other = 0; other < ids.length; other++)
                assertEquals(other == index, activity.findViewById(ids[other]).isSelected());
            for (int quantity = 1; quantity <= 10; quantity++) {
                assertText(R.id.txtQuantity, activity.getString(R.string.quantity_format, quantity));
                assertText(R.id.txtTotal, activity.getString(R.string.total_format, prices[index] * quantity));
                if (quantity < 10) tap(R.id.btnPlus);
            }
            for (int quantity = 9; quantity >= 1; quantity--) {
                tap(R.id.btnMinus);
                assertText(R.id.txtTotal, activity.getString(R.string.total_format, prices[index] * quantity));
            }
        }
        for (int order = 0; order < 2; order++) {
            tap(R.id.btnLatte);
            tap(R.id.btnPlus);
            tap(R.id.btnPlus);
            tap(R.id.btnPlaceOrder);
            assertText(R.id.txtQuantity, activity.getString(R.string.quantity_format, 1));
            assertText(R.id.txtTotal, activity.getString(R.string.total_format, 0));
            for (int id : ids) assertFalse(activity.findViewById(id).isSelected());
        }
        tap(R.id.btnPlus);
        assertText(R.id.txtTotal, activity.getString(R.string.total_format, 0));
    }

    @Test public void testAllSizesExtrasAndReset() throws Throwable {
        int[] drinks = {R.id.btnTea, R.id.btnCoffee, R.id.btnCappuccino, R.id.btnLatte};
        int[] prices = {10, 15, 20, 25};
        int[] sizes = {R.id.sizeSmall, R.id.sizeMedium, R.id.sizeLarge};
        int[] halves = {2, 3, 4};
        for (int drink = 0; drink < drinks.length; drink++) {
            tap(drinks[drink]);
            for (int size = 0; size < sizes.length; size++) {
                tap(sizes[size]);
                for (int extras = 0; extras < 4; extras++) {
                    final boolean sugar = (extras & 1) != 0;
                    final boolean milk = (extras & 2) != 0;
                    scenario.onActivity(activity -> {
                        CheckBox sugarBox = activity.findViewById(R.id.checkSugar);
                        CheckBox milkBox = activity.findViewById(R.id.checkMilk);
                        if (sugarBox.isChecked() != sugar) sugarBox.performClick();
                        if (milkBox.isChecked() != milk) milkBox.performClick();
                    });
                    long cupCents = prices[drink] * 50L * halves[size]
                            + (sugar ? 200 : 0) + (milk ? 300 : 0);
                    assertTotal(cupCents);
                    tap(R.id.btnPlus);
                    assertTotal(cupCents * 2);
                    tap(R.id.btnMinus);
                }
            }
        }
        tap(R.id.btnPlaceOrder);
        assertTotal(0);
        assertTrue(((RadioButton) getActivity().findViewById(R.id.sizeSmall)).isChecked());
        assertFalse(((CheckBox) getActivity().findViewById(R.id.checkSugar)).isChecked());
        assertFalse(((CheckBox) getActivity().findViewById(R.id.checkMilk)).isChecked());
    }

    private void assertTotal(long cents) {
        MainActivity activity = getActivity();
        assertText(R.id.txtTotal, cents % 100 == 0
                ? activity.getString(R.string.total_format, cents / 100)
                : activity.getString(R.string.total_decimal_format, cents / 100, cents % 100));
    }

    @Test public void testDraftSurvivesRecreationAndRelaunch() throws Throwable {
        tap(R.id.btnCoffee);
        tap(R.id.sizeMedium);
        tap(R.id.checkSugar);
        tap(R.id.checkMilk);
        tap(R.id.btnPlus);
        assertTotal(5500);
        scenario.recreate();
        assertDraft();
        scenario.close();
        scenario = ActivityScenario.launch(MainActivity.class);
        assertDraft();
        tap(R.id.btnPlaceOrder);
        scenario.recreate();
        assertTotal(0);
        assertText(R.id.txtQuantity, getActivity().getString(R.string.quantity_format, 1));
        assertFalse(getActivity().findViewById(R.id.btnCoffee).isSelected());
    }

    private void assertDraft() {
        assertTotal(5500);
        assertText(R.id.txtQuantity, getActivity().getString(R.string.quantity_format, 2));
        assertTrue(getActivity().findViewById(R.id.btnCoffee).isSelected());
        assertTrue(((RadioButton) getActivity().findViewById(R.id.sizeMedium)).isChecked());
        assertTrue(((CheckBox) getActivity().findViewById(R.id.checkSugar)).isChecked());
        assertTrue(((CheckBox) getActivity().findViewById(R.id.checkMilk)).isChecked());
    }
}
