package com.cleanadsi.cleanadsi.dao;

import com.cleanadsi.cleanadsi.models.UsuarioEntity;

import java.util.List;

public interface IUsuarioDao<T> {
    int addData(T data);
    int deleteData(T data);
    int updateData(T data);
    UsuarioEntity getData(Long data);
    List<T> getAll();
}
