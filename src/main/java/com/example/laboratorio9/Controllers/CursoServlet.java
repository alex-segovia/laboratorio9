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
        String action = request.getParameter("action")==null?"crear":request.getParameter("action");
        switch (action) {
            case "crear":
                String nombre = request.getParameter("nombreCurso");
                String codigo = request.getParameter("codigoCurso");
                if(!(nombre.isEmpty() || codigo.isEmpty())){
                    String idDocenteStr = request.getParameter("idDocente");
                    DaoFacultad daoFacultad = new DaoFacultad();
                    daoCurso.crearCurso(nombre,codigo,Integer.parseInt(idDocenteStr),daoFacultad.obtenerIdPorIdDecano(usuario.getIdUsuario()));
                }
                response.sendRedirect(request.getContextPath() + "/CursoServlet");
                break;
            case "editar":
                String nombreCurso = request.getParameter("nombreEditarCurso");
                if (!nombreCurso.isEmpty()) {
                    int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                    daoCurso.actualizarCurso(idCurso,nombreCurso);
                }
                response.sendRedirect(request.getContextPath() + "/CursoServlet");
                break;
            case "borrar":
                int idCurso = Integer.parseInt(request.getParameter("idCurso"));
                if(!(daoCurso.cursoConEvaluaciones(idCurso))){
                    daoCurso.borrarCurso(idCurso);
                }
                response.sendRedirect(request.getContextPath() + "/CursoServlet");
                break;
        }
    }
}

