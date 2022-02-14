package uepb.agendamentoconsultas.calendario;

import java.io.Serializable;

public class Periodo implements Serializable {
    private HoraDia de, ate;

    public Periodo(HoraDia de, HoraDia ate) {
        if(de.compareTo(ate) < 0){
            this.de = de;
            this.ate = ate;
        }else{
            this.de = new HoraDia(0,0);
            this.ate = new HoraDia(0,0);
        }
    }

    public Hora getDe() {
        return de;
    }
    public boolean setDe(HoraDia de) {
        if(de.compareTo(ate) < 0){
            this.de = de;
            return true;
        }
        return false;
    }
    public Hora getAte() {
        return ate;
    }
    public boolean setAte(HoraDia ate) {
        if(de.compareTo(ate) < 0){
            this.ate = ate;
            return true;
        }
        return false;
    }
    public Hora getTotal(){
        Hora horas = new Hora();
        horas.setHora(ate.getHora() - de.getHora());
        horas.setMinutos(ate.getMinutos() - de.getMinutos());
        return horas;
    }
    public Integer intersectWith(Periodo d){
        if(d.getDe().compareTo(this.getDe()) >= 0 && d.getAte().compareTo(this.getAte()) <=0){
            //Periodo esta contido no intervalo
            return 0;
        }else if(d.getDe().compareTo(this.getDe()) >= 0 && d.getDe().compareTo(this.getAte()) <= 0){
            //Hora inicial esta dentro do intervalo
            return 1;
        }else if(d.getAte().compareTo(this.getAte()) <= 0 && d.getAte().compareTo(this.getDe()) >= 0){
            //Hora final esta dentro do intervalo
            return -1;
        }
        //Sem interseção no periodo
        return null;
    }
}
