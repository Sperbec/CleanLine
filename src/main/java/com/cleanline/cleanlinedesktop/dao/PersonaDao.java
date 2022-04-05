package com.cleanline.cleanlinedesktop.dao;

import com.cleanline.cleanlinedesktop.models.PersonaEntity;
import com.cleanline.cleanlinedesktop.models.UsuarioEntity;
import com.cleanline.cleanlinedesktop.utility.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class PersonaDao implements IDao{
    @Override
    public int addData(Object data) {
        return 0;
    }

    @Override
    public int deleteData(Object data) {
        return 0;
    }

    @Override
    public int updateData(Object data) {
        return 0;
    }

    @Override
    public ObservableList<PersonaEntity> getAll() {
        Session s = HibernateUtil.getSession();
        CriteriaBuilder criteriaBuilder = s.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(PersonaEntity.class);
        criteriaQuery.from(PersonaEntity.class);

        List<PersonaEntity> personasList = s.createQuery(criteriaQuery).getResultList();

        s.close();

        return FXCollections.observableArrayList(personasList);
    }
}
