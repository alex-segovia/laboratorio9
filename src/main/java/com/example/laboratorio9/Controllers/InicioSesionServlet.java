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
                    }else if(((Usuario)request.getSession().getAttribute("usuario")).getRol().getNombre().equals("Docente")){
                        response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
                    }
                }else{
                    request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
                }
                break;
            case "logOut":
                request.getSession().removeAttribute("usuario");
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath());
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        DaoUsuario daoUsuario = new DaoUsuario();

        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        boolean logInValido = true;
        if(correo==null || password==null){
            logInValido=false;
        }else{
            if(correo.isEmpty()){
                logInValido=false;
            }
            if(password.isEmpty()){
                logInValido=false;
            }
            if(correo.length()>45){
                logInValido=false;
            }
            if(!daoUsuario.validarUsuario(correo,password)){
                logInValido=false;
            }
        }

        if(logInValido){
            Usuario usuario = daoUsuario.obtenerUsuario(correo);
            if(usuario.getRol().getNombre().equals("Decano")){
                daoUsuario.actualizarUltimaHoraYCantidadDeIngresos(usuario.getIdUsuario());
                request.getSession().setAttribute("usuario",usuario);
                request.getSession().setMaxInactiveInterval(600);
                response.sendRedirect(request.getContextPath()+"/CursoServlet");
            }else if(usuario.getRol().getNombre().equals("Docente")){
                daoUsuario.actualizarUltimaHoraYCantidadDeIngresos(usuario.getIdUsuario());
                request.getSession().setAttribute("usuario",usuario);
                request.getSession().setMaxInactiveInterval(600);
                response.sendRedirect(request.getContextPath()+"/EvaluacionesServlet");
            }else{
                request.setAttribute("rol",usuario.getRol().getNombre());
                request.getRequestDispatcher("vistaError.jsp").forward(request, response);
            }
        }else{
            request.getSession().setAttribute("errorLogIn",0);
            response.sendRedirect(request.getContextPath());
        }
    }
}

