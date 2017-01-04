import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Idioma extends JDialog implements ActionListener,MouseListener {

	private static final long serialVersionUID = 1L;

	JButton	confirmar;                // Boton de confirmacion de la ventana de configuracion "Modo de analisis".
    ButtonGroup bg1;                   // Grupo de botones para la seleccion del idioma de las busquedas.
    JRadioButton rb0, rb1;       // Opciones que se pueden escoger del grupo de botones del idioma de las busquedas.
    String idiomaBusqueda;                // Variable de tipo string en el que se almacena el idioma de busqueda que se establecera.
    JDialog ventana;                     // Ventana grafica en la que se muestran las opciones a realizar.
    AntiCopia ventanaPrincipal;       // Objeto de tipo "AntiCopia" en la que se almacena el objeto padre para poder llamar al metodo
                                      // nuevoNivelBusqueda(int) para devolverle el valor de "nivelBusqueda" modificado.
    JPanel panel;

    public Idioma(AntiCopia padre,String idiomaBusquedaAntigua){

        // Se inicializan las variables globales "idiomaBusqueda" y "ventanaPrincipal"
        // con los parametros recibidos "idiomaBusquedaAntigua" y "padre" respectivamente.
        idiomaBusqueda= idiomaBusquedaAntigua;
        ventanaPrincipal= padre;

        // Se crea el objeto "ventana" de tipo "JDialog", que es el objeto principal de esta clase, y se establecen
        // algunas propiedades basicas (titulo, icono de la barra y forma de cerrarse por defecto).
        ventana= this;
        if (idiomaBusqueda=="es")
            setTitle("Idioma");
        else setTitle("Language");
        setIconImage(ventanaPrincipal.crearImagen("images/lupa16x16.png"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panel = new JPanel();

	//*************************************************************************************************************//
        JPanel panelIdiomaBusqueda = new JPanel();

        // Se crea el objeto "bg1" del grupo de botones.
        bg1 = new ButtonGroup();

        // Se crea el primer boton "rb0" permitiendo que se recogan los eventos generados por el raton en el.
        // En caso que el idioma sea "es" (español) aparecera seleccionado. Se añade al grupo de botones "bg1".
        rb0 = new JRadioButton();
        if (idiomaBusqueda=="es")
            {
              rb0.setText("Español");
              rb0.setSelected(true);
            }
        else rb0.setText("Spanish");
        rb0.addMouseListener(this);
        bg1.add(rb0);

        // Se crea el primer boton "rb1" permitiendo que se recogan los eventos generados por el raton en el.
        // En caso que el idioma sea "en" (ingles) aparecera seleccionado. Se añade al grupo de botones "bg1".
        rb1 = new JRadioButton();
        if (idiomaBusqueda=="es")
            rb1.setText("Ingles");
        else {
              rb1.setText("English");
              rb1.setSelected(true);
             }
        rb1.addMouseListener(this);
        bg1.add(rb1);

        Box caja = Box.createVerticalBox();
        caja.add(rb0);
        caja.add(rb1);

        if (idiomaBusqueda=="es")
            panelIdiomaBusqueda.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Escoga el idioma "),BorderFactory.createEmptyBorder(5,5,5,5)));
        else panelIdiomaBusqueda.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Choose the language "),BorderFactory.createEmptyBorder(5,5,5,5)));
        panelIdiomaBusqueda.add(caja);
	//*************************************************************************************************************//

        JPanel panelConfirmacion = new JPanel();

        JButton confirmar = new JButton("OK");
        confirmar.addActionListener(new ActionListener() {

          // Si se recibe una accion sobre el objeto "confirmar" se llamara al metodo "salvarCambios" para que escriba en el fichero
          // "config" el nivel de busqueda que se haya establecido (en el caso que se haya modificado), se establecera
          // el nuevo "nivelBusqueda" de la clase "AntiCopia" para establecer el nivel de busqueda seleccionado en el
          // padre (ventana principal) y se cerrara la ventana "Nivel de busqueda".
            public void actionPerformed(ActionEvent e)
            {
              // Se salvan los cambios en el fichero "config".
              salvarCambios();
              // Se establece el idioma de busqueda en el objeto padre de la clase "AntiCopia".
              ventanaPrincipal.idiomaBusqueda= idiomaBusqueda;
              ventanaPrincipal.estableceIdioma(idiomaBusqueda);
              // Se cierra la ventana.
              ventana.dispose();
            }
        });

  //*************************************************************************************************************//
        panelConfirmacion.add(confirmar);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(panelIdiomaBusqueda);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(panelConfirmacion);
        panel.add(Box.createGlue());

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setSize(240,180);
        setResizable(false);
        setVisible(true);

    }

	//*************************************************************************************************************//
	// El metodo "salvarCambios" de la clase Idioma.java es el encargado de salvar los cambios del idioma de la    //
	// busqueda en el fichero de configuracion "config".                                                           //
	// Primero se almacena el contenido del fichero en la variable sConfig y despues se salva el contenido, con    //
	// los cambios realizados, de nuevo en el fichero.                                                             //
	//*************************************************************************************************************//
    public void salvarCambios (){

          try{
		      FileInputStream fEntrada = new FileInputStream ("config");    // Se abre el fichero "config" como lectura con la variable "fEntrada".
		      StringBuffer sConfig = new StringBuffer();                    // Variable de tipo "StringBuffer" para almacenar el contenido del
                                                                        //  fichero "config" de forma temporal para hacer las modificaciones.

		      BufferedReader br = new BufferedReader(new InputStreamReader(fEntrada));
		      String sCadena="";

		      // Se leen todas las lineas del fichero y se almacenan en la variable sConfig.		      
		      while ((sCadena = br.readLine())!=null)
		      {sConfig.append(sCadena);}
		      

          // Se cierra el fichero de lectura.
		      fEntrada.close();

          // Se abre el fichero "config" como escritura con la variable "fSalida".
          BufferedWriter fSalida = new BufferedWriter(new FileWriter("config"));

          int indice1=0, indice2=0;      // Variables para crear subStrings que empiecen y terminen en estas posiciones.

          // Bucle en el que se recorre todo el String "sConfig" y se va escribiendo en el fichero fSalida ("config").
          // Se va buscando el caracter "@", que indica final de linea, y se establece esa posicion en la variable "indice2".
          // En la variable "indice1" se tiene el valor, 0 en la primera iteracion, o una posicion mas del valor de "indice2"
          // de la busqueda anterior. Con esto se consigue un subtring que contiene toda una linea dentro del string "sConfig"
          // que empieza y termina en las posiciones "indice1" y "indice2" dentro del String "sConfig".
          while (indice2+1<sConfig.length())
            {
            // Se establece la variable "indice2" como la posicion que ocupa el caracter "@" dentro del String "sConfig"
            // sin contar con los que hubiera antes de la posicion que tiene la variable "indice1".
            indice2= sConfig.indexOf("@",indice1);

            // Si el subString que forma desde la posicion que ocupa la variable "indice1" hasta la posicion que ocupa la variable
            // "indice2" (este subString constituye una linea del fichero "config") empieza con la palabra "idioma" significa que
            // es la linea que establece el idioma de busqueda y, por tanto, ha de ser modificada con el idioma de busqueda que se haya
            // establecido. Notese que hay que volver a escribir el caracter "@" para marcar el final de la linea.
            if (sConfig.substring(indice1,indice2).startsWith("idioma"))
              fSalida.write("idioma="+idiomaBusqueda+"@");
            // En el caso que no empieza por la palabra "nivel" se escribira el contenido que hubiera antes, pues no se desea modificar.
            // Notese que hay que volver a escribir el caracter "@" para marcar el final de la linea.
            else fSalida.write(sConfig.substring(indice1,indice2)+"@");

            // Se realiza un salto de linea en el fichero "config".
            fSalida.newLine();

            // La variable "indice1" toma el valor de la variable "indice2"+1 para analizar la siguiente linea del ficher "config"
            // dentro del bucle while.
            indice1= indice2+1;
            }

          // Se salvan los datos que no se hayan salvado todavia a disco (flush) y se ciera el fichero de escritura.
          fSalida.flush();
          fSalida.close();
	         }
          catch(Exception Error){System.out.println(Error);}
      }

    public void mouseClicked(MouseEvent e){

          // Si el origen es el objeto "rb0" se establecera la variable "idiomaBusqueda" a "es" (Idioma español).
          if (e.getSource() == rb0){
            idiomaBusqueda="es";
          }
          // Si el origen es el objeto "rb1" se establecera la variable "idiomaBusqueda" a "en" (Idioma ingles).
          else if (e.getSource() == rb1){
            idiomaBusqueda="en";
          }

    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void actionPerformed(ActionEvent e){}

}

