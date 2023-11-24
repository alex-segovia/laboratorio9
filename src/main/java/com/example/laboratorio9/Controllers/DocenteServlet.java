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
    }
}

