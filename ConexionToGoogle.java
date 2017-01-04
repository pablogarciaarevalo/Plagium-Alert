import java.net.*;
import java.io.*;

class ConexionToGoogle {

	//*************************************************************************************************************//
	// El metodo "consultar" es el encargado de realizar la conexion http con GOOGLE y devolver el codigo html.    //
  // RECIBE: El string "palabrasAbuscar" del metodo "parsear" de la clase Analiza con 20 palabras.               //
  //         El String "idioma" segun el tipo de busqueda: "es" para español y "en" para ingles.                 //
  // DEVUELVE: Devuelve un string con el codigo html generado por la peticion Web.                               //
	//*************************************************************************************************************//
	String consultar(String palabrasAbuscar, String idioma) throws Exception {
	
	    StringBuffer strBuff = new StringBuffer();  // Variable en la que se va almacena el codigo html.
			String inputLine="";                        // Variable auxiliar para ir procesando las lineas del html devuelto.

      // En las siguientes tres lineas se inicializa y establece la variable "conexion" que es la encargada de la peticion Web.
			URL urlobjeto = new URL("http://www.google.es/search?hl="+idioma+"&q=%22"+palabrasAbuscar+"%22");
			URLConnection conexion = urlobjeto.openConnection();
			conexion.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");

      // Se almacena en la variable "in" el codigo html devuelto por la peticion de la variable "conexion".
			BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

      // Se pasa la informacion de la variable BufferedReader "in" a la variable StringBuffer "strBuff"
      // con la ayuda de la variable "inputLine" y se cierra la variable "in".
			while ((inputLine = in.readLine()) != null){
        strBuff.append(inputLine);
        };
			in.close();

      // Se devuelve el codigo html en una variable de tipo String.
			return strBuff.toString();
	}
}
