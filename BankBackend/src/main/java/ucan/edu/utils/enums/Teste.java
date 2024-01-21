/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.utils.enums;

import java.util.Random;

/**
 *
 * @author jussyleitecode
 */
public class Teste
{
  

    public static void main(String[] args) {
        // Criar um objeto da classe Random
        Random random = new Random();

        // Gerar um número aleatório com 4 dígitos
        int numeroAleatorio = random.nextInt(9000) + 1000;

        // Exibir o número gerado
        System.out.println("Número Aleatório: " + numeroAleatorio);
    }
    
}
