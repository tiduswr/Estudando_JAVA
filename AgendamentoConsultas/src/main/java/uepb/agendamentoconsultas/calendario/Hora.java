package uepb.agendamentoconsultas.calendario;

import java.io.Serializable;

public class Hora implements Comparable<Hora>, Serializable {
    protected int hora, minutos;

    public Hora(int hora, int minutos) {
        if(minutos > 60){
            this.hora = hora + (int) (minutos/60);
            this.minutos = minutos - (minutos%60);
        }else{
            this.hora = hora;
            this.minutos = minutos;
        }
    }

    public Hora() {
        this.hora = 0;
        this.minutos = 0;
    }

    public int getHora() {
        return hora;
    }
    public void setHora(int hora) {
        this.hora = hora;
    }
    public int getMinutos() {
        return minutos;
    }
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    @Override
    public String toString() {
        return hora + ":" + minutos;
    }
    
    @Override
    public int compareTo(Hora o) {
        int minsObj = this.getMinutos() + (this.getHora() * 60);
        int minsComp = o.getMinutos() + (o.getHora() * 60);
        if(minsObj > minsComp){
            return 1;
        }else if(minsObj < minsComp){
            return -1;
        }else{
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hora other = (Hora) obj;
        if (this.hora != other.hora) {
            return false;
        }
        if (this.minutos != other.minutos) {
            return false;
        }
        return true;
    }
    
}
