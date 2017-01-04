import java.io.*;
import java.util.Properties;

class DocToTxt {

	//*************************************************************************************************************//
	// El metodo "pasarAtxt" es el encargado de pasar un documento DOC a formato TXT.                              //
	// Es un requisito tener el ejecutable "antiword.exe" en la ruta C:\antiword\ para plataformas Windows.        //
  // RECIBE: El string "nombreArchivo" con el nombre del archivo DOC a transformar.                              //
  // DEVUELVE: Devuelve un BufferedReader con el contenido del documento DOC.                                    //
	//*************************************************************************************************************//
	BufferedReader pasarAtxt(String nombreArchivo) throws Exception {

    String comando;         // Variable en la que se almacena el comando a ejecutar.
    String sistemaOperativo= "";
    Process proceso=null;
    
    sistemaOperativo= System.getProperty("os.name");
    if (sistemaOperativo.compareTo("Linux")==0)
        {
        // Se forma el comando a ejecutar.
        comando= "antiword -t -w 0 "+nombreArchivo;
        String[] command= {"sh","-c",comando};

        // Se ejecuta el comando.
        proceso = Runtime.getRuntime().exec(command);
        }
    else
        {
          // Se forma el comando y se ejecuta.
          comando= "cmd /c C:\\antiword\\antiword.exe -m 8859-5.txt -t -w 0 \""+nombreArchivo;
          proceso = Runtime.getRuntime().exec (comando);
        }


    // Se captura el resultado en la variable "br" para su posterior tratamiento.
    BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
    return br;
	}
}
