package uepb.agendamentoconsultas.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import uepb.agendamentoconsultas.calendario.Dia;
import uepb.agendamentoconsultas.calendario.Mes;
import uepb.agendamentoconsultas.calendario.Periodo;
import uepb.agendamentoconsultas.calendario.SimpleDateStruct;
import uepb.agendamentoconsultas.database.DataBaseModel;
import uepb.agendamentoconsultas.services.products.Consulta;

public class Agendamento {
    private DataBaseModel<String, HashMap<String, Mes>> ponteiroDatabase;
    
    public Agendamento(DataBaseModel<String, HashMap<String, Mes>> database){
        this.ponteiroDatabase = database;
    }
    
    public boolean agendarConsulta(Consulta consulta, Periodo hora, Date date){
        Dia diaConsulta = this.getDia(date);
        return diaConsulta.agendarConsulta(String.valueOf(consulta.getIdConsulta()), hora);
    }
    
    public boolean desmarcarConsulta(String idConsulta){
        for(Entry<String, HashMap<String, Mes>> ano : this.getAgendamentos().entrySet()){
            for(Entry<String, Mes> mes : ano.getValue().entrySet()){
                for(Entry<Integer, HashMap<Integer, Dia>> semana : mes.getValue().getDias().entrySet()){
                    for(Entry<Integer, Dia> dia : semana.getValue().entrySet()){
                        if(dia.getValue().getHorariosMarcados().containsKey(idConsulta)){
                            return dia.getValue().desmarcarConsulta(idConsulta);
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public Dia getDia(Date date){
        SimpleDateStruct dayInfo = new SimpleDateStruct(date);
        
        HashMap<String, Mes> meses = this.getAgendamentos().get(String.valueOf(dayInfo.getAno()));
        if(meses == null) {
            this.getAgendamentos().put(String.valueOf(dayInfo.getAno()), new HashMap<>());
            meses = this.getAgendamentos().get(String.valueOf(dayInfo.getAno()));
        }
        Mes mesConsultas = meses.get(String.valueOf(dayInfo.getMes()));
        if(mesConsultas == null) {
            meses.put(String.valueOf(dayInfo.getMes()), new Mes(dayInfo.getMes(), dayInfo.getAno()));
            mesConsultas = meses.get(String.valueOf(dayInfo.getMes()));
        }
        
        Dia diaConsulta = mesConsultas.getDia(dayInfo.getDia());
        return diaConsulta;
    }
    
    public Dia getDia(String date){
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dt = sdf.parse(date);
            
            SimpleDateStruct dayInfo = new SimpleDateStruct(dt);
            
            HashMap<String, Mes> meses = this.getAgendamentos().get(String.valueOf(dayInfo.getAno()));
            if(meses == null) {
                this.getAgendamentos().put(String.valueOf(dayInfo.getAno()), new HashMap<>());
                meses = this.getAgendamentos().get(String.valueOf(dayInfo.getAno()));
            }
            Mes mesConsultas = meses.get(String.valueOf(dayInfo.getMes()));
            if(mesConsultas == null) {
                meses.put(String.valueOf(dayInfo.getMes()), new Mes(dayInfo.getMes(), dayInfo.getAno()));
                mesConsultas = meses.get(String.valueOf(dayInfo.getMes()));
            }
            
            Dia diaConsulta = mesConsultas.getDia(dayInfo.getDia());
            return diaConsulta;
            
        } catch (ParseException ex) {
            System.out.println("Erro ao tentar converter datas! ErrorMessage: " + ex.getMessage());
        }
        return null;
    }
    
    public HashMap<String, HashMap<String, Mes>> getAgendamentos(){
        return this.ponteiroDatabase.getHashMapDataBase();
    }
}
