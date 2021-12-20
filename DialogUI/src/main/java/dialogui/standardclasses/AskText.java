package dialogui.standardclasses;

import dialogui.interfaces.InputDialogFrame;
import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**Classe que pega um valor String com a classe JOptionPane
 * @author tiduswr*/
public class AskText implements InputDialogFrame {
    private String value;
    private String title, normalQuestion, errorInformation;
    private boolean validExpression;
    private String regex;
    private Component parentComponent;
    
    /**Construtor para a Classe AskText.
     */
    public AskText(){}
    
    /**Método de execução da interface Runnable.
     * É utilizado a JOptionPane para mostrar a mensagem ao usuário, essa implementação é pensada para se usar na
     * Thread Queue do Swing
     */
    @Override
    public void run() {
        value = JOptionPane.showInputDialog(this.parentComponent, this.normalQuestion, this.title,
                    JOptionPane.QUESTION_MESSAGE);
        System.out.println(value);
        this.validate(regex);
        if(!this.validExpression){
            JOptionPane.showMessageDialog(this.parentComponent, this.errorInformation, this.title,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**Valida o valor digitado pelo usuário através de uma expressão regular
     * @param regex String - Expressão regular de checagem
     * @return boolean - True caso tenha atendido os requisitos da expressão regular
     */
    private void validate(String regex){
        if(regex != null && value != null && !regex.equalsIgnoreCase("")){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            this.validExpression = matcher.find();
        }else{this.validExpression = regex != null && value != null && regex.equalsIgnoreCase("");}
    }
    
    
    @Override
    public String getValue(){
        return value;
    }
    
    
    @Override
    public boolean isValid(){
        return this.validExpression;
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
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setParentComponent(Component parent) {
        this.parentComponent = parent;
    }
}
