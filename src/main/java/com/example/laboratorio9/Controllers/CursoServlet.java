package com.example.laboratorio9.Controllers;

import com.example.laboratorio9.Beans.Usuario;
import com.example.laboratorio9.Daos.DaoCurso;
import com.example.laboratorio9.Daos.DaoFacultad;
import com.example.laboratorio9.Daos.DaoUsuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CursoServlet", value = "/CursoServlet")
public class CursoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        if(request.getSession().getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            DaoCurso daoCurso = new DaoCurso();
            DaoUsuario daoUsuario = new DaoUsuario();
            request.setAttribute("listaCursos",daoCurso.listarCurso(usuario.getIdUsuario()));
            request.setAttribute("listaDocentesSinCurso",daoUsuario.listarDocentesSinCurso());
            request.getSession().setAttribute("usuario",daoUsuario.obtenerUsuarioPorId(usuario.getIdUsuario()));
            request.getRequestDispatcher("menuCursos.jsp").forward(request,response);
        }else{
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        DaoCurso daoCurso = new DaoCurso();
        DaoUsuario daoUsuario = new DaoUsuario();
        DaoFacultad daoFacultad = new DaoFacultad();
        String action = request.getParameter("action")==null?"crear":request.getParameter("action");
        switch (action) {
            case "crear":
                String nombre = request.getParameter("nombreCurso");
                String codigo = request.getParameter("codigoCurso");
                String idDocenteStr = request.getParameter("idDocente");

                boolean cursoValido = true;
                if(nombre==null || codigo==null || idDocenteStr==null){
                    cursoValido=false;
                }else{
                    if(nombre.isEmpty()){
                        cursoValido=false;
                    }
                    if(codigo.isEmpty()){
                        cursoValido=false;
                    }
                    if(nombre.length()>45){
                        cursoValido=false;
                    }
                    if(codigo.length()>6){
                        cursoValido=false;
                    }
                    try{
                        int idDocente  = Integer.parseInt(idDocenteStr);
                        if(!daoUsuario.idExiste(idDocente)){
                            cursoValido=false;
                        }
                    }catch (NumberFormatException ex){
                        cursoValido=false;
                    }
                }

                if(cursoValido){
                    daoCurso.crearCurso(nombre,codigo,Integer.parseInt(idDocenteStr),daoFacultad.obtenerIdPorIdDecano(usuario.getIdUsuario()));
                    request.getSession().setAttribute("creacionExitosa","El curso se registró exitosamente.");
                }else{
                    request.getSession().setAttribute("errorCreacion","Ingrese los datos correctamente. El nombre no debe ser mayor a 45 caracteres, el código no debe ser mayor a 6 caracteres y el docente debe estar registrado en la base de datos.");
                }
                response.sendRedirect(request.getContextPath() + "/CursoServlet");
                break;
            case "editar":
                String nombreCurso = request.getParameter("nombreEditarCurso");
                String idCursoStr = request.getParameter("idCurso");

                boolean edicionValida = true;
                if(nombreCurso==null || idCursoStr==null){
                    edicionValida=false;
                }else{
                    if(nombreCurso.isEmpty()){
                        edicionValida=false;
                    }
                    if(nombreCurso.length()>45){
                        edicionValida=false;
                    }
                    try{
                        int idCurso = Integer.parseInt(idCursoStr);
                        if(!daoCurso.idExiste(idCurso)){
                            edicionValida=false;
                        }
                    }catch (NumberFormatException ex){
                        edicionValida=false;
                    }
                }

                if (edicionValida) {
                    int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                    if(!daoCurso.verificarDatosRepetidos(nombreCurso,idCurso)){
                        daoCurso.actualizarCurso(idCurso,nombreCurso);
                        request.getSession().setAttribute("edicionExitosa","El curso se editó exitosamente.");
                    }else{
                        request.getSession().setAttribute("datosRepetidos","Ingresó datos repetidos. No se realizó ninguna edición.");
                    }
                }else{
                    request.getSession().setAttribute("errorEdicion","Ingrese los datos correctamente. El nombre no debe ser mayor a 45 caracteres y el curso debe estar registrado en la base de datos.");
                }
                response.sendRedirect(request.getContextPath() + "/CursoServlet");
                break;
            case "borrar":
                String idCursoStr2 = request.getParameter("idCurso");

                boolean borradoValido = true;
                if(idCursoStr2==null){
                    borradoValido=false;
                }else{
                    try{
                        int idCurso = Integer.parseInt(idCursoStr2);
                        if(!daoCurso.idExiste(idCurso)){
                            borradoValido=false;
                        }
                        if(daoCurso.cursoConEvaluaciones(idCurso)){
                            borradoValido=false;
                        }
                    }catch (NumberFormatException ex){
                        borradoValido=false;
                    }
                }

                if(borradoValido){
                    int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                    daoCurso.borrarCurso(idCurso);
                    request.getSession().setAttribute("borradoExitoso","El curso se borró exitosamente.");
                }else{
                    request.getSession().setAttribute("errorBorrado","Este curso no puede borrarse.");
                }
                response.sendRedirect(request.getContextPath() + "/CursoServlet");
                break;
        }
    }
}

