package uepb.agendamentoconsultas.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import uepb.agendamentoconsultas.database.DataBase;
import uepb.agendamentoconsultas.users.Medico;
import uepb.agendamentoconsultas.users.Paciente;
import uepb.agendamentoconsultas.users.Pessoa;

public class PessoaTableModel extends AbstractTableModel{
    
    private ArrayList<Object> db;
    private String[] header = {"CPF", "NOME", "ENDEREÇO", "TIPO USUÁRIO"};
    
    public PessoaTableModel(DataBase dados, String load[]){
        this.db = new ArrayList<>();
        
        for(String i : load){
            if(dados.getPacientes().getHashMapDataBase() != null && i.equalsIgnoreCase("Paciente")){
                dados.getPacientes().getHashMapDataBase().values().forEach(p -> {
                    db.add(p);
                });
            }
            if(dados.getMedicos().getHashMapDataBase() != null && i.equalsIgnoreCase("Médico")){
                dados.getMedicos().getHashMapDataBase().values().forEach(p -> {
                    db.add(p);
                });
            }
            if(dados.getRecepcionistas().getHashMapDataBase() != null && i.equalsIgnoreCase("Recepcionista")){
                dados.getRecepcionistas().getHashMapDataBase().values().forEach(p -> {
                    db.add(p);
                });
            }
        }
        
        
    }
    
    @Override
    public String getColumnName(int c) {
        return header[c];
    }
    
    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        Pessoa p = (Pessoa) db.get(r);
        
        switch(c){
            case 0:
                return p.getNumCPF();
            case 1:
                return (p.getNome() + " " + p.getSobrenome());
            case 2:
                return p.getEndereco().getEnderecoCompleto();
            case 3:
                if(p instanceof Paciente){
                    return "Paciente";
                }else if(p instanceof Medico){
                    return "Médico";
                }else{
                    return "Recepcionista";
                }
            default:
                return null;
        }
    }

    public void updateData(Pessoa n, int row){
        if(row != -1){
            Pessoa u = (Pessoa) this.db.get(row);
            if(updatePessoa(u, n)){
                this.fireTableRowsUpdated(row, row);
            }
        }
        
    }
    
    public void addRow(Object p){
        if(p instanceof Pessoa){
            this.db.add(p);
            this.fireTableDataChanged();
        }
    }
    
    public void removeRow(int r){
        if(r != -1){
            this.db.remove(r);
            this.fireTableRowsDeleted(r, r);
        }
    }
    
    public boolean updatePessoa(Pessoa u, Pessoa n){
        boolean ret = false;
        if (!u.getNome().equals(n.getNome())) {
            u.setNome(n.getNome());
            ret = true;
        }
        if (!u.getSobrenome().equals(n.getSobrenome())) {
            u.setSobrenome(n.getSobrenome());
            ret = true;
        }
        if (!u.getDtNascimento().equals(n.getDtNascimento())) {
            u.setDtNascimento(n.getDtNascimento());
            ret = true;
        }
        if(!u.getEndereco().equals(n.getEndereco())){
            u.setEndereco(n.getEndereco());
            ret = true;
        }
        return ret;
    }
}
