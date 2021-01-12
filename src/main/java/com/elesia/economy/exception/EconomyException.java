package com.elesia.economy.exception;

/**
 * Exception appelé lors d'une erreur venant de la gestion des comptes joueurs
 */
public class EconomyException extends Exception{

    public EconomyException(String message){
        super(message);
    }

}
