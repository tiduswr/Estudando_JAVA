package dialogui.interfaces;

import java.awt.Component;

/**Interface com funções padrões para a Caixa de Dialogo de Mensagens
 * @author tiduswr*/
public interface MessageFrame extends Runnable{
    /**Seta a mensagem a ser mostrada para o usuário no formulário
     * @param message String - Mensagem para setar no formulario
     */
    public void setMessage(String message);
    
    /**Seta o titulo para o forumulário
     * @param title String - Titulo do formulário
     */
    public void setTitle(String title);
    
    /**Seta um componente de vinculo para o formulário
     * @param parent Component - Component
     */
    public void setParentComponent(Component parent);
}
