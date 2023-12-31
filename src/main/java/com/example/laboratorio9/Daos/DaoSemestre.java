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
                if(rs.next()) {
                    semestre.setIdSemestre(rs.getInt(1));
                    semestre.setNombre(rs.getString(2));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return semestre;
    }

    public int obtenerIdSemestreHabilitadoPorAdministradorPorIdDocente(int idDocente){
        String sql = "select s.idsemestre from semestre s " +
                "inner join usuario a on a.idusuario=s.idadmistrador " +
                "inner join universidad u on u.idadministrador=a.idusuario " +
                "inner join facultad f on f.iduniversidad=u.iduniversidad " +
                "inner join curso c on c.idfacultad=f.idfacultad " +
                "inner join curso_has_docente cd on cd.idcurso=c.idcurso " +
                "where cd.iddocente=? and s.habilitado=true;\n";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idDocente);
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

    public boolean idExiste(int idSemestre){
        String sql = "select idsemestre from semestre where idsemestre=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idSemestre);
            try(ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
