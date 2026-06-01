package com.jhonatan.tabelafipe.exceptions;

public class ExceptionApi extends RuntimeException{
    public String ErroNaBusca() {
        return "Erro, não foi possivel buscar a API, verifique a url.";
    }
}
