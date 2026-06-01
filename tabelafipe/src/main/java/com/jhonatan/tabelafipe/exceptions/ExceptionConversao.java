package com.jhonatan.tabelafipe.exceptions;

public class ExceptionConversao extends RuntimeException{
    public String ErroNaConversao() {
        return "Erro, não foi possivel converter o Json";
    }
}
