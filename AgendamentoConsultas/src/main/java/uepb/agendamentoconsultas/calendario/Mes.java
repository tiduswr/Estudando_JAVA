package uepb.agendamentoconsultas.calendario;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;

/**Classe para controle de agendamentos por dia, considerando a disponibilidade
 * @version 1.0
 */
public class Mes implements Serializable {
    private HashMap<Integer, HashMap<Integer, Dia>> dias;
    private final int mes;
    private final int ano;
    
     /**Construtor para criar o calendario de agendamentos
     * @param mes int - mes para agendamento
     * @param ano int - ano para agendamento
     */
    public Mes(int mes, int ano){
        this.dias = new HashMap<>();
        GregorianCalendar aux;
        
        if(mes < 1 || mes > 12 || ano < 1901){
            aux = new GregorianCalendar();
            aux.set(Calendar.DATE, 1);
            mes = aux.get(Calendar.MONTH);
            ano = aux.get(Calendar.YEAR);
        }else{
            mes--;
            aux = new GregorianCalendar(ano, mes, 1);
        }
         
        for(int i = 1; i <=6; i++){
            HashMap<Integer, Dia> semana = new HashMap<>();
            
            if(aux.get(Calendar.MONTH) == mes){
                int diaSemana = aux.get(Calendar.DAY_OF_WEEK);
                
                for(int j = 1; j <= 7; j++){
                    
                    if(j >= diaSemana){
                        semana.put(j, new Dia(aux.get(Calendar.DATE), aux.get(Calendar.DAY_OF_WEEK)));
                        aux.add(Calendar.DATE, 1);
                        diaSemana = aux.get(Calendar.DAY_OF_WEEK);
                        if(aux.get(Calendar.MONTH) != mes){break;}
                    }
                }
                
                this.dias.put(i, semana);
            }else{
                this.dias.put(i, semana);
                break;
                
            }
        }
        this.mes = mes;
        this.ano = ano;
    }
    
    /**Função que retorna os dias criados
     * @return HashMap{@literal <}Integer, HashMap{@literal <}Integer, Dia{@literal >>}
     */
    public HashMap<Integer, HashMap<Integer, Dia>> getDias() {
        return dias;
    }
    /**Retorna o mês em forma de inteiro
     * @return int
     */
    public int getMes() {
        return mes;
    }
    /**Retorna o ano em forma de inteiro
     * @return int
     */
    public int getAno() {
        return ano;
    }
    public Dia getDia(int dia){
        for(Entry<Integer, HashMap<Integer, Dia>> s : this.dias.entrySet()){
            for(Entry<Integer, Dia> d : s.getValue().entrySet()){
                if(d.getValue().getDia() == dia) return d.getValue();
            }
        }
        return null;
    }
    
    /**Função para teste de verificação da saida dos dados
     */
    public void print(){
        this.dias.entrySet().forEach(s -> {
            s.getValue().entrySet().forEach( d -> {
                System.out.println(d.getValue().toString());
            });
        });
    }
    
}
