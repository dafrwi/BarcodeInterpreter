package com.example.barcode_interpreter;
// Template Struktur

public class BcItem {
    String itemName;
    Integer itemStartBit;
    Integer itemAnzStellen;
    String itemDim;

    public BcItem (String itName, Integer itStartBit, Integer itAnzStellen, String itDim) {
        itemName = itName;
        itemStartBit = itStartBit;
        itemAnzStellen = itAnzStellen;
        itemDim = itDim;
    }
}
