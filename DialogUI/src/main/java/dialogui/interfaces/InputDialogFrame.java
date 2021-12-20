package dialogui.interfaces;

/**Interface para caixas de Dialogo da Função askText() da classe DialogUI
 * @author tiduswr
 */
public interface InputDialogFrame extends StandardDialogFrame{
     /**Seta a String de Regex a ser verificada no formulário
     * @param regex String - Expressão Regular(Regex)
     */
    public void setRegex(String regex);
}
