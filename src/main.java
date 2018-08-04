import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class main {

    public static void main (String args[]) throws IOException {

        //Creamos manager
        PDFManager manager = new PDFManager(System.getProperty("user.dir") + "/yotsuba.pdf",System.getProperty("user.dir") + "/test.pdf");

        manager.clone(11,16);
        manager.addBlancPageToAll();

        manager.save();
        manager.close();
    }

}
