import java.io.*;

class Calculos {

	//*************************************************************************************************************//
	// El metodo "numParrafos" es el encargado de contar el numero de parrafos que tiene un documento.             //
  // RECIBE: La variable BufferedReader "texto" con el contenido del documento a analziar.                       //
  // DEVUELVE: Devuelve un numero entero (int) con el numero de parrafos que contiene el documento.              //
	//*************************************************************************************************************//
  int numParrafos(BufferedReader texto) throws Exception {

  int cont=0;             // Variable de tipo entero que se incrementa cada vez que se descubre una nueva linea.
  //String sCadena="";      // Variable en la que se va almacenando y deshechando las lineas del documento.
  
  // Bucle en el que, cada vez que se recoge un parrafo en la variable "sCadena", se incrementa en uno la variable
  // "cont" para contabilizar el numero de parrados que contiene el documento "texto" de tipo "BufferedReader".

  while (texto.readLine()!=null)	  
    cont++;

  // Se devuelve el número de parrafos encontrados en una variable de tipo "int".
  return cont;
  }
}
