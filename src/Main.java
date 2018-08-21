import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class Main {

    public static void main (String args[]){

        //Creamos manager
        PDFManager manager = new PDFManager(System.getProperty("user.dir") + "/In2.pdf",System.getProperty("user.dir") + "/test1.pdf");

        //Clonamos paginas pedidas
        manager.clone(11,16);
        //manager.addBlancPage();
        //manager.addBlancPageToAll();

        //Guardamos y cerramos
        manager.save();
        manager.close();
    }

}
