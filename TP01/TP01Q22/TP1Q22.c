#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * Função recursiva para calcular a soma dos dígitos de um número.
 * @param num Número inteiro.
 * @return Soma dos dígitos do número.
 */
int somaDigitos(int num)
{
    // Caso base: quando o número for 0, retorna 0
    if (num == 0)
    {
        return 0;
    }
    // Chamada recursiva: soma o último dígito (num % 10) e continua com o restante
    return num % 10 + somaDigitos(num / 10);
}

int main()
{
    char entrada[50];

    // Lê as linhas até encontrar "FIM"
    while (1)
    {
        fgets(entrada, sizeof(entrada), stdin);

        // Remove o caractere de nova linha do final da string
        entrada[strcspn(entrada, "\n")] = 0;

        // Se a entrada for "FIM", interrompe a leitura
        if (strcmp(entrada, "FIM") == 0)
        {
            break;
        }

        // Converte a entrada de string para inteiro
        int numero = atoi(entrada);

        // Chama a função recursiva e imprime a soma dos dígitos
        printf("%d\n", somaDigitos(numero));
    }

    return 0;
}
