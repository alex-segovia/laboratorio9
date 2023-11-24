package com.example.laboratorio9.Controllers;

import com.example.laboratorio9.Beans.Usuario;
import com.example.laboratorio9.Daos.DaoCurso;
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
            request.getSession().setAttribute("usuario",daoUsuario.obtenerUsuarioPorId(usuario.getIdUsuario()));
            request.getRequestDispatcher("menuCursos.jsp").forward(request,response);
        }else{
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
    }
}

