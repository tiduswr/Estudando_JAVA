package uepb.agendamentoconsultas.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public final class CustomTable extends JTable{
    
    public CustomTable(){
    
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer(){
        
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                TableHeader header = new TableHeader(value + "");
                header.setHorizontalAlignment(JLabel.LEFT);
                return header;
            }
            
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
        
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                c.setBackground(Color.WHITE);
                setBorder(noFocusBorder);
                if(isSelected){
                    c.setForeground(new Color(13, 113, 182));
                    c.setFont(new Font(getFont().getName(), 1,getFont().getSize()));
                }else{
                    c.setForeground(new Color(102, 102, 102));
                    c.setFont(new Font(getFont().getName(), 0,getFont().getSize()));
                }
                return c;
            }
            
        });
        
    }
    
    public static void setBasicScrollConfigurations(JScrollPane scroll){
        CustomScrollBar sb = new CustomScrollBar();
        sb.setSizeBar(new java.awt.Dimension(10,10));
        sb.setColorBar(Color.gray);
        scroll.setVerticalScrollBar(sb);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(null);
    }
    
}
