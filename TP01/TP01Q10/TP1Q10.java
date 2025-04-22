import java.util.Scanner;

/**
 * Classe que conta o número de palavras em uma string de forma iterativa.
 * O programa considera que as palavras são sequências de caracteres separados
 * por espaços.
 */
public class TP1Q10 {

  /**
   * Conta quantas palavras existem na string.
   * Uma palavra é definida como uma sequência de caracteres não-espaciais.
   *
   * @param str String de entrada, que pode conter várias palavras separadas por
   *            espaços.
   * @return Quantidade de palavras na string. Retorna 0 se a string estiver vazia
   *         ou contiver apenas espaços.
   */
  public static int contarPalavras(String str) {
    // Se a string estiver vazia ou contiver apenas espaços, retorna 0
    if (str.trim().isEmpty()) {
      return 0;
    }

    int contador = 0; // Contador de palavras
    boolean dentroDaPalavra = false; // Flag para verificar se estamos dentro de uma palavra

    // Percorre cada caractere da string
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);

      // Se o caractere for um espaço, indica o fim de uma palavra
      if (Character.isWhitespace(c)) {
        dentroDaPalavra = false;
      } else if (!dentroDaPalavra) {
        // Se não está dentro de uma palavra, uma nova palavra começa
        contador++;
        dentroDaPalavra = true;
      }
    }

    return contador;
  }

  /**
   * Método principal que lê a entrada do usuário e imprime o número de palavras
   * em cada linha.
   * O programa continua executando até que o usuário insira a palavra "FIM".
   *
   * @param args Argumentos da linha de comando (não utilizados neste caso).
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Lê a entrada até encontrar "FIM"
    while (true) {
      String entrada = scanner.nextLine();

      // Verifica se a entrada é "FIM" para encerrar o programa
      if (entrada.equals("FIM")) {
        break;
      }

      // Chama o método contarPalavras e imprime o resultado
      System.out.println(contarPalavras(entrada));
    }
    // Fecha o scanner para liberar recursos
    scanner.close();
  }
}
