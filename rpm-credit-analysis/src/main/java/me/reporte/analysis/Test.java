package me.reporte.analysis;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String valor = "jonatasSantos";

        // Converter para camel case usando Pattern e Matcher
        String resultado = toCamelCaseWithMatcher(valor);

        System.out.println(resultado);  // Saída: jonatasSantos
    }

    public static String toCamelCaseWithMatcher(String input) {
        // Verifica se a entrada já está em camel case e ajusta a primeira letra, se necessário
        if (isCamelCase(input)) {
            // Retorna o input com a primeira letra em minúsculas
            return Character.toLowerCase(input.charAt(0)) + input.substring(1);
        }

        // Divide a string pelo caractere underscore
        return Arrays.stream(input.split("_"))
                .map(String::toLowerCase) // Transforma todas as palavras em minúsculas
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1)) // Capitaliza a primeira letra de cada palavra
                .reduce((first, second) -> first + second) // Combina as palavras em uma string
                .map(result -> Character.toLowerCase(result.charAt(0)) + result.substring(1)) // Corrige a primeira letra para minúscula
                .orElse(input); // Retorna a string original se algo falhar
    }

    // Método auxiliar para verificar se a string está em camel case
    private static boolean isCamelCase(String str) {
        // Verifica se a primeira letra é minúscula e existe pelo menos uma letra maiúscula
        return Character.isLowerCase(str.charAt(0)) && str.chars().anyMatch(Character::isUpperCase);
    }
}