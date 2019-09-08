package com.example.barcode_interpreter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    String bcString;
    Button scanButton, interpretButton;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.code_templates, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        scanButton = findViewById(R.id.bt_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        interpretButton = findViewById(R.id.bt_interpret);
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

        // Dummy Daten als der Scanner noch nicht funktioniert hat
        //TextView scanResult = findViewById(R.id.codeView);
        //scanResult.setText("Code:    " + "11223344016324080128");
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        TextView scanResult = findViewById(R.id.codeView);
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (Result != null) {
            if (Result.getContents() == null) {
                Log.d("MainActivity", "cancelled scan");
                scanResult.setText("Scanned Code: Scannen wurde abgebrochen");

            } else {
                Log.d("MainActivity", "Scanned");
                scanResult.setText("Scanned Code: " + Result.getContents());
                bcString = (String)Result.getContents();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void interpretCode() {

        /*  Beispiel-String mit folgendem Inhalt
            String bcString=("11223344016324080128");

            Beispiel-Ausgabe
            JobID:                  11223344
            Book bloc thickness     16.3
            Book bloc height        240.8
            Cut off length          12.8
         */

        //prüfung ob ein code gescannt wurde
        if (bcString == null) {
            TextView scanResult = findViewById(R.id.codeView);
            Log.d("MainActivity", "cancelled scan");
            //Toast.makeText(this, "cancelled", Toast.LENGTH_SHORT).show();
            scanResult.setText("es wurde kein code gescannt");
            }

        // Wenn win Code gescannt wurde wird hie weitergefahren
        else {
            ArrayList<BcItem> template_1 = new ArrayList<>();
            ArrayList<BcContent> bcContentList;
            BcItem item;

            item = new BcItem ("JobID", 1, 8, "");
            template_1.add(item);
            item = new BcItem ("Endsheet height", 9, 4, "mm");
            template_1.add(item);
            item = new BcItem ("Book bloc thickness", 13, 4, "mm");
            template_1.add(item);
            item = new BcItem ("Book bloc height", 17, 4, "mm");
            template_1.add(item);
            item = new BcItem ("Cut Off Length", 21, 4, "mm");
            template_1.add(item);
            item = new BcItem ("Final Height", 25, 4, "mm");
            template_1.add(item);
            item = new BcItem ("Final Width", 29, 4, "mm");
            template_1.add(item);
            item = new BcItem ("Last sheet indicator", 33, 2, "");
            template_1.add(item);
            item = new BcItem ("Current sheet indicator", 35, 3, "");
            template_1.add(item);
            item = new BcItem ("Total sheets", 38, 3, "");
            template_1.add(item);


            bcContentList = interpretBC(bcString, template_1);

            adapter = new ArrayAdapter<BcContent>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, bcContentList){

                @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = getLayoutInflater().inflate(R.layout.cell_layout,null);
                        TextView textView1 = v.findViewById(R.id.textView_name);
                        textView1.setText((CharSequence) getItem(position).contentName);
                        TextView textView2 = v.findViewById(R.id.textView_value);
                        textView2.setText((CharSequence) getItem(position).contentValue);
                        TextView textView3 = v.findViewById(R.id.textView_dim);
                        textView3.setText((CharSequence) getItem(position).contentDim);
                        return v;
                    }
                };
        }
        ListView res = findViewById(R.id.result_list);
        res.setAdapter(adapter);
    }

    private ArrayList<BcContent> interpretBC(String bcStr, ArrayList<BcItem> templ) {
        String bcString = bcStr;
        ArrayList<BcItem> template = templ;
        ArrayList<BcContent> bcContentList = new ArrayList<>();

        Integer anzItem = template.size();
        Integer y = 0;
        Integer z =0;
        BcItem itemX = null;

        for (int i = 0; i < anzItem; i++) {
            itemX = template.get(i);

            BcContent contentX = new BcContent("","","");
            contentX.contentName = itemX.itemName;
            contentX.contentDim = itemX.itemDim;

            y = itemX.itemStartBit;
            z = itemX.itemAnzStellen;

            String value = bcString.substring(y-1,(y+z-1));
            String value2 = null;

            if (contentX.contentDim.equals("mm")) {
                // String mit Dezimalpunkt ergänzen und führende 0 löschen
                value2 = value.substring(0,3) + "." + value.substring(3);

                if (value2.charAt(0) == '0') {
                    value2 = value2.substring(1,5);
                }
            } else {
                value2 = value;
            }
            contentX.contentValue = value2;
            bcContentList.add(contentX);
        }
        return bcContentList;
    }
}
