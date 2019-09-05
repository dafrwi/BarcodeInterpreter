package com.example.barcode_interpreter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = findViewById(R.id.bt_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        Button interpretButton = findViewById(R.id.bt_interpret);
        interpretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interpretCode();
            }
        });


    }

    private void scanCode() {
        // liest Barcode ein und zeigt das Resultat im codeView Fenster
        final Activity activity = this;
        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
        }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        TextView scanResult = findViewById(R.id.codeView);
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
                Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();

            } else {
                Log.d("MainActivity", "Scanned");
               // Toast.makeText(this, "Scanned -> " + Result.getContents(), Toast.LENGTH_SHORT).show();
                scanResult.setText("Scanned Code: " + Result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void interpretCode() {

        class Item{
            String itemName;
            String itemValue;
            String itemDim;
        }

        ArrayList<Item> itemList1 = new ArrayList<>();

        Item inputItem1 = new Item();
        Item inputItem2 = new Item();

            inputItem1.itemName = ("JobID");
            inputItem1.itemValue = ("123");
            inputItem1.itemDim = ("mm");
        itemList1.add(0, inputItem1);

            inputItem1.itemName = ("Book Bloc Thickness");
            inputItem1.itemValue = ("456");
            inputItem1.itemDim = ("mm");
        itemList1.add(1, inputItem1);

        ArrayList<String> itemList3 = new ArrayList<>();
            itemList3.add("JobID");
            itemList3.add("Book bloc thickness");
            itemList3.add("Book bloc height");
            itemList3.add("Head Trim");


        // Code für Code-Interpretation

        // Darstellung der Ergebnisse:

        adapter =new ArrayAdapter<String> (MainActivity.this.getBaseContext(),
                android.R.layout.simple_list_item_activated_1, itemList3);

        ListView res = findViewById(R.id.result_list);
        res.setAdapter(adapter);

        /*adapter =new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = getLayoutInflater().inflate(R.layout.cell_layout,null);

                    //TextView textView1 = v.findViewById(R.id.textView_name);
                    //textView1.setText(getItem(itemList);

                    //TextView textView2 = v.findViewById(R.id.textView_value);
                    //textView2.setText(getItem(itemList.itemValue);

                    return v;
                }
        };
        ListView res = findViewById(R.id.result_list);
        res.setAdapter(adapter);*/

    }
}
