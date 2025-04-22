import java.util.Scanner;

public class TP1Q05 {

  public static int primeiro(String palavra, int i) {
    for (int x = i; x < palavra.length(); x++) {
      if (palavra.charAt(x) >= '0' && palavra.charAt(x) <= '9') {
        return x;
      }
    }
    return -1;
  }

  public static int primeiro(String palavra) {
    return primeiro(palavra, 0);
  }

  public static int ultimo(String palavra) {
    int e = palavra.lastIndexOf("and");
    int ou = palavra.lastIndexOf("or");
    int nao = palavra.lastIndexOf("not");

    if (e > ou && e > nao) return e; else if (
      ou > e && ou > nao
    ) return ou; else if (nao > ou && nao > e) return nao;
    return -1;
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int quantidade = sc.nextInt();
      sc.nextLine(); // Limpa a quebra de linha após o número

      if (quantidade == 0) break;

      int[] letras = new int[quantidade];

      for (int x = 0; x < quantidade; x++) {
        letras[x] = sc.nextInt();
      }
      sc.nextLine(); // Limpa a quebra de linha após os inteiros

      String linha = sc.nextLine();

      if (linha.charAt(linha.length() - 1) == ' ') {
        linha = linha.substring(0, linha.length() - 1);
      }

      // Substitui as variáveis A, B, C por 0 ou 1
      for (int x = 0; x < quantidade; x++) {
        char letra = (char) ('A' + x);
        linha = linha.replace("not(" + letra + ")", letras[x] == 0 ? "1" : "0");
        linha =
          linha.replace(String.valueOf(letra), letras[x] == 0 ? "0" : "1");
      }

      while (linha.length() > 1) {
        int ultima = ultimo(linha);
        String expressao = linha.substring(
          ultima,
          linha.indexOf(")", ultima) + 1
        );

        if (expressao.startsWith("not")) {
          if (expressao.equals("not(0)")) linha =
            linha.replace("not(0)", "1"); else if (
            expressao.equals("not(1)")
          ) linha = linha.replace("not(1)", "0");
        } else {
          int c = 1;
          for (int x = 0; x < expressao.length(); x++) {
            if (expressao.charAt(x) == ',') c++;
          }

          int[] parametros = new int[c];
          int p = 0;
          for (int x = 0; x < c; x++) {
            p = primeiro(expressao, p);
            String valor = expressao.substring(p, p + 1);
            parametros[x] = Integer.parseInt(valor);
            p++;
          }

          if (expressao.startsWith("and")) {
            String resposta = "1";
            for (int x = 0; x < c; x++) {
              if (parametros[x] == 0) {
                resposta = "0";
                break;
              }
            }
            linha = linha.replace(expressao, resposta);
          } else if (expressao.startsWith("or")) {
            String resposta = "0";
            for (int x = 0; x < c; x++) {
              if (parametros[x] == 1) {
                resposta = "1";
                break;
              }
            }
            linha = linha.replace(expressao, resposta);
          }
        }
      }

      System.out.println(linha);
    }

    sc.close();
  }
}
