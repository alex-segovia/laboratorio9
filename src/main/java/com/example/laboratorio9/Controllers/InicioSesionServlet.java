package com.example.laboratorio9.Controllers;

import com.example.laboratorio9.Beans.Usuario;
import com.example.laboratorio9.Daos.DaoUsuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "InicioSesionServlet", value = "")
public class InicioSesionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String action = request.getParameter("action") == null ? "logIn" : request.getParameter("action");
        switch (action){
            case "logIn":
                if(request.getSession().getAttribute("usuario")!=null){
                    if(((Usuario)request.getSession().getAttribute("usuario")).getRol().getNombre().equals("Decano")){
                        response.sendRedirect(request.getContextPath()+"/CursoServlet");
                    }else{
                        response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                    }
                }else{
                    request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
                }
                break;
            case "logOut":
                request.getSession().removeAttribute("usuario");
                request.getSession().invalidate();
                request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        DaoUsuario daoUsuario = new DaoUsuario();

        if(daoUsuario.validarUsuario(correo,password)){
            Usuario usuario = daoUsuario.obtenerUsuario(correo);
            daoUsuario.actualizarUltimaHoraYCantidadDeIngresos(usuario.getIdUsuario());
            request.getSession().setAttribute("usuario",usuario);
            request.getSession().setMaxInactiveInterval(600);
            if(usuario.getRol().getNombre().equals("Decano")){
                response.sendRedirect(request.getContextPath()+"/CursoServlet");
            }else{
                response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
            }
        }else{
            response.sendRedirect(request.getContextPath());
        }
    }
}

