import java.text.Normalizer;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Classe que verifica se duas strings são anagramas uma da outra.
 */
public class TP1Q09 {

  /**
   * Verifica se duas strings são anagramas.
   *
   * @param str1 Primeira string.
   * @param str2 Segunda string.
   * @return true se forem anagramas, false caso contrário.
   */
  public static boolean saoAnagramas(String str1, String str2) {
    // Se os comprimentos forem diferentes, não são anagramas
    if (str1.length() != str2.length()) {
      return false;
    }

    // Converte as strings para arrays de caracteres, remove espaços, coloca em
    // minúsculo e normaliza para remover acentos
    char[] array1 = normalizarString(str1).toCharArray();
    char[] array2 = normalizarString(str2).toCharArray();

    // Ordena os arrays para comparar os caracteres em ordem
    Arrays.sort(array1);
    Arrays.sort(array2);

    // Compara os arrays ordenados, se forem iguais, são anagramas
    return Arrays.equals(array1, array2);
  }

  /**
   * Normaliza a string, removendo acentos, espaços e convertendo para minúsculo.
   *
   * @param str String a ser normalizada.
   * @return String normalizada.
   */
  private static String normalizarString(String str) {
    // Remove acentos e espaços, coloca em minúsculo
    str = str.toLowerCase().replaceAll("\\s", "");
    str = Normalizer.normalize(str, Normalizer.Form.NFD);
    str = str.replaceAll("[^\\p{ASCII}]", ""); // Remove caracteres não-ASCII (acentos)
    return str;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Lê as entradas até encontrar "FIM"
    while (scanner.hasNextLine()) {
      String entrada = scanner.nextLine().trim();

      // Verifica se a entrada é "FIM" para encerrar o programa
      if (entrada.equals("FIM")) {
        break;
      }

      // Divide a entrada em duas partes (strings separadas por " - ")
      String[] partes = entrada.split(" - ");
      if (partes.length == 2) {
        String str1 = partes[0].trim();
        String str2 = partes[1].trim();

        // Verifica se são anagramas e imprime "SIM" ou "NÃO"
        System.out.println(saoAnagramas(str1, str2) ? "SIM" : "N\u00C3O");
      }
    }
    scanner.close();
  }
}
