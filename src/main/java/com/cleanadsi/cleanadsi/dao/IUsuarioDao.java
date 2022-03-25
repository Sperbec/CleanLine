package com.cleanadsi.cleanadsi.dao;

import java.util.List;

public interface IUsuarioDao<T> {
    int addData(T data);
    int deleteData(T data);
    int updateData(T data);
    List<T> getAll();
}
