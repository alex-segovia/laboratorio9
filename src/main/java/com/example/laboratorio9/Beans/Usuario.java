package com.example.laboratorio9.Beans;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String password;
    private Rol rol;
    private String ultimoIngreso;
    private int cantidadIngresos;
    private String fechaRegistro;
    private String fechaEdicion;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getUltimoIngreso() {
        return ultimoIngreso;
    }

    public void setUltimoIngreso(String ultimoIngreso) {
        this.ultimoIngreso = ultimoIngreso;
    }

    public int getCantidadIngresos() {
        return cantidadIngresos;
    }

    public void setCantidadIngresos(int cantidadIngresos) {
        this.cantidadIngresos = cantidadIngresos;
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
