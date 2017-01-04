import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;


public class Indice extends JDialog implements TreeSelectionListener {

    private JEditorPane htmlPane;
    private JTree tree;
    private URL helpURL;
    private static boolean DEBUG = false;

    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";


	private static final long serialVersionUID = 1L;

    String idiomaBusqueda;                // Variable de tipo string en el que se almacena el idioma de busqueda que se establecera.
    JDialog ventana;                     // Ventana grafica en la que se muestran las opciones a realizar.
    AntiCopia ventanaPrincipal;       // Objeto de tipo "AntiCopia" en la que se almacena el objeto padre para poder llamar al metodo
                                      // nuevoNivelBusqueda(int) para devolverle el valor de "nivelBusqueda" modificado.
    JPanel panel;

    public Indice(AntiCopia padre,String idiomaBusquedaAntigua){

        // Se inicializan las variables globales "idiomaBusqueda" y "ventanaPrincipal"
        // con los parametros recibidos "idiomaBusquedaAntigua" y "padre" respectivamente.
        idiomaBusqueda= idiomaBusquedaAntigua;
        ventanaPrincipal= padre;

        // Se crea el objeto "ventana" de tipo "JDialog", que es el objeto principal de esta clase, y se establecen
        // algunas propiedades basicas (titulo, icono de la barra y forma de cerrarse por defecto).
        ventana= this;
        if (idiomaBusqueda=="es")
            setTitle("Indice");
        else setTitle("Index");
        setIconImage(ventanaPrincipal.crearImagen("images/lupa16x16.png"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	//*************************************************************************************************************//

        //Se crea un nodo.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("Plagium Alert");
        createNodes(top);

        //Se crea el arbol.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);

        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }

        //Se crea el panel de scroll y se añade el arbol
        JScrollPane treeView = new JScrollPane(tree);

        //Se crea el panel HTML
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        initHelp();
        JScrollPane htmlView = new JScrollPane(htmlPane);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        setContentPane(splitPane);
        setSize(800,600);
        setResizable(true);
        setVisible(true);
    }


	//*************************************************************************************************************//

    public void valueChanged(TreeSelectionEvent e) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf())
        {
            BookInfo book = (BookInfo)nodeInfo;
            displayURL(book.bookURL);
            if (DEBUG) {
                System.out.print(book.bookURL + ":  \n    ");
            }
        } else {
            displayURL(helpURL);
        }
        if (DEBUG) {
            System.out.println(nodeInfo.toString());
        }
    }

    private class BookInfo {
        public String bookName;
        public URL bookURL;

        public BookInfo(String book, String filename) {
            bookName = book;
            bookURL = getClass().getResource(filename);
            if (bookURL == null) {
                System.err.println("Couldn't find file: "+ filename);
            }
        }

        public String toString() {
            return bookName;
        }
    }

    private void initHelp() {
        String s ="";
        if (idiomaBusqueda=="es")
          s= "ayuda/menuAyuda_es.html";
        else s= "ayuda/menuAyuda_en.html";
        helpURL = getClass().getResource(s);
        if (helpURL == null) {
            System.err.println("Couldn't open help file: " + s);
        } else if (DEBUG) {
            System.out.println("Help URL is " + helpURL);
        }

        displayURL(helpURL);
    }

    private void displayURL(URL url) {
        try {
            if (url != null) {
                htmlPane.setPage(url);
            } else { //null url
                    if (idiomaBusqueda=="es")
		                    htmlPane.setText("Archivo no encontrado");
                    else htmlPane.setText("File Not Found");
                    if (DEBUG) {
                    System.out.println("Attempted to display a null URL.");
                    }
                  }
            } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
            }
    }

    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;
        DefaultMutableTreeNode book = null;
        String sTitulo="",sURL="";

//---------------------------- Menu 1 -------------------------------
        if (idiomaBusqueda=="es")
            sTitulo="Introducción";
        else sTitulo="Introduction";
        category = new DefaultMutableTreeNode(sTitulo);
        top.add(category);
