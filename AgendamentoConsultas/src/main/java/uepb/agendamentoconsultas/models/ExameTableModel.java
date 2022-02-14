package uepb.agendamentoconsultas.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import uepb.agendamentoconsultas.services.products.Exame;

public class ExameTableModel extends AbstractTableModel{
    
    private ArrayList<Exame> exames;
    
    private final String[] header = {"Nome do Exame"};
    
    public ExameTableModel(ArrayList<Exame> exames){
        if(exames == null){
            this.exames = new ArrayList<>();
        }else{
            this.exames = exames;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return header[column];
    }
    
    @Override
    public int getRowCount() {
        return exames.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Exame i = exames.get(rowIndex);
        if(i != null){
            return i.getNome();
        }else{
            return null;
        }
    }
    
    public void setValue(Exame e){
        exames.add(e);
        this.fireTableDataChanged();
    }
    
    public void removeValue(int i){
        if(i != -1){
            exames.remove(i);
            this.fireTableRowsDeleted(i, i);
        }
    }
    
    public ArrayList<Exame> getData(){
        return exames;
    }
    
}
