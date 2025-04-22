import java.util.Scanner;

/**
 * Classe TP1Q10 - Verifica se as palavras digitadas são palíndromos.
 */
public class TP01Q16 {

  /**
   * Verifica se a string é "FIM".
   *
   * @param s A string a ser analisada
   * @return true se for "FIM", false caso contrário
   */
  static boolean eFim(String s) {
    return s.equals("FIM");
  }

  /**
   * Verifica recursivamente se uma palavra é um palíndromo.
   *
   * @param palavra A string a ser analisada
   * @param x       Índice inicial
   * @param y       Índice final
   * @return true se for um palíndromo, false caso contrário
   */
  static boolean ePalindromo(String palavra, int x, int y) {
    if (x >= y) {
      return true;
    }
    if (palavra.charAt(x) != palavra.charAt(y)) {
      return false;
    }
    return ePalindromo(palavra, x + 1, y - 1);
  }

  /**
   * Método principal que recebe entrada do usuário, processa e imprime se as
   * palavras são palíndromos.
   *
   * @param args Argumentos da linha de comando (não utilizados)
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      String palavra = sc.nextLine();
      if (eFim(palavra)) break;

      System.out.println(
        ePalindromo(palavra, 0, palavra.length() - 1) ? "SIM" : "NAO"
      );
    }

    sc.close();
  }
}
