package uepb.agendamentoconsultas.services.products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Consulta implements Serializable {
    private final int idConsulta;
    private Date dtConsulta, dtSolicitacao;
    private String cpfPaciente, cpfMedico;
    private boolean finalizada;
    private String parecerMedico;
    private ArrayList<Medicamento> medicamentos;
    private ArrayList<Exame> exames;

    public Consulta(int idConsulta, Date dtConsulta, Date dtSolicitacao, String cpfPaciente, String cpfMedico) {
        this.dtConsulta = dtConsulta;
        this.dtSolicitacao = dtSolicitacao;
        this.cpfPaciente = cpfPaciente;
        this.cpfMedico = cpfMedico;
        this.idConsulta = idConsulta;
        this.finalizada = false;
        this.medicamentos = new ArrayList<>();
        this.exames =  new ArrayList<>();
    }
    
    public boolean addMedicamento(Medicamento m){
        return this.medicamentos.add(m);
    }
    public boolean addExame(Exame e){
        return this.exames.add(e);
    }
    public boolean removeMedicamento(int pos){
        return this.medicamentos.remove(pos) != null;
    }
    public boolean removeExame(int pos){
        return this.exames.remove(pos) != null;
    }
    
    public Medicamento getMedicamento(int pos){
        return this.medicamentos.get(pos);
    }
    public Exame getExame(int pos){
        return this.exames.get(pos);
    }
    public int getIdConsulta() {
        return idConsulta;
    }
    public Date getDtConsulta() {
        return dtConsulta;
    }
    public void setDtConsulta(Date dtConsulta) {
        this.dtConsulta = dtConsulta;
    }
    public Date getDtSolicitacao() {
        return dtSolicitacao;
    }
    public void setDtSolicitacao(Date dtSolicitacao) {
        this.dtSolicitacao = dtSolicitacao;
    }
    public String getCpfPaciente() {
        return cpfPaciente;
    }
    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }
    public String getCpfMedico() {
        return cpfMedico;
    }
    public void setCpfMedico(String cpfMedico) {
        this.cpfMedico = cpfMedico;
    }
    public String getParecerMedico() {
        return parecerMedico;
    }
    public void setParecerMedico(String parecerMedico) {
        this.parecerMedico = parecerMedico;
        this.finalizada = true;
    }
    public boolean isFinalizada() {
        return finalizada;
    }
    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }
    public ArrayList<Exame> getExames() {
        return exames;
    }
}
