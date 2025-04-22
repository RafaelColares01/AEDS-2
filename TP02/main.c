#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_STR 300
#define MAX_LIST 20
#define MAX_LINE 1000

typedef struct {
    char show_id[MAX_STR];
    char type[MAX_STR];
    char title[MAX_STR];
    char director[MAX_STR];
    char cast[MAX_LIST][MAX_STR];
    int cast_count;
    char country[MAX_STR];
    char date_added[MAX_STR];
    int release_year;
    char rating[MAX_STR];
    char duration[MAX_STR];
    char listed_in[MAX_LIST][MAX_STR];
    int listed_count;
} Show;

void inicializarShow(Show* s) {
    strcpy(s->show_id, "NaN");
    strcpy(s->type, "NaN");
    strcpy(s->title, "NaN");
    strcpy(s->director, "NaN");
    s->cast_count = 0;
    strcpy(s->country, "NaN");
    strcpy(s->date_added, "March 1, 1900");
    s->release_year = 0;
    strcpy(s->rating, "NaN");
    strcpy(s->duration, "NaN");
    s->listed_count = 0;
}

void ordenarLista(char lista[MAX_LIST][MAX_STR], int n) {
    char temp[MAX_STR];
    for (int i = 0; i < n - 1; i++)
        for (int j = i + 1; j < n; j++)
            if (strcmp(lista[i], lista[j]) > 0) {
                strcpy(temp, lista[i]);
                strcpy(lista[i], lista[j]);
                strcpy(lista[j], temp);
            }
}

void removerAspas(char* s) {
    if (s[0] == '"') {
        memmove(s, s + 1, strlen(s));
        if (s[strlen(s) - 1] == '"')
            s[strlen(s) - 1] = '\0';
    }
}

void splitLista(char* campo, char lista[MAX_LIST][MAX_STR], int* count) {
    *count = 0;
    char* token = strtok(campo, ",");
    while (token && *count < MAX_LIST) {
        while (isspace(*token)) token++;
        strncpy(lista[*count], token, MAX_STR);
        lista[*count][MAX_STR - 1] = '\0';
        (*count)++;
        token = strtok(NULL, ",");
    }
    ordenarLista(lista, *count);
}

void lerShow(Show* s, char* linha) {
    inicializarShow(s);

    char* campos[12];
    int i = 0;
    int entre_aspas = 0;
    char* ptr = strtok(linha, ",");
    char buffer[MAX_STR * 2] = "";

    while (ptr) {
        if (entre_aspas) {
            strcat(buffer, ",");
            strcat(buffer, ptr);
            if (strchr(ptr, '"')) {
                campos[i++] = strdup(buffer);
                entre_aspas = 0;
                buffer[0] = '\0';
            }
        } else {
            if (strchr(ptr, '"') && !strchr(ptr + 1, '"')) {
                strcpy(buffer, ptr);
                entre_aspas = 1;
            } else {
                campos[i++] = strdup(ptr);
            }
        }
        ptr = strtok(NULL, ",");
    }

    if (i < 12) return;

    strncpy(s->show_id, campos[0], MAX_STR);
    strncpy(s->type, campos[1], MAX_STR);
    strncpy(s->title, campos[2], MAX_STR);
    strncpy(s->director, strlen(campos[3]) ? campos[3] : "NaN", MAX_STR);

    removerAspas(campos[4]);
    splitLista(campos[4], s->cast, &s->cast_count);

    strncpy(s->country, strlen(campos[5]) ? campos[5] : "NaN", MAX_STR);

    if (strlen(campos[6])) strncpy(s->date_added, campos[6], MAX_STR);

    s->release_year = strlen(campos[7]) ? atoi(campos[7]) : 0;
    strncpy(s->rating, strlen(campos[8]) ? campos[8] : "NaN", MAX_STR);
    strncpy(s->duration, strlen(campos[9]) ? campos[9] : "NaN", MAX_STR);

    removerAspas(campos[10]);
    splitLista(campos[10], s->listed_in, &s->listed_count);

    for (int j = 0; j < i; j++) free(campos[j]);
}

void imprimirShow(const Show* s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->show_id, s->title, s->type, s->director);
    for (int i = 0; i < s->cast_count; i++) {
        printf("%s", s->cast[i]);
        if (i < s->cast_count - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, s->date_added,
           s->release_year, s->rating, s->duration);
    for (int i = 0; i < s->listed_count; i++) {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_count - 1) printf(", ");
    }
    printf("] ##\n");
}

int main() {
    char entrada[MAX_STR];
    char linha[MAX_LINE];
    FILE* arq = fopen("/tmp/disneyplus.csv", "r");
    if (!arq) {
        printf("Erro ao abrir /tmp/disneyplus.csv\n");
        return 1;
    }

    // Ignorar cabeçalho
    fgets(linha, sizeof(linha), arq);

    // Guardar todos os shows na memória
    Show shows[10000];
    int count = 0;
    while (fgets(linha, sizeof(linha), arq)) {
        lerShow(&shows[count++], linha);
    }
    fclose(arq);

    // Ler entrada padrão com IDs
    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\n")] = '\0'; // remover \n
        if (strcmp(entrada, "FIM") == 0) break;

        int found = 0;
        for (int i = 0; i < count; i++) {
            if (strcmp(shows[i].show_id, entrada) == 0) {
                imprimirShow(&shows[i]);
                found = 1;
                break;
            }
        }
        if (!found) {
            printf("=> %s ## NaN ## NaN ## NaN ## [] ## NaN ## March 1, 1900 ## 0 ## NaN ## NaN ## [] ##\n", entrada);
        }
    }

    return 0;
}
