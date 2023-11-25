package com.example.laboratorio9.Controllers;

import com.example.laboratorio9.Beans.Usuario;
import com.example.laboratorio9.Daos.DaoCurso;
import com.example.laboratorio9.Daos.DaoUsuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DocenteServlet", value = "/DocenteServlet")
public class DocenteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        if(request.getSession().getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            DaoUsuario daoUsuario = new DaoUsuario();
            request.setAttribute("listaDocentes",daoUsuario.listarDocentes(usuario.getIdUsuario()));
            request.getSession().setAttribute("usuario",daoUsuario.obtenerUsuarioPorId(usuario.getIdUsuario()));
            request.getRequestDispatcher("menuDocentes.jsp").forward(request,response);
        }else{
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        DaoUsuario daoUsuario = new DaoUsuario();
        DaoCurso daoCurso = new DaoCurso();
        String action = request.getParameter("action")==null?"crear":request.getParameter("action");
        switch (action) {
            case "crear":
                String nombre = request.getParameter("nombreDocente");
                String correo = request.getParameter("correoDocente");
                String contrasena = request.getParameter("passwdDocente");

                boolean docenteValido = true;
                if(nombre==null || contrasena==null || correo==null){
                    docenteValido=false;
                }else{
                    if(nombre.isEmpty()){
                        docenteValido=false;
                    }
                    if(correo.isEmpty()){
                        docenteValido=false;
                    }
                    if(contrasena.isEmpty()){
                        docenteValido=false;
                    }
                    if(nombre.length()>45){
                        docenteValido=false;
                    }
                    if(correo.length()>45){
                        docenteValido=false;
                    }
                }

                if(docenteValido){
                    daoUsuario.crearDocente(nombre,correo,contrasena);
                    request.getSession().setAttribute("creacionExitosa","El docente se registró exitosamente.");
                }else{
                    request.getSession().setAttribute("errorCreacion","Ingrese los datos correctamente. El nombre y el correo no deben ser mayores a 45 caracteres.");
                }
                response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                break;
            case "editar":
                String nombreDocente = request.getParameter("nombreEditarDocente");
                String idDocenteStr = request.getParameter("idDocente");

                boolean edicionValida = true;
                if(nombreDocente==null || idDocenteStr==null){
                    edicionValida=false;
                }else{
                    if(nombreDocente.isEmpty()){
                        edicionValida=false;
                    }
                    if(nombreDocente.length()>45){
                        edicionValida=false;
                    }
                    try{
                        int idDocente = Integer.parseInt(idDocenteStr);
                        if(!daoUsuario.idExiste(idDocente)){
                            edicionValida=false;
                        }
                    }catch (NumberFormatException ex){
                        edicionValida=false;
                    }
                }

                if (edicionValida) {
                    int idDocente = Integer.parseInt(request.getParameter("idDocente"));

                    if(daoUsuario.verificarDatosRepetidos(nombreDocente,idDocente)){
                        daoUsuario.actualizarNombreDocente(idDocente, nombreDocente);
                        request.getSession().setAttribute("edicionExitosa","El docente se editó exitosamente.");
                    }else{
                        request.getSession().setAttribute("datosRepetidos","Ingresó datos repetidos. No se realizó ninguna edición.");
                    }
                }else{
                    request.getSession().setAttribute("errorEdicion","Ingrese los datos correctamente. El nombre no debe ser mayor a 45 caracteres y el docente debe estar registrado en la base de datos.");
                }
                response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                break;
            case "borrar":
                String idDocenteStr2 = request.getParameter("idDocente");

                boolean borradoValido = true;
                if(idDocenteStr2==null){
                    borradoValido=false;
                }else{
                    try{
                        int idDocente = Integer.parseInt(idDocenteStr2);
                        if(!(daoCurso.obtenerNombreCursoPorDocente(idDocente)).equals("Ninguno")){
                            borradoValido=false;
                        }
                    }catch (NumberFormatException ex){
                        borradoValido=false;
                    }
                }

                if(borradoValido){
                    int idDocente = Integer.parseInt(request.getParameter("idDocente"));

                    daoUsuario.borrarDocente(idDocente);
                    request.getSession().setAttribute("borradoExitoso","El docente se borró exitosamente.");
                }else{
                    request.getSession().setAttribute("errorBorrado","Este docente no puede borrarse.");
                }
                response.sendRedirect(request.getContextPath() + "/DocenteServlet");
                break;
        }
    }
}

