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

    protected ArrayList<BcContent> CreateInitial(){
        ArrayList<BcContent> bcContentList = new ArrayList<>();
        BcContent contentX = new BcContent("","","");

        return bcContentList;
    }

    protected ArrayList<BcItem> CreateDefault() {

        /* Default - Template */
        ArrayList<BcItem> template_default = new ArrayList<>();
        BcItem item;

        item = new BcItem ("JobID", 1, 8, "");
        template_default.add(item);
        item = new BcItem ("Book bloc thickness", 9, 4, "mm");
        template_default.add(item);
        item = new BcItem ("Book bloc height", 13, 4, "mm");
        template_default.add(item);
        item = new BcItem ("Cut-off length", 17, 4, "mm");
        template_default.add(item);

        return template_default;
    }

    protected ArrayList<BcItem> CreateSilhouet() {

        /* Template Combined Code for Project Silhouet (BookLine-VBA-Vareo-InfiniTrim) */
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

        /* Template for Vareo Binder */
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

        /* Template for InfiniTrim Trimmer */
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

    protected ArrayList<BcItem> CreateExaktaVareo() {

        /* Default Combined Code for Project Exakta (BookLine-Vareo) */
        ArrayList<BcItem> template_ExaktaVareo = new ArrayList<>();
        BcItem item;

        item = new BcItem ("JobID", 1, 8, "");
        template_ExaktaVareo.add(item);
        item = new BcItem ("Book bloc thickness", 9, 3, "mm");
        template_ExaktaVareo.add(item);
        item = new BcItem ("Book bloc height", 12, 5, "mm");
        template_ExaktaVareo.add(item);
        item = new BcItem ("CS6 Finishing Co.", 16, 2, "");
        template_ExaktaVareo.add(item);
        item = new BcItem ("Sheet Number", 19, 3, "");
        template_ExaktaVareo.add(item);
        item = new BcItem ("Total Sheets", 22, 3, "");
        template_ExaktaVareo.add(item);
        item = new BcItem ("Spare Digits", 25, 8, "");
        template_ExaktaVareo.add(item);

        return template_ExaktaVareo;
    }

    protected ArrayList<BcItem> CreateExaktaVareoCover() {

        /* Template for Project Exakta (Vareo-Cover)*/
        ArrayList<BcItem> template_ExaktaVareoCover = new ArrayList<>();
        BcItem item;

        item = new BcItem("JobID", 1, 8, "");
        template_ExaktaVareoCover.add(item);
        item = new BcItem("Final format height", 9, 4, "mm");
        template_ExaktaVareoCover.add(item);
        item = new BcItem("Final format width", 13, 4, "mm");
        template_ExaktaVareoCover.add(item);
        item = new BcItem("Cut-off length", 17, 4, "mm");
        template_ExaktaVareoCover.add(item);

        return template_ExaktaVareoCover;
    }

    protected ArrayList<BcItem> CreateExaktaPrimera() {

        /* Template for Project Exakta (Stitcher-PrimeraMC)*/
        ArrayList<BcItem> template_ExaktaPrimera = new ArrayList<>();
        BcItem item;

        item = new BcItem("JobID", 1, 7, "");
        template_ExaktaPrimera.add(item);
        item = new BcItem("Signature Nr.", 8, 3, "");
        template_ExaktaPrimera.add(item);

        return template_ExaktaPrimera;
    }
}
