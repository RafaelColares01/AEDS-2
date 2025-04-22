import java.util.Scanner;

public class TP1Q03 {

  // Verifica se a string é "FIM"
  public static boolean isFim(String s) {
    return (
      s.length() == 3 &&
      s.charAt(0) == 'F' &&
      s.charAt(1) == 'I' &&
      s.charAt(2) == 'M'
    );
  }

  private static int chave = 3;

  // Método para realizar o ciframento de César
  public static String ciframento(String palavra) {
    StringBuilder cif = new StringBuilder();

    for (int i = 0; i < palavra.length(); i++) {
      cif.append((char) (palavra.charAt(i) + chave));
    }

    return cif.toString();
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String palavra;

    do {
      palavra = sc.nextLine();
      if (!isFim(palavra)) {
        String cifrada = ciframento(palavra);
        System.out.println(cifrada);
      }
    } while (!isFim(palavra));

    sc.close();
  }
}
