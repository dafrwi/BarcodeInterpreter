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

import java.util.ArrayList;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    String bcString;
    Button scanButton, interpretButton;
    Spinner spinner;
    ArrayAdapter adapter;
    ArrayList<BcContent> bcContentList;
    ListView res;
    TextView errorText;
    ArrayList<BcItem> selectedTmplate;
    CreateTemplate template = new CreateTemplate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /* create an initial Template that it is not NULL */
        bcContentList = new CreateTemplate().CreateInitial();

        //
        spinner = findViewById(R.id.spinner);
        /* Create an ArrayAdapter using the string array and a default spinner layout */
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.code_templates, android.R.layout.simple_spinner_item);
        /* Specify the layout to use when the list of choices appears */
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /* Apply the spinnerAdapter to the spinner */
        spinner.setAdapter(spinnerAdapter);

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
        final Activity activity = this;
        IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
        intentIntegrator.setDesiredBarcodeFormats(intentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setPrompt("SCAN");
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();

        /*setAdapter();
        res = findViewById(R.id.result_list);
        res.setAdapter(adapter); */
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        TextView scanResult = findViewById(R.id.codeView);
        errorText = findViewById(R.id.errorView);
        IntentResult Result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (Result != null) {
            if (Result.getContents() == null) {
                bcContentList.clear();

                errorText.setVisibility(View.VISIBLE);
                errorText.setText(R.string.scan_cancelled);


            } else {
                scanResult.setText("Scanned Code: " + Result.getContents());
                bcString = Result.getContents();
                bcContentList.clear();

            }
            res = findViewById(R.id.result_list);
            res.setAdapter(adapter);
            
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void interpretCode() {

        /* check if a barcode is scanned */
        if (bcString != null) {
            String select = spinner.getSelectedItem().toString();
            switch(spinner.getSelectedItem().toString()) {
                case "Silhouet":
                    selectedTmplate = template.CreateSilhouet();
                    break;
                case "InfiniTrim":
                    selectedTmplate = template.CreateInfiniTrim();
                    break;
                case "Vareo":
                    selectedTmplate = template.CreateVareo();
                    break;
                case "ExaktaVareo":
                    selectedTmplate = template.CreateExaktaVareo();
                    break;
                case "ExaktaVareoCover":
                    selectedTmplate = template.CreateExaktaVareoCover();
                    break;
                case "ExaktaPrimera":
                    selectedTmplate = template.CreateExaktaPrimera();
                    break;
                default:
                    selectedTmplate = template.CreateDefault();
            }
                /* Check if Barcode and Template has the same length */
                Boolean templOK = checkBcTemplate (bcString, selectedTmplate);

                if (templOK == true) {
                    errorText.setVisibility(View.INVISIBLE);
                    bcContentList = interpretBC(bcString, selectedTmplate);
                }
                else {
                    /* noTemplateMatch(); */
                    bcContentList.clear();
                    errorText = findViewById(R.id.errorView);
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText(R.string.noTemplate_match);
                }
        }

        else {
            bcContentList.clear();
            errorText = findViewById(R.id.errorView);
            errorText.setVisibility(View.VISIBLE);
            errorText.setText(R.string.noCode_scanned);
        }

        //adapter.notifyDataSetChanged();
        setAdapter();
        res = findViewById(R.id.result_list);
        res.setAdapter(adapter);
    }


    private void setAdapter (){

        adapter = new ArrayAdapter<BcContent>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, bcContentList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = getLayoutInflater().inflate(R.layout.cell_layout, null);
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

    private Boolean checkBcTemplate (String bcStr, ArrayList<BcItem> templ) {

        /* check if the length of the bcString is equal to the length defined with the template */
        String bcString = bcStr;
        ArrayList<BcItem> template = templ;

        Boolean checkOK= false;

        Integer anzItem = template.size();
        BcItem lastItem = templ.get(anzItem-1);
        int bclength = bcStr.length();
        int templength = (lastItem.itemStartBit + lastItem.itemAnzStellen - 1);

        if (bcStr.length() == (lastItem.itemStartBit + lastItem.itemAnzStellen - 1)) {
            checkOK = true;
        }
        return checkOK;
    }

    private ArrayList<BcContent> interpretBC(String bcStr, ArrayList<BcItem> templ) {

        /* read the values out of the bcString and fill up bcContentList */
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
                /* add a decimal point to the strings with dimension ""mm" and delete leading 0 */
                value2 = value.substring(0,3) + "." + value.substring(3);

                if (value2.charAt(0) == '0') {
                    value2 = value2.substring(1,5);

                    /* delete a second leading 0 */
                    if (value2.charAt(0) == '0') {
                        value2 = value2.substring(1,4);
                    }
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
