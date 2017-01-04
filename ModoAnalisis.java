import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class ModoAnalisis extends JDialog implements ActionListener,MouseListener {

	private static final long serialVersionUID = 1L;
	
	JButton	confirmar;                // Boton de confirmacion de la ventana de configuracion "Modo de analisis".
    ButtonGroup bg;                   // Grupo de botones para la seleccion del modo de analisis de las busquedas.
    JRadioButton rb0, rb1, rb2;       // Opciones que se pueden escoger del grupo de botones.
    JCheckBox busquedaLimitada;
    JTextField segundosPorBusqueda;
    int nivelBusqueda;                // Variable de tipo entero en el que se almacena el nivel de busqueda que se establecera.
    JDialog ventana;                     // Ventana grafica en la que se muestran las opciones a realizar.
    AntiCopia ventanaPrincipal;       // Objeto de tipo "AntiCopia" en la que se almacena el objeto padre para poder llamar al metodo
                                      // nuevoNivelBusqueda(int) para devolverle el valor de "nivelBusqueda" modificado.
    JPanel panel;
    boolean hayLimiteBusqueda;
    int segundosLimiteBusqueda;

    public ModoAnalisis(AntiCopia padre,int nivelBusquedaAntigua,String idiomaBusqueda,boolean hayLimite,int segundosLimite){

        if (idiomaBusqueda=="es")
            setTitle("Modo de analisis");
        else setTitle("Configure check");
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ventanaPrincipal= padre;
        nivelBusqueda= nivelBusquedaAntigua;
        hayLimiteBusqueda= hayLimite;
        segundosLimiteBusqueda= segundosLimite;
        
        ventana= this;
        ventana.setIconImage(ventanaPrincipal.crearImagen("images/lupa16x16.png"));

        panel = new JPanel();
        JPanel panelMetodoAnalisis = new JPanel();
        
        // Se crea el objeto "bg" del grupo de botones.
        bg = new ButtonGroup();

        // Se crea el primer boton "rb0" permitiendo que se recogan los eventos generados por el raton en el.
        // En caso que el nivel de busqueda sea 0 (rapida) aparecera seleccionado. Se añade al grupo de botones "bg".
        rb0 = new JRadioButton();
        if (idiomaBusqueda=="es")
            rb0.setText("Busqueda rapida");
        else rb0.setText("Tiny search");
        if (nivelBusqueda==0)
          rb0.setSelected(true);
        rb0.addMouseListener(this);
        bg.add(rb0);

        // Se crea el segundo boton "rb1" permitiendo que se recogan los eventos generados por el raton en el.
        // En caso que el nivel de busqueda sea 1 (normal) aparecera seleccionado. Se añade al grupo de botones "bg".
        rb1 = new JRadioButton();
        if (idiomaBusqueda=="es")
            rb1.setText("Busqueda normal");
        else rb1.setText("Normal search");
        if (nivelBusqueda==1)
          rb1.setSelected(true);
        rb1.addMouseListener(this);
        bg.add(rb1);

        // Se crea el primer boton "rb2" permitiendo que se recogan los eventos generados por el raton en el.
        // En caso que el nivel de busqueda sea 2 (exhaustiva) aparecera seleccionado. Se añade al grupo de botones "bg".
        rb2 = new JRadioButton();
        if (idiomaBusqueda=="es")
            rb2.setText("Busqueda exhaustiva");
        else rb2.setText("Exhaustive search");
        if (nivelBusqueda==2)
          rb2.setSelected(true);
        rb2.addMouseListener(this);
        bg.add(rb2);
        
        Box caja = Box.createVerticalBox();

        caja.add(rb0);
        caja.add(rb1);
        caja.add(rb2);
        if (idiomaBusqueda=="es")
            panelMetodoAnalisis.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Metodo de analisis "),BorderFactory.createEmptyBorder(5,5,5,5)));
        else panelMetodoAnalisis.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Check type "),BorderFactory.createEmptyBorder(5,5,5,5)));
        panelMetodoAnalisis.add(caja);

        JPanel panelLimiteAnalisis = new JPanel();
        busquedaLimitada= new JCheckBox();
        if (idiomaBusqueda=="es")
            busquedaLimitada.setText("Limitar el numero de segundos por busqueda");
        else busquedaLimitada.setText("Limit the seconds by search");

        if (hayLimiteBusqueda)
            busquedaLimitada.setSelected(true);
        else busquedaLimitada.setSelected(false);

        busquedaLimitada.addMouseListener(this);
      
        JLabel etiqSegundosPorBusqueda = new JLabel();
        if (idiomaBusqueda=="es")
            etiqSegundosPorBusqueda.setText("Numero de segundos por busqueda:");
        else etiqSegundosPorBusqueda.setText("Seconds number by search:");

        segundosPorBusqueda = new JTextField(2);

        segundosPorBusqueda.setText(Integer.toString(segundosLimiteBusqueda));
        segundosPorBusqueda.addMouseListener(this);
        
        if (hayLimiteBusqueda)
            segundosPorBusqueda.setEnabled(true);
        else segundosPorBusqueda.setEnabled(false);

        panelLimiteAnalisis.add(busquedaLimitada);
        panelLimiteAnalisis.add(etiqSegundosPorBusqueda);
        panelLimiteAnalisis.add(segundosPorBusqueda);
        if (idiomaBusqueda=="es")
            panelLimiteAnalisis.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Limite de busquedas "),BorderFactory.createEmptyBorder(5,5,5,5)));
        else panelLimiteAnalisis.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Limit of search "),BorderFactory.createEmptyBorder(5,5,5,5)));
        JPanel panelConfirmacion = new JPanel();

        JButton confirmar = new JButton("OK");
        confirmar.addActionListener(new ActionListener() {

          // Si se recibe una accion sobre el objeto "confirmar" se llamara al metodo "salvarCambios" para que escriba en el fichero
          // "config" el nivel de busqueda que se haya establecido (en el caso que se haya modificado), se establecera
          // el nuevo "nivelBusqueda" de la clase "AntiCopia" para establecer el nivel de busqueda seleccionado en el
          // padre (ventana principal) y se cerrara la ventana "Nivel de busqueda".
            public void actionPerformed(ActionEvent e)
            {

              segundosLimiteBusqueda= Integer.parseInt(segundosPorBusqueda.getText().trim());

              // Se salvan los cambios en el fichero "config".
              salvarCambios();

              // Se establece el nivel de busqueda, si existe limite de busqueda y los segundos de este limite en el objeto padre
              // de la clase "AntiCopia".
              ventanaPrincipal.nivelBusqueda= nivelBusqueda;
              ventanaPrincipal.hayLimite= hayLimiteBusqueda;
              ventanaPrincipal.segundosLimite= segundosLimiteBusqueda;

              // Se cierra la ventana.
              ventana.dispose();
            }
        });
      
        panelConfirmacion.add(confirmar);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(panelMetodoAnalisis);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(panelLimiteAnalisis);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(panelConfirmacion);
        panel.add(Box.createGlue());

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setSize(320,340);
        setResizable(false);
        setVisible(true);

    }
    
  //*************************************************************************************************************//
	// El metodo "salvarCambios" de la clase Configuracion.java es el encargado de salvar los cambios del nivel de //
	// de busqueda en el fichero de configuracion "config".                                                        //
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
            // "indice2" (este subString constituye una linea del fichero "config") empieza con la palabra "nivel" significa que
            // es la linea que establece el nivel de busqueda y, por tanto, ha de ser modificada con el nivel de busqueda que se haya
            // establecido. Notese que hay que volver a escribir el caracter "@" para marcar el final de la linea.
            if (sConfig.substring(indice1,indice2).startsWith("nivel"))
              fSalida.write("nivel="+nivelBusqueda+"@");

            // Si el subString empieza con la palabra "hayLimite" significa que es la linea que establece si hay limite de busqueda
            // y, por tanto, ha de ser modificada con el valor del limite de busqueda que se haya establecido. Notese que hay que
            // volver a escribir el caracter "@" para marcar el final de la linea.
            else if (sConfig.substring(indice1,indice2).startsWith("hayLimite"))
                    if (hayLimiteBusqueda)
                        fSalida.write("hayLimite=0@");
                    else fSalida.write("hayLimite=1@");

            // Si el subString empieza con la palabra "segundosLimite" significa que es la linea que establece el numero de segundos
            // del limite de busqueda y, por tanto, ha de ser modificada con el valor de los segundos para el limite de busqueda que se
            // haya establecido. Notese que hay que volver a escribir el caracter "@" para marcar el final de la linea.
            else if (sConfig.substring(indice1,indice2).startsWith("segundosLimite"))
              fSalida.write("segundosLimite="+segundosLimiteBusqueda+"@");
              
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
    
    private int mostrarAvisoCambio(){

    Object[] opcionesEs = {"Si","No"};
    Object[] opcionesEn = {"Yes","No"};
    int valor=1;

    if (ventanaPrincipal.idiomaBusqueda=="es")
        valor = JOptionPane.showOptionDialog(ventana,
        "¿Estas seguro que deseas deshabilitar el limite de busquedas?\n"+"Este cambio puede producir bloqueos en las busquedas.\n",
        "Aviso",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcionesEs,opcionesEs[1]);
    else
        valor = JOptionPane.showOptionDialog(ventana,
        "Are you sure you want to disable the limit of search?\n"+"This change can block the searchs.\n",
        "Warning",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcionesEn,opcionesEn[1]);

    return valor;
    }

    public void mouseClicked(MouseEvent e){

          if (e.getSource() == busquedaLimitada){
            if (!busquedaLimitada.isSelected())
                {
                  if (mostrarAvisoCambio()==0)    // Se ha aceptado el menu de aviso
                    {
                      hayLimiteBusqueda= false;
                      segundosPorBusqueda.setEnabled(false);
                    }
                  else                            // No se ha aceptado el menu de aviso
                    {
                      hayLimiteBusqueda= true;
                      busquedaLimitada.setSelected(true);
                      segundosPorBusqueda.setEnabled(true);
                    }
                }
            else
                {
                  hayLimiteBusqueda= true;
                  segundosPorBusqueda.setEnabled(true);
                }
          }
          // Si el origen es el objeto "rb0" se establecera la variable "nivelBusqueda" a 0 (Busqueda rapida).
          else if (e.getSource() == rb0){
            nivelBusqueda=0;
          }
          // Si el origen es el objeto "rb1" se establecera la variable "nivelBusqueda" a 1 (Busqueda normal).
          else if (e.getSource() == rb1){
            nivelBusqueda=1;
          }
          // Si el origen es el objeto "rb2" se establecera la variable "nivelBusqueda" a 2 (Busqueda exhaustiva).
          else if (e.getSource() == rb2){
            nivelBusqueda=2;
          }

    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void actionPerformed(ActionEvent e){}

}

