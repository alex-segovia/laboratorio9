package com.example.laboratorio9.Controllers;

import com.example.laboratorio9.Beans.Usuario;
import com.example.laboratorio9.Daos.DaoEvaluaciones;
import com.example.laboratorio9.Daos.DaoUsuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "EvaluacionesServlet", value = "/EvaluacionesServlet")
public class EvaluacionesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        if(request.getSession().getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            DaoEvaluaciones daoEvaluaciones = new DaoEvaluaciones();
            DaoUsuario daoUsuario = new DaoUsuario();
            request.setAttribute("listaEvaluaciones",daoEvaluaciones.listarEvaluaciones(usuario.getIdUsuario()));
            request.getSession().setAttribute("usuario",daoUsuario.obtenerUsuarioPorId(usuario.getIdUsuario()));
            request.getRequestDispatcher("menuEvaluaciones.jsp").forward(request,response);
        }else{
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        DaoEvaluaciones daoEvaluaciones = new DaoEvaluaciones();
        String action = request.getParameter("action")==null?"crear":request.getParameter("action");
        switch (action){
            case "crear":
                break;
            case "editar":
                break;
            case "borrar":
                break;
        }
    }
}

