import java.util.Scanner;

/**
 * Classe que verifica se uma senha é válida de acordo com regras predefinidas.
 * A senha deve conter pelo menos 8 caracteres, uma letra maiúscula, uma letra
 * minúscula,
 * um dígito e um caractere especial.
 */
public class TP1Q12 {

  /**
   * Verifica se a senha fornecida é válida.
   * Uma senha é considerada válida se:
   * - Contém pelo menos 8 caracteres.
   * - Contém pelo menos uma letra maiúscula.
   * - Contém pelo menos uma letra minúscula.
   * - Contém pelo menos um dígito numérico.
   * - Contém pelo menos um caractere especial (não é letra ou número).
   *
   * @param senha A senha fornecida para validação.
   * @return true se a senha for válida, false caso contrário.
   */
  public static boolean isValidPassword(String senha) {
    // A senha deve ter pelo menos 8 caracteres
    if (senha.length() < 8) {
      return false;
    }

    // Flags para verificar as condições da senha
    boolean hasUpper = false; // Verifica se tem letra maiúscula
    boolean hasLower = false; // Verifica se tem letra minúscula
    boolean hasDigit = false; // Verifica se tem dígito
    boolean hasSpecial = false; // Verifica se tem caractere especial

    // Itera sobre cada caractere da senha
    for (int i = 0; i < senha.length(); i++) {
      char c = senha.charAt(i);

      // Verifica se é uma letra maiúscula
      if (Character.isUpperCase(c)) {
        hasUpper = true;
      }
      // Verifica se é uma letra minúscula
      if (Character.isLowerCase(c)) {
        hasLower = true;
      }
      // Verifica se é um dígito
      if (Character.isDigit(c)) {
        hasDigit = true;
      }
      // Verifica se é um caractere especial
      if (!Character.isLetterOrDigit(c)) {
        hasSpecial = true;
      }
    }

    // A senha é válida se atende todas as condições
    return hasUpper && hasLower && hasDigit && hasSpecial;
  }

  /**
   * Método principal que lê as senhas fornecidas pelo usuário e verifica se são
   * válidas.
   * O programa continuará até que o usuário insira "FIM".
   *
   * @param args Argumentos da linha de comando (não utilizados neste caso).
   */
  public static void main(String[] args) {
    // Scanner para ler a entrada do usuário
    Scanner scanner = new Scanner(System.in);

    // Lê as linhas até encontrar a palavra "FIM"
    while (true) {
      String senha = scanner.nextLine();

      // Se a entrada for "FIM", interrompe a leitura
      if (senha.equals("FIM")) {
        break;
      }

      // Verifica se a senha é válida e imprime "SIM" ou "NÃO"
      if (isValidPassword(senha)) {
        System.out.println("SIM");
      } else {
        System.out.println("NAO");
      }
    }

    // Fecha o scanner para liberar recursos
    scanner.close();
  }
}
