package com.example.laboratorio9.Daos;

import com.example.laboratorio9.Beans.Semestre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoSemestre extends DaoBase{
    public String obtenerNombreSemestre(int idSemestre){
        String sql = "select nombre from semestre where idsemestre=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idSemestre);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getString(1);
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean semestreHabilitado(int idSemestre){
        String sql = "select habilitado from semestre where idsemestre=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idSemestre);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getBoolean(1);
                }else{
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Semestre obtenerSemestrePorId(int idSemestre){
        Semestre semestre = new Semestre();
        String sql = "select idsemestre, nombre from semestre where idsemestre=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idSemestre);
            try(ResultSet rs = pstmt.executeQuery()){
                semestre.setIdSemestre(rs.getInt(1));
                semestre.setNombre(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return semestre;
    }
}
