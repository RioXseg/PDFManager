package PDFManager;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFManager {

    private String dirOut;          //ruta de archivo de salida
    private PDDocument PDFOut;      //archivo de salida

    //Constructor de la clase manager
    public PDFManager(String dirOut) {
        this.dirOut = dirOut;
        this.PDFOut = new PDDocument();
    }
    public PDFManager() {
        this("salida.pdf");
    }


    /**
     * Funcion que carga un documento PDF y lo almacena en el PDF de salida.
     * WARNING: El PDF que se almacene reemplazara a lo que haya estado antes en el PDF de salida. Para almacenar
     * otro documento posteriormente a lo que se tiene almacenado se debe llamar al metodo add(dir).
     * @param dir: Directorio del PDF que se desea cargar
     */
    public void load(String dir) {
        try {
            File file = new File(dir);
            this.PDFOut = PDDocument.load(file);
        } catch (IOException e) {
            System.out.println("Archivo no encontrado o dannado.");
        }
    }

    /**
     * Funcion que annade otro documento PDF al final del documento que se esta creando.
     * @param dir: Directorio del documento que se desea agregar.
     */
    public void add(String dir) {
        try {

            //Obtenemos lista de paginas del pdf a annadir
            ArrayList<PDPage> listaPaginas = new ArrayList<>();

            File file = new File(dir);
            PDDocument paraAnadir = PDDocument.load(file);

            for (int i = 0; i < paraAnadir.getNumberOfPages(); i++) {
                listaPaginas.add(paraAnadir.getPage(i));
            }

            //annadimos las paginas almacenadas anteriormente
            for(PDPage page : listaPaginas){
                this.PDFOut.addPage(page);
            }

        } catch (IOException e) {
            System.out.println("Archivo no encontrado o dannado.");
        }
    }

    /**
     * Funcion que guarda el PDF que se esta generando en disco.
     * WARNING: Esta funcion no cierra el documento. hay que llamar a close().
     */
    public void save() {
        try {
            File file = new File(this.dirOut);
            this.PDFOut.save(file);
        } catch (IOException e) {
            System.out.println("Directorio no encontrado.");
        }
    }

    /**
     * Funcion que cierra el PDF que se esta generando
     */
    public void close() {
        try {
            this.PDFOut.close();
        } catch (IOException e) {
            System.out.println("No se pudieron guardar los cambios.");
        }
    }

}
