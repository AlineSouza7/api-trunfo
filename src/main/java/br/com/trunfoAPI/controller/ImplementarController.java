package br.com.trunfoAPI.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ImplementarController<T,S> {

    ResponseEntity<T> create(S dto);
    ResponseEntity<List<T>> listAll();
    ResponseEntity<T> listOne(Long id);
    ResponseEntity<T> update(S dto, Long id);
    ResponseEntity<String> delete(Long id);
}
