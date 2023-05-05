package br.com.trunfoAPI.service;

import java.util.List;

public interface ImplementarService<T,S> {

    T create(S dto);
    List<T> listAll();
    T listOne(Long id);
    T update(S dto,Long id);
    String delete(Long id);
}
