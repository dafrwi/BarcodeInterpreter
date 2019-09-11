package com.example.barcode_interpreter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CreateTemplate {




    public CreateTemplate() {

    }


    protected ArrayList<BcItem> CreateSilhouet() {
        ArrayList<BcItem> template_Silhouet = new ArrayList<>();
        BcItem item;

        item = new BcItem ("JobID", 1, 8, "");
        template_Silhouet.add(item);
        item = new BcItem ("Endsheet height", 9, 4, "mm");
        template_Silhouet.add(item);
        item = new BcItem ("Book bloc thickness", 13, 4, "mm");
        template_Silhouet.add(item);
        item = new BcItem ("Book bloc height", 17, 4, "mm");
        template_Silhouet.add(item);
        item = new BcItem ("Cut Off Length", 21, 4, "mm");
        template_Silhouet.add(item);
        item = new BcItem ("Final Height", 25, 4, "mm");
        template_Silhouet.add(item);
        item = new BcItem ("Final Width", 29, 4, "mm");
        template_Silhouet.add(item);
        item = new BcItem ("Last sheet indicator", 33, 2, "");
        template_Silhouet.add(item);
        item = new BcItem ("Current sheet indicator", 35, 3, "");
        template_Silhouet.add(item);
        item = new BcItem ("Total sheets", 38, 3, "");
        template_Silhouet.add(item);

        return template_Silhouet;
    }

    protected ArrayList<BcItem> CreateVareo() {
        ArrayList<BcItem> template_Vareo = new ArrayList<>();
        BcItem item;

        item = new BcItem ("JobID", 1, 8, "");
        template_Vareo.add(item);
        item = new BcItem ("Book bloc thickness", 9, 4, "mm");
        template_Vareo.add(item);
        item = new BcItem ("Book bloc height", 13, 4, "mm");
        template_Vareo.add(item);

        return template_Vareo;
    }

    protected ArrayList<BcItem> CreateInfiniTrim() {
        ArrayList<BcItem> template_InfiniTrim = new ArrayList<>();
        BcItem item;

        item = new BcItem ("Final format height", 1, 4, "mm");
        template_InfiniTrim.add(item);
        item = new BcItem ("Final format width", 5, 4, "mm");
        template_InfiniTrim.add(item);
        item = new BcItem ("Cut-off length", 9, 4, "mm");
        template_InfiniTrim.add(item);

        return template_InfiniTrim;
    }




}
