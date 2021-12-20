package dialogui.standardclasses;

import dialogui.interfaces.MessageFrame;
import java.awt.Component;
import javax.swing.JOptionPane;

/**Interface padrão para os formulários da função showMessage() da classe DialogUI
 * @author tiduswr*/
public class ShowMessage implements MessageFrame{
    private String message, title;
    private Component parent;

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setParentComponent(Component parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        JOptionPane.showMessageDialog(this.parent, this.message, this.title, JOptionPane.DEFAULT_OPTION);
    }   
}