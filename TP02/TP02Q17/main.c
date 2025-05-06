#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_LINE 2048
#define MAX_CAST 20
#define MAX_LISTED 10
#define MAX_SHOWS 500

// Estrutura para armazenar informações de um show
typedef struct
{
    char show_id[20];                // ID único do show
    char type[50];                   // Tipo de show (Ex: filme, série, etc.)
    char title[200];                 // Título do show
    char director[100];              // Diretor do show
    char cast[MAX_CAST][100];        // Elenco do show
    int cast_count;                  // Número de atores no elenco
    char country[100];               // País de origem
    char date_added[50];             // Data de adição à plataforma
    int release_year;                // Ano de lançamento
    char rating[20];                 // Classificação do show
    char duration[30];               // Duração do show
    char listed_in[MAX_LISTED][100]; // Categorias em que o show está listado
    int listed_count;                // Número de categorias
} Show;

/**
 * Remove a quebra de linha (\n ou \r) no final da string.
 * @param str: String a ser processada.
 */
void removeNewline(char *str)
{
    str[strcspn(str, "\r\n")] = '\0';
}

/**
 * Limpa os campos da string, removendo as aspas duplicadas e escapando caracteres.
 * @param field: String a ser limpa.
 */
void cleanField(char *field)
{
    int len = strlen(field);
    if (len > 1 && field[0] == '"' && field[len - 1] == '"')
    {
        field[len - 1] = '\0';
        memmove(field, field + 1, len - 1);
    }

    char *src = field, *dst = field;
    while (*src)
    {
        if (*src == '"' && *(src + 1) == '"')
        {
            *dst++ = '"';
            src += 2;
        }
        else
        {
            *dst++ = *src++;
        }
    }
    *dst = '\0';
}

/**
 * Divide uma linha CSV em campos separados por vírgula.
 * @param line: Linha do CSV.
 * @param fields: Vetor para armazenar os campos.
 * @param maxFields: Número máximo de campos que podem ser extraídos.
 * @return O número de campos extraídos.
 */
int splitCSVFields(char *line, char fields[][200], int maxFields)
{
    int i = 0, j = 0, k = 0;
    bool inQuotes = false;

    while (line[i] && k < maxFields)
    {
        j = 0;
        inQuotes = false;

        while (line[i])
        {
            if (line[i] == '"' && (i == 0 || line[i - 1] != '\\'))
            {
                inQuotes = !inQuotes;
            }
            else if (line[i] == ',' && !inQuotes)
            {
                i++;
                break;
            }
            else
            {
                fields[k][j++] = line[i];
            }
            i++;
        }

        fields[k][j] = '\0';
        cleanField(fields[k]);
        k++;
    }

    return k;
}

/**
 * Ordena uma lista de strings em ordem alfabética crescente.
 * @param list: Lista de strings.
 * @param n: Número de elementos na lista.
 */
void sortList(char list[][100], int n)
{
    char temp[100];
    for (int i = 0; i < n - 1; i++)
        for (int j = i + 1; j < n; j++)
            if (strcmp(list[i], list[j]) > 0)
            {
                strcpy(temp, list[i]);
                strcpy(list[i], list[j]);
                strcpy(list[j], temp);
            }
}

/**
 * Cria uma cópia de um show.
 * @param s: O show original.
 * @return Uma cópia do show.
 */
Show copyShow(Show *s)
{
    Show copy = *s;
    return copy;
}

/**
 * Exibe as informações de um show.
 * @param s: O show a ser exibido.
 */
void printShow(Show *s)
{
    printf("=> %s ## %s ## %s ## %s ## [", s->show_id, s->title, s->type, s->director);
    for (int i = 0; i < s->cast_count; i++)
    {
        printf("%s", s->cast[i]);
        if (i < s->cast_count - 1)
            printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, s->date_added, s->release_year, s->rating, s->duration);
    for (int i = 0; i < s->listed_count; i++)
    {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_count - 1)
            printf(", ");
    }
    printf("] ##\n");
}

/**
 * Busca um show no arquivo CSV pelo ID.
 * @param filename: Caminho do arquivo CSV.
 * @param id: ID do show a ser buscado.
 * @param show: Ponteiro para o show encontrado.
 * @return True se o show for encontrado, False caso contrário.
 */
