package com.example.laboratorio9.Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoRol extends DaoBase{
    public String obtenerRol(int idRol){
        String sql = "select nombre from rol where idrol=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idRol);
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

    public int obtenerIdPorNombre(String nombre){
        String sql = "select idrol from rol where nombre=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombre);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }else{
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
