/**
 * Classe que realiza o ciframento de César com chave fixa 3.
 * Lê uma série de strings até encontrar "FIM" e imprime cada uma cifrada.
 */
import java.util.Scanner;

public class TP1Q03 {

  /**
   * Verifica se a string de entrada é "FIM".
   *
   * @param s String de entrada.
   * @return true se for "FIM", false caso contrário.
   */
  public static boolean isFim(String s) {
    return (
      s.length() == 3 &&
      s.charAt(0) == 'F' &&
      s.charAt(1) == 'I' &&
      s.charAt(2) == 'M'
    );
  }

  // Chave de deslocamento do ciframento de César
  private static final int CHAVE = 3;

  /**
   * Aplica o ciframento de César à string fornecida.
   * Cada caractere é deslocado pela chave definida.
   *
   * @param palavra A palavra a ser cifrada.
   * @return A palavra cifrada.
   */
  public static String ciframento(String palavra) {
    StringBuilder cifrada = new StringBuilder();

    for (int i = 0; i < palavra.length(); i++) {
      cifrada.append((char) (palavra.charAt(i) + CHAVE));
    }

    return cifrada.toString();
  }

  /**
   * Método principal: lê entradas até "FIM" e imprime as versões cifradas.
   *
   * @param args Argumentos da linha de comando (não utilizados).
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String palavra;

    do {
      palavra = sc.nextLine();
      if (!isFim(palavra)) {
        System.out.println(ciframento(palavra));
      }
    } while (!isFim(palavra));

    sc.close();
  }
}
