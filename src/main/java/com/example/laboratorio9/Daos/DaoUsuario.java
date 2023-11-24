package com.example.laboratorio9.Daos;

import com.example.laboratorio9.Beans.Rol;
import com.example.laboratorio9.Beans.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DaoUsuario extends DaoBase{
    public boolean validarUsuario(String correo, String password){
        String sql = "select idUsuario from usuario where correo=? and password=sha2(?,256)";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,correo);
            pstmt.setString(2,password);
            try(ResultSet rs = pstmt.executeQuery()){
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario obtenerUsuario(String correo){
        String sql = "select idusuario, nombre, correo, idrol, ultimo_ingreso, cantidad_ingresos, fecha_registro, fecha_edicion from usuario where correo=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,correo);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Usuario usuario = new Usuario();
                    llenarDatosUsuario(usuario,rs);
                    return usuario;
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario obtenerUsuarioPorId(int idUsuario){
        String sql = "select idusuario, nombre, correo, idrol, ultimo_ingreso, cantidad_ingresos, fecha_registro, fecha_edicion from usuario where idusuario=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idUsuario);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Usuario usuario = new Usuario();
                    llenarDatosUsuario(usuario,rs);
                    return usuario;
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Usuario> listarDocentes(int idDecano){
        ArrayList<Usuario> listaDocentes = new ArrayList<>();

        String sql = "select d.idusuario, d.nombre, d.correo, d.ultimo_ingreso, d.cantidad_ingresos, d.fecha_registro, d.fecha_edicion from usuario d " +
                "left join rol r on d.idrol = r.idrol " +
                "left join curso_has_docente cd on d.idusuario = cd.iddocente " +
                "left join curso c on cd.idcurso = c.idcurso " +
                "left join facultad f on c.idfacultad = f.idfacultad " +
                "left join facultad_has_decano fd on f.idfacultad = fd.idfacultad " +
                "where (r.nombre='Docente' and d.idusuario not in (select iddocente from curso_has_docente)) or fd.iddecano=?";

        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idDecano);
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()) {
                    Usuario docente = new Usuario();
                    llenarDatosDocente(docente, rs);
                    listaDocentes.add(docente);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaDocentes;
    }

    public String obtenerNombreDocentePorIdCurso(int idCurso){
        String sql = "select d.nombre from usuario d " +
                "inner join curso_has_docente cd on d.idusuario = cd.iddocente " +
                "where cd.idcurso=?";
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

    public void llenarDatosDocente(Usuario docente, ResultSet rs) throws SQLException {
        docente.setIdUsuario(rs.getInt(1));
        docente.setNombre(rs.getString(2));
        docente.setCorreo(rs.getString(3));
        docente.setUltimoIngreso(rs.getString(4));
        docente.setCantidadIngresos(rs.getInt(5));
        docente.setFechaRegistro(rs.getString(6));
        docente.setFechaEdicion(rs.getString(7));
    }

    public void llenarDatosUsuario(Usuario usuario, ResultSet rs) throws SQLException {
        usuario.setIdUsuario(rs.getInt(1));
        usuario.setNombre(rs.getString(2));
        usuario.setCorreo(rs.getString(3));

        Rol rol = new Rol();
        rol.setIdRol(rs.getInt(4));
        DaoRol daoRol = new DaoRol();
        rol.setNombre(daoRol.obtenerRol(rs.getInt(4)));

        usuario.setRol(rol);
        usuario.setUltimoIngreso(rs.getString(5));
        usuario.setCantidadIngresos(rs.getInt(6));
        usuario.setFechaRegistro(rs.getString(7));
        usuario.setFechaEdicion(rs.getString(8));
    }

    public void actualizarUltimaHoraYCantidadDeIngresos(int idUsuario){
        String sql = "update usuario set ultimo_ingreso=now(), cantidad_ingresos=cantidad_ingresos+1 where idusuario=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarNombreDocente(int idDocente, String nombreDocente){
        String sql = "update usuario set nombre=?, fecha_edicion=now() where idusuario=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombreDocente);
            pstmt.setInt(2,idDocente);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarDocente(int idUsuario){
        String sql = "delete from usuario where idusuario=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void crearDocente(String nombre, String correo, String contrasena){
        String sql = "insert into usuario (nombre,correo,password,idrol,cantidad_ingresos,fecha_registro) values (?,?,sha2(?,256),?,?,now())";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,nombre);
            pstmt.setString(2,correo);
            pstmt.setString(3,contrasena);
            pstmt.setInt(4,new DaoRol().obtenerIdPorNombre("Docente"));
            pstmt.setInt(5,0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Usuario> listarDocentesSinCurso(){
        ArrayList<Usuario> listaDocentesSinCurso = new ArrayList<>();
        String sql = "select u.idusuario, u.nombre from usuario u " +
                "inner join rol r on u.idrol = r.idrol " +
                "where r.nombre='Docente' and u.idusuario not in (select iddocente from curso_has_docente)";
        try(Connection conn = getConnection();
            ResultSet rs = conn.prepareStatement(sql).executeQuery()){
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                listaDocentesSinCurso.add(usuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaDocentesSinCurso;
    }
}
