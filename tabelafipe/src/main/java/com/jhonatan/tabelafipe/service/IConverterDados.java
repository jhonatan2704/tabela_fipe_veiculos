package com.jhonatan.tabelafipe.service;

public interface IConverterDados {
    <T> T Conversor(String Api, Class<T> classe);
}
