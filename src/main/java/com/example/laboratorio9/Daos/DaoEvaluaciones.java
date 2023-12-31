package com.example.laboratorio9.Daos;

import com.example.laboratorio9.Beans.Curso;
import com.example.laboratorio9.Beans.Evaluaciones;
import com.example.laboratorio9.Beans.Semestre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoEvaluaciones extends DaoBase{
    public ArrayList<Evaluaciones>listarEvaluaciones(int idDocente){
        ArrayList<Evaluaciones> listaEvaluaciones = new ArrayList<>();
        String sql = "select ev.idevaluacion, ev.nombre_estudiante, ev.codigo_estudiante, ev.correo_estudiante, ev.nota, ev.idcurso, ev.idsemestre, ev.fecha_registro, ev.fecha_edicion from evaluacion ev " +
                "inner join curso c on ev.idcurso = c.idcurso " +
                "inner join curso_has_docente cd on c.idcurso = cd.idcurso " +
                "where cd.iddocente=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idDocente);
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    Evaluaciones ev = new Evaluaciones();
                    llenarDatosEvaluacion(ev,rs);
                    listaEvaluaciones.add(ev);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaEvaluaciones;
    }

    public ArrayList<Evaluaciones> listarEvaluacionesPorSemestre(int idDocente, int idSemestre){
        ArrayList<Evaluaciones> listaEvaluaciones = new ArrayList<>();
        String sql = "select ev.idevaluacion, ev.nombre_estudiante, ev.codigo_estudiante, ev.correo_estudiante, ev.nota, ev.idcurso, ev.idsemestre, ev.fecha_registro, ev.fecha_edicion from evaluacion ev " +
                "inner join curso c on ev.idcurso = c.idcurso " +
                "inner join curso_has_docente cd on c.idcurso = cd.idcurso " +
                "where cd.iddocente=? and ev.idsemestre=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idDocente);
            pstmt.setInt(2,idSemestre);
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    Evaluaciones ev = new Evaluaciones();
                    llenarDatosEvaluacion(ev,rs);
                    listaEvaluaciones.add(ev);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaEvaluaciones;
    }

    public void llenarDatosEvaluacion(Evaluaciones ev, ResultSet rs) throws SQLException {
        ev.setIdEvaluaciones(rs.getInt(1));
        ev.setNombreEstudiantes(rs.getString(2));
        ev.setCodigoEstudiantes(rs.getString(3));
        ev.setCorreoEstudiantes(rs.getString(4));
        ev.setNota(rs.getInt(5));

        Curso curso = new Curso();
        curso.setIdCurso(rs.getInt(6));
        curso.setNombre(new DaoCurso().obtenerNombreCurso(rs.getInt(6)));

        ev.setCurso(curso);

        Semestre semestre = new Semestre();
        semestre.setIdSemestre(rs.getInt(7));
        semestre.setNombre(new DaoSemestre().obtenerNombreSemestre(rs.getInt(7)));

        ev.setSemestre(semestre);

        ev.setFechaRegistro(rs.getString(8));
        ev.setFechaEdicion(rs.getString(9));
    }

    public void borrarEvaluacion(int idEvaluacion){
        String sql = "delete from evaluacion where idevaluacion=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idEvaluacion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarEvaluacion(int idEvaluacion, String nombreAlumno, String codigoAlumno, String correoAlumno, int notaAlumno){
        String sql = "update evaluacion set nombre_estudiante=?, correo_estudiante=?, codigo_estudiante=?, nota=?, fecha_edicion=now() where idevaluacion=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombreAlumno);
            pstmt.setString(2,correoAlumno);
            pstmt.setString(3,codigoAlumno);
            pstmt.setInt(4,notaAlumno);
            pstmt.setInt(5,idEvaluacion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void crearEvaluacion(String nombre, String correo, String codigo, int nota, int idCurso, int idSemestre){
        String sql = "insert into evaluacion (nombre_estudiante,codigo_estudiante,correo_estudiante,nota,idcurso,idsemestre,fecha_registro) values (?,?,?,?,?,?,now())";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombre);
            pstmt.setString(2,codigo);
            pstmt.setString(3,correo);
            pstmt.setInt(4,nota);
            pstmt.setInt(5,idCurso);
            pstmt.setInt(6,idSemestre);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean idExiste(int idEvaluacion){
        String sql = "select idevaluacion from evaluacion where idevaluacion=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idEvaluacion);
            try(ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificarDatosRepetidos(String nombre, String correo, String codigo, int nota, int idEvaluacion){
        String sql = "select idevaluacion from evaluacion where nombre_estudiante=? and correo_estudiante=? and codigo_estudiante=? and nota=? and idevaluacion=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombre);
            pstmt.setString(2,correo);
            pstmt.setString(3,codigo);
            pstmt.setInt(4,nota);
            pstmt.setInt(5,idEvaluacion);
            try(ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
