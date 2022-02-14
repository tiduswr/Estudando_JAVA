package uepb.agendamentoconsultas.calendario;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimpleDateStruct {
    private final int dia, mes, ano;

    public SimpleDateStruct(Date date) {
        GregorianCalendar dayInfo = new GregorianCalendar();
        dayInfo.setTime(date);
        
        this.ano = dayInfo.get(Calendar.YEAR);
        this.mes = dayInfo.get(Calendar.MONTH);
        this.dia = dayInfo.get(Calendar.DATE);
    }

    public int getDia() {
        return dia;
    }
    public int getMes() {
        return mes;
    }
    public int getAno() {
        return ano;
    }
}
