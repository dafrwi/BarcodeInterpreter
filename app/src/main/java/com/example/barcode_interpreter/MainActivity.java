package com.example.barcode_interpreter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        TextView scanResult = findViewById(R.id.codeView);
        scanResult.setText("Code:    " + "11223344016324080128");
    }

    private void interpretCode() {

        String bcString=("11223344016324080128");
        /*  Beispiel-String mit folgendem Inhalt
            JobID:                  11223344
            Book bloc thickness     16.3
            Book bloc height        240.8
            Cut off length          12.8
         */

        ArrayList<BcItem> template_1 = new ArrayList<>();
            BcItem item;
            item = new BcItem ("JobID", 1, 8, "");
            template_1.add(item);
            item = new BcItem ("Book bloc thickness", 9, 4, "mm");
            template_1.add(item);
            item = new BcItem ("Book bloc height", 13, 4, "mm");
            template_1.add(item);
            item = new BcItem ("Cut Off Length", 17, 4, "mm");
            template_1.add(item);

        ArrayList<BcContent> bcContentList = new ArrayList<>();

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
