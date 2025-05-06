import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Representa um show ou filme disponível em uma plataforma de streaming.
 * Essa classe é imutável em arrays e datas, utilizando cópias defensivas.
 *
 * <p>Implements Cloneable para permitir cópias seguras de objetos Shows.</p>
 *
 * @author Rafael Mortimer Colares
 */
public class Shows implements Cloneable {

  private String show_id;
  private String type;
  private String title;
  private String director;
  private String[] cast;
  private String country;
  private Date date_added;
  private int release_year;
  private String rating;
  private String duration;
  private String[] listed_in;

  /** Formatador de datas para exibição (não utilizado diretamente nos métodos atuais). */
  private static final SimpleDateFormat sdf = new SimpleDateFormat(
    "MMMM d, yyyy",
    Locale.ENGLISH
  );

  /**
   * Construtor padrão que inicializa todos os atributos com valores genéricos (ou nulos).
   */
  public Shows() {
    this(
      "NaN",
      "NaN",
      "NaN",
      "NaN",
      new String[] { "NaN" },
      "NaN",
      null,
      0,
      "NaN",
      "NaN",
      new String[] { "NaN" }
    );
  }

  /**
   * Construtor completo da classe Shows.
   *
   * @param show_id Identificador do show.
   * @param type Tipo (Filme, Série etc).
   * @param title Título da produção.
   * @param director Nome do diretor.
   * @param cast Lista dos atores principais.
   * @param country País de origem.
   * @param date_added Data de adição à plataforma.
   * @param release_year Ano de lançamento.
   * @param rating Classificação indicativa.
   * @param duration Duração (ex: "90 min", "1 Season").
   * @param listed_in Gêneros associados.
   */
  public Shows(
    String show_id,
    String type,
    String title,
    String director,
    String[] cast,
    String country,
    Date date_added,
    int release_year,
    String rating,
    String duration,
    String[] listed_in
  ) {
    this.show_id = show_id;
    this.type = type;
    this.title = title;
    this.director = director;
    this.cast = cast != null ? Arrays.copyOf(cast, cast.length) : null;
    this.country = country;
    this.date_added =
      date_added != null ? new Date(date_added.getTime()) : null;
    this.release_year = release_year;
    this.rating = rating;
    this.duration = duration;
    this.listed_in =
      listed_in != null ? Arrays.copyOf(listed_in, listed_in.length) : null;
  }

  /** @return Identificador único do show. */
  public String getShow_id() {
    return show_id;
  }

  /** @param show_id Define o identificador único do show. */
  public void setShow_id(String show_id) {
    this.show_id = show_id;
  }

  /** @return Tipo da produção (ex: "Movie", "TV Show"). */
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  /** @return Título da produção. */
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /** @return Nome do diretor. */
  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  /**
   * Retorna uma cópia defensiva da lista de atores.
   *
   * @return Vetor de nomes dos atores principais.
   */
  public String[] getCast() {
    return cast != null ? Arrays.copyOf(cast, cast.length) : null;
  }

  public void setCast(String[] cast) {
    this.cast = cast != null ? Arrays.copyOf(cast, cast.length) : null;
  }

  /** @return País de origem da produção. */
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Retorna uma cópia defensiva da data de adição.
   *
   * @return Data de adição à plataforma.
   */
  public Date getDate_added() {
    return date_added != null ? new Date(date_added.getTime()) : null;
  }

  public void setDate_added(Date date_added) {
    this.date_added =
      date_added != null ? new Date(date_added.getTime()) : null;
  }

  /** @return Ano de lançamento da produção. */
  public int getRelease_year() {
    return release_year;
  }

  public void setRelease_year(int release_year) {
    this.release_year = release_year;
  }

  /** @return Classificação indicativa. */
  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  /** @return Duração da produção. */
  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  /**
   * Retorna uma cópia defensiva da lista de gêneros.
   *
   * @return Vetor de categorias/gêneros.
   */
  public String[] getListed_in() {
    return listed_in != null
      ? Arrays.copyOf(listed_in, listed_in.length)
      : null;
  }

  public void setListed_in(String[] listed_in) {
    this.listed_in =
      listed_in != null ? Arrays.copyOf(listed_in, listed_in.length) : null;
  }

