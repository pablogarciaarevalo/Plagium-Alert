import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.Desktop;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.text.*;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.File;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.net.URI;

public class AntiCopia extends JFrame implements ActionListener,MouseListener,PropertyChangeListener,HyperlinkListener {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JToolBar toolBar;
    
    JMenu aFile;
    JMenu eFile;
    JMenu cFile;
    JMenu yFile;
    
    JMenuItem menuNuevo;
    JMenuItem menuCerrar;
    JMenuItem menuAnalizar;
    JMenuItem menuAbrir;
    JMenuItem menuGuardar;
    JMenuItem menuImprimir;
    JMenuItem menuSalir;
    JMenuItem menuCopiar;
    JMenuItem menuCortar;
    JMenuItem menuPegar;
    JMenuItem menuModoAnalisis;
    JMenuItem menuIdioma;
    JMenuItem menuIndice;
    JMenuItem menuAcercaDe;
    
    JButton botonNuevo;
    JButton botonAbrir;
    JButton botonGuardar;
    JButton botonImprimir;
    JButton botonAnalizar;
    
    ImageIcon iconoAnalizar = crearIcono("images/lupa32x32.png");
    ImageIcon iconoParar = crearIcono("images/stop32x32.png");
    ImageIcon semaforoFinal;
    ImageIcon imagen_logo;
    
    String ResultadosAvanzados= "";
    JTextPane m_jtpTexto;
    JTextField m_jtfFichero;
    JEditorPane	mejoresResultados;
    JLabel semaforo;
    JLabel logo;
    JLabel m_jlbFichero;
    JLabel m_jlbAnalizando;
    JScrollPane m_jspScrollTexto;
    JButton m_jbtExaminar;
    JButton m_jbtResultadosAvanzados;
    
    private Task task;
    
    int numeroParrafos=0;
    int progreso= 0;
    JProgressBar barraProgreso;
    FuncionesGrafico funcion = new FuncionesGrafico(this);
	  int nivelBusqueda= 0;
	  String idiomaBusqueda= "en";
	  boolean analizando= false;
    boolean hayLimite;
    int segundosLimite;
    String url0="",url1="",url2="";
    int porcentajeUrl0= 0, porcentajeUrl1= 0, porcentajeUrl2= 0;
    String textoHTML="";
    String textoHTMLimprimir="";
    String textoOrigen="";
    String nombreGuardado="", apellidosGuardado="", identificadorGuardado="";
    String tituloGuardado="", asignaturaGuardado="", mesGuardado="", anioGuardado="";

