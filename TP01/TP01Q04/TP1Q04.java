import java.util.Random;
import java.util.Scanner;

public class TP1Q04 {

  // Verifica se a string é "FIM"
  public static boolean isFim(String s) {
    return (
      s.length() == 3 &&
      s.charAt(0) == 'F' &&
      s.charAt(1) == 'I' &&
      s.charAt(2) == 'M'
    );
  }

  // Método que realiza a troca de letras aleatórias
  public static String trocarLetra(String palavra, Random gerador) {
    char[] stringChar = palavra.toCharArray();
    StringBuilder saida = new StringBuilder();

    char letra1 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);
    char letra2 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);

    for (int x = 0; x < palavra.length(); x++) {
      if (stringChar[x] == letra1) {
        saida.append(letra2);
      } else {
        saida.append(stringChar[x]);
      }
    }

    return saida.toString();
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Random gerador = new Random();
    gerador.setSeed(4);

    String[] palavra = new String[1000];
    int x = 0;

    // Leitura dos dados
    do {
      palavra[x] = sc.nextLine();
    } while (!isFim(palavra[x++]));
    x--; // Ignora o "FIM"

    // Realiza a troca de letras
    for (int y = 0; y < x; y++) {
      System.out.println(trocarLetra(palavra[y], gerador));
    }

    sc.close();
  }
}
