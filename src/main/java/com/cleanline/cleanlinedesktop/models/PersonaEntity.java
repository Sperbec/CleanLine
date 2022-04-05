package com.cleanline.cleanlinedesktop.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "personas", schema = "cleanadsi_adsi192")
public class PersonaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_persona")
    private int idPersona;
    @Basic
    @Column(name = "nombres")
    private String nombres;
    @Basic
    @Column(name = "apellidos")
    private String apellidos;
    @Basic
    @Column(name = "id_opcion_genero")
    private int idOpcionGenero;
    @Basic
    @Column(name = "id_opcion_tipo_documento")
    private int idOpcionTipoDocumento;
    @Basic
    @Column(name = "numero_documento")
    private String numeroDocumento;
    @Basic
    @Column(name = "natalicio")
    private Date natalicio;
    @Basic
    @Column(name = "habilitado")
    private byte habilitado;
    @Basic
    @Column(name = "deleted_at")
    private Timestamp deletedAt;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdOpcionGenero() {
        return idOpcionGenero;
    }

    public void setIdOpcionGenero(int idOpcionGenero) {
        this.idOpcionGenero = idOpcionGenero;
    }

    public int getIdOpcionTipoDocumento() {
        return idOpcionTipoDocumento;
    }

    public void setIdOpcionTipoDocumento(int idOpcionTipoDocumento) {
        this.idOpcionTipoDocumento = idOpcionTipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getNatalicio() {
        return natalicio;
    }

    public void setNatalicio(Date natalicio) {
        this.natalicio = natalicio;
    }

    public byte getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(byte habilitado) {
        this.habilitado = habilitado;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonaEntity that = (PersonaEntity) o;
        return idPersona == that.idPersona && idOpcionGenero == that.idOpcionGenero && idOpcionTipoDocumento == that.idOpcionTipoDocumento && habilitado == that.habilitado && Objects.equals(nombres, that.nombres) && Objects.equals(apellidos, that.apellidos) && Objects.equals(numeroDocumento, that.numeroDocumento) && Objects.equals(natalicio, that.natalicio) && Objects.equals(deletedAt, that.deletedAt) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersona, nombres, apellidos, idOpcionGenero, idOpcionTipoDocumento, numeroDocumento, natalicio, habilitado, deletedAt, createdAt, updatedAt);
    }
}
