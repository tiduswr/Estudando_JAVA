package dialogui;

/**Classe para padronizar o retorno de informações da classe DialogUI
 * @author tiduswr
 * @param <E> - Tipo de valor digitado pelo usuário
 */
public class DialogValue<E> {
    private final E value;
    private final boolean isValid;
    
    /**Construtor principal do Objeto DialogValue
     * @param value - Valor dinamico contendo a informação solicitada ao usuário
     * @param isValid boolean - Informa se o input do usuário é válido
     */
    public DialogValue(E value, boolean isValid) {
        this.value = value;
        this.isValid = isValid;
    }
    
    /**Retorna o valor digitado pelo usuário na classe DialogUI
     * @return Valor digitado pelo usuário
     */
    public E getValue() {
        return value;
    }
    
    /**Informa se o input do usuário é valido
     * @return boolean - Retorna True caso o usuário tenha digitado corretamente
     */
    public boolean isValid() {
        return isValid;
    }
}
