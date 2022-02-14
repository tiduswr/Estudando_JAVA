package uepb.agendamentoconsultas.ui.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidation {
    public static boolean TestString(StandardRegexString regex, String value){
        Pattern pattern = Pattern.compile(regex.toString());
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
    
   public enum StandardRegexString {
  
    /**Não realiza nenhuma verificação via regex
     */
    DEFAULT{
        @Override
        public String toString(){
          return "";
        }
    },

    /**Expressão regular que só aceita números inteiros
     */
    ONLY_NUMBERS {
        @Override
        public String toString() {
            return "^\\d+$";
        }
    },

    /**Expressão regular para números com ponto flutuante
     */
    DECIMAL_VALUE {
        @Override
        public String toString() {
            return "[+-]?([0-9]*[,])?[0-9]+";
        }
    },

    /**Expressão regular para sites
     */
    SITE {
        @Override
        public String toString() {
            return "((http|ftp|https)://)?([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
        }
    },

    /**Expressão regular para E-Mails
     */
    EMAIL {
        @Override
        public String toString(){
            return "^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?$";
        }
    },

    /**Expressão regular para Telefones
     * Formato do numero: (##)######### ou (##)#####-#### ou (##) ######### ou (##) #####-####
     */
    FONE{
      @Override
      public String toString(){
          return "^\\((?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\)( )?(?:[2-8]|9[1-9])[0-9]{3}(\\-)?[0-9]{4}$";
      }
    },

    /**Expressão regular para CPF
     * Formato do CPF: ########### ou ###.###.###-##
     */
    CPF {
        @Override
        public String toString(){
            return "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)|(^\\d{3}\\d{3}\\d{3}\\d{2}$)";
        }
    },

    /**Expressão regular para CNPJ
     * Formato do CNPJ: ##.###.###/####-## ou ##############
     */
    CNPJ {
        @Override
        public String toString(){
            return "(^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$)|(^\\d{2}\\d{3}\\d{3}\\d{4}\\d{2}$)";
        }
    },

    /**Expressão regular que representa um String não vazia
     */
    NOT_EMPTY{
        @Override
        public String toString(){
            return "(.|\\s)*\\S(.|\\s)*";
        }
    },
    
    /**Expressão regular que representa um String só com letras não vazia
     */
    NOT_EMPTY_ALPHABETIC{
        @Override
        public String toString(){
            return "^[A-Za-z ]+$";
        }
    }
  }
    
}
