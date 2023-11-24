package com.example.laboratorio9.Daos;

import com.example.laboratorio9.Beans.Curso;
import com.example.laboratorio9.Beans.Facultad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoCurso extends DaoBase{
    public String obtenerNombreCurso(int idCurso){
        String sql = "select nombre from curso where idcurso=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idCurso);
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

    public ArrayList<Curso> listarCurso(int idDecano){
        ArrayList<Curso> listaCursos = new ArrayList<>();
        String sql = "select c.idcurso, c.nombre, c.idfacultad, c.fecha_registro, c.fecha_edicion, c.codigo from curso c " +
                "inner join facultad f on c.idfacultad = f.idfacultad " +
                "inner join facultad_has_decano fd on f.idfacultad = fd.idfacultad " +
                "where fd.iddecano=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idDecano);
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    Curso curso = new Curso();
                    llenarDatosCurso(curso, rs);
                    listaCursos.add(curso);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaCursos;
    }

    private void llenarDatosCurso(Curso curso, ResultSet rs) throws SQLException {
        curso.setIdCurso(rs.getInt(1));
        curso.setNombre(rs.getString(2));
        curso.setCodigo(rs.getString(6));

        Facultad facultad = new Facultad();
        facultad.setIdFacultad(rs.getInt(3));
        facultad.setNombre(new DaoFacultad().obtenerNombreFacultad(rs.getInt(3)));

        curso.setFacultad(facultad);
        curso.setFechaRegistro(rs.getString(4));
        curso.setFechaEdicion(rs.getString(5));
    }

    public String obtenerNombreCursoPorDocente(int idDocente){
        String sql = "select c.nombre from curso c " +
                "inner join curso_has_docente cd on c.idcurso = cd.idcurso " +
                "where cd.iddocente=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idDocente);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getString(1);
                }else{
                    return "Ninguno";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cursoConEvaluaciones(int idCurso){
        String sql = "select idevaluacion from evaluacion where idcurso=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idCurso);
            try(ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarCurso(int idCurso, String nombreCurso){
        String sql = "update curso set nombre=?, fecha_edicion=now() where idcurso=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombreCurso);
            pstmt.setInt(2,idCurso);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarCurso(int idCurso){
        String sql = "delete from curso_has_docente where idcurso=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idCurso);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql2 = "delete from curso where idcurso=?";
        try(Connection conn2 = getConnection();
            PreparedStatement pstmt2 = conn2.prepareStatement(sql2)){
            pstmt2.setInt(1,idCurso);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
