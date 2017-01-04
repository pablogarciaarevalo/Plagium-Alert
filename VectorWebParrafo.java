import java.util.*;


@SuppressWarnings("unchecked")
class VectorWebParrafo extends Vector {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
// THIS                                       // El propio objeto almacena todas las URLs que aparecen en un parrafo.
Vector aparicionesUrlTotales = new Vector();  // Vector en el que se almacenan el numero de veces que aparecen todas las URLs.
String urlFrecuentes[] = new String[3];       // Array de 3 campos en el que se almacenan las 3 URLs que mas aparecen en un parrafo.
int aparicionesUrlFrecuentes[] = new int[3];  // Array de 3 campos en el que se almacenan el numero de veces que aparecen las URLs.

	//*************************************************************************************************************//
	// El metodo "IniVectorWebParrafo" es el encargado de inicializar el propio objeto y las 3 variables globales. //
	//*************************************************************************************************************//
void IniVectorWebParrafo (){
	this.clear();
	aparicionesUrlTotales.clear();
	aparicionesUrlFrecuentes[0]= 0;
	aparicionesUrlFrecuentes[1]= 0;
	aparicionesUrlFrecuentes[2]= 0;
	}

	//*************************************************************************************************************//
	// El metodo "ReturnUrls" es el encargado de devolver el Array "urlFrecuentes" para su tratamiento.            //
	//*************************************************************************************************************//
String[] ReturnUrls() {
	return urlFrecuentes;
	}

	//*************************************************************************************************************//
	// El metodo "ReturnAparicionesUrls" es el encargado de devolver el Array "aparicionesUrlFrecuentes" para su   //
	// tratamiento.                                                                                                //
	//*************************************************************************************************************//
int[] ReturnAparicionesUrls() {
	return aparicionesUrlFrecuentes;
	}

	//*************************************************************************************************************//
	// El metodo "AgregarElemento" agrega un elemento en la variable "aparicionesUrlTotales".                      //
	// RECIBE: El elemento a insertar.                                                                             //
	//*************************************************************************************************************//
void AgregarElemento (int numero){
	aparicionesUrlTotales.addElement(numero);
	}

	//*************************************************************************************************************//
	// El metodo "AgregarElementoEn" agrega un elemento en una posicion determinada en la variable                 //
	// "aparicionesUrlTotales".                                                                                    //
	// RECIBE: Como primer parametro el elemento a insertar.                                                       //
	//         Como segundo parametro la posicion dentro de "aparicionesUrlTotales" donde se ha de insertar.       //
	//*************************************************************************************************************//
void AgregarElementoEn (int numero, int posicion){
	aparicionesUrlTotales.insertElementAt (numero, posicion);
	}

	//*************************************************************************************************************//
	// El metodo "AddElementoWeb" es el encargado de agregar nuevas URLs producidas por una nueva busqueda.        //
	// RECIBE: El VectorWebParrafo "vectorResult" que almacena URLs producidas por una nueva busqueda.             //
	// DEVUELVE: El propio objeto "this" modificado con las nuevas URLs.                                           //
	//*************************************************************************************************************//
VectorWebParrafo AddElementoWeb (VectorWebParrafo vectorResult) {
	for (int i = 0; i < vectorResult.size (); i++)
	
	     	// Si el propio objeto contiene la URL de vectorResult  se incrementa el numero de veces que aparece.
     		if (this.contains(vectorResult.elementAt(i)))
     			{
     			// Se calcula la posicion en la que esta.
     			//int pos= (int) this.indexOf(vectorResult.elementAt (i));
     			int pos= this.indexOf(vectorResult.elementAt (i));

     			// Se incrementa en uno el elemento de aparicionesUrlTotales de esa posicion.
     			Integer aux= (Integer) aparicionesUrlTotales.elementAt(pos);
     			int num= aux.intValue();
     			num++;
     			aparicionesUrlTotales.remove(pos);
     			AgregarElementoEn (num, pos);
    			}
        // Si la URL no esta indexada se crea un nuevo elemento.
     		else
     				{
     				this.addElement(vectorResult.elementAt (i));
     				AgregarElemento(1);
     				}	
	return this;
	}

	//*************************************************************************************************************//
	// El metodo "CalculoPrincipalesWeb" es el encargado de realizar la busqueda, dentro del propio objeto, de     //
	// las 3 principales URLs.                                                                                     //
	//*************************************************************************************************************//
void CalculoPrincipalesUrls () {

    // Se hace un recorrido por todo el vector (this) comparando el numero de veces que aparecen.
		for (int i=0; i<aparicionesUrlTotales.size();i++)
			{
			 Integer aux= (Integer) aparicionesUrlTotales.elementAt(i);

       // La variable "num" toma el valor del numero de apariciones de la url que se trata en cada iteracion del bucle for.
       int num= aux.intValue();

       // Se busca la url que menos veces aparece de las 3 principales (si las hubiera) para sustituir la que menos aparezca,
       // en el caso que la que se esta tratando en esta iteracion sea mayor que , por lo menos, una de ellas.
       int j=1;
       int menor= 0;    // En la variable "menor" se almacena el indice de la url a sustituir dentro del array de las 3 principales.
       while (j<3)
        {
          if (aparicionesUrlFrecuentes[j]<aparicionesUrlFrecuentes[menor])
            menor= j;
          j++;
        }

       // Se sustituye la nueva url encontrada (que tiene mas apariciones) por la url que menos apariciones tiene de las 3 que tiene el array.
       if (num>aparicionesUrlFrecuentes[menor])
       			{
       			aparicionesUrlFrecuentes[menor]= num;
       			String nombreWeb= (String) this.elementAt(i);
       			urlFrecuentes[menor]= nombreWeb;
       			}
			}

	}

}
