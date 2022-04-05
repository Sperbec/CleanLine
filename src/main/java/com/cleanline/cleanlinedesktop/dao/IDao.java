package com.cleanline.cleanlinedesktop.dao;

import java.util.List;

public interface IDao<T> {
    int addData(T data);
    int deleteData(T data);
    int updateData(T data);
    List<T> getAll();
}
