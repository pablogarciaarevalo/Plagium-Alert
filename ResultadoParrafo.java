class ResultadoParrafo {

	int numPalabras= 1;          // Contabiliza el numero de palabras que hay en un parrafo.
	String primerasPalabras;     // Almacena las primeras palabras de un parrafo.
  String ultimasPalabras;      // Almacena las ultimas palabras de un parrafo.
  String resumenParrafo;       // Almacena las palabras que resumen un parrafo (primerasPalabras...ultimasPalabras).

	//*************************************************************************************************************//
	// El metodo "Parrafo" devuelve la variable "resumenParrafo" para resumir el texto de un parrafo.              //
	//*************************************************************************************************************//
	String Parrafo(){
		return resumenParrafo;
		}

  //*************************************************************************************************************//
	// El metodo "NumeroPalabras" devuelve el numero de palabras que hay en un parrafo. Si el parrafo contiene     //
	// menos de 100 caracteres, se devuelve el valor 1 a "NumeroPalabras" ya que se realiza una sola busqueda.     //
	// El objetivo de conocer el numero de palabras de un parrafo, y sabiendo el numero de veces que una URL se    //
  // repite en las busquedas de un parrafo,  es poder calcular un porcentaje de copia del parrafo de una URL.    //
	//*************************************************************************************************************//

	int NumeroPalabras(){
		return numPalabras;
		}

  //*************************************************************************************************************//
	// El metodo "nuevaPalabra" incrementa el numero de palabras en un parrafo.                                    //
	//*************************************************************************************************************//
	void nuevaPalabra(){
		numPalabras++;
		}

	//*************************************************************************************************************//
	// El metodo "primerasPalabrasParrafo" establece las primeras palabras del resumen del parrafo para el         //
	// resultado del analisis del parrafo. Este metodo es llamado cuando el parrado tiene mas de 100 caracteres.   //
	// RECIBE: El String "palabras" con las primeras palabras del parrafo que se analiza.                          //
	//*************************************************************************************************************//
	void primerasPalabrasParrafo(String palabras){
		primerasPalabras= palabras;
		}	

	//*************************************************************************************************************//
	// El metodo "ultimasPalabrasParrafo" establece las ultimas palabras del resumen del parrafo para el           //
	// resultado del analisis del parrafo. Este metodo es llamado cuando el parrado tiene mas de 100 caracteres.   //
	// RECIBE: El String "palabras" con las ultimas palabras del parrafo que se analiza.                           //
	//*************************************************************************************************************//
	void ultimasPalabrasParrafo(String palabras){
		ultimasPalabras= palabras;
		}

	//*************************************************************************************************************//
	// El metodo "resumenParrafo" establece las palabras del resumen del parrafo para el resultado del analisis    //
	// del parrafo. Este metodo es llamado cuando el parrado tiene menos de 100 caracteres, por lo que se          //
	// almacenan todos los caracteres como resumen del parrafo.                                                    //
	// RECIBE: El String "palabras" con todas las palabras del parrafo que se analiza.                             //
	//*************************************************************************************************************//
	void resumenParrafo(String palabras){
		resumenParrafo= palabras;
		}

	//*************************************************************************************************************//
	// El metodo "resumenParrafo" concatena las variables "primerasPalabras" con "ultimasPalabras" separandolas    //
	// con puntos suspensivos para mostrar el resumen del parrado.                                                 //
	//*************************************************************************************************************//
	void resumenParrafo(){
		resumenParrafo= primerasPalabras+"... "+ultimasPalabras;
		}
			
	}
