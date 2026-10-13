package com.yahya.brewmorning;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class OrderScreenTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public OrderScreenTest() { super(MainActivity.class); }

    private void tap(int id) throws Throwable {
        runTestOnUiThread(() -> getActivity().findViewById(id).performClick());
        getInstrumentation().waitForIdleSync();
    }

    private void assertText(int id, String expected) {
        assertEquals(expected, ((TextView) getActivity().findViewById(id)).getText().toString());
    }

    public void testOrderLifecycleAndAllDrinkTotals() throws Throwable {
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
}
