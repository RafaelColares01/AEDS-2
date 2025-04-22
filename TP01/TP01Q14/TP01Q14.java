import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * Classe TP1Q08 - Lê números do teclado, armazena em um arquivo e depois os lê
 * na ordem inversa.
 */
public class TP01Q14 {

  /**
   * Método principal que executa a lógica de leitura, escrita e leitura inversa
   * de números em um arquivo.
   *
   * @param args Argumentos da linha de comando (não utilizados)
   * @throws IOException Se ocorrer um erro de I/O ao manipular o arquivo
   */
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    RandomAccessFile raf = new RandomAccessFile("arquivo.txt", "rw");

    // Lê a quantidade de números que serão armazenados
    int x = sc.nextInt();

    // Grava os números no arquivo
    for (int y = 0; y < x; y++) {
      double z = sc.nextDouble();
      raf.writeDouble(z);
    }

    raf.close(); // Fecha o arquivo após a escrita

    raf = new RandomAccessFile("arquivo.txt", "r"); // Reabre o arquivo para leitura

    // Lê e imprime os números na ordem inversa
    for (int y = 0; y < x; y++) {
      raf.seek((x - y - 1) * 8); // Posiciona o ponteiro no número desejado
      double z = raf.readDouble(); // Lê o número
      int z2 = (int) z; // Converte para inteiro para verificar se é um número inteiro

      if (z == z2) System.out.println(z2); // Imprime sem casa decimal se for inteiro
      else System.out.println(z); // Imprime normalmente caso contrário
    }

    raf.close(); // Fecha o arquivo após a leitura
    sc.close(); // Fecha o Scanner
  }
}
