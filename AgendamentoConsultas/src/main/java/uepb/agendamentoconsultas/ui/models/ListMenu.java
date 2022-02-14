package uepb.agendamentoconsultas.ui.models;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import uepb.agendamentoconsultas.ui.centralmenu.MenuItem;
import uepb.agendamentoconsultas.ui.event.EventMenuSelected;

public final class ListMenu<E extends Object> extends JList<E>{
    
    private final DefaultListModel model;
    private int selectedIndex = -1;
    private int overIndex = -1;
    private EventMenuSelected event;
    
    public void addEventMenuSelected(EventMenuSelected e){
        this.event = e;
    }
    
    public ListMenu(){
        model = new DefaultListModel();
        setModel(model);
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    int index = locationToIndex(e.getPoint());
                    Object o = model.getElementAt(index);
                    if(o instanceof ModelMenu){
                        ModelMenu menu = (ModelMenu) o;
                        if(menu.getType() == ModelMenu.MenuType.MENU){
                            selectedIndex = index;
                            if(event != null){
                                event.selected(index);
                            }
                        }
                    }else{
                        selectedIndex = index;
                    }
                    repaint();
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                overIndex = -1;
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                if(index!=overIndex){
                    Object o = model.getElementAt(index);
                    if(o instanceof ModelMenu){
                        ModelMenu menu = (ModelMenu) o;
                        if(menu.getType() == ModelMenu.MenuType.MENU){
                            overIndex = index;
                        }else{
                            overIndex = -1;
                        }
                        repaint();
                    }
                }
                super.mouseMoved(e);
            }
        });
    }
    
    @Override
    public ListCellRenderer<? super E> getCellRenderer(){
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                ModelMenu data;
                if(value instanceof ModelMenu){
                    data = (ModelMenu) value;
                }else{
                    data = new ModelMenu("", value + "", ModelMenu.MenuType.EMPTY);
                }
                MenuItem item = new MenuItem(data);
                item.setSelected(selectedIndex == index);
                item.setOver(overIndex == index);
                return item;
            }
        };
    }
    
    public void addItem(ModelMenu data){
        this.model.addElement(data);
    }
}
