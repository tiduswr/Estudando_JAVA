package uepb.agendamentoconsultas.calendario;

import java.io.Serializable;

public class HoraDia extends Hora implements Serializable {

    public HoraDia(int hora, int minutos) {
        if(hora > 23){
            this.hora = 0;
            this.minutos = 0;
        }else{
            if(minutos > 59){
                this.hora = hora + (int) (minutos/60);
                this.minutos = minutos - (minutos%60);
            }else{
                this.hora = hora;
                this.minutos = minutos;
            }
            if(hora > 23){
                this.hora = 0;
                this.minutos = 0;
            }
        }   
    }
    
    public HoraDia(Hora h){
        if(hora > 23){
            this.hora = 0;
            this.minutos = 0;
        }else{
            if(minutos > 59){
                this.hora = hora + (int) (minutos/60);
                this.minutos = minutos - (minutos%60);
            }else{
                this.hora = h.getHora();
                this.minutos = h.getMinutos();
            }
            if(hora > 23){
                this.hora = 0;
                this.minutos = 0;
            }
        }   
    }
    
}
