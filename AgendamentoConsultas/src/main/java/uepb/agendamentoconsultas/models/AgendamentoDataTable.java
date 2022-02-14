package uepb.agendamentoconsultas.models;

import java.util.Objects;
import uepb.agendamentoconsultas.calendario.Periodo;
import uepb.agendamentoconsultas.users.Medico;
import uepb.agendamentoconsultas.users.Paciente;

public class AgendamentoDataTable {
    
    private int idConsulta;
    private Paciente paciente;
    private Medico medico;
    private Periodo horaMarcada;

    public AgendamentoDataTable(int idConsulta, Paciente paciente, Medico medico, Periodo horaMarcada) {
        this.idConsulta = idConsulta;
        this.paciente = paciente;
        this.medico = medico;
        this.horaMarcada = horaMarcada;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Periodo getHoraMarcada() {
        return horaMarcada;
    }

    public void setHoraMarcada(Periodo horaMarcada) {
        this.horaMarcada = horaMarcada;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final AgendamentoDataTable other = (AgendamentoDataTable) obj;
        if (this.idConsulta != other.idConsulta) {
            return false;
        }
        if (!this.paciente.getNumCPF().equalsIgnoreCase(other.getPaciente().getNumCPF())) {
            return false;
        }
        if (!this.medico.getNumCPF().equalsIgnoreCase(other.getMedico().getNumCPF())) {
            return false;
        }
        if (!this.horaMarcada.equals(other.getHoraMarcada())) {
            return false;
        }
        return true;
    }
    
    
    
}
