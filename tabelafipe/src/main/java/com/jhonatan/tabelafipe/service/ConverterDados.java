package com.jhonatan.tabelafipe.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterDados implements IConverterDados{
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T Conversor(String Api, Class<T> classe) {
        try {
            return objectMapper.readValue(Api, classe);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
