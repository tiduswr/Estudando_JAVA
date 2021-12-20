package dialogui.interfaces;

/**Interface para implementação de formulários na Classe DialogUI
 * ATENÇÃO! Deve haver um construtor vazio na classe que implementa essa interface
 * @author tiduswr
 */
interface StandardDialogFrame extends MessageFrame{
    /**Méodo para retornar o input do usuário
     * @return String - Retorna o texto inserido pelo usuário*/
    public String getValue();
    
    /**Verifica se a expressão digitada pelo usuário não é vazia
     * @return boolean - Retorna true caso o usuário tenha digitado pelo menos um caractere
     */
    public boolean isValid();
       
    /**Seta a mensagem de erro a ser mostrada para o usuário no formulário
     * @param message String - Mensagem de Erro para setar no formulario
     */
    public void setErrorMessage(String message);
    
}
