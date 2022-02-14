package uepb.agendamentoconsultas.users.data;

import java.io.Serializable;
import java.util.Objects;

public class Endereco implements Serializable {
    private String rua, bairro, cidade, estado;
    private int numCasa;

    public Endereco(String rua, String bairro, String cidade, String estado, int numCasa) {
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.numCasa = numCasa;
    }
    
    public String getEnderecoCompleto(){
        return getRua() + ", " + String.valueOf(getNumCasa()) + ", " + 
                getBairro() + ", " + getEstado() + ", Brasil.";
    }
    
    public String getRua() {
        return rua;
    }
    public String getBairro() {
        return bairro;
    }
    public String getCidade() {
        return cidade;
    }
    public String getEstado() {
        return estado;
    }
    public int getNumCasa() {
        return numCasa;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setNumCasa(int numCasa) {
        this.numCasa = numCasa;
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
        final Endereco other = (Endereco) obj;
        if (this.numCasa != other.numCasa) {
            return false;
        }
        if (!Objects.equals(this.rua, other.rua)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        return true;
    }
    
}
