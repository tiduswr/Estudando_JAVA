package uepb.agendamentoconsultas.calendario;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Dia implements Serializable {
    private final int dia;
    private final int diaSemana;
    private HashMap<String, Periodo> consultas; //id consulta e hora marcada
    
    public Dia(int dia, int diaSemana) {
        this.dia = dia;
        this.diaSemana = diaSemana;
        this.consultas = new HashMap<>();
    }
    
    private boolean alreadyRegistered(Iterator<Entry<String, Periodo>> consultas, Periodo horaConsulta){
        while(consultas != null && consultas.hasNext()){
            Entry<String, Periodo> aux = consultas.next();
            if(aux.getValue().intersectWith(horaConsulta) != null){
                return true;
            }
        }
        return false;
    }
    public boolean agendarConsulta(String idConsulta, Periodo horaConsulta){
        if(!this.alreadyRegistered(this.consultas.entrySet().iterator(), horaConsulta)){
            if(this.consultas.get(idConsulta) == null){
                this.consultas.put(idConsulta, horaConsulta);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    public HashMap<String, Periodo> getHorariosMarcados(){
        return this.consultas;
    }
    public boolean desmarcarConsulta(String idConsulta){
        return this.consultas.remove(idConsulta) != null;
    }
    public int getDiaSemana(){
        return this.diaSemana;
    }
    public int getDia() {
        return dia;
    }
    
}
