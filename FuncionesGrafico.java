import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.util.StringTokenizer;
import java.util.Properties;
import javax.swing.JOptionPane;
import java.io.*;

import javax.swing.text.*;

class FuncionesGrafico {

  AntiCopia padre;

    public FuncionesGrafico(AntiCopia ventanaPrincipal){
      padre= ventanaPrincipal;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    void establecerBusquedaDocumento(boolean modo){
      padre.m_jlbFichero.setVisible(modo);
      padre.m_jbtExaminar.setVisible(modo);
      padre.m_jtfFichero.setVisible(modo);
      padre.m_jlbAnalizando.setVisible(modo);
      padre.barraProgreso.setVisible(modo);
      padre.m_jspScrollTexto.setVisible(modo);
      padre.mejoresResultados.setVisible(modo);
      padre.semaforo.setVisible(modo);
      padre.m_jbtResultadosAvanzados.setVisible(modo);
      padre.logo.setVisible(!modo);
      
      padre.botonAnalizar.setEnabled(modo);
      padre.botonImprimir.setEnabled(modo);
      padre.menuImprimir.setEnabled(modo);
      padre.botonGuardar.setEnabled(modo);
      padre.m_jbtResultadosAvanzados.setEnabled(false);
      padre.menuCopiar.setEnabled(modo);
      padre.menuCortar.setEnabled(modo);
      padre.menuGuardar.setEnabled(modo);
      padre.menuPegar.setEnabled(modo);
      padre.menuAnalizar.setEnabled(modo);
      padre.menuCerrar.setEnabled(modo);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
    void onCerrar(){
      establecerBusquedaDocumento(false);
      padre.funcion.estableceTitulo("");
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    void onNuevo(){

      padre.progreso= 0;
      padre.numeroParrafos= 0;
      padre.ResultadosAvanzados= "";
      padre.botonAnalizar.setIcon(padre.iconoAnalizar);
      padre.barraProgreso.setValue(0);
      padre.m_jtfFichero.setText("");
	  	padre.m_jtpTexto.setText("");
	  	ImageIcon icono2 = padre.crearIcono("images/sem_vacio.png");
			padre.semaforo.setIcon(icono2);
    	String textoInicial = "<html>\n<body bgcolor=\"EEEEEE\">\n";
      if (padre.idiomaBusqueda=="es")
          textoInicial+= "<b>Webs Principales:</b>\n";
      else textoInicial+= "<b>Main Webs:</b>\n";
      textoInicial+= "<ul>\n <li>\n <li>\n <li>\n </ul>\n";
      padre.mejoresResultados.setText(textoInicial);
      establecerBusquedaDocumento(true);
      padre.botonImprimir.setEnabled(false);
      padre.menuImprimir.setEnabled(false);
      padre.botonGuardar.setEnabled(false);
      padre.menuGuardar.setEnabled(false);
      padre.url0="";
      padre.url1="";
      padre.url2="";
      padre.porcentajeUrl0= 0;
      padre.porcentajeUrl1= 0;
      padre.porcentajeUrl2= 0;
      
      padre.nombreGuardado="";
      padre.apellidosGuardado="";
      padre.identificadorGuardado="";
      padre.tituloGuardado="";
      padre.asignaturaGuardado="";
      padre.mesGuardado="";
      padre.anioGuardado="";
      
      padre.funcion.estableceTitulo("");
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

    String resultadosAmostrar(String resultadosFinales,String url0,String url1,String url2,int porcentajeUrl0,int porcentajeUrl1,int porcentajeUrl2, boolean paraImprimir){

        String texto="";

        if (paraImprimir)
            {
              texto+= "<html>\n<body><br><br>\n";
              texto+= "<table width=\"100%\" align=\"center\" border=\"0\" style=\"font-size: 8pt\">\n";
            }
        else
            {
              texto+= "<html>\n<body bgcolor=\"EEEEEE\"><br><br>\n";
              texto+= "<table width=\"100%\" align=\"center\" border=\"0\">\n";
            }

        if ((padre.nombreGuardado!="")&&(padre.apellidosGuardado!="")&&(padre.idiomaBusqueda=="es"))
            {
              texto+= "<tr><td width=\"10%\"> </td>\n <td width=\"30%\">";
              texto+= "<b>Nombre:</b> "+padre.nombreGuardado+" </td>\n <td width=\"60%\"><b>Apellidos:</b> "+padre.apellidosGuardado+" </td>\n <td width=\"10%\"> </td></tr>\n";
            }
        if ((padre.nombreGuardado!="")&&(padre.apellidosGuardado!="")&& (padre.idiomaBusqueda=="en"))
            {
              texto+= "<tr><td width=\"10%\"> </td>\n <td width=\"30%\">";
              texto+= "<b>Name:</b> "+padre.nombreGuardado+" </td>\n <td width=\"60%\"><b>Surname:</b> "+padre.apellidosGuardado+" </td>\n <td width=\"10%\"> </td></tr>\n";
            }
        if ((padre.identificadorGuardado!="")&&(padre.idiomaBusqueda=="es"))
            {
              texto+= "<tr><td width=\"10%\"> </td>\n <td width=\"30%\">";
              texto+= "<b>Identificador:</b> "+padre.identificadorGuardado+" </td>\n <td width=\"60%\"> </td>\n <td width=\"10%\"> </td></tr>\n";
            }
        if ((padre.identificadorGuardado!="")&&(padre.idiomaBusqueda=="en"))
            {
              texto+= "<tr><td width=\"10%\"> </td>\n <td width=\"30%\">";
              texto+= "<b>Identificator:</b> "+padre.identificadorGuardado+" </td>\n <td width=\"60%\"> </td>\n <td width=\"10%\"> </td></tr>\n";
            }
        if ((padre.tituloGuardado!="")&&(padre.asignaturaGuardado!="")&&(padre.idiomaBusqueda=="es"))
            {
              texto+= "<tr><td width=\"10%\"> </td>\n <td width=\"30%\">";
              texto+= "<b>Titulo:</b> "+padre.tituloGuardado+" </td>\n <td width=\"60%\"><b>Asignatura:</b> "+padre.asignaturaGuardado+" </td>\n <td width=\"10%\"> </td></tr>\n";
            }
        if ((padre.tituloGuardado!="")&&(padre.asignaturaGuardado!="")&&(padre.idiomaBusqueda=="en"))
            {
              texto+= "<tr><td width=\"10%\"> </td>\n <td width=\"30%\">";
              texto+= "<b>Title:</b> "+padre.tituloGuardado+" </td>\n <td width=\"60%\"><b>Subject:</b> "+padre.asignaturaGuardado+" </td>\n <td width=\"10%\"> </td></tr>\n";
            }
        if ((padre.mesGuardado!="")&&(padre.anioGuardado!="")&&(padre.idiomaBusqueda=="es"))
            {
              texto+= "<tr><td width=\"10%\"> </td>\n <td width=\"30%\">";
              texto+= "<b>Mes:</b> "+padre.mesGuardado+" </td>\n <td width=\"60%\"><b>Año:</b> "+padre.anioGuardado+" </td>\n <td width=\"10%\"> </td></tr>\n";
            }
        if ((padre.mesGuardado!="")&&(padre.anioGuardado!="")&&(padre.idiomaBusqueda=="en"))
            {
              texto+= "<tr><td width=\"10%\"> </td>\n <td width=\"30%\">";
              texto+= "<b>Month:</b> "+padre.mesGuardado+" </td>\n <td width=\"60%\"><b>Year:</b> "+padre.anioGuardado+" </td>\n <td width=\"10%\"> </td></tr>\n";
            }

        texto+= "</table><br><hr width=\"80%\"><br>\n";

        if (paraImprimir)
              texto+= "<table width=\"100%\" align=\"center\" border=\"0\" style=\"font-size: 8pt\">\n";
        else texto+= "<table width=\"100%\" align=\"center\" border=\"0\">\n";

        texto+= "<tr>";
        texto+= "<td width=\"5%\"> </td>\n";
        texto+= "<td width=\"80%\">";
        if (padre.idiomaBusqueda=="es")
            texto+= " <b><u>Webs Principales:</u></b> ";
        else texto+= " <b><u>Main Webs:</u></b> ";
        texto+= "</td>";
        if (padre.idiomaBusqueda=="es")
            texto+= "<td align=\"center\" width=\"10%\"><u>Porcentaje</u></td>\n";
        else texto+= "<td align=\"center\" width=\"10%\"><u>Percentage</u></td>\n";
        texto+= "<td width=\"5%\"> </td>\n";
        texto+= "</tr>";

        texto+= "<tr><td width=\"5%\"> </td>\n <td width=\"80%\"> </td>\n <td align=\"center\" width=\"10%\"> </td> <td width=\"5%\"> </td></tr>\n";

        if (url0!="")
          {
            texto+= "<tr>";
            texto+= "<td width=\"5%\"> </td>\n";
            texto+= "<td width=\"80%\">";
            texto+= "<a href=\""+url0+"\">"+url0+"</a";
            texto+= "</td>";
            texto+= "<td align=\"center\" width=\"10%\">"+porcentajeUrl0+"% </td>\n";
            texto+= "<td width=\"5%\"> </td>\n";
            texto+= "</tr>";
          }
        if (url1!="")
          {
            texto+= "<tr>";
            texto+= "<td width=\"5%\"> </td>\n";
            texto+= "<td width=\"80%\">";
            texto+= "<a href=\""+url1+"\">"+url1+"</a";
            texto+= "</td>";
            texto+= "<td align=\"center\" width=\"10%\">"+porcentajeUrl1+"% </td>\n";
            texto+= "<td width=\"5%\"> </td>\n";
            texto+= "</tr>";
          }
        if (url2!="")
          {
            texto+= "<tr>";
            texto+= "<td width=\"5%\"> </td>\n";
            texto+= "<td width=\"80%\">";
            texto+= "<a href=\""+url2+"\">"+url2+"</a";
            texto+= "</td>";
            texto+= "<td align=\"center\" width=\"10%\">"+porcentajeUrl2+"% </td>\n";
            texto+= "<td width=\"5%\"> </td>\n";
            texto+= "</tr>";
          }

        texto+= "<tr><td width=\"5%\"> </td>\n <td width=\"80%\"> </td>\n <td align=\"center\" width=\"10%\"> </td> <td width=\"5%\"> </td></tr>\n";

        texto+= "<tr>";
        texto+= "<td width=\"5%\"> </td>\n";
        texto+= "<td width=\"80%\">";
        if (padre.idiomaBusqueda=="es")
            texto+= " <b><u>Detalles por parrafo:</u></b> ";
        else texto+= " <b><u>Details by paragraph:</u></b> ";
        texto+= "</td>";
        if (padre.idiomaBusqueda=="es")
            texto+= "<td align=\"center\" width=\"10%\"><u>Porcentaje</u></td>\n";
        else texto+= "<td align=\"center\" width=\"10%\"><u>Percentage</u></td>\n";
        texto+= "<td width=\"5%\"> </td>\n";
        texto+= "</tr>";

        int aux1=0;
        int aux2=0;
        boolean seHaEscritoParrafo= false;
        int aSumar= 0;
        while (aux2+1<resultadosFinales.length())
          {
            aux2= resultadosFinales.indexOf("\n",aux1);

            if ((resultadosFinales.substring(aux1,aux2).startsWith("En el parrafo "))||
                (resultadosFinales.substring(aux1,aux2).startsWith("In the paragraph ")))
              {
                if (padre.idiomaBusqueda=="es")
                    aSumar= 14; //Caracteres de "En el parrafo ".
                else aSumar= 17; // Caracteres de "In the paragraph "

                texto+= "<tr><td width=\"5%\"> </td>\n <td width=\"80%\"> </td>\n <td align=\"center\" width=\"10%\"> </td> <td width=\"5%\"> </td></tr>\n";
                texto+= "<tr>";
                texto+= "<td width=\"5%\"></td>\n";
                texto+= "<td width=\"80%\">";

                texto+= resultadosFinales.substring(aux1,aux1+aSumar);
                texto+= "<b>"+resultadosFinales.substring(aux1+aSumar,aux2)+"</b>";

                seHaEscritoParrafo= true;
              }
            else if ((resultadosFinales.substring(aux1,aux2).startsWith("- La Web "))||
                    (resultadosFinales.substring(aux1,aux2).startsWith("- Web ")))
              {
                if (padre.idiomaBusqueda=="es")
                    aSumar= 9; // Caracteres de "- La Web ".
                else aSumar= 6; // Caracteres de "- Web ".

                String sistemaOperativo= System.getProperty("os.name");

                if (seHaEscritoParrafo)
                {
                  if (sistemaOperativo.compareTo("Linux")==0)
                    texto+= "</td>\n <td align=\"center\" width=\"10%\"> <img src=\"images//x.png\"> </td><td width=\"5%\"></td></tr>";
                  else texto+= "</td>\n <td align=\"center\" width=\"10%\"> <img src=\"images//x.png\"> </td><td width=\"5%\"></td></tr>";
                }

                texto+= "<tr>";
                texto+= "<td width=\"5%\"></td>\n";
                texto+= "<td width=\"80%\">";

                texto+= "<a href=\""+resultadosFinales.substring(aux1+aSumar,aux2)+"\">";
                texto+= resultadosFinales.substring(aux1+aSumar,aux2)+"</a>";
                seHaEscritoParrafo= false;
              }
            else if ((resultadosFinales.substring(aux1,aux2).startsWith("No se ha encontrado ninguna coincidencia."))||
                    (resultadosFinales.substring(aux1,aux2).startsWith("No results found.")))
              {

                String sistemaOperativo= System.getProperty("os.name");
                if (seHaEscritoParrafo)
                {
                  if (sistemaOperativo.compareTo("Linux")==0)
                      texto+= "</td>\n <td align=\"center\" width=\"10%\"> <img src=\"images//ok.png\"> </td><td width=\"5%\"></td></tr>";
                  else texto+= "</td>\n <td align=\"center\" width=\"10%\"> <img src=\"images\\ok.png\"> </td><td width=\"5%\"></td></tr>";
                }
                
                texto+= "<tr>";
                texto+= "<td width=\"5%\"></td>\n";
                texto+= "<td width=\"80%\">";

                if (padre.idiomaBusqueda=="es")
                    texto+= "No se ha encontrado ninguna coincidencia.";
                else texto+= "No results found.";

                seHaEscritoParrafo= false;
              }
            else if ((resultadosFinales.substring(aux1,aux2).startsWith("Porcentaje de copia"))||
                    (resultadosFinales.substring(aux1,aux2).startsWith("Copy percentage")))
              {
                if (padre.idiomaBusqueda=="es")
                    aSumar= 21; // Caracteres de "Porcentaje de copia".
                else aSumar= 17; // Caracteres de "Copy percentage".

                texto+= "</td>\n <td align=\"center\" width=\"10%\">";
                texto+= resultadosFinales.substring(aux1+aSumar,aux2);
                texto+= "</td><td width=\"5%\"></td></tr>";
              }
            aux1= aux2+1;
          }
        texto+= "</table>\n";
        texto+= "<br></body></html>";

        return texto;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
    String htmlParaImprimir(String textoHTML){
        String salida="";

        int aux1=0;
        int aux2=textoHTML.indexOf("<table",0);
        salida+="<html>\n<body><br><br>\n";
        salida+="<table width=\"100%\" align=\"center\" border=\"0\" style=\"font-size: 8pt\">\n";

        aux1= aux2+46;
        aux2=textoHTML.indexOf("<table",aux1);
        salida+=textoHTML.substring(aux1,aux2);
        
        salida+="<table width=\"100%\" align=\"center\" border=\"0\" style=\"font-size: 8pt\">\n";

        aux1= aux2+46;
        aux2=textoHTML.length();
        salida+=textoHTML.substring(aux1,aux2);

        return salida;

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
    void onAbrir(){

      StringBuffer bufferLectura = new StringBuffer();
      String nombreFichero="";

      JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		  chooser.setFileFilter(new PGAfilter());
		  int returnVal = chooser.showOpenDialog(padre);
      if(returnVal == JFileChooser.APPROVE_OPTION)
          {

            File Abrir =chooser.getSelectedFile();
            try {
                FileInputStream fin = new FileInputStream (Abrir);
                BufferedReader br = new BufferedReader(new InputStreamReader(fin));
                String sCadena="";
                
                while ((sCadena = br.readLine())!=null)
                  {
                  bufferLectura.append(sCadena);
                  }
                fin.close();
                nombreFichero =Abrir.getName();
                }
            catch(Exception Error){}
            
            int aux1=0;
            int aux2=0;
            while (aux2+1<bufferLectura.length())
              {
              aux2= bufferLectura.indexOf("@",aux1);
              
              if (bufferLectura.substring(aux1,aux2).startsWith("Texto origen"))
                {
                   int numeroCaracteresTexto= Integer.parseInt(bufferLectura.substring(aux1,aux2).substring(13,aux2).trim());
                   padre.textoOrigen= bufferLectura.substring(aux2+1,aux2+numeroCaracteresTexto);
                   ponTexto(bufferLectura.substring(aux2+1,aux2+numeroCaracteresTexto));
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Idioma"))
                {
                    if (bufferLectura.substring(aux1,aux2).substring(7,9).compareTo("en")==0)
                        {
                           padre.idiomaBusqueda= "en";
                           padre.estableceIdioma("en");
                        }
                    else if (bufferLectura.substring(aux1,aux2).substring(7,9).compareTo("es")==0)
                        {
                           padre.idiomaBusqueda= "es";
                           padre.estableceIdioma("es");
                        }
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("NivelBusqueda"))
                {
                    if (bufferLectura.substring(aux1,aux2).substring(14,15).compareTo("0")==0)
                        {
                           padre.nivelBusqueda= 0;
                        }
                    else if (bufferLectura.substring(aux1,aux2).substring(14,15).compareTo("1")==0)
                        {
                           padre.nivelBusqueda= 1;
                        }
                    else if (bufferLectura.substring(aux1,aux2).substring(14,15).compareTo("2")==0)
                        {
                           padre.nivelBusqueda= 2;
                        }
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("url0"))
                {
                   padre.url0= bufferLectura.substring(aux1,aux2).substring(5,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("url1"))
                {
                   padre.url1= bufferLectura.substring(aux1,aux2).substring(5,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("url2"))
                {
                   padre.url2= bufferLectura.substring(aux1,aux2).substring(5,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Porcentaje1"))
                {
                   padre.porcentajeUrl0= Integer.parseInt(bufferLectura.substring(aux1,aux2).substring(12,aux2-aux1).trim());
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Porcentaje2"))
                {
                   padre.porcentajeUrl1= Integer.parseInt(bufferLectura.substring(aux1,aux2).substring(12,aux2-aux1).trim());
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Porcentaje3"))
                {
                   padre.porcentajeUrl2= Integer.parseInt(bufferLectura.substring(aux1,aux2).substring(12,aux2-aux1).trim());
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Resultados html"))
                {
                   int numeroCaracteresHTML= Integer.parseInt(bufferLectura.substring(aux1,aux2).substring(16,aux2-aux1).trim());
                   padre.textoHTML= bufferLectura.substring(aux2+1,bufferLectura.length());
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Nombre"))
                {
                    padre.nombreGuardado= bufferLectura.substring(aux1,aux2).substring(7,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Apellidos"))
                {
                    padre.apellidosGuardado= bufferLectura.substring(aux1,aux2).substring(10,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Identificador"))
                {
                    padre.identificadorGuardado= bufferLectura.substring(aux1,aux2).substring(14,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Titulo"))
                {
                    padre.tituloGuardado= bufferLectura.substring(aux1,aux2).substring(7,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Asignatura"))
                {
                    padre.asignaturaGuardado= bufferLectura.substring(aux1,aux2).substring(11,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Mes"))
                {
                    padre.mesGuardado= bufferLectura.substring(aux1,aux2).substring(4,aux2-aux1);
                }
              else if (bufferLectura.substring(aux1,aux2).startsWith("Anio"))
                {
                    padre.anioGuardado= bufferLectura.substring(aux1,aux2).substring(5,aux2-aux1);
                }

              aux1= aux2+1;
              }

          String textoFinal= "<html>\n<body bgcolor=\"EEEEEE\">\n";
          if (padre.idiomaBusqueda=="es")
              textoFinal += "<b>Webs Principales:</b>\n";
          else textoFinal += "<b>Main Webs:</b>\n";
          textoFinal+= "<ul>\n";

          if (padre.url0!="")
              textoFinal+= "<li> <a href=\""+padre.url0+"\">"+padre.url0+"</a>\n";
          else textoFinal+= "<li>\n";
          if (padre.url1!="")
              textoFinal+= "<li> <a href=\""+padre.url1+"\">"+padre.url1+"</a>\n";
          else textoFinal+= "<li>\n";
          if (padre.url2!="")
              textoFinal+= "<li> <a href=\""+padre.url2+"\">"+padre.url2+"</a>\n";
          else textoFinal+= "<li>\n";

          textoFinal+= "</ul>\n";
          
          padre.textoHTMLimprimir= padre.htmlGuardado(true);
          padre.mejoresResultados.setText(textoFinal);
          establecerBusquedaDocumento(true);
          padre.m_jbtResultadosAvanzados.setEnabled(true);
          padre.botonGuardar.setEnabled(true);
          padre.menuGuardar.setEnabled(true);
          padre.botonImprimir.setEnabled(true);
          padre.menuImprimir.setEnabled(true);
          
          padre.funcion.estableceTitulo(nombreFichero.substring(0,nombreFichero.length()-4));


          if ((padre.porcentajeUrl0>=80)||(padre.porcentajeUrl1>=80)||(padre.porcentajeUrl2>=80))
              padre.semaforoFinal = padre.crearIcono("images/sem_rojo.png");
          else if ((padre.porcentajeUrl0>=40)||(padre.porcentajeUrl1>=40)||(padre.porcentajeUrl2>=40))
                  padre.semaforoFinal = padre.crearIcono("images/sem_ambar.png");
              else padre.semaforoFinal = padre.crearIcono("images/sem_verde.png");
			    padre.semaforo.setIcon(padre.semaforoFinal);

          
          }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
    void onExaminar(){

		  BufferedReader texto=null;
		  String sCadena="";
		  DocToTxt ArchivoDoc= new DocToTxt();
		  String path="";
		  String m_strTexto;

      JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		  chooser.setFileFilter(new DOCfilter());
		  int returnVal = chooser.showOpenDialog(padre);
    	if(returnVal == JFileChooser.APPROVE_OPTION)
        {
			  padre.m_jtfFichero.setText(chooser.getSelectedFile().getPath());
			  padre.numeroParrafos= 0;
        padre.progreso= 0;
        padre.ResultadosAvanzados= "";
        padre.barraProgreso.setValue(0);
  	  	padre.menuGuardar.setEnabled(false);
        padre.botonGuardar.setEnabled(false);
        padre.m_jbtResultadosAvanzados.setEnabled(false);
        padre.botonImprimir.setEnabled(false);
        padre.menuImprimir.setEnabled(false);
        ImageIcon icono3 = padre.crearIcono("images/sem_vacio.png");
        padre.semaforo.setIcon(icono3);
        String textoInicial = "<html>\n<body bgcolor=\"EEEEEE\">\n";
        if (padre.idiomaBusqueda=="es")
            textoInicial+= "<b>Webs Principales:</b>\n";
        else textoInicial+= "<b>Main Webs:</b>\n";
        textoInicial+= "<ul>\n <li>\n <li>\n <li>\n </ul>\n";
        padre.mejoresResultados.setText(textoInicial);
        padre.url0="";
        padre.url1="";
        padre.url2="";
        padre.porcentajeUrl0= 0;
        padre.porcentajeUrl1= 0;
        padre.porcentajeUrl2= 0;

		    }
		  path= chooser.getSelectedFile().getPath();

		  try{
        texto= ArchivoDoc.pasarAtxt(path);
		  }
		  catch(Exception Error){}
		  // CUIDADOOOOOOOOOOOOOOOOOO AQUI SE PRODUCEN BASTANTES ERRORES

      if (padre.m_jtfFichero.getText().compareTo("") == 0){  // si no hay nombre
			   JOptionPane.showMessageDialog(padre,"No hay un nombre de fichero para abrir");
         return;
		  }

      m_strTexto = "";
		  if (texto!=null)
    try{
        while ((sCadena = texto.readLine())!=null)
        {
         m_strTexto= m_strTexto + sCadena + "\n";
         //padre.numeroParrafos++;
        }
      ponTexto(m_strTexto);
		  }

    catch(Exception Error){}

      }
//////////////////////////////////////////////////////////////////////////////////////////////////////
   public void ponTexto(String strTexto){

		// vvv: el StringTokenizer separa por espacios, retornos de carro o tabuladores
		///    atención porque se pone un true como tercer parámetro para pedir que también devuelva los delimintadores como tokens, de modo
		//     que no los perdemos y los podemos ir añadiendo también al JTextPane para mantener la estructura del fichero
		StringTokenizer strTok = new StringTokenizer(strTexto," \n\t\r",true);
		String strPalabraActual;
		String strDelimActual;
		Document doc;

		padre.m_jtpTexto.setText("");
		doc = padre.m_jtpTexto.getDocument();

    try {
			while (strTok.hasMoreTokens() == true){
			    // se toma el token
			    strPalabraActual = strTok.nextToken();
				// si es un delimitador en lugar de un token normal, lo añade sin mas
				if ((strPalabraActual.compareTo(" ")== 0) || (strPalabraActual.compareTo("\n")== 0) || (strPalabraActual.compareTo("\t")== 0) || (strPalabraActual.compareTo("\r")== 0) ){
				    	doc.insertString(doc.getLength(), strPalabraActual,padre.m_jtpTexto.getStyle("normal"));
					continue;  // vuelve de nuevo al principio del bucle while
				}
				// se toma el delimitador, si lo hay detrás (pq quizás haya terminado el fichero).
				if (strTok.hasMoreTokens() == true)
					strDelimActual   = strTok.nextToken();
				else
				    strDelimActual = "";
					  doc.insertString(doc.getLength(), strPalabraActual,padre.m_jtpTexto.getStyle("normal"));
						doc.insertString(doc.getLength(), strDelimActual,padre.m_jtpTexto.getStyle("normal")); // ahora se inserta el delimintador
			}

      }
      catch (Exception ble) {System.err.println("No se pudo añadir texto.");}

      return;

   }
//////////////////////////////////////////////////////////////////////////////////////////////////////

    public void cargarConfiguracion(){
      try{

		      FileInputStream fin = new FileInputStream ("config");
		      StringBuffer prueba = new StringBuffer();

			    boolean variableIdiomaEsCorrecta= false;
			    boolean variableNivelEsCorrecta= false;
			    boolean variableLimiteEsCorrecta= false;
			    boolean variableSegundosLimiteEsCorrecta= false;

		      BufferedReader br = new BufferedReader(new InputStreamReader(fin));
		      String sCadena="";

		      // Se leen todas las lineas del fichero y se almacenan en la variable sConfig.		      
		      while ((sCadena = br.readLine())!=null)
		      {prueba.append(sCadena);}
		      

		      fin.close();

          int aux1=0;
          int aux2=0;
          while (aux2+1<prueba.length())
            {
            aux2= prueba.indexOf("@",aux1);
            if (prueba.substring(aux1,aux2).startsWith("idioma"))
              {
                if (prueba.substring(aux1,aux2).substring(7,9).compareTo("en")==0)
                  {
                    padre.idiomaBusqueda= "en";
                    padre.estableceIdioma("en");
                    variableIdiomaEsCorrecta= true;
                  }
                else if (prueba.substring(aux1,aux2).substring(7,9).compareTo("es")==0)
                  {
                    padre.idiomaBusqueda= "es";
                    padre.estableceIdioma("es");
                    variableIdiomaEsCorrecta= true;
                  }
              }
            else if (prueba.substring(aux1,aux2).startsWith("nivel"))
                {
                if (prueba.substring(aux1,aux2).substring(6,7).compareTo("0")==0)
                    {
                      padre.nivelBusqueda= 0;
                      variableNivelEsCorrecta= true;
                    }
                else if (prueba.substring(aux1,aux2).substring(6,7).compareTo("1")==0)
                    {
                      padre.nivelBusqueda= 1;
                      variableNivelEsCorrecta= true;
                    }

                else if (prueba.substring(aux1,aux2).substring(6,7).compareTo("2")==0)
                    {
                      padre.nivelBusqueda= 2;
                      variableNivelEsCorrecta= true;
                    }
                }
            else if (prueba.substring(aux1,aux2).startsWith("hayLimite"))
                {
                if (prueba.substring(aux1,aux2).substring(10,11).compareTo("0")==0)
                    {
                      padre.hayLimite= true;
                      variableLimiteEsCorrecta= true;
                    }
                else if (prueba.substring(aux1,aux2).substring(10,11).compareTo("1")==0)
                    {
                      padre.hayLimite= false;
                      variableLimiteEsCorrecta= true;
                    }
                }
            else if (prueba.substring(aux1,aux2).startsWith("segundosLimite"))
                {
                // Hay que analizar si lo que se coge del fichero "config" es un numero entero positivo !!!!
                padre.segundosLimite= Integer.parseInt(prueba.substring(aux1,aux2).substring(15,16).trim());
                variableSegundosLimiteEsCorrecta= true;
                }

            aux1= aux2+1;
            }
            
          if (!variableIdiomaEsCorrecta)
            {
              if (padre.idiomaBusqueda=="es")
                  mostrarDialogoError("Se ha encontrado un error en la variable \"idioma\" del fichero \"config\".");
              else mostrarDialogoError("Error in \"idioma\" variable found in the \"config\" file.");
              System.exit(0);
            }
          else if (!variableNivelEsCorrecta)
            {
              if (padre.idiomaBusqueda=="es")
                  mostrarDialogoError("Se ha encontrado un error en la variable \"nivel de analisis\" del fichero \"config\".");
              else mostrarDialogoError("Error in \"level of analisis\" variable found in the \"config\" file.");
              System.exit(0);
            }
          else if (!variableLimiteEsCorrecta)
            {
              if (padre.idiomaBusqueda=="es")
                  mostrarDialogoError("Se ha encontrado un error en la variable \"limite de busquedas\" del fichero \"config\".");
              else mostrarDialogoError("Error in \"limit of searchs\" variable found in the \"config\" file.");
              System.exit(0);
            }
          else if (!variableSegundosLimiteEsCorrecta)
            {
              if (padre.idiomaBusqueda=="es")
                  mostrarDialogoError("Se ha encontrado un error en la variable \"numero de segundos del limite de busquedas\" del fichero \"config\".");
              else mostrarDialogoError("Error in \"number of seconds in the limit of search\" variable found in the \"config\" file.");
              System.exit(0);
            }

	         }
      catch(Exception Error){}
    }
    
    public void estableceTitulo (String archivo){
      if (archivo!="")
          padre.setTitle(" Plagium Alert  - "+archivo);
      else padre.setTitle(" Plagium Alert ");
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////
    public void mostrarDialogoError(String error){
      if (padre.idiomaBusqueda=="es")
          JOptionPane.showMessageDialog(padre,error,"Se ha producido un error",JOptionPane.ERROR_MESSAGE);
      else JOptionPane.showMessageDialog(padre,error,"An error has taken place",JOptionPane.ERROR_MESSAGE);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
    public int mostrarAvisoSobreescribir(){

    Object[] opcionesEs = {"Si","No"};
    Object[] opcionesEn = {"Yes","No"};
    int valor=1;

    if (padre.idiomaBusqueda=="es")
        valor = JOptionPane.showOptionDialog(padre,
        "¿Estas seguro que desea sobreescribir el fichero?\n",
        "Aviso",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcionesEs,opcionesEs[1]);
    else
        valor = JOptionPane.showOptionDialog(padre,
        "Are you sure you want to overwrite the file?\n",
        "Warning",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcionesEn,opcionesEn[1]);

    return valor;
    }


}