  /**
   * Cria uma cópia do objeto atual, garantindo que arrays também sejam clonados corretamente.
   *
   * @return Uma nova instância de Shows com os mesmos valores.
   */
  @Override
  public Shows clone() {
    try {
      Shows copia = (Shows) super.clone();
      copia.cast = cast != null ? cast.clone() : null;
      copia.listed_in = listed_in != null ? listed_in.clone() : null;
      copia.date_added =
        date_added != null ? new Date(date_added.getTime()) : null;
      return copia;
    } catch (CloneNotSupportedException e) {
      // Nunca deve acontecer, pois implementamos Cloneable
      throw new AssertionError("Clonagem falhou: " + e.getMessage(), e);
    }
  }

  /**
   * Imprime os dados do show formatados, incluindo tratamento de campos nulos.
   * Utiliza `SimpleDateFormat` para formatar a data de adição.
   */
  public void imprimir() {
    System.out.print(
      "=> " +
      show_id +
      " ## " +
      title +
      " ## " +
      type +
      " ## " +
      director +
      " ## "
    );
    Arrays.sort(cast);
    System.out.print(Arrays.toString(cast) + " ## " + country + " ## ");
    System.out.print(
      (date_added != null ? sdf.format(date_added) : "NaN") + " ## "
    );
    System.out.print(
      release_year + " ## " + rating + " ## " + duration + " ## "
    );
    System.out.println(Arrays.toString(listed_in) + " ##");
  }

  /**
   * Constrói um objeto {@code Shows} a partir de uma linha CSV.
   * A leitura trata campos vazios e converte arrays e datas com segurança.
   *
   * @param linha Linha do arquivo CSV.
   * @return Objeto {@code Shows} populado.
   */
  public static Shows ler(String linha) {
    String[] campos = dividirLinhaCSV(linha);
    Shows show = new Shows();

    show.setShow_id(valorOuNaN(campos, 0));
    show.setType(valorOuNaN(campos, 1));
    show.setTitle(valorOuNaN(campos, 2));
    show.setDirector(valorOuNaN(campos, 3));

    String castStr = valorOuNaN(campos, 4);
    show.setCast(
      castStr.equals("NaN") ? new String[] { "NaN" } : castStr.split(", ?")
    );

    show.setCountry(valorOuNaN(campos, 5));

    try {
      show.setDate_added(
        campos.length > 6 && !campos[6].isEmpty()
          ? new java.sql.Date(sdf.parse(campos[6]).getTime())
          : null
      );
    } catch (ParseException e) {
      show.setDate_added(null);
    }

    try {
      show.setRelease_year(Integer.parseInt(valorOuNaN(campos, 7)));
    } catch (NumberFormatException e) {
      show.setRelease_year(0);
    }

    show.setRating(valorOuNaN(campos, 8));
    show.setDuration(valorOuNaN(campos, 9));

    String listedInStr = valorOuNaN(campos, 10);
    show.setListed_in(
      listedInStr.equals("NaN")
        ? new String[] { "NaN" }
        : listedInStr.split(", ?")
    );

    return show;
  }

  /**
   * Retorna o valor no índice informado ou "NaN" caso o campo esteja fora do array ou vazio.
   *
   * @param campos Array de campos extraídos do CSV.
   * @param index Índice do campo a acessar.
   * @return Valor do campo ou "NaN".
   */
  private static String valorOuNaN(String[] campos, int index) {
    return (index < campos.length && !campos[index].isEmpty())
      ? campos[index]
      : "NaN";
  }

  /**
   * Divide uma linha CSV em campos, tratando corretamente aspas e vírgulas.
   *
   * @param linha A linha CSV a ser dividida.
   * @return Um array de strings com os campos separados.
   */
  private static String[] dividirLinhaCSV(String linha) {
    List<String> campos = new ArrayList<>();
    StringBuilder campoAtual = new StringBuilder();
    boolean dentroDeAspas = false;

    for (char caractere : linha.toCharArray()) {
      switch (caractere) {
        case '"':
          dentroDeAspas = !dentroDeAspas;
          break;
        case ',':
          if (dentroDeAspas) {
            campoAtual.append(caractere);
          } else {
            campos.add(campoAtual.toString().trim());
            campoAtual.setLength(0);
          }
          break;
        default:
          campoAtual.append(caractere);
      }
    }

    campos.add(campoAtual.toString().trim());

    return campos.toArray(new String[0]);
  }

