#include <stdio.h>
#include <string.h>

/**
 * Função recursiva para inverter uma string.
 * @param str String a ser invertida.
 * @param index Índice atual.
 * @param n Tamanho da string.
 */
void inverterString(char str[], int index, int n)
{
    // Caso base: quando o índice ultrapassa o tamanho da string
    if (index >= n)
    {
        return;
    }

    // Chama recursivamente para o próximo caractere
    inverterString(str, index + 1, n);

    // Imprime o caractere na posição atual
    printf("%c", str[index]);
}

int main()
{
    char entrada[100];

    // Lê as strings até encontrar "FIM"
    while (1)
    {
        fgets(entrada, sizeof(entrada), stdin);

        // Remove o caractere de nova linha (caso exista)
        entrada[strcspn(entrada, "\n")] = '\0';

        // Se a entrada for "FIM", interrompe a leitura
        if (strcmp(entrada, "FIM") == 0)
        {
            break;
        }

        // Chama a função recursiva para inverter a string
        inverterString(entrada, 0, strlen(entrada));
        printf("\n");
    }

    return 0;
}