bool searchById(const char *filename, const char *id, Show *show)
{
    FILE *fp = fopen(filename, "r");
    if (!fp)
    {
        perror("Error opening file");
        return false;
    }

    char line[MAX_LINE];
    fgets(line, MAX_LINE, fp); // Ignora o cabeçalho

    while (fgets(line, MAX_LINE, fp))
    {
        removeNewline(line);
        char fields[20][200];
        int total = splitCSVFields(line, fields, 20);
        if (strcmp(fields[0], id) != 0)
            continue;

        Show temp;
        strcpy(temp.show_id, fields[0]);
        strcpy(temp.type, fields[1]);
        strcpy(temp.title, strlen(fields[2]) > 0 ? fields[2] : "NaN");
        strcpy(temp.director, strlen(fields[3]) > 0 ? fields[3] : "NaN");

        temp.cast_count = 0;
        if (strlen(fields[4]) > 0)
        {
            char *actor = strtok(fields[4], ",");
            while (actor && temp.cast_count < MAX_CAST)
            {
                while (*actor == ' ')
                    actor++;
                strcpy(temp.cast[temp.cast_count++], actor);
                actor = strtok(NULL, ",");
            }
        }
        else
        {
            strcpy(temp.cast[0], "NaN");
            temp.cast_count = 1;
        }
        sortList(temp.cast, temp.cast_count);

        strcpy(temp.country, strlen(fields[5]) > 0 ? fields[5] : "NaN");
        strcpy(temp.date_added, strlen(fields[6]) > 0 ? fields[6] : "March 1, 1900");
        temp.release_year = strlen(fields[7]) > 0 ? atoi(fields[7]) : 0;
        strcpy(temp.rating, strlen(fields[8]) > 0 ? fields[8] : "NaN");
        strcpy(temp.duration, strlen(fields[9]) > 0 ? fields[9] : "NaN");

        temp.listed_count = 0;
        if (strlen(fields[10]) > 0)
        {
            char *category = strtok(fields[10], ",");
            while (category && temp.listed_count < MAX_LISTED)
            {
                while (*category == ' ')
                    category++;
                strcpy(temp.listed_in[temp.listed_count++], category);
                category = strtok(NULL, ",");
            }
        }
        else
        {
            strcpy(temp.listed_in[0], "NaN");
            temp.listed_count = 1;
        }
        sortList(temp.listed_in, temp.listed_count);

        *show = copyShow(&temp);
        fclose(fp);
        return true;
    }

    fclose(fp);
    return false;
}

/**
 * Função comparadora para ordenar shows.
 * A comparação é feita primeiro pelo diretor e, se necessário, pelo título.
 * @param a: Ponteiro para o primeiro show.
 * @param b: Ponteiro para o segundo show.
 * @return Um valor negativo, zero ou positivo dependendo da comparação.
 */
int comparacoes = 0;
int compararShows(Show *a, Show *b)
{
    comparacoes++;
    int dir_cmp = strcmp(a->director, b->director);
    if (dir_cmp == 0)
    {
        return strcmp(a->title, b->title);
    }
    return dir_cmp;
}

/**
 * Troca dois shows de lugar.
 * @param a: Ponteiro para o primeiro show.
 * @param b: Ponteiro para o segundo show.
 */
void swap(Show *a, Show *b)
{
    Show temp = *a;
    *a = *b;
    *b = temp;
}

/**
 * Função auxiliar para manter a propriedade do heap.
 * @param arr: Array de shows.
 * @param n: Número de elementos no array.
 * @param i: Índice do nó atual.
 */
void heapify(Show arr[], int n, int i)
{
    int largest = i;       // raiz
    int left = 2 * i + 1;  // filho esquerdo
    int right = 2 * i + 2; // filho direito

    if (left < n && compararShows(&arr[left], &arr[largest]) > 0)
        largest = left;

    if (right < n && compararShows(&arr[right], &arr[largest]) > 0)
        largest = right;

    if (largest != i)
    {
        swap(&arr[i], &arr[largest]);
        heapify(arr, n, largest);
    }
}

/**
 * Ordena um array de shows usando o algoritmo HeapSort.
 * @param arr: Array de shows.
 * @param n: Número de elementos no array.
 */
void heapSort(Show arr[], int n)
{
    for (int i = n / 2 - 1; i >= 0; i--)
        heapify(arr, n, i);

    for (int i = n - 1; i > 0; i--)
    {
        swap(&arr[0], &arr[i]);
        heapify(arr, i, 0);
    }
}

/**
 * Escreve o log de execução em um arquivo.
 * @param tempo: Tempo de execução do algoritmo.
 */
void escreverLog(double tempo)
{
    FILE *log = fopen("771703_heapsort.txt", "w");
    if (log)
    {
        fprintf(log, "771703\t%.6f\t%d\n", tempo, comparacoes);
        fclose(log);
    }
}

/**
 * Função principal que executa a leitura do arquivo, busca por shows e ordenação.
 */
void main()
{
    char input[200];
    Show shows[MAX_SHOWS];
    int total = 0;

    // Loop para ler os IDs de show e buscar no arquivo
    while (true)
    {
        if (!fgets(input, sizeof(input), stdin))
            break;
        removeNewline(input);
        if (strcmp(input, "FIM") == 0)
            break;

        if (searchById("/tmp/disneyplus.csv", input, &shows[total]))
        {
            total++;
        }
        else
        {
            printf("ID %s not found.\n", input);
        }
    }

    // Ordenação dos shows usando HeapSort
    clock_t inicio = clock();
    heapSort(shows, total);
    clock_t fim = clock();

    double tempo_execucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    escreverLog(tempo_execucao);

    // Exibição dos primeiros 10 shows (ou menos, se não houver tantos)
    int limite = total < 10 ? total : 10;
    for (int i = 0; i < limite; i++)
    {
        printShow(&shows[i]);
    }
}
