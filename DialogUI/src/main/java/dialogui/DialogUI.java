package dialogui;

import dialogui.standardclasses.AskOptions;
import dialogui.standardclasses.ShowMessage;
import dialogui.standardclasses.AskText;
import dialogui.interfaces.InputDialogFrame;
import dialogui.interfaces.OptionDialogFrame;
import dialogui.interfaces.MessageFrame;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**Classe de controle para formulários de Input, é possivel atribuir um novo formulário 
 * contanto que ela implemente a interface correta para cada método
 * @author tiduswr
 */
public class DialogUI{
    private final String title;
    private Class askTextClassName;
    private Class askOptionsClassName;
    private Class showMessageForm;
    private final Component parentComp;
    
    /**Construtor para a classe DialogUI
     * @param t String - Titulo para os formulários
     * @param p Component - Componente em que os formulários vão estar associados
     */
    public DialogUI(String t, Component p){
        this.title = t;
        this.parentComp = p;
        this.askTextClassName = AskText.class;
        this.askOptionsClassName = AskOptions.class;
        this.showMessageForm = ShowMessage.class;
    }
    
    /**Atribui um novo formulário par ser usado nessa classe, a Classe passada no parametro 
     * precisa implementar a interface InputDialogFrame
     * @param form Class - Classe que implementa a interface InputDialogFrame
     * @return boolean - Retorna true caso a classe implemente a interface InputDialogFrame
     */
    public boolean setAskTextForm(Class form){
        if(form.isAssignableFrom(InputDialogFrame.class)){
            this.askTextClassName = form;
            return true;
        }else{return false;}
    }
    
    /**Atribui um novo formulário par ser usado nessa classe, a Classe passada no parametro 
     * precisa implementar a interface OptionDialogFrame
     * @param form Class - Classe que implementa a interface OptionDialogFrame
     * @return boolean - Retorna true caso a classe implemente a interface OptionDialogFrame
     */
    public boolean setAskOptionsForm(Class form){
        if(form.isAssignableFrom(OptionDialogFrame.class)){
            this.askOptionsClassName = form;
            return true;
        }else{return false;}
    }
    
    /**Atribui um novo formulário par ser usado nessa classe, a Classe passada no parametro 
     * precisa implementar a interface ShowMessage
     * @param form Class - Classe que implementa a interface ShowMessage
     * @return boolean - Retorna true caso a classe implemente a interface ShowMessage
     */
    public boolean setShowMessageForm(Class form){
        if(form.isAssignableFrom(ShowMessage.class)){
            this.showMessageForm = form;
            return true;
        }else{return false;}
    }
    
    /**Mostra uma mensagem na tela através do formulário atribuido nessa interface, por padrão 
     * é o JOptionPane
     * @param message String - Mensagem no formato String para ser mostrada na tela
     */
    public void showMsg(String message){
        MessageFrame run;
        try {                
            run = (MessageFrame) this.showMessageForm.getConstructor().newInstance();
            run.setMessage(message);
            run.setTitle(this.title);
            run.setParentComponent(this.parentComp);
            
            SwingUtilities.invokeAndWait(run);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | 
                IllegalAccessException | InterruptedException | InvocationTargetException ex) {
            System.out.println("Erro na classe " + ex.getClass() + " | Detalhes: " + ex.getLocalizedMessage());
        }
    }
    
    /**Solicita um input do usuário através do formulário atribuido nessa interface, por padrão
     * é através do JOptionPane
     * @param normalQuestion String - Mensagem para mostrar no formulário
     * @param errorInformation String - Mensagem caso algum erro ocorra
     * @param regex String - Validação Regex para o Input
     * @return DialogValue{@literal <}String{@literal >} - Retorna o valor de Input e validação através da 
     * classe DialogValue
     */
    public DialogValue<String> askText(String normalQuestion, String errorInformation, String regex){
        InputDialogFrame run;
        
        try {
            run = (InputDialogFrame) this.askTextClassName.getConstructor().newInstance();
            run.setErrorMessage(errorInformation);
            run.setMessage(normalQuestion);
            run.setParentComponent(this.parentComp);
            run.setRegex(regex);
            run.setTitle(this.title);
            
            SwingUtilities.invokeAndWait(run);
            return new DialogValue(run.getValue(), run.isValid());
            
        } catch (NoSuchMethodException | SecurityException | InstantiationException | 
                IllegalAccessException | InterruptedException | InvocationTargetException ex) {
            System.out.println("Erro na classe " + ex.getClass() + " | Detalhes: " + ex.getLocalizedMessage());
        }
        return null;
    }
    
    /**Solicita um input do usuário através do formulário atribuido nessa interface, por padrão
     * é através do JOptionPane
     * @param normalQuestion String - Mensagem para mostrar no formulário
     * @param errorInformation String - Mensagem caso algum erro ocorra
     * @param regex StandardRegexString - Validação Regex para o Input
     * @return DialogValue{@literal <}String{@literal >} - Retorna o valor de Input e validação através da 
     * classe DialogValue
     */
    public DialogValue<String> askText(String normalQuestion, String errorInformation, StandardRegexString regex){
        return this.askText(normalQuestion, errorInformation, regex.toString());
    }
    
    /**Solicita um input de opções ao usuário através do formulário atribuido nessa interface, por padrão
     * é através do JOptionPane
     * @param normalQuestion String - Mensagem para mostrar no formulário
     * @param errorInformation String - Mensagem caso algum erro ocorra
     * @param options String[] - Opções para o usuário selecionar através do formulário de input
     * @return DialogValue{@literal <}String{@literal >} - Retorna o valor de Input e validação através da 
     * classe DialogValue*/
    public DialogValue<String> askOptions(String normalQuestion, String errorInformation, String[] options){
        
        OptionDialogFrame run;
        
        try {
            run = (OptionDialogFrame) this.askOptionsClassName.getConstructor().newInstance();
            run.setErrorMessage(errorInformation);
            run.setMessage(normalQuestion);
            run.setParentComponent(this.parentComp);
            run.setOptions(options);
            run.setTitle(this.title);
            
            SwingUtilities.invokeAndWait(run);
            return new DialogValue(run.getValue(), run.isValid());
            
        } catch (NoSuchMethodException | SecurityException | InstantiationException | 
                IllegalAccessException | InterruptedException | InvocationTargetException ex) {
            System.out.println("Erro na classe " + ex.getClass() + " | Detalhes: " + ex.getLocalizedMessage());
        }
        return null;
    }
}
