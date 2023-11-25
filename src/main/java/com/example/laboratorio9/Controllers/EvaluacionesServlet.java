package com.example.laboratorio9.Controllers;

import com.example.laboratorio9.Beans.Usuario;
import com.example.laboratorio9.Daos.DaoCurso;
import com.example.laboratorio9.Daos.DaoEvaluaciones;
import com.example.laboratorio9.Daos.DaoSemestre;
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
            DaoSemestre daoSemestre = new DaoSemestre();
            String action = request.getParameter("action")==null?"default":request.getParameter("action");
            switch (action){
                case "default":
                    request.setAttribute("listaEvaluaciones",daoEvaluaciones.listarEvaluaciones(usuario.getIdUsuario()));
                    break;
                case "filtro":
                    String idSemestreStr = request.getParameter("idsemestre");

                    boolean validacion=true;
                    if(idSemestreStr==null){
                        validacion=false;
                    }else{
                        try{
                            int idSemestre = Integer.parseInt(request.getParameter("idsemestre"));
                            if(!daoSemestre.idExiste(idSemestre)){
                                validacion=false;
                            }
                        }catch (NumberFormatException ex){
                            validacion=false;
                        }
                    }

                    if(validacion){
                        request.setAttribute("listaEvaluaciones",daoEvaluaciones.listarEvaluacionesPorSemestre(usuario.getIdUsuario(),Integer.parseInt(request.getParameter("idsemestre"))));
                    }else{
                        request.setAttribute("listaEvaluaciones",daoEvaluaciones.listarEvaluaciones(usuario.getIdUsuario()));
                    }
                    break;
            }
            request.getSession().setAttribute("usuario",daoUsuario.obtenerUsuarioPorId(usuario.getIdUsuario()));
            request.getRequestDispatcher("menuEvaluaciones.jsp").forward(request,response);
        }else{
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        DaoEvaluaciones daoEvaluaciones = new DaoEvaluaciones();
        DaoSemestre daoSemestre = new DaoSemestre();
        DaoCurso daoCurso = new DaoCurso();
        String action = request.getParameter("action")==null?"crear":request.getParameter("action");
        switch (action){
            case "crear":
                String nombre = request.getParameter("nombreAlumno");
                String correo = request.getParameter("correoAlumno");
                String codigo = request.getParameter("codigoAlumno");
                String notaStr = request.getParameter("notaAlumno");

                boolean evaluacionValida = true;
                if(nombre==null || codigo==null || correo==null || notaStr==null){
                    evaluacionValida=false;
                }else {
                    if (nombre.isEmpty()) {
                        evaluacionValida = false;
                    }
                    if (codigo.isEmpty()) {
                        evaluacionValida = false;
                    }
                    if (correo.isEmpty()) {
                        evaluacionValida = false;
                    }
                    if (notaStr.isEmpty()) {
                        evaluacionValida = false;
                    }
                    if (nombre.length() > 45) {
                        evaluacionValida = false;
                    }
                    if (codigo.length() > 45) {
                        evaluacionValida = false;
                    }
                    if (correo.length() > 45) {
                        evaluacionValida = false;
                    }
                    try {
                        int nota = Integer.parseInt(notaStr);
                        if (nota < 0) {
                            evaluacionValida = false;
                        }
                    } catch (NumberFormatException ex) {
                        evaluacionValida = false;
                    }
                }

                if(evaluacionValida){
                    daoEvaluaciones.crearEvaluacion(nombre,correo,codigo,Integer.parseInt(notaStr),daoCurso.obtenerIdCursoPorDocente(usuario.getIdUsuario()),daoSemestre.obtenerIdSemestreHabilitadoPorAdministradorPorIdDocente(usuario.getIdUsuario()));
                    request.getSession().setAttribute("creacionExitosa","La evaluación se registró exitosamente.");
                }else{
                    request.getSession().setAttribute("errorCreacion","Ingrese los datos correctamente. El nombre, el correo y el código del estudiante no deben tener más de 45 caracteres. Además, la nota debe ser un número entero.");
                }
                response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                break;
            case "editar":
                String nombreAlumno = request.getParameter("nombreEditarAlumno");
                String correoAlumno = request.getParameter("correoEditarAlumno");
                String codigoAlumno = request.getParameter("codigoEditarAlumno");
                String notaAlumno = request.getParameter("notaEditarAlumno");
                String idEvaluacionStr = request.getParameter("idEvaluacion");

                boolean edicionValida = true;
                if(nombreAlumno==null || codigoAlumno==null || correoAlumno==null || notaAlumno==null || idEvaluacionStr==null){
                    edicionValida=false;
                }else {
                    if (nombreAlumno.isEmpty()) {
                        edicionValida = false;
                    }
                    if (codigoAlumno.isEmpty()) {
                        edicionValida = false;
                    }
                    if (correoAlumno.isEmpty()) {
                        edicionValida = false;
                    }
                    if (notaAlumno.isEmpty()) {
                        edicionValida = false;
                    }
                    if (nombreAlumno.length() > 45) {
                        edicionValida = false;
                    }
                    if (codigoAlumno.length() > 45) {
                        edicionValida = false;
                    }
                    if (correoAlumno.length() > 45) {
                        edicionValida = false;
                    }
                    try {
                        int nota = Integer.parseInt(notaAlumno);
                        int idEvaluacion = Integer.parseInt(idEvaluacionStr);
                        if (nota < 0) {
                            edicionValida = false;
                        }
                        if (!daoEvaluaciones.idExiste(idEvaluacion)) {
                            edicionValida = false;
                        }
                    } catch (NumberFormatException ex) {
                        edicionValida = false;
                    }
                }

                if(edicionValida){
                    int idEvaluacion = Integer.parseInt(request.getParameter("idEvaluacion"));
                    daoEvaluaciones.editarEvaluacion(idEvaluacion,nombreAlumno,codigoAlumno,correoAlumno,Integer.parseInt(notaAlumno));
                    request.getSession().setAttribute("edicionExitosa","La evaluación se editó exitosamente.");
                }else{
                    request.getSession().setAttribute("errorEdicion","Ingrese los datos correctamente. El nombre, el correo y el código del estudiante no deben tener más de 45 caracteres. Además, la nota debe ser un número entero y la evaluación debe estar registrada en base de datos.");
                }
                response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                break;
            case "borrar":
                String idEvaluacionStr2 = request.getParameter("idEvaluacion");
                String idSemestreStr = request.getParameter("idSemestre");

                boolean borradoValido = true;
                if(idEvaluacionStr2==null || idSemestreStr==null){
                    borradoValido=false;
                }else {
                    try {
                        int idEvaluacion = Integer.parseInt(idEvaluacionStr2);
                        int idSemestre = Integer.parseInt(idSemestreStr);

                        if (!daoEvaluaciones.idExiste(idEvaluacion)) {
                            borradoValido = false;
                        }
                        if (!daoSemestre.idExiste(idSemestre)) {
                            borradoValido = false;
                        }
                        if (!daoSemestre.semestreHabilitado(idSemestre)) {
                            borradoValido = false;
                        }
                    } catch (NumberFormatException ex) {
                        borradoValido = false;
                    }
                }

                if(borradoValido){
                    int idEvaluacion = Integer.parseInt(request.getParameter("idEvaluacion"));
                    daoEvaluaciones.borrarEvaluacion(idEvaluacion);
                    request.getSession().setAttribute("borradoExitoso","La evaluación se borró exitosamente.");
                }else{
                    request.getSession().setAttribute("errorBorrado","Esta evaluación no puede borrarse.");
                }
                response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                break;
        }
    }
}

