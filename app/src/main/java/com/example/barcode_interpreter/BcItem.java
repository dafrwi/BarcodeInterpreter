package com.example.barcode_interpreter;
// Template Struktur

import java.util.ArrayList;

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

    public ArrayList<BcItem> CreateSilhouet() {
        ArrayList<BcItem> template_1 = new ArrayList<>();
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

        return template_1;
    }

}
