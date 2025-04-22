import java.util.Scanner;

public class TP01Q08 {

  /**
   * Método recursivo para calcular a soma dos dígitos de um número.
   * @param n Número inteiro.
   * @return Soma dos dígitos.
   */
  public static int somaDigitos(int n) {
    if (n == 0) return 0; // Caso base: se o número for 0, retorna 0.
    return (n % 10) + somaDigitos(n / 10); // Soma o último dígito e chama recursivamente para o restante.
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (sc.hasNextInt()) { // Lê enquanto houver números na entrada.
      int num = sc.nextInt();
      System.out.println(somaDigitos(Math.abs(num))); // Usa Math.abs para lidar com números negativos.
    }

    sc.close();
  }
}
