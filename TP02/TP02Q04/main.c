#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_LINE 2048
#define MAX_CAST 20
#define MAX_LISTED 10
#define MAX_SHOWS 500

/**
 * Estrutura que representa um show (filme ou série) com seus principais dados.
 */
typedef struct
{
    char show_id[20];
    char type[50];
    char title[200];
    char director[100];
    char cast[MAX_CAST][100];
    int cast_count;
    char country[100];
    char date_added[50];
    int release_year;
    char rating[20];
    char duration[30];
    char listed_in[MAX_LISTED][100];
    int listed_count;
} Show;

/**
 * Remove quebras de linha de uma string.
 */
void removeNewline(char *str)
{
    str[strcspn(str, "\r\n")] = '\0';
}

/**
 * Limpa um campo CSV:
 * - Remove aspas externas.
 * - Converte aspas duplas ("") para uma única aspas (").
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
 * Divide uma linha CSV em campos.
 * Retorna a quantidade de campos encontrados.
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
 * Ordena uma lista de strings em ordem alfabética.
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
 * Retorna uma cópia de uma struct Show.
 */
Show copyShow(Show *s)
{
    Show copy = *s;
    return copy;
}

/**
 * Imprime os dados de um Show no formato especificado.
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
 * Procura um show pelo ID no arquivo CSV e armazena os dados na struct Show.
 * Retorna true se encontrado.
 */
bool searchById(const char *filename, const char *id, Show *show)
{
    FILE *fp = fopen(filename, "r");
    if (!fp)
    {
        perror("Erro ao abrir o arquivo");
        return false;
    }

    char line[MAX_LINE];
    fgets(line, MAX_LINE, fp); // Ignora cabeçalho

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

int comparacoes = 0;

/**
 * Escreve o log com tempo de execução e comparações realizadas.
 */
void escreverLog(double tempo)
{
    FILE *log = fopen("771703_binaria.txt", "w");
    if (log)
    {
        fprintf(log, "771703\t%.6f\t%d\n", tempo, comparacoes);
        fclose(log);
    }
}

/**
 * Realiza busca binária em um array de strings.
 * Retorna 1 se encontrado, 0 caso contrário.
 */
int buscaBinaria(char titulos[][200], int n, char *alvo)
{
    int esq = 0, dir = n - 1;
    while (esq <= dir)
    {
        comparacoes++;
        int meio = (esq + dir) / 2;
        int cmp = strcmp(alvo, titulos[meio]);
        if (cmp == 0)
            return 1;
        else if (cmp < 0)
            dir = meio - 1;
        else
            esq = meio + 1;
    }
    return 0;
}

/**
 * Função de comparação para qsort.
 */
int compararTitulos(const void *a, const void *b)
{
    return strcmp((char *)a, (char *)b);
}

/**
 * Função principal: lê IDs, busca no CSV, armazena e verifica títulos com busca binária.
 */
int main()
{
    char input[200];
    Show shows[MAX_SHOWS];
    char titulos[MAX_SHOWS][200];
    int total = 0;

    // Leitura dos IDs
    while (true)
    {
        if (!fgets(input, sizeof(input), stdin))
            break;
        removeNewline(input);
        if (strcmp(input, "FIM") == 0)
            break;

        if (searchById("/tmp/disneyplus.csv", input, &shows[total]))
        {
            strcpy(titulos[total], shows[total].title);
            total++;
        }
    }

    // Ordenação dos títulos
    qsort(titulos, total, sizeof(titulos[0]), compararTitulos);

    // Leitura de buscas e contagem de tempo
    clock_t inicio = clock();
    while (true)
    {
        if (!fgets(input, sizeof(input), stdin))
            break;
        removeNewline(input);
        if (strcmp(input, "FIM") == 0)
            break;

        if (buscaBinaria(titulos, total, input))
            printf("SIM\n");
        else
            printf("NAO\n");
    }
    clock_t fim = clock();

    double tempo_execucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    escreverLog(tempo_execucao);

    return 0;
}
