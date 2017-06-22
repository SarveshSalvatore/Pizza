package com.example.android.pizza;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order pizza.
 */
public class MainActivity extends AppCompatActivity {

    int margherita_quantity = 0;
    int chicago_quantity = 0;
    int marinara_quantity = 0;
    int sicilian_quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void incrementmarg(View view) {
        if (margherita_quantity == 100) {
            return;
        }
        margherita_quantity = margherita_quantity + 1;
        displayQuantityMarg(margherita_quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrementmarg(View view) {
        if (margherita_quantity == 0) {
            return;
        }
        margherita_quantity = margherita_quantity - 1;
        displayQuantityMarg(margherita_quantity);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void incrementmari(View view) {
        if (marinara_quantity == 100) {
            return;
        }
        marinara_quantity = marinara_quantity + 1;
        displayQuantityMari(marinara_quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrementmari(View view) {
        if (marinara_quantity == 0) {
            return;
        }
        marinara_quantity = marinara_quantity - 1;
        displayQuantityMari(marinara_quantity);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void incrementchic(View view) {
        if (chicago_quantity == 100) {
            return;
        }
        chicago_quantity = chicago_quantity + 1;
        displayQuantityChic(chicago_quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrementchic(View view) {
        if (chicago_quantity == 0) {
            return;
        }
        chicago_quantity = chicago_quantity - 1;
        displayQuantityChic(chicago_quantity);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void incrementsici(View view) {
        if (sicilian_quantity == 100) {
            return;
        }
        sicilian_quantity = sicilian_quantity + 1;
        displayQuantitySici(sicilian_quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrementsici(View view) {
        if (sicilian_quantity == 0) {
            return;
        }
        sicilian_quantity = sicilian_quantity - 1;
        displayQuantitySici(sicilian_quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants margherita pizza
        CheckBox margherita_checkbox = (CheckBox) findViewById(R.id.margherita_checkbox);
        boolean hasMargherita = margherita_checkbox.isChecked();

        // Figure out if the user wants marinara pizza
        CheckBox marinara_checkbox = (CheckBox) findViewById(R.id.marinara_checkbox);
        boolean hasMarinara = marinara_checkbox.isChecked();


        // Figure out if the user wants chicago style pizza
        CheckBox chicago_checkbox = (CheckBox) findViewById(R.id.chicago_style_checkbox);
        boolean hasChicago = chicago_checkbox.isChecked();

        // Figure out if the user wants sicilian pizza
        CheckBox sicilian_checkbox = (CheckBox) findViewById(R.id.sicilian_checkbox);
        boolean hasSicilian = sicilian_checkbox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasMargherita, hasMarinara, hasChicago, hasSicilian);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price,hasMargherita, hasMarinara, hasChicago, hasSicilian);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean addMargherita, boolean addMarinara, boolean addChicago, boolean addSicilian) {

        int basePriceMarg = 0;
        int basePriceMari = 0;
        int basePriceChic = 0;
        int basePriceSici = 0;


        if (addMargherita) {
           basePriceMarg = 100 * margherita_quantity;
        }


        if (addMarinara) {
            basePriceMari = 200 *marinara_quantity;
        }


        if (addChicago) {
            basePriceChic = 120 * chicago_quantity;
        }


        if (addSicilian) {
            basePriceSici = 140 * sicilian_quantity;
        }


        return basePriceMarg + basePriceMari + basePriceChic +basePriceSici;
    }


    private String createOrderSummary(String name, int price,boolean addMargherita, boolean addMarinara,
                                      boolean addChicago, boolean addSicilian) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_quantitymarg, margherita_quantity);
        priceMessage += "\n" + getString(R.string.order_summary_quantitymari, marinara_quantity);
        priceMessage += "\n" + getString(R.string.order_summary_quantitychic, chicago_quantity);
        priceMessage += "\n" + getString(R.string.order_summary_quantitysici, sicilian_quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantityMarg(int numberOfMargPizz) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.margherita_quantity);
        quantityTextView.setText("" + numberOfMargPizz);
    }

    private void displayQuantityMari(int numberOfMariPizz) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.marinara_quantity);
        quantityTextView.setText("" + numberOfMariPizz);
    }

    private void displayQuantityChic(int numberOfChicPizz) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.chicago_quantity);
        quantityTextView.setText("" + numberOfChicPizz);
    }

    private void displayQuantitySici(int numberOfSiciPizz) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.sicilian_quantity);
        quantityTextView.setText("" + numberOfSiciPizz);
    }
}