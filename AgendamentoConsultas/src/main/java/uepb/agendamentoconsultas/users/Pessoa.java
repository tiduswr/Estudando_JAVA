package uepb.agendamentoconsultas.users;

import uepb.agendamentoconsultas.users.data.Endereco;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public abstract class Pessoa implements Serializable {
    private String nome, sobrenome;
    private Date dtNascimento;
    private String numCPF;
    private Endereco endereco;
    
    public Pessoa(){}
    public Pessoa(String nome, String sobrenome, Date dtNascimento, String numCPF, Endereco endereco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dtNascimento = dtNascimento;
        this.numCPF = numCPF;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public Date getDtNascimento() {
        return dtNascimento;
    }
    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }
    public String getNumCPF() {
        return numCPF;
    }
    public void setNumCPF(String numCPF) {
        this.numCPF = numCPF;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.sobrenome, other.sobrenome)) {
            return false;
        }
        if (!Objects.equals(this.numCPF, other.numCPF)) {
            return false;
        }
        if (!Objects.equals(this.dtNascimento, other.dtNascimento)) {
            return false;
        }
        return true;
    }
    
    
    
}
