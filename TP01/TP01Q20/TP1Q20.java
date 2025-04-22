import java.util.Scanner;

/**
 * Classe que verifica se uma string é composta apenas por consoantes, vogais,
 * inteiros ou números reais.
 */
public class TP1Q20 {

  /**
   * Verifica se a string de entrada é "FIM".
   *
   * @param s String a ser verificada.
   * @return true se for "FIM", false caso contrário.
   */
  public static boolean isFim(String s) {
    return s.equals("FIM");
  }

  /**
   * Verifica se a palavra contém apenas consoantes.
   */
  public static boolean Consoante(String palavra, int x) {
    if (x == palavra.length()) return true;
    char c = Character.toLowerCase(palavra.charAt(x));
    if ("aeiou".indexOf(c) != -1) return false;
    return Consoante(palavra, x + 1);
  }

  /**
   * Verifica se a palavra contém apenas vogais.
   */
  public static boolean Vogal(String palavra, int x) {
    if (x == palavra.length()) return true;
    char c = Character.toLowerCase(palavra.charAt(x));
    if ("aeiou".indexOf(c) == -1) return false;
    return Vogal(palavra, x + 1);
  }

  /**
   * Verifica se a palavra representa um número inteiro.
   */
  public static boolean Int(String palavra, int x) {
    if (x == palavra.length()) return true;
    if (!Character.isDigit(palavra.charAt(x))) return false;
    return Int(palavra, x + 1);
  }

  /**
   * Verifica se a palavra representa um número real.
   */
  public static boolean Real(String palavra, int x, int pontos, int virgulas) {
    if (x == palavra.length()) return pontos + virgulas <= 1;
    char c = palavra.charAt(x);
    if (c == '.') pontos++; else if (c == ',') virgulas++; else if (
      !Character.isDigit(c)
    ) return false;
    return Real(palavra, x + 1, pontos, virgulas);
  }

  /**
   * Processa a entrada e imprime os resultados.
   */
  public static void processarEntrada(String[] palavras, int x) {
    if (x < 0) return;

    System.out.print(Consoante(palavras[x], 0) ? "SIM " : "NAO ");
    System.out.print(Vogal(palavras[x], 0) ? "SIM " : "NAO ");
    System.out.print(Int(palavras[x], 0) ? "SIM " : "NAO ");
    System.out.println(Real(palavras[x], 0, 0, 0) ? "SIM" : "NAO");

    processarEntrada(palavras, x - 1);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String[] palavras = new String[1000];
    int x = 0;

    do {
      palavras[x] = scanner.nextLine();
    } while (!isFim(palavras[x++]));

    processarEntrada(palavras, x - 2);
    scanner.close();
  }
}
