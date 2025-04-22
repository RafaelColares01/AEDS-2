/**
 * Classe que verifica se uma sequência de palavras é um palíndromo.
 */
import java.util.Scanner;

class TP1Q01 {

  /**
   * Método principal que controla a leitura de entrada e a verificação de palíndromos.
   *
   * @param args Argumentos de linha de comando (não utilizados).
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String[] entradas = new String[1000];
    int contador = 0;

    // Leitura das palavras até encontrar "FIM"
    do {
      entradas[contador] = scanner.nextLine();
    } while (!verificarFim(entradas[contador++]));
    contador--;

    // Verifica e imprime se cada palavra é um palíndromo
    for (int i = 0; i < contador; i++) {
      if (verificarPalindromo(entradas[i])) {
        System.out.println("SIM");
      } else {
        System.out.println("NAO");
      }
    }
    scanner.close();
  }

  /**
   * Verifica se a entrada indica o fim do programa.
   *
   * @param entrada A string de entrada a ser verificada.
   * @return true se a entrada for "FIM", false caso contrário.
   */
  public static boolean verificarFim(String entrada) {
    return (entrada.length() == 3 && entrada.equals("FIM"));
  }

  /**
   * Verifica se uma palavra é um palíndromo.
   *
   * @param palavra A string a ser analisada.
   * @return true se a palavra for um palíndromo, false caso contrário.
   */
  public static boolean verificarPalindromo(String palavra) {
    boolean resultado = true;
    int meio = palavra.length() / 2;

    for (int i = 0; i < meio; i++) {
      if (palavra.charAt(i) != palavra.charAt(palavra.length() - 1 - i)) {
        resultado = false;
        break;
      }
    }
    return resultado;
  }
}