	//*************************************************************************************************************//
	// El metodo "AntiCopia" crea el entorno grafico.                                                              //
	//*************************************************************************************************************//
  public AntiCopia() {
    super(" Plagium Alert ");
    setSize(1024,740);
    setExtendedState(JFrame.MAXIMIZED_BOTH );
    setIconImage(crearImagen("images/lupa16x16.png"));

    JMenuBar menuBar = crearBarraMenu();
    setJMenuBar(menuBar);
//  ----------------------------------------------------------------------------
    m_jlbFichero = new JLabel();//"Fichero:");
		m_jlbFichero.setLocation(40,70);
		m_jlbFichero.setSize(70,25);
		m_jlbFichero.setVisible(false);
		getContentPane().add(m_jlbFichero);
		
		m_jbtExaminar = new JButton("Examinar...");
    m_jbtExaminar.setLocation(120,70);
    m_jbtExaminar.setSize(100,25);
    m_jbtExaminar.setVisible(false);
    m_jbtExaminar.addActionListener(this);
    getContentPane().add(m_jbtExaminar);

		m_jtfFichero = new JTextField();
		m_jtfFichero.setLocation(230,70);
		m_jtfFichero.setSize(600,25);
		m_jtfFichero.setVisible(false);
		getContentPane().add(m_jtfFichero);

		m_jlbAnalizando = new JLabel("Analisis:");
		m_jlbAnalizando.setLocation(40,110);
		m_jlbAnalizando.setSize(70,25);
		m_jlbAnalizando.setVisible(false);
		getContentPane().add(m_jlbAnalizando);

    barraProgreso = new JProgressBar(0,100);
	  barraProgreso.setValue(0);
    barraProgreso.setStringPainted(true);
    barraProgreso.setLocation(120,110);
    barraProgreso.setSize(710,25);
		barraProgreso.setVisible(false);
    this.add(barraProgreso);

		//vvv: cambia el nombre de la variable a m_jtpTexto
    m_jtpTexto = new JTextPane();
		m_jspScrollTexto = new JScrollPane(m_jtpTexto);
		m_jspScrollTexto.setLocation(20,200);
		m_jspScrollTexto.setSize(980,400);
		m_jspScrollTexto.setVisible(false);
		getContentPane().add(m_jspScrollTexto);

    String textoInicial = "<html>\n<body bgcolor=\"EEEEEE\">\n" +
                "<b>Webs Principales:</b>\n" +
                "<ul>\n" +
                "<li>\n" +
                "<li>\n" +
                "<li>\n" +
                "</ul>\n";

		mejoresResultados = new JEditorPane ("text/html",textoInicial);
		mejoresResultados.setLocation(40,600);
		mejoresResultados.setSize(650,100);
		mejoresResultados.setBorder(null);
		mejoresResultados.setEditable(false);
		mejoresResultados.addHyperlinkListener(this);
		mejoresResultados.setVisible(false);
		getContentPane().add(mejoresResultados);

		ImageIcon icono = crearIcono("images/sem_vacio.png");
		semaforo = new JLabel(icono);
		semaforo.setLocation(660,50);
		semaforo.setSize(500,150);
		semaforo.setVisible(false);
		getContentPane().add(semaforo);

		m_jbtResultadosAvanzados = new JButton("Resultados avanzados");
		m_jbtResultadosAvanzados.setLocation(820,620);
		m_jbtResultadosAvanzados.setSize(180,30);
		m_jbtResultadosAvanzados.addMouseListener(this);
		m_jbtResultadosAvanzados.setVisible(false);
		m_jbtResultadosAvanzados.addActionListener(this);
		getContentPane().add(m_jbtResultadosAvanzados);
		
		imagen_logo = crearIcono("images/PlagiumAlert.png");
		logo = new JLabel(imagen_logo);
		logo.setLocation(350,80);
		logo.setSize(500,150);
		logo.setVisible(true);
		getContentPane().add(logo);

    JLabel pruebaaaaaaaaaaaaaaaaaaa = new JLabel("Fichero:");
		pruebaaaaaaaaaaaaaaaaaaa.setVisible(false);
		getContentPane().add(pruebaaaaaaaaaaaaaaaaaaa);

//  ----------------------------------------------------------------------------

    WindowListener wndCloser = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    };
    addWindowListener(wndCloser);

    setVisible(true);
    
