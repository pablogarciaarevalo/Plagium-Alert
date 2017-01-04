import java.io.*;
import java.util.*;

class Analiza {

  String parrafo="";                            // Almacena las palabras que resumen un parrafo (primerasPalabras...ultimasPalabras).
  int numeroPalabrasParrafo= 0;                 // Almacena el numero de palabras que hay en un parrafo.
  String urlFrecuentes[] = new String[3];       // Array de 3 campos en el que se almacenan las 3 URLs que mas aparecen en un parrafo.
  int aparicionesUrlFrecuentes[] = new int[3];  // Array de 3 campos en el que se almacenan el numero de veces que aparecen las URLs de un parrafo.
  int numeroBusquedasDocumento= 0;				      // Almacena el numero de busquedas que se hacen en todo el documento.
  String resultadoDetallado="";					        // Variable en la que se almacena los detalles del resultado.
  String urlFinales[] = new String[3];          // Array de 3 campos en el que se almacenan las 3 URLs que mas aparecen en un documento.
  int aparicionesUrlFinales[] = new int[3];     // Array de 3 campos en el que se almacenan el numero de veces que aparecen las URLs de un documento.
  
  CalculosTiempo tiempo = new CalculosTiempo();
  Calendar momentoInicio = Calendar.getInstance();
  int contador= 0;
  boolean hayLimite= false;
  int segundosLimite= 0;

  //*************************************************************************************************************//
	// El metodo "urlFinales" devuelve la variable "urlFinales" que almacena las 3 URLs que mas se han encontrado. //
	//*************************************************************************************************************//
  String[] urlFinales() {
	  return urlFinales;
  }

	//*************************************************************************************************************//
	// El metodo "aparicionesUrlFinales" devuelve la variable "aparicionesUrlFinales" que almacena el numero de    //
  	// veces que aparecen las 3 URLs que mas se han encontrado.													   //
	//*************************************************************************************************************//
  int[] aparicionesUrlFinales() {
	  return aparicionesUrlFinales;
  }

    //*************************************************************************************************************//
	// El metodo "resultadoDetallado" devuelve el string "resultadoDetallado" que el resultado detallado.		   //
	//*************************************************************************************************************//  
  String resultadoDetallado() {
	  return resultadoDetallado;
  }

  	//*************************************************************************************************************//
	// El metodo "numeroBusquedasDocumento" devuelve el entero "numeroBusquedasDocumento" que almacena el numero   //
    // de busquedas que se realizan en todo el documento.														   //
	//*************************************************************************************************************//  
  int numeroBusquedasDocumento() {
	  return numeroBusquedasDocumento;
  }
  
  
  
