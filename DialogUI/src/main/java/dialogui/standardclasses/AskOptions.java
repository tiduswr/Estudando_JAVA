package dialogui.standardclasses;

import dialogui.interfaces.OptionDialogFrame;
import java.awt.Component;
import javax.swing.JOptionPane;

/**Classe de solicitação de opções para o usuário Utilizando o JOptionPane impmentada com a 
 * OptionDialogFrame
 * @author tiduswr
 */
public class AskOptions implements OptionDialogFrame{
    private String values[];
    private String selectedValue;
    private String title, normalQuestion, errorInformation;
    private Component parentComponent;
    private boolean isValid;
    
    /**Construtor vazio da Classe AskOptions
     */
    public AskOptions(){}
    
    @Override
    public void setOptions(String[] options) {
        this.values = options;
    }

    @Override
    public String getValue() {
        return this.selectedValue;
    }

    @Override
    public void setMessage(String message) {
        this.normalQuestion = message;
    }

    @Override
    public void setErrorMessage(String message) {
        this.errorInformation = message;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setParentComponent(Component parent) {
        this.parentComponent = parent;
    }
    
    @Override
    public void run() {
        if(this.values != null){
            int optionSelected;
            optionSelected = JOptionPane.showOptionDialog(this.parentComponent, this.normalQuestion, this.title, 
                                                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
                                                            null, this.values, this.values[0]);
            if(optionSelected != -1){
                this.selectedValue = this.values[optionSelected];
                this.isValid = true;
            }else{
                JOptionPane.showMessageDialog(this.parentComponent, this.errorInformation, this.title, 
                        JOptionPane.ERROR_MESSAGE);
                this.selectedValue = null;
                this.isValid = false;
            }
        }
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }
    
}
