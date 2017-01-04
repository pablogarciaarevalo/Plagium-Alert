import javax.swing.*;
import javax.swing.text.*;
import java.awt.Desktop;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;
import java.net.URI;
import java.io.File;
import java.io.StringReader;

public class ResultadosAvanzados extends JDialog implements HyperlinkListener{

	private static final long serialVersionUID = 1L;

    String idiomaBusqueda;                // Variable de tipo string en el que se almacena el idioma de busqueda que se establecera.
    JDialog ventana;                     // Ventana grafica en la que se muestran las opciones a realizar.
    AntiCopia ventanaPrincipal;       // Objeto de tipo "AntiCopia" en la que se almacena el objeto padre para poder llamar al metodo
                                      // nuevoNivelBusqueda(int) para devolverle el valor de "nivelBusqueda" modificado.

    public ResultadosAvanzados(AntiCopia padre,String idiomaBusquedaAntigua, String texto){

        // Se inicializan las variables globales "idiomaBusqueda" y "ventanaPrincipal"
        // con los parametros recibidos "idiomaBusquedaAntigua" y "padre" respectivamente.
        idiomaBusqueda= idiomaBusquedaAntigua;
        ventanaPrincipal= padre;
        
        // Se crea el objeto "ventana" de tipo "JDialog", que es el objeto principal de esta clase, y se establecen
        // algunas propiedades basicas (titulo, icono de la barra y forma de cerrarse por defecto).
        ventana= this;
        if (idiomaBusqueda=="es")
            setTitle("Resultados avanzados");
        else setTitle("Advanced Results");
        setIconImage(ventanaPrincipal.crearImagen("images/lupa16x16.png"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	//*************************************************************************************************************//
        JPanel panelResultadosAvanzados = new JPanel();

        setSystemImagePath(SystemImagePath);
        JEditorPane editorPane = new JEditorPane();
        MyHTMLEditorKit kit = new MyHTMLEditorKit();
        editorPane.setEditorKit(kit);

        Document doc = editorPane.getDocument();
        StringReader reader = new StringReader(texto);
        try { kit.read(reader, doc, 0); }
        catch (Exception e) { e.printStackTrace(); }
        
  //*************************************************************************************************************//

        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorPane.setBorder(null);
        editorScrollPane.setBorder(null);
        setContentPane(editorScrollPane);
        pack();
        setSize(800,600);
        setVisible(true);
    }

  public static final String ApplicationImagePath =
    new File("").getAbsolutePath();

  public static final String SystemImagePathKey =
    "system.image.path.key";
  private static final String SystemImagePath =
    new File("").getAbsolutePath();

  public void setSystemImagePath(String path) {
    System.setProperty(SystemImagePathKey, path);
  }

  public void hyperlinkUpdate(HyperlinkEvent evt) {

      if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
        try
        {
            if (Desktop.isDesktopSupported())
            {
              Desktop escritorio = Desktop.getDesktop();
              escritorio.browse(new URI(evt.getURL().toString()));
            }
            else
            {
              if (idiomaBusqueda=="es")
                  ventanaPrincipal.funcion.mostrarDialogoError("Es imposible localizar su navegador por defecto.");
              else ventanaPrincipal.funcion.mostrarDialogoError("Your default browser have not been found.");
            }
        }
        catch (Exception e) {}
      }

    }


}

