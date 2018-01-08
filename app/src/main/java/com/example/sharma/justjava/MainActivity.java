package com.example.sharma.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static int quantity = 0;
    static String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method increases the total count of coffee by pressing + button.
     * @param view
     */
    public void inc(View view) {
        quantity++;
        display(quantity);
    }

    /**
     * This method increases the total count of coffee by pressing + button.
     * @param view
     */
    public void dec(View view) {
        if (quantity > 0) {
            quantity--;
            display(quantity);
        } else {
            Toast.makeText(getApplicationContext(), "Atleast order 1 cup of coffee",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void update(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        EditText editText = (EditText) findViewById(R.id.name_field);
        // use toString as return type of editText is editable.
        String name = editText.getText().toString();

        int number = calculatePrice(hasChocolate, hasWhippedCream);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only e-mail apps should handle this.
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order by Just Java app for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, displayPrice(name, number, hasWhippedCream, hasChocolate));

        // start intent service only when there is atleast one app which can handle it.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 10;
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        return basePrice * quantity;
    }

    /*private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    */


    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of coffee and displays the output message.
     *
     * @param name      Name of buyer
     * @param number    total price
     * @param whipped   bool topping of Whipped Cream
     * @param chocolate bool topping of Chocolate
     */
    private String displayPrice(String name, int number, boolean whipped, boolean chocolate) {
        // TextView quantityTextView = (TextView) findViewById(R.id.act_text_view);
        output = "Name: " + name + "\n" + "Add Whipped Cream? " + whipped + "\n" + "Add Chocolate? " + chocolate + "\n" + "Quantity: " + quantity + "\n" + "Total: " + number + "\n" + "Thank you\n";
        return output;
        // quantityTextView.setText(output);//"$" + number
    }

}
