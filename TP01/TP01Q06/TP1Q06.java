import java.util.Scanner;

/**
 * Classe TP1Q06 - Verifica se as palavras digitadas são compostas apenas por
 * vogais, consoantes, números inteiros ou reais.
 */
public class TP1Q06 {

  /**
   * Verifica se a string é "FIM".
   *
   * @param s A string a ser analisada
   * @return true se for "FIM", false caso contrário
   */
  public static boolean isFim(String s) {
    return s.equals("FIM");
  }

  /**
   * Verifica se a string contém apenas consoantes.
   *
   * @param palavra A string a ser analisada
   * @return true se contiver apenas consoantes, false caso contrário
   */
  public static boolean Consoante(String palavra) {
    for (char c : palavra.toLowerCase().toCharArray()) {
      if ("aeiou".indexOf(c) != -1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verifica se a string contém apenas vogais.
   *
   * @param palavra A string a ser analisada
   * @return true se contiver apenas vogais, false caso contrário
   */
  public static boolean Vogal(String palavra) {
    for (char c : palavra.toLowerCase().toCharArray()) {
      if ("aeiou".indexOf(c) == -1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verifica se a string representa um número inteiro.
   *
   * @param palavra A string a ser analisada
   * @return true se for um número inteiro, false caso contrário
   */
  public static boolean Int(String palavra) {
    return palavra.matches("\\d+");
  }

  /**
   * Verifica se a string representa um número real.
   *
   * @param palavra A string a ser analisada
   * @return true se for um número real, false caso contrário
   */
  public static boolean Real(String palavra) {
    return palavra.matches("\\d+([.,]\\d+)?");
  }

  /**
   * Método principal que recebe entrada do usuário, processa e imprime os
   * resultados.
   *
   * @param args Argumentos da linha de comando (não utilizados)
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      String palavra = sc.nextLine();
      if (isFim(palavra)) break;

      System.out.print(Consoante(palavra) ? "SIM " : "NAO ");
      System.out.print(Vogal(palavra) ? "SIM " : "NAO ");
      System.out.print(Int(palavra) ? "SIM " : "NAO ");
      System.out.println(Real(palavra) ? "SIM" : "NAO");
    }

    sc.close();
  }
}
