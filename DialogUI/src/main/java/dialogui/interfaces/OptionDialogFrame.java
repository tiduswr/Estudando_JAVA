package dialogui.interfaces;

/**Interface para caixas de Dialogo da Função askOptions() da classe DialogUI
 * @author tiduswr
 */
public interface OptionDialogFrame extends StandardDialogFrame{
    /**Seta as opções que vão aparecer no formulário.
     * @param options String[] - Array com as opções do formulário
     */
    public void setOptions(String[] options);
}
