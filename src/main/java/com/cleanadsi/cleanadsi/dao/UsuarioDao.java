package com.cleanadsi.cleanadsi.dao;

import com.cleanadsi.cleanadsi.models.UsuarioEntity;
import com.cleanadsi.cleanadsi.utility.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UsuarioDao implements IUsuarioDao<UsuarioEntity>{
    @Override
    public int addData(UsuarioEntity data) {
        return 0;
    }

    @Override
    public int deleteData(UsuarioEntity data) {
        return 0;
    }

    @Override
    public int updateData(UsuarioEntity data) {
        return 0;
    }

    @Override
    public UsuarioEntity getData(Long data) {
        return null;
    }

    @Override
    public ObservableList<UsuarioEntity> getAll() {
        Session s = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery criteriaQuery =criteriaBuilder.createQuery(UsuarioEntity.class);
        criteriaQuery.from(UsuarioEntity.class);

        List<UsuarioEntity> usuariosList = s.createQuery(criteriaQuery).getResultList();
        s.close();

        return FXCollections.observableArrayList(usuariosList);
    }
}
