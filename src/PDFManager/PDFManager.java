package PDFManager;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFManager {

    private String dirOut;          //ruta de archivo de salida
    private ArrayList<PDDocument> listaPDF;
    private PDDocument PDFOut;      //archivo de salida

    //Constructor de la clase manager
    public PDFManager(String dirOut) {
        this.dirOut = dirOut;
        this.PDFOut = new PDDocument();
        this.listaPDF = new ArrayList<>();
    }

    public PDFManager() {
        this("salida.pdf");
    }


    /**
     * Funcion que carga un documento PDF y lo almacena en el PDF de salida.
     * WARNING: El PDF que se almacene reemplazara a lo que haya estado antes en el PDF de salida. Para almacenar
     * otro documento posteriormente a lo que se tiene almacenado se debe llamar al metodo add(dir).
     *
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
     * WARNING: No funciona para annadir mas de 4 PDF al mismo tiempo.
     * @param dir: Directorio del documento que se desea agregar.
     */
    public void add(String dir) {
        try {

            //Obtenemos lista de paginas del pdf a annadir
            ArrayList<PDPage> listaPaginas = new ArrayList<>();

            File file = new File(dir);
            PDDocument paraAnadir = PDDocument.load(file);
            listaPDF.add(paraAnadir);

            for (int i = 0; i < paraAnadir.getNumberOfPages(); i++) {
                listaPaginas.add(paraAnadir.getPage(i));
            }

            //annadimos las paginas almacenadas anteriormente
            for (PDPage page : listaPaginas) {
                this.PDFOut.addPage(page);
            }

        } catch (IOException e) {
            System.out.println("Archivo no encontrado o dannado.");
        }
    }


    /**
     * Funcion que junta una lista dada de PDF.
     * @param lista: Lista con las direcciones de los PDF a ingresar.
     * @throws IOException: En caso de que no se ingrese una direccion valida.
     */
    public void merge(String[] lista) throws IOException {

        //Instantiating PDFMergerUtility class
        PDFMergerUtility PDFmerger = new PDFMergerUtility();

        for (int i = 0; i < lista.length; i++) {

            //Loading an existing PDF document
            File file1 = new File(lista[i]);
            PDDocument doc1 = PDDocument.load(file1);

            //Setting the destination file
            PDFmerger.setDestinationFileName(this.dirOut);

            //adding the source files
            PDFmerger.addSource(file1);

            doc1.close();
        }
        //Merging the two documents
        PDFmerger.mergeDocuments();
    }

    /**
     * Funcion que guarda el PDF que se esta generando en disco.
     * WARNING: Esta funcion no cierra el documento. hay que llamar a close().
     */
    public void save() {
        try {
            this.PDFOut.save(this.dirOut);
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
