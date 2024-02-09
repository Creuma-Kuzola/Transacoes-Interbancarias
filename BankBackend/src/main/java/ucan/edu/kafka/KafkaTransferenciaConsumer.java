/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ucan.edu.kafka;

import ucan.edu.services.implementacao.TransferenciaServiceImpl;

/**
 *
 * @author jussyleitecode
 */
public class KafkaTransferenciaConsumer
{
    
    //s
    private final TransferenciaServiceImpl transferenciaServiceImpl;

    public KafkaTransferenciaConsumer(TransferenciaServiceImpl transferenciaServiceImpl)
    {
        this.transferenciaServiceImpl = transferenciaServiceImpl;
    }
    
    
    //@KafkaListen
    public void readTransferenciaFrom()
    {
        /*
        1- Ler o topic
        2- Verficar se a chave do topic recebida == ao numero do banco
        3- se topic.key == banknumber  
                - Persistir as informacoes do object transferancia serialized na banco de dados do banco destino
           se nao
                - não persistir
        */
        
      
    }
}
