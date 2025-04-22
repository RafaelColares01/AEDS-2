#include <string.h>
#include <stdio.h>
#include <stdbool.h>

/**
 * Verifica se a string recebida é "FIM"
 *
 * @param palavra A string a ser analisada.
 * @return true se for "FIM", false caso contrário.
 */
bool isFim(char palavra[40])
{
    return (strlen(palavra) == 3 &&
            palavra[0] == 'F' &&
            palavra[1] == 'I' &&
            palavra[2] == 'M');
}

/**
 * Verifica se a string é um palíndromo.
 *
 * @param palavra A string a ser verificada.
 * @return true se for palíndromo, false caso contrário.
 */
bool ePalindromo(char palavra[])
{
    int esquerda = 0;
    int direita = strlen(palavra) - 1;

    while (esquerda < direita)
    {
        if (palavra[esquerda] != palavra[direita])
        {
            return false;
        }
        esquerda++;
        direita--;
    }
    return true;
}

int main()
{
    char palavra[1000][100];
    int total = 0;

    // Leitura das palavras
    do
    {
        scanf(" %[^\n]s", palavra[total]);
    } while (!isFim(palavra[total++]));
    total--;

    // Verifica se cada palavra é um palíndromo
    for (int i = 0; i < total; i++)
    {
        if (ePalindromo(palavra[i]))
        {
            printf("SIM\n");
        }
        else
        {
            printf("NAO\n");
        }
    }

    return 0;
}
