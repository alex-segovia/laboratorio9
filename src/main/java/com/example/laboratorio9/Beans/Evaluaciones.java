package com.example.laboratorio9.Beans;

public class Evaluaciones {
    private int idEvaluaciones;
    private String nombreEstudiantes;
    private String codigoEstudiantes;
    private String correoEstudiantes;
    private int nota;
    private Curso curso;
    private Semestre semestre;
    private String fechaRegistro;
    private String fechaEdicion;

    public int getIdEvaluaciones() {
        return idEvaluaciones;
    }

    public void setIdEvaluaciones(int idEvaluaciones) {
        this.idEvaluaciones = idEvaluaciones;
    }

    public String getNombreEstudiantes() {
        return nombreEstudiantes;
    }

    public void setNombreEstudiantes(String nombreEstudiantes) {
        this.nombreEstudiantes = nombreEstudiantes;
    }

    public String getCodigoEstudiantes() {
        return codigoEstudiantes;
    }

    public void setCodigoEstudiantes(String codigoEstudiantes) {
        this.codigoEstudiantes = codigoEstudiantes;
    }

    public String getCorreoEstudiantes() {
        return correoEstudiantes;
    }

    public void setCorreoEstudiantes(String correoEstudiantes) {
        this.correoEstudiantes = correoEstudiantes;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(String fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }
}
