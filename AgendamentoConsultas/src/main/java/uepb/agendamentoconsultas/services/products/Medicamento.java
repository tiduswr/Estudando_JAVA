package uepb.agendamentoconsultas.services.products;

import java.io.Serializable;

public class Medicamento implements Serializable{
    private String nomeMedicamento;
    private double qtdMlsPorDia;
    private int diasUsando;

    public Medicamento(String nomeMedicamento, double qtdMlsPorDia, int diasUsando) {
        this.nomeMedicamento = nomeMedicamento;
        this.qtdMlsPorDia = qtdMlsPorDia;
        this.diasUsando = diasUsando;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }
    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }
    public double getQtdMlsPorDia() {
        return qtdMlsPorDia;
    }
    public void setQtdMlsPorDia(double qtdMlsPorDia) {
        this.qtdMlsPorDia = qtdMlsPorDia;
    }
    public int getDiasUsando() {
        return diasUsando;
    }
    public void setDiasUsando(int diasUsando) {
        this.diasUsando = diasUsando;
    }
}
