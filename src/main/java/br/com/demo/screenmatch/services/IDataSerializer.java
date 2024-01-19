package br.com.demo.screenmatch.services;

public interface IDataSerializer {
    <T> T deserialize(String data, Class<T> clazz);
}
