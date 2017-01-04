import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Guardar extends JDialog implements ActionListener,MouseListener {

	private static final long serialVersionUID = 1L;

	  JButton	confirmar;                // Boton de confirmacion de la ventana de configuracion "Modo de analisis".

    JLabel etiqNombre, etiqApellidos, etiqIdentificador;
    JLabel etiqTitulo, etiqAsignatura, etiqMes, etiqAnio;

    JTextField nombre, apellidos, identificador;
    JTextField titulo, asignatura, mes, anio;

    JButton botonGuardar;

    AntiCopia ventanaPrincipal;       // Objeto de tipo "AntiCopia" en la que se almacena el objeto padre para poder llamar al metodo
                                      // nuevoNivelBusqueda(int) para devolverle el valor de "nivelBusqueda" modificado.
    JPanel panel;
    JDialog ventana;

    public Guardar(AntiCopia padre, String idiomaBusqueda){

        if (idiomaBusqueda=="es")
            setTitle("Guardar");
        else setTitle("Save");
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ventanaPrincipal= padre;
        ventana= this;
        ventana.setIconImage(ventanaPrincipal.crearImagen("images/lupa16x16.png"));

        panel = new JPanel();
//------------------------------------------------------------------------------------
        JPanel panelAutor = new JPanel();

        etiqNombre = new JLabel();
        nombre = new JTextField(20);
        nombre.setText(ventanaPrincipal.nombreGuardado);
        etiqApellidos = new JLabel();
        apellidos = new JTextField(30);
        apellidos.setText(ventanaPrincipal.apellidosGuardado);
        etiqIdentificador = new JLabel();
        identificador = new JTextField(10);
        identificador.setText(ventanaPrincipal.identificadorGuardado);
        if (idiomaBusqueda=="es")
            {
              etiqNombre.setText("Nombre: ");
              etiqApellidos.setText("Apellidos: ");
              etiqIdentificador.setText("Identificador: ");
            }
        else
            {
              etiqNombre.setText("Name: ");
              etiqApellidos.setText("Surname: ");
              etiqIdentificador.setText("Identifier: ");
            }

        JPanel caja1 = new JPanel(new GridLayout(0,1));
        caja1.add(etiqNombre);
        caja1.add(etiqApellidos);
        caja1.add(etiqIdentificador);

        JPanel caja2 = new JPanel(new GridLayout(0,1));
        caja2.add(nombre);
        caja2.add(apellidos);
        caja2.add(identificador);

        panelAutor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelAutor.add(caja1, BorderLayout.CENTER);
        panelAutor.add(caja2, BorderLayout.LINE_END);

        if (idiomaBusqueda=="es")
            panelAutor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Autor "),BorderFactory.createEmptyBorder(5,5,5,5)));
        else panelAutor.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Author "),BorderFactory.createEmptyBorder(5,5,5,5)));
//------------------------------------------------------------------------------------

        JPanel panelDocumento = new JPanel();

        etiqTitulo = new JLabel();
        titulo = new JTextField(30);
        titulo.setText(ventanaPrincipal.tituloGuardado);
        etiqAsignatura = new JLabel();
        asignatura = new JTextField(30);
        asignatura.setText(ventanaPrincipal.asignaturaGuardado);
        etiqMes = new JLabel();
        mes = new JTextField(10);
        mes.setText(ventanaPrincipal.mesGuardado);
        etiqAnio = new JLabel();
        anio = new JTextField(4);
        anio.setText(ventanaPrincipal.anioGuardado);

        if (idiomaBusqueda=="es")
            {
              etiqTitulo.setText("Titulo: ");
              etiqAsignatura.setText("Asignatura: ");
              etiqMes.setText("Mes: ");
              etiqAnio.setText("Año: ");
            }
        else
            {
              etiqTitulo.setText("Title: ");
              etiqAsignatura.setText("Subject: ");
              etiqMes.setText("Month: ");
              etiqAnio.setText("Year: ");
            }

        JPanel caja3 = new JPanel(new GridLayout(0,1));
        caja3.add(etiqTitulo);
        caja3.add(etiqAsignatura);
        caja3.add(etiqMes);
        caja3.add(etiqAnio);

        JPanel caja4 = new JPanel(new GridLayout(0,1));
        caja4.add(titulo);
        caja4.add(asignatura);
        caja4.add(mes);
        caja4.add(anio);

        panelDocumento.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelDocumento.add(caja3, BorderLayout.CENTER);
        panelDocumento.add(caja4, BorderLayout.LINE_END);

        if (idiomaBusqueda=="es")
            panelDocumento.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Documento "),BorderFactory.createEmptyBorder(5,5,5,5)));
        else panelDocumento.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(" Document "),BorderFactory.createEmptyBorder(5,5,5,5)));