  /**
   * Método principal que lê o arquivo CSV, processa entradas do usuário
   * e imprime os dados formatados dos shows correspondentes.
   *
   * @param args Argumentos da linha de comando (não utilizados).
   * @throws IOException Em caso de erro na leitura do arquivo.
   */
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    Map<String, String> mapaCsv = new HashMap<>();

    BufferedReader br = new BufferedReader(
      new FileReader("/tmp/disneyplus.csv")
    );
    String linha = br.readLine(); // Ignora cabeçalho

    while ((linha = br.readLine()) != null) {
      String id = linha.split(",", 2)[0];
      mapaCsv.put(id, linha);
    }
    br.close();

    List<Shows> lista = new ArrayList<>();
    String entrada;
    while (!(entrada = sc.nextLine()).equals("FIM")) {
      if (mapaCsv.containsKey(entrada)) {
        Shows show = Shows.ler(mapaCsv.get(entrada));
        lista.add(show);
      }
    }
    long inicio = System.nanoTime();
    int[] resultado = ordenarQuicksortParcial(lista, 10);
    long fim = System.nanoTime();

    double tempoSegundos = (fim - inicio) / 1_000_000_000.0;
    int comparacoes = resultado[0];
    int movimentacoes = resultado[1];

    //Gerando arquivo de log
    String matricula = "771703";
    PrintWriter log = new PrintWriter(
      new FileWriter(matricula + "_mergesort.txt")
    );

    log.printf(
      "%s\t%d\t%d\t%.6f",
      matricula,
      comparacoes,
      movimentacoes,
      tempoSegundos
    );
    log.close();

    for (int i = 0; i < Math.min(10, lista.size()); i++) {
      lista.get(i).imprimir();
    }

    sc.close();
  }

  /**
   * Ordena os 10 primeiro elementos da lista de Shows usando o algoritmo de Quicksort parcial.
   * Critério: type (primário), desempate por title.
   *
   * @param lista Lista de Shows a ser ordenada.
   * @return Vetor contendo [comparações, movimentações].
   */
  public static int[] quicksortParcial(
    List<Shows> lista,
    int esq,
    int dir,
    int k,
    int[] comparacoes,
    int[] movimentacoes
  ) {
    if (esq >= dir) return new int[] { comparacoes[0], movimentacoes[0] };

    int i = esq, j = dir;
    Shows pivo = lista.get((esq + dir) / 2);

    while (i <= j) {
      while (menor(lista.get(i), pivo, comparacoes)) i++;
      while (menor(pivo, lista.get(j), comparacoes)) j--;

      if (i <= j) {
        Collections.swap(lista, i, j);
        movimentacoes[0] += 3;
        i++;
        j--;
      }
    }

    if (esq < j) quicksortParcial(lista, esq, j, k, comparacoes, movimentacoes);
    if (i < k && i < dir) quicksortParcial(
      lista,
      i,
      dir,
      k,
      comparacoes,
      movimentacoes
    );

    return new int[] { comparacoes[0], movimentacoes[0] };
  }

  private static boolean menor(Shows a, Shows b, int[] comparacoes) {
    comparacoes[0]++;
    if (a.getDate_added() == null && b.getDate_added() != null) return true;
    if (a.getDate_added() != null && b.getDate_added() == null) return false;

    if (a.getDate_added() == null && b.getDate_added() == null) {
      return a.getTitle().compareTo(b.getTitle()) < 0;
    }

    int cmp = a.getDate_added().compareTo(b.getDate_added());
    if (cmp != 0) return cmp < 0;

    return a.getTitle().compareTo(b.getTitle()) < 0;
  }

  public static int[] ordenarQuicksortParcial(List<Shows> lista, int k)
    throws IOException {
    int[] comparacoes = { 0 };
    int[] movimentacoes = { 0 };

    quicksortParcial(lista, 0, lista.size() - 1, k, comparacoes, movimentacoes);
    return new int[] { comparacoes[0], movimentacoes[0] };
  }

  public static void swap(List<Shows> lista, int i, int j) {
    Shows temp = lista.get(i);
    lista.set(i, lista.get(j));
    lista.set(j, temp);
  }
}