	//*************************************************************************************************************//
	// El metodo "analisisParrafo" es el encargado de separar el texto en parrafos y llamar al metodo "extraer"    //
  // para proceder a la busqueda del mismo. Despues de ello, realiza los calculos pertinentes para conseguir     //
  // estadisticas sobre el procentaje de copia (apariciones en google) del parrafo y del documento entero.       //
	// RECIBE: El BufferedReader "texto" con el contenido del documento a analizar.                                //
  //         El String "idioma" segï¿½n el tipo de busqueda: "es" para espaï¿½ol y "en" para ingles.                 //
  //         La variable "nivelBusqueda" que puede tomar los valores 0(busqueda rapida), 1 (busqueda normal)     //
  //            o 2 (Busqueda exhaustiva).                                                                       //
	//*************************************************************************************************************//
  @SuppressWarnings("unchecked")
void analisisParrafo (AntiCopia.Task tarea, BufferedReader texto, String idioma, int nivelBusqueda, int saltoDeProceso, boolean varHayLimite, int varSegundosLimite) throws Exception, ExcepcionGoogle {

  String sCadena="";                                    // Variable en la que se va almacenando cada parrafo del documento.
  VectorWebParrafo urlTotales= new VectorWebParrafo();  // Almacena todas las urls de un documento y sus apariciones.
  VectorWebParrafo vectorAux= new VectorWebParrafo();   // Variable auxiliar para almacenar y agregar una nueva url al vector "urlTotales".
  int numeroBusquedasParrafo= 0;                        // Variable que almacena el numero de busquedas que se hacen por cada parrafo para poder conseguir
                                                        // estadisticas sobre el procentaje de copia (apariciones en google) del parrafo y del documento entero.
  
  urlTotales.IniVectorWebParrafo();
  hayLimite= varHayLimite;
  segundosLimite= varSegundosLimite;

    if (texto!=null)
      {
      // Por cada parrafo (linea) se realiza una busqueda (rapida, normal o exhaustiva).
      int cont=0;
      while (((sCadena = texto.readLine())!=null)&&(!tarea.isCancelled()))
        {
         tarea.EstableceProceso(saltoDeProceso*cont);
         cont+= 1;

         // Se llama al metodo "extraer" que es el encargado de realizar
         extraer(sCadena,idioma,nivelBusqueda);

         // Por cada url devuelta (las 3 que mas aparecen) de cada parrado se agrega a la variable "urlTotales".
         int i= 0;
         while ((i<3)&&(aparicionesUrlFrecuentes[i]!=0))
          {
          // Se inicializa la variable "vectorAux" y se agrega la url.
          vectorAux.IniVectorWebParrafo();
          vectorAux.addElement(urlFrecuentes[i]);

          // Se agrega la url (vectorAux) al vector "urlTotales" tantas veces como veces haya aparecido en la busqueda del parrafo.
          for (int k=0;k<aparicionesUrlFrecuentes[i];k++)
                urlTotales.AddElementoWeb(vectorAux);
          i++;
          }

          // Se calcula el numero de busquedas que se han realizado en el parrafo.
         if (numeroPalabrasParrafo!=0)
            {
            if (numeroPalabrasParrafo==1)
                numeroBusquedasParrafo= 1;
            // Si hay mas de una palabra, el numero de busquedas depende del nivel(rapida, normal o exhaustiva) de busqueda que se realiza.
            else if (nivelBusqueda==0)      // Se realiza solo una busqueda con las primeras 20 palabras.
                     numeroBusquedasParrafo= 1;
                 else if (nivelBusqueda==1) // Se realizan busquedas de 20 palabras haciendo un salto de 20 en 20 palabras.
                        numeroBusquedasParrafo= numeroPalabrasParrafo/20;
                 else // nivelBusqueda==2      Se realizan busquedas de 20 palabras haciendo un salto de 5 en 5 palabras.
                    numeroBusquedasParrafo= (numeroPalabrasParrafo-15)/5;
            
            // Se suman el numero de busquedas de un parrafo al numero de busquedas totales del documento. 
            numeroBusquedasDocumento= numeroBusquedasDocumento + numeroBusquedasParrafo;
            
            if (idioma=="es")
                resultadoDetallado+= "\n\nEn el parrafo \""+parrafo+"\"\n";// OJOOOOOOOOOOOOOOOOOOO+ "\" hay "+numeroPalabrasParrafo+" palabras.";
            else resultadoDetallado+= "\n\nIn the paragraph \""+parrafo+"\"\n";// OJOOOOOOOOOOOOOOOOOOO+ "\" hay "+numeroPalabrasParrafo+" palabras.";

            for (int k=0;k<3;k++)
            {
              if (aparicionesUrlFrecuentes[k]!=0)
              {
              if (idioma=="es")
              {
                resultadoDetallado+= "- La Web "+urlFrecuentes[k];// OJOOOOOOOOOOOOOOOOOOO+" aparece "+aparicionesUrlFrecuentes[k]+" veces";
                resultadoDetallado+= "\nPorcentaje de copia: "+(aparicionesUrlFrecuentes[k]*100)/numeroBusquedasParrafo+"%\n";
              }
              else
              {
                resultadoDetallado+= "- Web "+urlFrecuentes[k];// OJOOOOOOOOOOOOOOOOOOO+" aparece "+aparicionesUrlFrecuentes[k]+" veces";
                resultadoDetallado+= "\nCopy percentage: "+(aparicionesUrlFrecuentes[k]*100)/numeroBusquedasParrafo+"%\n";
              }
              
              }
            }
            
            if ((aparicionesUrlFrecuentes[0]==0)&&(aparicionesUrlFrecuentes[1]==0)&&(aparicionesUrlFrecuentes[2]==0))
              {
                if (idioma=="es")
                  {
                    resultadoDetallado+= "No se ha encontrado ninguna coincidencia.";
                    resultadoDetallado+= "\nPorcentaje de copia: 0%\n";
                  }
                else
                  {
                    resultadoDetallado+= "No results found.";
                    resultadoDetallado+= "\nCopy percentage: 0%\n";
                  }
              }
            for (int k=0;k<3;k++)
            {
              urlFrecuentes[k]="";
              aparicionesUrlFrecuentes[k]= 0;
            }

            }
         // Se inicializan las variables "parrafo" y "numeroPalabrasParrafo" para que en la siguiente iteracion del bucle no esten
         // los valores anteriores.
         parrafo="";
         numeroPalabrasParrafo= 0;
        }

      // Se calculan las 3 principales URLs de todo el documento y el numero de veces que aparecen.
      urlTotales.CalculoPrincipalesUrls();
      
      // Se almacenan los valores calculados en la instruccion anterior en las variables "urlFinales" y "aparicionesUrlFinales".
     	urlFinales= urlTotales.ReturnUrls();
		  aparicionesUrlFinales= urlTotales.ReturnAparicionesUrls();
		  
      }

    }

