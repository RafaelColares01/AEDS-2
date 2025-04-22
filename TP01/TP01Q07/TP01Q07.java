import java.util.Scanner;

public class TP01Q07 {

  /**
   * Verifica se a string de entrada é "FIM".
   * @param s String a ser verificada.
   * @return true se for "FIM", false caso contrário.
   */
  public static boolean isFim(String s) {
    return s.equals("FIM");
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    String string = sc.nextLine();

    while (!isFim(string)) {
      StringBuilder sb = new StringBuilder(string);
      sb.reverse();
      System.out.println(sb);

      string = sc.nextLine();
    }

    sc.close();
  }
}
