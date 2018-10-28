package Program;

import PDFManager.PDFManager;

public class Main {

    public static void main (String args[]){

        PDFManager manager = new PDFManager();

        manager.load("cover.pdf");
        manager.add("Ane Naru Mono - Vol1.pdf");

        manager.save();

        manager.close();

    }
}