//------------------------------------------------------------------------------------

        JPanel panelGuardar = new JPanel();

        botonGuardar = new JButton();
        botonGuardar.addActionListener(new ActionListener() {

          // Si se recibe una accion sobre el objeto "botonGuardar" se guardarn los resultaods producidos tras la busqueda con la informacion
          // adicional que el usuario quiera sobre el documento analizado.
            public void actionPerformed(ActionEvent e)
            {
              try{
                JFileChooser fc=new JFileChooser(System.getProperty("user.dir"));
                fc.setFileFilter(new PGAfilter());
                fc.showSaveDialog(ventanaPrincipal); //Muestra el diálogo
                File GuardarAux =fc.getSelectedFile();
                String nombreFichero =GuardarAux.getName();
                String rutaFichero =GuardarAux.getPath();

                if (((nombreFichero.length()>4)&&(nombreFichero.substring(nombreFichero.length()-4,nombreFichero.length()).compareTo(".pga")!=0))
                    || (nombreFichero.length()<=4))
                    {
                      nombreFichero+= ".pga";
                      rutaFichero+= ".pga";
                    }

                File Guardar= new File(nombreFichero);
                File RutaGuardar= new File(rutaFichero);

                int sobreescribir= 0;
                if (RutaGuardar.exists())
                    {
                      sobreescribir= ventanaPrincipal.funcion.mostrarAvisoSobreescribir();
                    }

                if ((Guardar !=null)&&(sobreescribir==0))
                  {
                    ventanaPrincipal.nombreGuardado= nombre.getText();
                    ventanaPrincipal.apellidosGuardado= apellidos.getText();
                    ventanaPrincipal.identificadorGuardado= identificador.getText();
                    ventanaPrincipal.tituloGuardado= titulo.getText();
                    ventanaPrincipal.asignaturaGuardado= asignatura.getText();
                    ventanaPrincipal.mesGuardado= mes.getText();
                    ventanaPrincipal.anioGuardado= anio.getText();
                    
                    ventanaPrincipal.textoHTML= ventanaPrincipal.htmlGuardado(false);
                    ventanaPrincipal.textoHTMLimprimir= ventanaPrincipal.htmlGuardado(true);

                    FileWriter Guardx=new FileWriter(Guardar);
                    Guardx.write("Texto origen:"+ventanaPrincipal.textoOrigen.substring(0,ventanaPrincipal.textoOrigen.length()).length()+"@"+ventanaPrincipal.textoOrigen.substring(0,ventanaPrincipal.textoOrigen.length())+"\n");
                    Guardx.write("Idioma:"+ventanaPrincipal.idiomaBusqueda+"@\n");
                    Guardx.write("NivelBusqueda:"+ventanaPrincipal.nivelBusqueda+"@\n");
                    Guardx.write("url0:"+ventanaPrincipal.url0+"@\n");
                    Guardx.write("url1:"+ventanaPrincipal.url1+"@\n");
                    Guardx.write("url2:"+ventanaPrincipal.url2+"@\n");
                    Guardx.write("Porcentaje1:"+ventanaPrincipal.porcentajeUrl0+"@\n");
                    Guardx.write("Porcentaje2:"+ventanaPrincipal.porcentajeUrl1+"@\n");
                    Guardx.write("Porcentaje3:"+ventanaPrincipal.porcentajeUrl2+"@\n");

                    Guardx.write("Nombre:"+ventanaPrincipal.nombreGuardado+"@\n");
                    Guardx.write("Apellidos:"+ventanaPrincipal.apellidosGuardado+"@\n");
                    Guardx.write("Identificador:"+ventanaPrincipal.identificadorGuardado+"@\n");
                    Guardx.write("Titulo:"+ventanaPrincipal.tituloGuardado+"@\n");
                    Guardx.write("Asignatura:"+ventanaPrincipal.asignaturaGuardado+"@\n");
                    Guardx.write("Mes:"+ventanaPrincipal.mesGuardado+"@\n");
                    Guardx.write("Anio:"+ventanaPrincipal.anioGuardado+"@\n");
                    
                    Guardx.write("Resultados html:"+ventanaPrincipal.textoHTML.length()+"@"+ventanaPrincipal.textoHTML+"\n");
                    
                    Guardx.write("@\n");

                    ventanaPrincipal.funcion.estableceTitulo(nombreFichero.substring(0,nombreFichero.length()-4));
                    ventana.dispose();

                    Guardx.close(); //Cierra el fichero
                  }
                }
              catch (Exception ee){}

            }
        });

        if (idiomaBusqueda=="es")
              botonGuardar.setText("Guardar");
        else botonGuardar.setText("Save");

        panelGuardar.add(botonGuardar);

//------------------------------------------------------------------------------------

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(panelAutor);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(panelDocumento);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(panelGuardar);
        panel.add(Box.createGlue());

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setSize(570,320);
        setResizable(false);
        setVisible(true);

    }

    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void actionPerformed(ActionEvent e){}

}