	//*************************************************************************************************************//
	// El metodo "extraer" es el encargado de agrupar las palabras de un segun el tipo de busqueda y llamar al     //
	// metodo "parsear".                                                                                           //
	// Si "nivelBusqueda"==0, se realiza una sola busqueda con las 20 palabras de cada parrafo.                    //
	// Si "nivelBusqueda"==1, se hace 1 busqueda por cada 20 palabras de cada parrafo (3 busquedas con 60 palabras)//
	// Si "nivelBusqueda"==2, se realizan busquedas de 20 palabras, realizando un salto de 5 palabras (Al terminar //
	//   una busqueda se borran las primeras 5 palabras y se agregan al final las siguientes 5 palabras del        //
  //   parrafo). Se hacen 3 busquedas con 30 palabras.                                                           //
	// RECIBE: El String "sCadena" es la informacion de un parrafo.                                                //
  //         El String "idioma" segï¿½n el tipo de busqueda: "es" para espaï¿½ol y "en" para ingles.                 //
  //         La variable "nivelBusqueda" que puede tomar los valores 0(busqueda rapida), 1 (busqueda normal)     //
  //            o 2 (Busqueda exhaustiva).                                                                       //
	//*************************************************************************************************************//
	void extraer (String sCadena,String idioma,int nivelBusqueda) throws Exception, ExcepcionGoogle {

	    String busqueda="";                     // Es el string que es buscado en google con una longitud de 20 palabras.
  	  int indice= 0;                          // Variable para ir haciendo el recorrido por el string de cada letra de todo el parrafo.
	    int indBusqueda= 0;                     // Variable para ir contando los caracteres que hay en el string de 20 palabras.
	    int numPalabras= 0;                     // Almacena el numero de palabras (o espacios entre palabras) que encuentra.
	    int aBorrar= 0;                         // Posicion que ocupa la primera letra de la sexta palabra en el string "busqueda".
		    					                            // Se utiliza para quitar las primeras 5 palabras del string y concatenar al final otras 5.
	    int iPalabra5= 0;                       // Posicion que ocupa la 6 palabra en el string "busqueda".
      int iPalabra10= 0;                      // Posicion que ocupa la 11 palabra en el string "busqueda".
      int iPalabra15= 0;                      // Posicion que ocupa la 16 palabra en el string "busqueda".
	    char caracter;                          // Variable para ir leyendo los caracteres del string a analizar.
      //char posibleEspacio;                    // Variable para ir eliminando posibles espacios dobles, triples, ...
      boolean finBusqueda= false;             // Variable para salir del bucle que recorre todo el parrafo en el caso que se haga una busqueda
                                              // rapida, en la que solo se observan las 20 primeras palabras de cada parrafo.
      boolean esPrincipioDeParrafo= true;     // Variable para conocer las 5 primeras palabras reales del principio del parrafo en caso de busqueda normal.

	    VectorWebParrafo vctUnResultado = new VectorWebParrafo();          // Almacena las 10 URLs, si las hubiera, obtenidas de una unica busqueda
	    VectorWebParrafo vctResultadoParrafo = new VectorWebParrafo();     // Almacena las URLs obtenidas durante las busquedas de todo el parrafo.
		  ResultadoParrafo resultado = new ResultadoParrafo(); // Objeto de la clase "ResultadoParrafo" para resumir y contabilizar el parrafo.

      // Se inicializan los vectores.
		  vctUnResultado.IniVectorWebParrafo();
		  vctResultadoParrafo.IniVectorWebParrafo();
		  
		  // Se prepara el string quitando los espacios del principio y del final.
		  sCadena= sCadena.trim();

  	  // Si un parrafo tiene menos de 20 palabras (125 caracteres) se hace una sola busqueda con todo el contenido.
		  if (sCadena.length()<125)
		  	{
		  	vctResultadoParrafo= parsear(sCadena,idioma);
				for (int i=0;i<vctResultadoParrafo.size (); i++)
						 vctResultadoParrafo.AgregarElemento(1);
				resultado.resumenParrafo(sCadena);
		  	}

		  else // Parrafos con mas de 20 palabras.
		  {
       finBusqueda= false;
		   while ((indice<sCadena.length()&& (!finBusqueda)))
		    {

		    // Si el caracter NO es un espacio, se concatena en el string busqueda y se incrementa indice.
		    if ((caracter= sCadena.charAt(indice))!=' ')
		       {
		    	indice++;
		    	indBusqueda++;
		    	busqueda= busqueda+caracter;
		       }

		    // Si el caracter es un espacio hay que dejar solo un espacio en el caso en el que haya mas de uno.
		    // Si el numero de palabras es multiplo de 5:
		    //     - Si es 5, 10 o 15 se anota la posicion en la que se encuentra.
		    //     - Si es 20: Se realiza la busqueda y se quitan las primeras 5 palabras.
		    else
		       {
		      resultado.nuevaPalabra();
		    	busqueda= busqueda+caracter;
		    	numPalabras++;
		    	indice++;
		    	indBusqueda++;

          // Se eliminan los posibles dobles espacios.
		    	while (sCadena.charAt(indice)==' ')
		    			indice++;

		    	if ((numPalabras==5)&& esPrincipioDeParrafo)
		    		{
		    			iPalabra5= indBusqueda;
		    			resultado.primerasPalabrasParrafo(busqueda);
		    			esPrincipioDeParrafo= false;
		    		}
		    	if (numPalabras==10)
		    		iPalabra10= indBusqueda;
		    	if (numPalabras==15)
		    		iPalabra15= indBusqueda;

		    	if (numPalabras==20)
		    		{
            // Se realiza la busqueda con las 20 palabras y se indexa los resultados a la variable final "vctResultadoParrafo".
		    		vctUnResultado= parsear(busqueda,idioma);
						vctResultadoParrafo.AddElementoWeb(vctUnResultado);
						
            if (nivelBusqueda==0)      // Busqueda rapida.
              {
              // Se inicializan todas las variables y se establece "finBusqueda" a TRUE para que no siga con la busqueda del parrafo.
              finBusqueda= true;
              numPalabras= 0;
              aBorrar= 0;
              busqueda="";
              iPalabra5= 0;
              iPalabra10= 0;
              iPalabra15= 0;
              indBusqueda= 0;
              resultado.resumenParrafo(sCadena+"...");
              }
            else if (nivelBusqueda==1) // Busqueda normal.
              {
              // Se inicializan todas las variables para poder empezar a buscar desde las 20 palabras siguientes.
              numPalabras= 0;
              aBorrar= 0;
              busqueda="";
              iPalabra5= 0;
              iPalabra10= 0;
              iPalabra15= 0;
              indBusqueda= 0;
              }
            else                       // Busqueda exhaustiva (nivelBusqueda==2).
              {
              // Se quitan de la variable "busqueda" las primeras 5 palabras y se resta la longitud de estas de la variable "indBusqueda".
		    		  numPalabras= 15;
		    		  aBorrar= iPalabra5;
		    		  busqueda= busqueda.substring(aBorrar);

              // Se modifican las variables "iPalabra5", "iPalabra10" y "iPalabra15".
		    		  iPalabra5= iPalabra10 - aBorrar;
		    		  iPalabra10= iPalabra15 - aBorrar;
		    		  iPalabra15= busqueda.length();
		    		  indBusqueda= indBusqueda - aBorrar;
		    		  }
		    		}
		       } //fin del else
		    } //fin del while
		   } //fin del else

		   // Se devuelve los 3 resultados que mas aparecen en las busquedas de un parrafo

		   if (vctResultadoParrafo.size()!=0)
		   {
       // Establecemos las ultimas palabras para el resumen del parrafo.
       // Es el caso que se hayan quedado pocas palabras fuera del rango de los multiplos de 5.
		   if (iPalabra15+10>busqueda.length())
			   resultado.ultimasPalabrasParrafo(busqueda.substring(iPalabra10));
		   else resultado.ultimasPalabrasParrafo(busqueda.substring(iPalabra15));

       // Si un parrafo tiene menos de 125 caracteres se llama a "resumenParrafo" para que establezca el resumen del mismo.
		   if (sCadena.length()>=125)
  				resultado.resumenParrafo();

       // Se calculan y se obtienen las 3 principales URLs.
		   vctResultadoParrafo.CalculoPrincipalesUrls();
		   urlFrecuentes= vctResultadoParrafo.ReturnUrls();
		   aparicionesUrlFrecuentes= vctResultadoParrafo.ReturnAparicionesUrls();

       // Se obtiene el resumen del parrafo y el numero de palabras para la presentacion de resultados y el calculo de probabilidad de copia.
		   parrafo= resultado.Parrafo();
		   numeroPalabrasParrafo= resultado.NumeroPalabras();

		   }
		   else //
		   {
		   if (iPalabra15+10>busqueda.length())
			   resultado.ultimasPalabrasParrafo(busqueda.substring(iPalabra10));
		   else resultado.ultimasPalabrasParrafo(busqueda.substring(iPalabra15));

       // Si un parrafo tiene menos de 125 caracteres se llama a "resumenParrafo" para que establezca el resumen del mismo.
		   if (sCadena.length()>=125)
  				resultado.resumenParrafo();
       // Se obtiene el resumen del parrafo y el numero de palabras para la presentacion de resultados y el calculo de probabilidad de copia.
		   parrafo= resultado.Parrafo();
		   numeroPalabrasParrafo= resultado.NumeroPalabras();

       }
	}

