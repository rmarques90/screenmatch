package br.com.demo.screenmatch.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSerializer implements IDataSerializer {

    private ObjectMapper objectMapper;

    public DataSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public <T> T deserialize(String data, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(data, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
