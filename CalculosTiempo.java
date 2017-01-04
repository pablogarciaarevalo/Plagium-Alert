import java.util.Calendar;

public class CalculosTiempo{

  int horaInicio=0;
  int minutosInicio=0;
  int segundosInicio=0;
  int anioInicio=0;
  int mesInicio=0;
  int diaInicio=0;

  int horaMomentoActual=0;
  int minutosMomentoActual=0;
  int segundosMomentoActual=0;
  int anioMomentoActual=0;
  int mesMomentoActual=0;
  int diaMomentoActual=0;
  
  int segundosDeDiferencia= 0;

  boolean esBisiesto(int anio){
  if ((anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0)))
	   return true;
  else
	   return false;
  }
  
  boolean esElMismoDia(){

      return ((anioInicio==anioMomentoActual)&&(mesInicio==mesMomentoActual)&&(diaInicio==diaMomentoActual));
  }
  
  boolean esElMismoMes(){

      return ((anioInicio==anioMomentoActual)&&(mesInicio==mesMomentoActual));
  }

  boolean esElMismoAnio(){

      return (anioInicio==anioMomentoActual);
  }

  boolean hanPasadoSuficientesSegundo(Calendar momentoInicio, Calendar momentoActual, int saltos, int segundosLimite){

      segundosDeDiferencia= segundosLimite;

      horaInicio= momentoInicio.get(java.util.Calendar.HOUR_OF_DAY);
      minutosInicio= momentoInicio.get(java.util.Calendar.MINUTE);
      segundosInicio= momentoInicio.get(java.util.Calendar.SECOND);
      anioInicio= momentoInicio.get(java.util.Calendar.YEAR);
      mesInicio= momentoInicio.get(java.util.Calendar.MONTH);
      diaInicio= momentoInicio.get(java.util.Calendar.DAY_OF_MONTH);
      
      horaMomentoActual= momentoActual.get(java.util.Calendar.HOUR_OF_DAY);
      minutosMomentoActual= momentoActual.get(java.util.Calendar.MINUTE);
      segundosMomentoActual= momentoActual.get(java.util.Calendar.SECOND);
      anioMomentoActual= momentoActual.get(java.util.Calendar.YEAR);
      mesMomentoActual= momentoActual.get(java.util.Calendar.MONTH);
      diaMomentoActual= momentoActual.get(java.util.Calendar.DAY_OF_MONTH);

      //System.out.println("PRIMERO: "+diaInicio+" de "+mesInicio+" de "+anioInicio+" a las "+horaInicio+":"+minutosInicio+":"+segundosInicio);
      //System.out.println("SEGUNDO: "+diaMomentoActual+" de "+mesMomentoActual+" de "+anioMomentoActual+" a las "+horaMomentoActual+":"+minutosMomentoActual+":"+segundosMomentoActual);

      int segundosTotalesInicio= (horaInicio*60*60)+(minutosInicio*60)+segundosInicio;
      int segundosTotalesMomentoActual= (horaMomentoActual*60*60)+(minutosMomentoActual*60)+segundosMomentoActual;

      if ((esElMismoDia())&&(segundosTotalesInicio+(segundosDeDiferencia*saltos)-1<segundosTotalesMomentoActual))
        {
          return true;
        }
      else if ((esElMismoDia())&&(segundosTotalesInicio+(segundosDeDiferencia*saltos)-1>=segundosTotalesMomentoActual))
        {
          return false;
        }
      // 86400 es el numero de segundos que tiene un dia.
      else if ((esElMismoMes())&&(diaInicio<diaMomentoActual)&&(segundosTotalesInicio+(segundosDeDiferencia*saltos)-1<segundosTotalesMomentoActual+86400))
        {
          return true;
        }
      else if ((esElMismoMes())&&(diaInicio<diaMomentoActual)&&(segundosTotalesInicio+(segundosDeDiferencia*saltos)-1>=segundosTotalesMomentoActual+86400))
        {
          return false;
        }
      else if ((esElMismoAnio())&&(mesInicio<mesMomentoActual)&&(segundosTotalesInicio+(segundosDeDiferencia*saltos)-1<segundosTotalesMomentoActual+86400))
        {
          return true;
        }
      else if ((esElMismoAnio())&&(mesInicio<mesMomentoActual)&&(segundosTotalesInicio+(segundosDeDiferencia*saltos)-1>=segundosTotalesMomentoActual+86400))
        {
          return false;
        }
      else return false;
  }
}
