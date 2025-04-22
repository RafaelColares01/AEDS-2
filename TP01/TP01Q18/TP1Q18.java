import java.util.Scanner;

public class TP1Q18 {

  private static int chave = 3;

  // Verifica se a string é "FIM"
  public static boolean isFim(String s) {
    return (
      s.length() == 3 &&
      s.charAt(0) == 'F' &&
      s.charAt(1) == 'I' &&
      s.charAt(2) == 'M'
    );
  }

  // Método para realizar o ciframento recursivo
  public static String ciframento(String palavra, int i) {
    if (i < palavra.length()) {
      return (char) (palavra.charAt(i) + chave) + ciframento(palavra, i + 1);
    }
    return "";
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String palavra;

    do {
      palavra = sc.nextLine();
      if (!isFim(palavra)) {
        String cifrada = ciframento(palavra, 0);
        System.out.println(cifrada);
      }
    } while (!isFim(palavra));

    sc.close();
  }
}
