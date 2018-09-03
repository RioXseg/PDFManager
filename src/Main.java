import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class Main {

    public static void main (String args[]){

        //Creamos manager
        PDFManager manager = new PDFManager(System.getProperty("user.dir") + "/entrada.pdf",System.getProperty("user.dir") + "/salida.pdf");
        manager.load();

        //Clonamos paginas pedidas
        manager.clone(1,171);
        manager.addBlancPageToAll();

        //Guardamos y cerramos
        manager.save();
        manager.close();
    }

}