	//*************************************************************************************************************//
	// El metodo "parsear" es el encargado de analizar el documento html devuelto para encontrar si la consulta a  //
	// producido resultados en Google o no se ha encontrado ningun resultado.                                      //
	// RECIBE: El String "linea" del metodo "extraer" con la cadena a buscar en Google.                            //
  //         El String "idioma" segï¿½n el tipo de busqueda: "es" para espaï¿½ol y "en" para ingles.             //
  // DEVUELVE: La variable "vectorURL" con las 10 primeras, si las hubiera, URLs devueltas por el buscador.      //
	//*************************************************************************************************************//

	@SuppressWarnings("unchecked")
	VectorWebParrafo parsear(String linea, String idioma)throws Exception, ExcepcionGoogle {

	String Salida;                              // Variable donde el metodo "ConexionToGoogle" devuelve el codigo html.
  String SalidaParseada;                      // Variable auxiliar para ir parseando el codigo html devuelto.
  String SalidaAuxiliar1="",SalidaAuxiliar2="";     // Variables auxiliares para ir parseando el codigo html devuelto.
  String urls;                                // Variable utilizada en un bucle para almacenar las URLs devueltas.
	int indice;                                 // Variable para el recorrido secuencial para el analisis del codigo html.
	VectorWebParrafo vectorURL = new VectorWebParrafo();      // Variable donde se almacenan todas las URLs encontradas.
	ConexionToGoogle peticionWeb= new ConexionToGoogle();     // Variable para poder realizar la conexion con Google.

  // Se inicializa la variable "vectorURL" que se devuelve.
	vectorURL.IniVectorWebParrafo();

	if (linea.length()!=0)
    {
         if (hayLimite)
            {
            contador++;
            Calendar momentoActual = Calendar.getInstance();

            while (!(tiempo.hanPasadoSuficientesSegundo(momentoInicio,momentoActual,contador,segundosLimite)))
              {
                Thread.sleep(1000);
                momentoActual = Calendar.getInstance();
              }
            }

        // Se sustituyen los espacios por "+" segun hace el buscador en la URL.
				linea= linea.replace(' ','+');
				linea= linea.replace('\t',' ');

        // Se realiza la consulta del string "linea" devolviendo en el String "Salida" el codigo html.
				Salida= peticionWeb.consultar(linea,idioma);

				// El documento html que devuelve google de la peticion tiene una 'cabecera' identica.
				// El documento cambia (existe resultados o no) a partir de la cadena pasada como parametro al metodo indexOf.
				// En SalidaParseada se guarda todo el documento, quitando la parte del principio (que es siempre igual).
				
				// Estos comentarios son parte del codigo html devuelto por Google que han hido cambiando a lo largo del desarrollo del codigo.
				// SalidaParseada= Salida.substring(Salida.indexOf("<font size=+1>&nbsp;<b>La Web</b></font>&nbsp;</td>")+51);
				// SalidaParseada= Salida.substring(Salida.indexOf("<span id=sd>&nbsp;La Web</span>&nbsp;</td>")+42);
				// SalidaParseada= Salida.substring(Salida.indexOf("<span id=sd>&nbsp;La Web&nbsp;</span></td>")+42);

				if (idioma.compareTo("es")==0) // Espaï¿½ol
				    SalidaParseada= Salida.substring(Salida.indexOf("<div id=prs><b>La Web</b></div>")+31);
				else // idioma=="en". Ingles
          SalidaParseada= Salida.substring(Salida.indexOf("div id=prs><b>Web</b></div>")+27);

        // Se analiza si se han encontrado resultados.
				if (((SalidaParseada.startsWith("<td align=right nowrap><font size=-1>Results <b>"))&&(idioma.compareTo("en")==0))
            || ((SalidaParseada.startsWith("<td align=right nowrap><font size=-1>Resultados <b>"))&&(idioma.compareTo("es")==0))
            || ((SalidaParseada.startsWith("</table><div id=res><div><div class="))&&(idioma.compareTo("es")==0))
            || ((SalidaParseada.startsWith("<p>&nbsp;Resultados <b>"))&&(idioma.compareTo("es")==0))
            || ((SalidaParseada.startsWith("<p>&nbsp;Results <b>"))&&(idioma.compareTo("en")==0))
            || ((SalidaParseada.startsWith("</table><table cellspacing=0 cellpadding=0  width=30%"))&&(idioma.compareTo("es")==0)))
            
            {
				      // En caso que si se han encontrado, se buscan todos los resultados (por defecto son 10) devueltos por Google.
							while ((indice= SalidaParseada.indexOf("<h3 class=r><a href=")+21)!=20)
								{
								urls= SalidaParseada.substring(indice);
								urls= urls.substring(0,urls.indexOf('"'));
								// Se indexa cada URL encontrada a la variable "vectorURL".
								vectorURL.addElement(urls);
								SalidaParseada= SalidaParseada.substring(SalidaParseada.indexOf(urls)+urls.length());
								}
            }
				else 
					{
					if (idioma.compareTo("es")==0) // Espaï¿½ol
              {
					     SalidaAuxiliar1= SalidaParseada.substring(SalidaParseada.indexOf("height=14 width=14>&nbsp;")+25);
					     SalidaAuxiliar2= SalidaParseada.substring(SalidaParseada.indexOf("Sugerencias:<ul>")+16);
					    }
					else // idioma=="en". Ingles
              {
						  SalidaAuxiliar1= SalidaParseada.substring(SalidaParseada.indexOf("height=14 width=14>&nbsp;")+25);
						  SalidaAuxiliar2= SalidaParseada.substring(SalidaParseada.indexOf("Suggestions:<ul>")+16);
						  }
					
          // Se controla las salidas html de google en las que no encuentra ningun resultado pero busca automaticamente
          // las palabras sin comillas y propone los resultados sin comillas, y las salidas html de google en las que no encuentra
          // ningun resultado del contenido.
          if ((!((SalidaAuxiliar1.startsWith("No results found for"))&&(idioma.compareTo("en")==0)))
            &&(!((SalidaAuxiliar1.startsWith("No se ha encontrado"))&&(idioma.compareTo("es")==0)))
            &&(!((SalidaAuxiliar2.startsWith("<li>Make sure all words are spelled correctly."))&&(idioma.compareTo("en")==0)))
            &&(!((SalidaAuxiliar2.startsWith("<li>Try different keywords."))&&(idioma.compareTo("en")==0)))
            &&(!((SalidaAuxiliar2.startsWith("<li>Asegúrese de que todas las palabras estén escritas correctamente."))&&(idioma.compareTo("es")==0)))
            &&(!((SalidaAuxiliar2.startsWith("<li>Intente usar otras palabras."))&&(idioma.compareTo("es")==0)))
            &&(!((SalidaAuxiliar2.startsWith("<li>AsegÃºrese de que todas las palabras estÃ©n escritas correctamente."))&&(idioma.compareTo("es")==0))))
            
						  {
              System.out.println("Salida: "+Salida);
              ExcepcionGoogle errorConexionGoogle = new ExcepcionGoogle("errorConexionGoogle");
						  throw errorConexionGoogle;
						  }
					}
          SalidaAuxiliar1= "";
	        SalidaAuxiliar2= "";
				}
  // Si no entra en el IF significa que no hay ningun resultado encontrado para la busqueda realizada.
	return vectorURL;
	}

}
