package com.cleanadsi.cleanadsi.dao;

import com.cleanadsi.cleanadsi.models.ProductoEntity;
import com.cleanadsi.cleanadsi.utility.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ProductoDao implements IDao<ProductoEntity> {
    @Override
    public int addData(ProductoEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.save(data);
        t.commit();
        s.close();

        return 0;
    }

    @Override
    public int deleteData(ProductoEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(data);
        t.commit();
        s.close();

        return 0;
    }

    @Override
    public int updateData(ProductoEntity data) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.update(data);
        t.commit();
        s.close();

        return 0;
    }

    @Override
    public ObservableList<ProductoEntity> getAll() {
        Session s = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(ProductoEntity.class);
        criteriaQuery.from(ProductoEntity.class);

        List<ProductoEntity> productosList = s.createQuery(criteriaQuery).getResultList();
        s.close();

        return FXCollections.observableArrayList(productosList);
    }
}
