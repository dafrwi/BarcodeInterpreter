package com.example.barcode_interpreter;
/* Barcode Content structure */

public class BcContent {
    String contentName;
    String contentValue;
    String contentDim;

    public BcContent (String ctName, String ctValue, String ctDim) {
        contentName = ctName;
        contentValue = ctValue;
        contentDim = ctDim;
    }
}
