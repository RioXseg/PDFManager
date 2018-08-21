import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDDestinationOrAction;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.File;
import java.io.IOException;

public class PDFManager {

    private String dirOut;          //ruta de archivo de salida
    private String dirIn;           //ruta de archivo de entrada
    private PDDocument PDFIn;       //archivo de entrada
    private PDDocument PDFOut;      //archivo de salida
    private int nPagesIn;             //numero de paginas archivo de entrada

    //Constructor de la clase manager
    PDFManager(String dirIn, String dirOut) {
        this.dirIn = dirIn;
        this.dirOut = dirOut;

        //Si ingreso ruta de entrada, entonces cargamos el archivo inmediatamente
        if (this.dirIn.equals("")) {
            load();
            this.nPagesIn = this.PDFIn.getNumberOfPages();
        }else{
            this.nPagesIn = 0;
        }
        this.PDFOut = new PDDocument();
    }


    //Funcion que lee un PDF y lo almacena en la variable de pdf de entrada
    private void load() {
        try {
            File file = new File(dirIn);
            this.PDFIn = PDDocument.load(file);
        } catch (IOException e) {
            print("Archivo no encontrado o dannado");
        }
    }

    //Funcion que guarda el PDF cambiado en un archivo de disco
    void save() {
        try {
            this.PDFOut.save(dirOut);
        } catch (IOException e) {
            print("Directorio no encontrado");
        }
    }

    //Funcion que copia de las paginas start hasta la end (incluidas) del archivo de entrada al de salida
    void clone(int start, int end) {
        //bajamos uno pues necesitamos los indices
        start -= 1;
        //Si ingresa -1, se vas hasta la ultima pagina
        if (end == -1 || end > this.nPagesIn){
            end = this.nPagesIn - 1;
        }else{
            end -= 1;
        }

        //Extraemos las hojas y las agregamos al archivo de salida
        PDPage page;
        for(int i = start;i<=end;i++){
            page = PDFIn.getPage(i);

            PDRectangle rectangle = new PDRectangle();
            rectangle.setUpperRightY(page.getCropBox().getUpperRightY());
            rectangle.setLowerLeftY(page.getCropBox().getLowerLeftY());
            rectangle.setUpperRightX(page.getCropBox().getUpperRightX());
            rectangle.setLowerLeftX(page.getCropBox().getLowerLeftX());

            page.setMediaBox(rectangle);                //Funcion importante para definir tamanno de la hoja
            PDFOut.addPage(page);
        }

    }

    //Funcion que cierra los archivos PDF para crearlos
    void close() {
        try {
            this.PDFOut.close();
            this.PDFIn.close();
        } catch (IOException e) {
            print("No se pudieron cerrar los archivos.");
        }
    }

    //Funcion que annade una pagina en blanco luego de cada pagina en el documento de salida
    void addBlancPageToAll(){
        //numero de paginas en blanco a agregar
        int nPagesOut = this.PDFOut.getNumberOfPages();

        PDDocument documentAux = new PDDocument();
        PDPage page;
        PDPage blancPage = new PDPage();
        for(int i = 0; i < nPagesOut; i++){
            page = PDFOut.getPage(i);

            PDRectangle rectangle = new PDRectangle();
            rectangle.setUpperRightY(page.getCropBox().getUpperRightY());
            rectangle.setLowerLeftY(page.getCropBox().getLowerLeftY());
            rectangle.setUpperRightX(page.getCropBox().getUpperRightX());
            rectangle.setLowerLeftX(page.getCropBox().getLowerLeftX());

            page.setMediaBox(rectangle);                //Funcion importante para definir tamanno de la hoja
            documentAux.addPage(page);

            //Ahora annadimos pagina en blanco
            documentAux.addPage(blancPage);
        }

        //Finalmente cambiamos el auxiliar por el archivo de salida
        this.PDFOut = documentAux;
    }

    //Funcion que annade una pagina en blanco al documento de salida
    void addBlancPage(){
        //Creamos nueva pagina con tamanno por defecto
        PDPage blancPage = new PDPage();
        //La annadimos al documento de salida
        this.PDFOut.addPage(blancPage);
    }

    //Funcion que imprime
    static void print(String statement) {
        System.out.println(statement);
    }

}