    funcion.cargarConfiguracion();

  }

	//*************************************************************************************************************//
	// El metodo "crearBarraMenu" crea la barra de menu (menubar) y la barra de herramientas (toolbar).            //
	//*************************************************************************************************************//
  protected JMenuBar crearBarraMenu() {
    final JMenuBar menuBar = new JMenuBar();

//===========================================================================
//==================== Configuracion de la barra de menu ====================
//===========================================================================

//-------------------------- Primer menu principal --------------------------
    aFile = new JMenu("Archivo");
    aFile.setMnemonic('a');
// Menu "nuevo" dentro del primer menu principal
    ImageIcon iconNew = crearIcono("images/nuevo16x16.png");
    menuNuevo = new JMenuItem("Nuevo",iconNew);
    menuNuevo.setEnabled(true);
    menuNuevo.addActionListener(this);
    aFile.add(menuNuevo);

// Menu "cerrar" dentro del primer menu principal
    ImageIcon iconClose = crearIcono("images/cerrar16x16.png");
    menuCerrar = new JMenuItem("Cerrar",iconClose);
    menuCerrar.setEnabled(false);
    menuCerrar.addActionListener(this);
    aFile.add(menuCerrar);
    
    aFile.addSeparator();
    
// Menu "analizar" dentro del primer menu principal
    ImageIcon iconAnalizar = crearIcono("images/lupa16x16.png");
    menuAnalizar = new JMenuItem("Analizar",iconAnalizar);
    menuAnalizar.setEnabled(false);
    menuAnalizar.addActionListener(this);
    aFile.add(menuAnalizar);
    
    aFile.addSeparator();

// Menu "abrir" dentro del primer menu principal
    ImageIcon iconOpen = crearIcono("images/abrir16x16.png");
    menuAbrir = new JMenuItem("Abrir",iconOpen);
    menuAbrir.setEnabled(true);
    menuAbrir.addActionListener(this);
    aFile.add(menuAbrir);

// Menu "guardar" dentro del primer menu principal
    ImageIcon iconSave = crearIcono("images/guardar16x16.png");
    menuGuardar = new JMenuItem("Guardar",iconSave);
    menuGuardar.setEnabled(false);
    menuGuardar.addActionListener(this);
    aFile.add(menuGuardar);
// Menu "imprimir" dentro del primer menu principal
    ImageIcon iconImprimir = crearIcono("images/imprimir16x16.png");
    menuImprimir = new JMenuItem("Imprimir",iconImprimir);
    menuImprimir.setEnabled(false);
    menuImprimir.addActionListener(this);
    aFile.add(menuImprimir);

    aFile.addSeparator();
// Menu "salir" dentro del primer menu principal
    ImageIcon iconExit = crearIcono("images/exit16x16.png");
    menuSalir = new JMenuItem("Salir",iconExit);
    menuSalir.setEnabled(true);
    menuSalir.addActionListener(this);
    menuSalir.setMnemonic('x');
    aFile.add(menuSalir);
    
    menuBar.add(aFile);

//------------------------- Segundo menu principal -------------------------
    eFile = new JMenu("Edicion");
    eFile.setMnemonic('e');
// Menu "copiar" dentro del segundo menu principal
    ImageIcon iconCopiar = crearIcono("images/copiar16x16.png");
    menuCopiar = new JMenuItem(new DefaultEditorKit.CopyAction());
    menuCopiar.setIcon(iconCopiar);
    menuCopiar.setText("Copiar");
    menuCopiar.setMnemonic(KeyEvent.VK_C);
    menuCopiar.setEnabled(false);
    eFile.add(menuCopiar);
// Menu "cortar" dentro del segundo menu principal
    ImageIcon iconCortar = crearIcono("images/cortar16x16.png");
    menuCortar = new JMenuItem(new DefaultEditorKit.CutAction());
    menuCortar.setIcon(iconCortar);
    menuCortar.setText("Cortar");
    menuCortar.setMnemonic(KeyEvent.VK_T);
    menuCortar.setEnabled(false);
    eFile.add(menuCortar);
// Menu "pegar" dentro del segundo menu principal
    ImageIcon iconPegar = crearIcono("images/pegar16x16.png");
    menuPegar = new JMenuItem(new DefaultEditorKit.PasteAction());
    menuPegar.setIcon(iconPegar);
    menuPegar.setText("Pegar");
    menuPegar.setMnemonic(KeyEvent.VK_P);
    menuPegar.setEnabled(false);
    eFile.add(menuPegar);
    menuBar.add(eFile);

//------------------------- Tercer menu principal -------------------------
    cFile = new JMenu("Configuracion");
    cFile.setMnemonic('c');
// Menu "modo de analisis" dentro del tercer menu principal
    ImageIcon iconModoAnalisis = crearIcono("images/modoAnalisis16x16.png");
    menuModoAnalisis = new JMenuItem("Modo de analisis",iconModoAnalisis);
    menuModoAnalisis.setEnabled(true);
    menuModoAnalisis.addActionListener(this);
    cFile.add(menuModoAnalisis);
// Menu "modo de idioma" dentro del tercer menu principal
    ImageIcon iconIdioma = crearIcono("images/idioma16x16.png");
    menuIdioma = new JMenuItem("Idioma",iconIdioma);
    menuIdioma.setEnabled(true);
    menuIdioma.addActionListener(this);
    cFile.add(menuIdioma);
    menuBar.add(cFile);

//-------------------------- Cuarto menu principal --------------------------
    yFile = new JMenu("Ayuda");
    yFile.setMnemonic('y');
// Menu "indice" dentro del cuarto menu principal
    ImageIcon iconIndice = crearIcono("images/indice16x16.png");
    menuIndice = new JMenuItem("Indice",iconIndice);
    menuIndice.setEnabled(true);
    menuIndice.addActionListener(this);
    yFile.add(menuIndice);
// Menu "acerca de" dentro del cuarto menu principal
    ImageIcon iconAcercaDe = crearIcono("images/acercaDe16x16.png");
    menuAcercaDe = new JMenuItem("Acerca de",iconAcercaDe);
    menuAcercaDe.setEnabled(true);
    menuAcercaDe.addActionListener(this);
    yFile.add(menuAcercaDe);
    menuBar.add(yFile);
//===========================================================================
//================ Configuracion de la barra de herramientas ================
//===========================================================================
    toolBar = new JToolBar();
//------------------------------ Icono "nuevo" ------------------------------
    ImageIcon iconoNuevo = crearIcono("images/nuevo32x32.png");
		botonNuevo = new JButton(iconoNuevo);
		botonNuevo.addMouseListener(this);
    toolBar.add(botonNuevo);
    botonNuevo.setToolTipText("Nuevo");
//------------------------------ Icono "abrir" ------------------------------
    ImageIcon iconoAbrir = crearIcono("images/abrir32x32.png");
		botonAbrir = new JButton(iconoAbrir);
		botonAbrir.setEnabled(true);
		botonAbrir.addMouseListener(this);
    toolBar.add(botonAbrir);
    botonAbrir.setToolTipText("Abrir");
//----------------------------- Icono "guardar" -----------------------------
    ImageIcon iconoGuardar = crearIcono("images/guardar32x32.png");
		botonGuardar = new JButton(iconoGuardar);
		botonGuardar.setEnabled(false);
		botonGuardar.addMouseListener(this);
    toolBar.add(botonGuardar);
    botonGuardar.setToolTipText("Guardar");
//----------------------------- Icono "imprimir" ----------------------------
    ImageIcon iconoImprimir = crearIcono("images/imprimir32x32.png");
		botonImprimir = new JButton(iconoImprimir);
		botonImprimir.setEnabled(false);
		botonImprimir.addMouseListener(this);
    toolBar.add(botonImprimir);
    botonImprimir.setToolTipText("Imprimir");
//------------------------------ Icono "lupa" -------------------------------
    ImageIcon iconoAnalizar = crearIcono("images/lupa32x32.png");
		botonAnalizar = new JButton(iconoAnalizar);
		botonAnalizar.addMouseListener(this);
		botonAnalizar.setEnabled(false);
    toolBar.add(botonAnalizar);
    botonAnalizar.setToolTipText("Analizar");

//    ActionListener fontListener = new ActionListener() {
//      public void actionPerformed(ActionEvent e) {} };

    getContentPane().add(toolBar, BorderLayout.NORTH);
//===========================================================================
    return menuBar;
  }

	//*************************************************************************************************************//
	// El metodo "onModoAnalisis" crea una ventana para establecer el modo de analisis (rapido, normal o           //
  // exhaustivo) y el limite en las busquedas.                                                                   //
	//*************************************************************************************************************//
  void onModoAnalisis(){
    new ModoAnalisis(this,nivelBusqueda,idiomaBusqueda,hayLimite,segundosLimite);
  }
  
	//*************************************************************************************************************//
	// El metodo "onIdioma" crea una ventana para establecer el idioma de busqueda (ingles o español).             //
	//*************************************************************************************************************//
  void onIdioma(){
    new Idioma(this,idiomaBusqueda);
  }

	//*************************************************************************************************************//
	// El metodo "Guardar" crea una ventana para establecer las opciones a guardar.                                //
	//*************************************************************************************************************//
  void onGuardar(){
    new Guardar(this,idiomaBusqueda);
  }

	//*************************************************************************************************************//
	// El metodo "onModoAnalisis" crea una ventana para establecer el modo de analisis (rapido, normal o           //
  // exhaustivo) y el limite en las busquedas.                                                                   //
	//*************************************************************************************************************//
  void onIndice(){
    new Indice(this,idiomaBusqueda);
  }

	//*************************************************************************************************************//
	// El metodo "crearIcono" devuelve el icono del archivo que se pasa como parametro en la variable "path".      //
	//*************************************************************************************************************//
    protected ImageIcon crearIcono(String path) {
        java.net.URL imgURL = AntiCopia.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	//*************************************************************************************************************//
	// El metodo "crearImagen" devuelve la imagen del archivo que se pasa como parametro en la variable "path".    //
	//*************************************************************************************************************//
    protected Image crearImagen(String path) {
        java.net.URL imgURL = AntiCopia.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }
    
	//*************************************************************************************************************//
	// El metodo "crearImagen" devuelve la imagen del archivo que se pasa como parametro en la variable "path".    //
	//*************************************************************************************************************//
    void estableceIdioma(String idioma) {
        if (idiomaBusqueda=="en")
        {
          m_jlbFichero.setText("File:");
          m_jbtExaminar.setText("Browser...");
          m_jlbAnalizando.setText("Check:");
          String textoInicial = "<html>\n<body bgcolor=\"EEEEEE\">\n" +
                "<b>Main Webs:</b>\n" +
                "<ul>\n" +
                "<li>\n" +
                "<li>\n" +
                "<li>\n" +
                "</ul>\n";
          mejoresResultados.setText(textoInicial);
          m_jbtResultadosAvanzados.setText("Advanced results");
          
          aFile.setText("File");
          menuNuevo.setText("New");
          menuCerrar.setText("Close");
          menuAnalizar.setText("Check");
          menuAbrir.setText("Open");
          menuGuardar.setText("Save");
          menuImprimir.setText("Print");
          menuSalir.setText("Exit");
          eFile.setText("Edit");
          menuCopiar.setText("Copy");
          menuCortar.setText("Cut");
          menuPegar.setText("Paste");
          cFile.setText("Tools");
          menuModoAnalisis.setText("Configure check");
          menuIdioma.setText("Language");
          yFile.setText("Help");
          menuIndice.setText("Index");
          menuAcercaDe.setText("About");
          
          botonNuevo.setToolTipText("New");
          botonAbrir.setToolTipText("Open");
          botonGuardar.setToolTipText("Save");
          botonImprimir.setToolTipText("Print");
          botonAnalizar.setToolTipText("check");
          
          aFile.setMnemonic('f');
          eFile.setMnemonic('e');
          cFile.setMnemonic('t');
          yFile.setMnemonic('h');
        }
        else
        {
          m_jlbFichero.setText("Fichero:");
          m_jbtExaminar.setText("Examinar...");
          m_jlbAnalizando.setText("Analisis:");
          String textoInicial = "<html>\n<body bgcolor=\"EEEEEE\">\n" +
                "<b>Webs Principales:</b>\n" +
                "<ul>\n" +
                "<li>\n" +
                "<li>\n" +
                "<li>\n" +
                "</ul>\n";
          mejoresResultados.setText(textoInicial);
          m_jbtResultadosAvanzados.setText("Resultados avanzados");

          aFile.setText("Archivo");
          menuNuevo.setText("Nuevo");
          menuCerrar.setText("Cerrar");
          menuAnalizar.setText("Analisis");
          menuAbrir.setText("Abrir");
          menuGuardar.setText("Guardar");
          menuImprimir.setText("Imprimir");
          menuSalir.setText("Salir");
          eFile.setText("Edicion");
          menuCopiar.setText("Copiar");
          menuCortar.setText("Cortar");
          menuPegar.setText("Pegar");
          cFile.setText("Configuracion");
          menuModoAnalisis.setText("Modo de analisis");
          menuIdioma.setText("Idioma");
          yFile.setText("Ayuda");
          menuIndice.setText("Indice");
          menuAcercaDe.setText("Acerca de");
          
          botonNuevo.setToolTipText("Nuevo");
          botonAbrir.setToolTipText("Abrir");
          botonGuardar.setToolTipText("Guardar");
          botonImprimir.setToolTipText("Imprimir");
          botonAnalizar.setToolTipText("Analizar");
          
          aFile.setMnemonic('a');
          eFile.setMnemonic('e');
          cFile.setMnemonic('c');
          yFile.setMnemonic('y');

        }
    }

	//*************************************************************************************************************//
    class Task extends SwingWorker<Void, Void> {

        public void EstableceProceso(int proceso){
          setProgress(Math.min(proceso, 100));
        }

        @Override
        public Void doInBackground() {

            BufferedReader textoAbuscar = new BufferedReader(new StringReader(m_jtpTexto.getText()));
            Analiza analizaFich= new Analiza();

            BufferedReader textoAbuscar2= textoAbuscar;
            String cadenaAux="";
            textoOrigen="";
            try{
            while ((cadenaAux = textoAbuscar2.readLine())!=null)
              if (cadenaAux.length()!=0)
                {
                  numeroParrafos++;
                  textoOrigen= textoOrigen + cadenaAux+ "\n";
                }
            } catch (Exception e){}
            textoAbuscar = new BufferedReader(new StringReader(textoOrigen));

            setProgress(0);
            int saltoDeProceso= 100/numeroParrafos;

            try
            {
              analizaFich.analisisParrafo(this,textoAbuscar,idiomaBusqueda,nivelBusqueda,saltoDeProceso,hayLimite,segundosLimite);
              if (!task.isCancelled())
              {
                EstableceProceso(100);
                m_jbtResultadosAvanzados.setEnabled(true);
                botonImprimir.setEnabled(true);
                menuImprimir.setEnabled(true);
                ResultadosAvanzados= analizaFich.resultadoDetallado();

                String textoFinal= "<html>\n<body bgcolor=\"EEEEEE\">\n";
                if (idiomaBusqueda=="es")
                    textoFinal += "<b>Webs Principales:</b>\n";
                else textoFinal += "<b>Main Webs:</b>\n";
                textoFinal+= "<ul>\n";
		            for (int j=0;j<3;j++)
                    {
                      if (analizaFich.urlFinales[j]!=null)
                          {
                            textoFinal+= "<li> <a href=\""+analizaFich.urlFinales[j]+"\">"+analizaFich.urlFinales[j]+"</a>\n";
                            if (j==0)
                                {
                                  url0= analizaFich.urlFinales[j];
                                  porcentajeUrl0= (analizaFich.aparicionesUrlFinales[j]*100)/analizaFich.numeroBusquedasDocumento;
                                }
                            else if (j==1)
                                {
                                  url1= analizaFich.urlFinales[j];
                                  porcentajeUrl1= (analizaFich.aparicionesUrlFinales[j]*100)/analizaFich.numeroBusquedasDocumento;
                                }
                            else
                                {
                                  url2= analizaFich.urlFinales[j];
                                  porcentajeUrl2= (analizaFich.aparicionesUrlFinales[j]*100)/analizaFich.numeroBusquedasDocumento;
                                }
                          }
                      else textoFinal+= "<li>\n";
                    }
                textoFinal+= "</ul>\n";
                mejoresResultados.setText(textoFinal);
                //////////////////////////////////////////////////////////////////////////////

                if (analizaFich.numeroBusquedasDocumento==0)
                  analizaFich.numeroBusquedasDocumento= 1;

                porcentajeUrl0= (analizaFich.aparicionesUrlFinales[0]*100)/analizaFich.numeroBusquedasDocumento;
                porcentajeUrl1= (analizaFich.aparicionesUrlFinales[1]*100)/analizaFich.numeroBusquedasDocumento;
                porcentajeUrl2= (analizaFich.aparicionesUrlFinales[2]*100)/analizaFich.numeroBusquedasDocumento;

                if ((porcentajeUrl0>=80)||(porcentajeUrl1>=80)||(porcentajeUrl2>=80))
                    semaforoFinal = crearIcono("images/sem_rojo.png");
                else if ((porcentajeUrl0>=40)||(porcentajeUrl1>=40)||(porcentajeUrl2>=40))
                        semaforoFinal = crearIcono("images/sem_ambar.png");
                else semaforoFinal = crearIcono("images/sem_verde.png");
			          semaforo.setIcon(semaforoFinal);

                textoHTML= htmlGuardado(false);
                textoHTMLimprimir= htmlGuardado(true);

                menuGuardar.setEnabled(true);
                botonGuardar.setEnabled(true);
			        }
			        else
			        {
               System.out.println("Se ha cancelado");
              }
             

            }
            catch (InterruptedException ignore) {}

            catch (ExcepcionGoogle e){
              if (idiomaBusqueda=="es")
                funcion.mostrarDialogoError("Error con la conexion con Google");
              else funcion.mostrarDialogoError("Error with the Google's conexion");
            }
            catch (Exception e){
              System.out.println(e);
            }
          return null;
        }
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            analizando= false;
            botonAnalizar.setIcon(iconoAnalizar);
            setCursor(null);
            if (progreso>=100)
           	    {ImageIcon icono2 = crearIcono("images/sem_rojo.png");
			           semaforo.setIcon(icono2);
                }
        }
    }

	//*************************************************************************************************************//
    public void propertyChange(PropertyChangeEvent evt) {
        if (("progress" == evt.getPropertyName())&&(analizando)) {
            int progress = (Integer) evt.getNewValue();
            barraProgreso.setValue(progress);
        }
    }

	//*************************************************************************************************************//
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
                  funcion.mostrarDialogoError("Es imposible localizar su navegador por defecto.");
              else funcion.mostrarDialogoError("Your default browser have not been found.");
            }
        }
        catch (Exception e) {}
      }

    }

	//*************************************************************************************************************//
    String htmlGuardado(boolean paraImprimir) {

      return funcion.resultadosAmostrar(ResultadosAvanzados,url0,url1,url2,porcentajeUrl0,porcentajeUrl1,porcentajeUrl2,paraImprimir);
    }

	//*************************************************************************************************************//
    public void onAnalizar() {
        analizando= true;
        progreso= 0;
        numeroParrafos= 0;
        ResultadosAvanzados= "";
	  	  menuGuardar.setEnabled(false);
        botonGuardar.setEnabled(false);
        barraProgreso.setValue(0);
        m_jbtResultadosAvanzados.setEnabled(false);
      	ImageIcon icono3 = crearIcono("images/sem_vacio.png");
			  semaforo.setIcon(icono3);
    	  String textoInicial = "<html>\n<body bgcolor=\"EEEEEE\">\n";
        if (idiomaBusqueda=="es")
            textoInicial+= "<b>Webs Principales:</b>\n";
        else textoInicial+= "<b>Main Webs:</b>\n";
        textoInicial+= "<ul>\n <li>\n <li>\n <li>\n </ul>\n";
        mejoresResultados.setText(textoInicial);

		    botonAnalizar.setIcon(iconoParar);
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }
	//*************************************************************************************************************//
	
    private int mostrarAvisoParar(){

    Object[] opcionesEs = {"Si","No"};
    Object[] opcionesEn = {"Yes","No"};

    int valor=1;
    if (idiomaBusqueda=="es")
        valor = JOptionPane.showOptionDialog(this,
        "¿Estas seguro que quieres parar la busqueda?.\n",
        "Aviso",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcionesEs,opcionesEs[1]);
    else
        valor = JOptionPane.showOptionDialog(this,
        "Are you sure you want stop the search?.\n",
        "Warning",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcionesEn,opcionesEn[1]);

    return valor;
    }
	//*************************************************************************************************************//
	
    public void onPararAnalisis(){

      if (mostrarAvisoParar()==0)    // Se ha aceptado el menu de aviso
        {
          analizando= false;
          botonAnalizar.setIcon(iconoAnalizar);
          task.cancel(true);
        }
    }

	//*************************************************************************************************************//
    public void onImprimir(){

        setSystemImagePath(SystemImagePath);
        JEditorPane editorPane = new JEditorPane();
        MyHTMLEditorKit kit = new MyHTMLEditorKit();
        editorPane.setEditorKit(kit);
        textoHTMLimprimir= funcion.htmlParaImprimir(textoHTML);

        Document doc = editorPane.getDocument();
        StringReader reader = new StringReader(textoHTMLimprimir);
        try { kit.read(reader, doc, 0); }
        catch (Exception e) { e.printStackTrace(); }
        
        editorPane.setBorder(null);
        try {
          boolean complete = editorPane.print();
        }
        catch (Exception e){}
    }

  public static final String SystemImagePathKey = "system.image.path.key";
  private static final String SystemImagePath = new File("").getAbsolutePath();
  public void setSystemImagePath(String path) {System.setProperty(SystemImagePathKey, path);}


	//*************************************************************************************************************//
	// Interfaz MouseListener.                                                                                     //
	//*************************************************************************************************************//
	public void mouseClicked(MouseEvent e){

		if (e.getSource() == botonNuevo){
      funcion.onNuevo();
		}
    else if (e.getSource() == botonAbrir){
      funcion.onAbrir();
    }
    else if (e.getSource() == botonGuardar){
      onGuardar();
    }
    else if (e.getSource() == botonImprimir){
      onImprimir();
    }
    else if ((e.getSource() == botonAnalizar)&&(!analizando)){
      onAnalizar();
    }
    else if ((e.getSource() == botonAnalizar)&&(analizando)){
      onPararAnalisis();
    }
	}

	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void actionPerformed(ActionEvent e){
    if (e.getSource() == menuNuevo){
      funcion.onNuevo();
    }
    else if (e.getSource() == menuCerrar){
      funcion.onCerrar();
    }
    else if ((e.getSource() == menuAnalizar)&&(!analizando)){
      onAnalizar();
    }
    else if ((e.getSource() == menuAnalizar)&&(analizando)){
      onPararAnalisis();
    }
    else if (e.getSource() == menuAbrir){
      funcion.onAbrir();
    }
    else if (e.getSource() == menuGuardar){
      onGuardar();
    }
    else if (e.getSource() == menuImprimir){
      onImprimir();
    }
    else if (e.getSource() == menuSalir){
      System.exit(0);
    }
    else if (e.getSource() == menuModoAnalisis){
      onModoAnalisis();
    }
    else if (e.getSource() == menuIdioma){
      onIdioma();
    }
    else if (e.getSource() == menuIndice){
      onIndice();
    }
    else if (e.getSource() == menuAcercaDe){

      if (idiomaBusqueda=="es")
          JOptionPane.showMessageDialog(this,
          "                   Plagium Alert v1.0\n"+"         - Proyecto de Fin de Carrera -\n\n"+"Alumno: Pablo García Arévalo\n"
          +"Tutor: Jorge Ramió Aguirre\n\n"+"Ingeniería Técnica en Informática de Gestión\n"
          +"Escuela Universitaria de Informática\n"+"Universidad Politécnica de Madrid\n"
          ," Acerca de",JOptionPane.INFORMATION_MESSAGE,iconoAnalizar);
      else
          JOptionPane.showMessageDialog(this,
          "                   Plagium Alert v1.0\n"+"         - Proyecto de Fin de Carrera -\n\n"+"Student: Pablo García Arévalo\n"
          +"Teacher: Jorge Ramió Aguirre\n\n"+"Ingeniería Técnica en Informática de Gestión\n"
          +"Escuela Universitaria de Informática\n"+"Universidad Politécnica de Madrid\n"
          ," About",JOptionPane.INFORMATION_MESSAGE,iconoAnalizar);


    }
	  else if (e.getSource() == m_jbtExaminar){
     funcion.onExaminar();
	  }
    else if (e.getSource() == m_jbtResultadosAvanzados){
      new ResultadosAvanzados(this,idiomaBusqueda,textoHTML);
    }

  }

	//*************************************************************************************************************//
	// El metodo principal "main" crea el objeto "AntiCopia" para crear el entorno grafico.                        //
	//*************************************************************************************************************//
  public static void main(String argv[]) {
    new AntiCopia();
  }
}