// Menu 1.1
        if (idiomaBusqueda=="es")
            {
              sTitulo="¿Qué es Plagium Alert?";
              sURL="ayuda/queEsPlagiumAlert_es.html";
            }
        else
            {
              sTitulo="What is Plagium Alert?";
              sURL="ayuda/queEsPlagiumAlert_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);
// Menu 1.2
        if (idiomaBusqueda=="es")
            {
              sTitulo="Objetivo";
              sURL="ayuda/objetivo_es.html";
            }
        else
            {
              sTitulo="Objetive";
              sURL="ayuda/objetivo_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);
//---------------------------- Menu 2 -------------------------------
        if (idiomaBusqueda=="es")
            sTitulo= "Información general";
        else sTitulo= "General information";
        category = new DefaultMutableTreeNode(sTitulo);
        top.add(category);
// Menu 2.1
        if (idiomaBusqueda=="es")
            {
              sTitulo="Instalación";
              sURL="ayuda/instalacion_es.html";
            }
        else
            {
              sTitulo="Install";
              sURL="ayuda/instalacion_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);
// Menu 2.2
        if (idiomaBusqueda=="es")
            {
              sTitulo="Antiword";
              sURL="ayuda/antiword_es.html";
            }
        else
            {
              sTitulo="Antiword";
              sURL="ayuda/antiword_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);
// Menu 2.3
        if (idiomaBusqueda=="es")
            {
              sTitulo="Licencia";
              sURL="ayuda/licencia_es.html";
            }
        else
            {
              sTitulo="License";
              sURL="ayuda/licencia_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);
//---------------------------- Menu 3 -------------------------------
        if (idiomaBusqueda=="es")
            sTitulo= "Utilización";
        else sTitulo= "Use";
        category = new DefaultMutableTreeNode(sTitulo);
        top.add(category);
// Menu 3.1
        if (idiomaBusqueda=="es")
            {
              sTitulo="Analisis de texto";
              sURL="ayuda/analisisTexto_es.html";
            }
        else
            {
              sTitulo="Text analysis";
              sURL="ayuda/analisisTexto_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);

// Menu 3.2
        if (idiomaBusqueda=="es")
            {
              sTitulo="Análisis de documentos Microsoft Office Word";
              sURL="ayuda/analisisWord_es.html";
            }
        else
            {
              sTitulo="Microsoft Office Word analysis";
              sURL="ayuda/analisisWord_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);

// Menu 3.3
        if (idiomaBusqueda=="es")
            {
              sTitulo="Resultados";
              sURL="ayuda/resultados_es.html";
            }
        else
            {
              sTitulo="Results";
              sURL="ayuda/resultados_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);

// Menu 3.4
        if (idiomaBusqueda=="es")
            {
              sTitulo="Abrir y guardar resultados";
              sURL="ayuda/abrirGuardar_es.html";
            }
        else
            {
              sTitulo="Open & save results";
              sURL="ayuda/abrirGuardar_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);
// Menu 3.5
        if (idiomaBusqueda=="es")
            {
              sTitulo="Impresión de resultados";
              sURL="ayuda/impresion_es.html";
            }
        else
            {
              sTitulo="Print results";
              sURL="ayuda/impresion_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);

//---------------------------- Menu 4 -------------------------------
        if (idiomaBusqueda=="es")
            sTitulo= "Configuración";
        else sTitulo= "Configuration";
        category = new DefaultMutableTreeNode(sTitulo);
        top.add(category);

// Menu 4.1
        if (idiomaBusqueda=="es")
            {
              sTitulo="Idioma";
              sURL="ayuda/idioma_es.html";
            }
        else
            {
              sTitulo="Language";
              sURL="ayuda/idioma_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);

// Menu 4.2
        if (idiomaBusqueda=="es")
            {
              sTitulo="Modo de análisis";
              sURL="ayuda/modoAnalisis_es.html";
            }
        else
            {
              sTitulo="Analysis mode";
              sURL="ayuda/modoAnalisis_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);

//---------------------------- Menu 5 -------------------------------
        if (idiomaBusqueda=="es")
            sTitulo= "Créditos";
        else sTitulo= "Credits";
        category = new DefaultMutableTreeNode(sTitulo);
        top.add(category);

// Menu 5.1
        if (idiomaBusqueda=="es")
            {
              sTitulo="Autores";
              sURL="ayuda/autores_es.html";
            }
        else
            {
              sTitulo="Authors";
              sURL="ayuda/autores_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);

// Menu 5.2
        if (idiomaBusqueda=="es")
            {
              sTitulo="Recursos utilizados";
              sURL="ayuda/recursosUtilizados_es.html";
            }
        else
            {
              sTitulo="Resources";
              sURL="ayuda/recursosUtilizados_en.html";
            }
        book = new DefaultMutableTreeNode(new BookInfo(sTitulo,sURL));
        category.add(book);

        
    }

}

