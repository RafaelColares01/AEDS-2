import java.util.Scanner;

/**
 * Classe TP1Q11 que contém um método iterativo para encontrar o comprimento da
 * substring mais longa sem caracteres repetidos em uma string.
 */
public class TP1Q11 {

  /**
   * Método para encontrar o comprimento da substring mais longa sem caracteres
   * repetidos.
   * Utiliza a técnica de janela deslizante com um array de marcação.
   *
   * @param str A string de entrada.
   * @return O comprimento da substring mais longa sem repetição.
   */
  public static int comprimentoSubstringSemRepeticao(String str) {
    int n = str.length(); // Obtém o tamanho da string
    int maxLength = 0; // Comprimento máximo da substring sem repetição
    int start = 0; // Posição inicial da janela deslizante

    // Array para armazenar o último índice de cada caractere (ASCII 256 caracteres)
    int[] lastIndex = new int[256];

    // Inicializa o array lastIndex com -1 (indicando que nenhum caractere foi
    // encontrado ainda)
    for (int i = 0; i < 256; i++) {
      lastIndex[i] = -1;
    }

    // Percorre a string utilizando a técnica da janela deslizante
    for (int end = 0; end < n; end++) {
      // Atualiza a posição inicial da janela caso o caractere já tenha aparecido
      start = Math.max(start, lastIndex[str.charAt(end)] + 1);

      // Atualiza o índice do caractere atual na string
      lastIndex[str.charAt(end)] = end;

      // Atualiza o comprimento máximo encontrado
      maxLength = Math.max(maxLength, end - start + 1);
    }

    return maxLength; // Retorna o comprimento da maior substring sem repetição
  }

  /**
   * Método principal que lê entradas do usuário e imprime o comprimento da
   * substring
   * mais longa sem caracteres repetidos para cada entrada.
   *
   * O programa para quando a entrada for "FIM".
   *
   * @param args Argumentos da linha de comando (não utilizados).
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in); // Scanner para leitura da entrada

    // Lê e processa as entradas até encontrar "FIM"
    while (true) {
      String entrada = scanner.nextLine();

      // Se a entrada for "FIM", interrompe a leitura
      if (entrada.equals("FIM")) {
        break;
      }

      // Chama o método e imprime o resultado
      System.out.println(comprimentoSubstringSemRepeticao(entrada));
    }

    scanner.close(); // Fecha o scanner para evitar vazamento de recursos
  }
}
