package com.example.laboratorio9.Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoFacultad extends DaoBase{
    public String obtenerNombreFacultad(int idFacultad){
        String sql = "select nombre from facultad where idfacultad=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idFacultad);
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

    public int obtenerIdPorIdDecano(int idDecano){
        String sql = "select idfacultad from facultad_has_decano where iddecano=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idDecano);
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
