<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.laboratorio9.Beans.Usuario" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.laboratorio9.Beans.Evaluaciones" %>
<%@ page import="com.example.laboratorio9.Daos.DaoSemestre" %>
<%@ page import="com.example.laboratorio9.Beans.Semestre" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="com.example.laboratorio9.Daos.DaoCurso" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="iconoInicio.ico">
    <title>Menú de Evaluaciones</title>
    <%Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");%>
    <%ArrayList<Evaluaciones> listaEvaluaciones = (ArrayList<Evaluaciones>) request.getAttribute("listaEvaluaciones");%>
    <%ArrayList<Evaluaciones> listaEvaluacionesTotal = (ArrayList<Evaluaciones>) request.getAttribute("listaEvaluacionesTotal");%>
    <%int idSemestreSeleccionado = request.getAttribute("semestreSeleccionado")==null?0:(int) request.getAttribute("semestreSeleccionado");%>
    <%ArrayList<Semestre> listaSemestres = new ArrayList<>();
        HashSet<Integer> idsSemestres = new HashSet<>();%>
    <%for(Evaluaciones ev : listaEvaluacionesTotal){
        idsSemestres.add(ev.getSemestre().getIdSemestre());
    }
    for(int id : idsSemestres){
        listaSemestres.add(new DaoSemestre().obtenerSemestrePorId(id));
    }%>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@100;600;700&display=swap');
        @import url('https://fonts.googleapis.com/css2?family=Roboto&display=swap');
        @import url('https://fonts.googleapis.com/css2?family=Lobster+Two:ital,wght@1,400;1,700&display=swap');
        *{
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-size: 20px;
        }

        body{
            background-color: #bebc9b;
        }

        .header1{
            font-family: 'Poppins', sans-serif;
            background-color: #1b3039;
            display: flex;
            justify-content: space-between;
            align-items: center;
            height: 85px;
            padding: 5px 10%;
        }

        .header1 .logo h2{
            display: flex;
            color: #eceff1;
            align-items: center;
            height: 70px;
            width: auto;
            transition: all 0.3s;
        }

        .header1 .logo h2:hover{
            transform: scale(1.2);
        }

        .header1 .nav-links{
            list-style: none;
        }

        .header1 .nav-links li{
            display: inline-block;
            padding: 10px 40px 0 0;
        }

        .header1 .nav-links li:hover{
            transform: scale(1.1);
        }

        .header1 .nav-links a{
            font-size: 30px;
            color: #eceff1;
            text-decoration: none;
        }

        .header1 .nav-links li a:hover{
            color: #bebc9b;
        }

        .header1 .btn1 button{
            font-weight: 700;
            color: #1b3039;
            padding: 9px 25px;
            background: #eceff1;
            border: none;
            border-radius: 50px;
            cursor: pointer;
            transition: all 0.3s ease 0s;
        }

        .header1 .btn1 button:hover{
            transform: scale(1.1);
        }

        .overlay {
            opacity: 0;
            pointer-events: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.7);
            z-index: 10000;
        }

        /* Estilo para el contenido del popup */
        .popup {
            opacity: 0;
            pointer-events: none;
            position: fixed;
            top: 25%;
            left: 37.5%;
            border-radius: 20px;
            background-color: rgba(190, 188, 105,0.8);
            padding-top: 20px;
            z-index: 10001;
            box-shadow: 5px 20px 50px #000;
            transition: all 0.3s ease;
        }
        .popup i {
            font-size: 70px;
            color: #4070f4;
        }
        .popup h2 {
            margin-top: 20px;
            font-size: 25px;
            font-weight: 500;
            color: #333;
        }
        .popup h3 {
            font-size: 16px;
            font-weight: 400;
            color: #333;
            text-align: center;
        }

        /* Estilo para el botón de cerrar */
        .cerrarPopup {
            position: absolute;
            top: 10px;
            right: 10px;
            cursor: pointer;
        }
        input::placeholder {
            color: rgba(236, 96, 144, 1); /* Color sólido para el placeholder */
            opacity: 1; /* Opacidad completa para el placeholder */
        }

        input[type="text"] {
            padding: 8px; /* Espaciado interno */
            font-size: 17px; /* Tamaño de la fuente */
            border: 0 solid #ff00ff; /* Borde de tono magenta */
            border-radius: 5px; /* Bordes redondeados */
            background-color: inherit; /* Fondo en tono magenta */
            color: #ec6090; /* Texto en negro */
            width: 35%; /* Ancho automático basado en el contenido */
            box-sizing: border-box; /* Incluir padding y border en el ancho total */
            float: right; /* Alineación a la derecha */
            text-align: center;
        }
        input[type="text"]:focus {
            outline: none; /* Eliminar el contorno de enfoque */
            box-shadow: 0 0 5px rgba(255, 0, 255, 0.5); /* Sombra al estar enfocado en tono magenta con transparencia */
        }
        input[type="email"] {
            padding: 8px; /* Espaciado interno */
            font-size: 17px; /* Tamaño de la fuente */
            border: 0 solid #ff00ff; /* Borde de tono magenta */
            border-radius: 5px; /* Bordes redondeados */
            background-color: inherit; /* Fondo en tono magenta */
            color: #ec6090; /* Texto en negro */
            width: 35%; /* Ancho automático basado en el contenido */
            box-sizing: border-box; /* Incluir padding y border en el ancho total */
            float: right; /* Alineación a la derecha */
            text-align: center;
        }
        input[type="email"]:focus {
            outline: none; /* Eliminar el contorno de enfoque */
            box-shadow: 0 0 5px rgba(255, 0, 255, 0.5); /* Sombra al estar enfocado en tono magenta con transparencia */
        }
        input[type="number"] {
            padding: 8px; /* Espaciado interno */
            font-size: 17px; /* Tamaño de la fuente */
            border: 0 solid #ff00ff; /* Borde de tono magenta */
            border-radius: 5px; /* Bordes redondeados */
            background-color: inherit; /* Fondo en tono magenta */
            color: #ec6090; /* Texto en negro */
            width: 35%; /* Ancho automático basado en el contenido */
            box-sizing: border-box; /* Incluir padding y border en el ancho total */
            float: right; /* Alineación a la derecha */
            text-align: center;
        }
        input[type="number"]:focus {
            outline: none; /* Eliminar el contorno de enfoque */
            box-shadow: 0 0 5px rgba(255, 0, 255, 0.5); /* Sombra al estar enfocado en tono magenta con transparencia */
        }
        select {
            padding: 8px; /* Espaciado interno */
            font-size: 17px; /* Tamaño de la fuente */
            border: 0 solid black; /* Borde de tono magenta */
            border-radius: 5px; /* Bordes redondeados */
            appearance: none; /* Elimina el estilo por defecto del sistema */
            -webkit-appearance: none; /* Para navegadores basados en WebKit */
            -moz-appearance: none; /* Para navegadores basados en Gecko (Firefox) */
            background-color: inherit; /* Fondo en tono magenta */
            color: #000000; /* Texto en negro */
            background-image: url('data:image/svg+xml;utf8,<svg fill="#000000" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>'); /* Icono de flecha personalizado en negro */
            background-repeat: no-repeat; /* No repetir el icono */
            background-size: 30px; /* Tamaño del icono */
            width: 35%; /* Ancho automático basado en el contenido */
            max-width: 100%; /* Ancho máximo */
            box-sizing: border-box; /* Incluir padding y border en el ancho total */
            float: right; /* Alineación a la derecha */
            text-align: center;
        }

        /* Estilos al pasar el mouse sobre el ComboBox */
        select:hover {
            border-color: #bebc69; /* Cambio de color del borde al pasar el mouse */
        }

        /* Estilos cuando el ComboBox está enfocado */
        select:focus {
            outline: none; /* Eliminar el contorno de enfoque */
            box-shadow: 0 0 5px rgba(190, 180, 105, 0.5); /* Sombra al estar enfocado en tono magenta con transparencia */
        }
    </style>
</head>
<body>
<header class="header1">
    <div class="logo mt-1">
        <h2 style="font-weight: bold; font-size: 25px;">Bienvenido, <%=usuario.getNombre()%></h2>
    </div>
    <div class="logo mt-1">
        <h2 style="font-weight: bold; font-size: 14px">Última hora de ingreso: <%=usuario.getUltimoIngreso()%></h2>
    </div>
    <div class="logo mt-1">
        <h2 style="font-weight: bold; font-size: 14px">Cantidad de ingresos: <%=usuario.getCantidadIngresos()%></h2>
    </div>
    <nav>
        <ul class="nav-links">
            <li><a href="<%=request.getContextPath()%>/EvaluacionesServlet" style="color: #bebc9b !important;"><b>Evaluaciones</b></a></li>
        </ul>
    </nav>
    <a href="<%=request.getContextPath()%>?action=logOut" class="btn1"><button>Cerrar Sesión</button></a>
</header>
<div class="container-fluid mt-5" style="max-width: 180vh; font-family: 'Roboto',sans-serif !important;">
    <%if(!(new DaoCurso().obtenerNombreCursoPorDocente(usuario.getIdUsuario()).equals("Ninguno"))){%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-4 mt-3"><button class="btn btn-secondary" id="mostrarPopupCrear">Registrar evaluación</button></div>
            <div class="col-4 mb-3 mt-2" style="font-size: 45px; font-weight: bold; font-style: italic; color: black; text-align: center">Evaluaciones</div>
            <div class="col-4">
                <%if(!listaEvaluaciones.isEmpty()){%>
                <form style="display: flex; justify-content: center; align-items: center; margin-top: 10px" method="get" action="<%=request.getContextPath()%>/EvaluacionesServlet">
                    <input type="hidden" name="action" value="filtro">
                    <label for="idsemestre" style="color: #000000;"><b>Semestre:</b></label>
                    <select style="height: 55px;" name="idsemestre" id="idsemestre" required>
                        <option style="background-color: rgba(218,227,189,0.8); color: #000000;" value="0">Todos</option>
                        <%for(Semestre semestre : listaSemestres){%>
                        <option style="background-color: rgba(218,227,189,0.8); color: #000000;" value="<%=semestre.getIdSemestre()%>" <%if(idSemestreSeleccionado==semestre.getIdSemestre()){%>selected<%}%>><%=semestre.getNombre()%></option>
                        <%}%>
                    </select>
                    <button class="btn btn-secondary">Aplicar filtro</button>
                </form>
                <%}%>
            </div>
        </div>
    </div>

    <%if(request.getSession().getAttribute("creacionExitosa")!=null){%>
        <div><div class="alert alert-success" role="alert"><%=request.getSession().getAttribute("creacionExitosa")%></div></div>
        <%request.getSession().removeAttribute("creacionExitosa");%>
    <%}%>
    <%if(request.getSession().getAttribute("errorCreacion")!=null){%>
        <div><div class="alert alert-success" role="alert"><%=request.getSession().getAttribute("errorCreacion")%></div></div>
        <%request.getSession().removeAttribute("errorCreacion");%>
    <%}%>
    <%if(request.getSession().getAttribute("edicionExitosa")!=null){%>
        <div><div class="alert alert-success" role="alert"><%=request.getSession().getAttribute("edicionExitosa")%></div></div>
        <%request.getSession().removeAttribute("edicionExitosa");%>
    <%}%>
    <%if(request.getSession().getAttribute("datosRepetidos")!=null){%>
        <div><div class="alert alert-success" role="alert"><%=request.getSession().getAttribute("datosRepetidos")%></div></div>
        <%request.getSession().removeAttribute("datosRepetidos");%>
    <%}%>
    <%if(request.getSession().getAttribute("errorEdicion")!=null){%>
        <div><div class="alert alert-success" role="alert"><%=request.getSession().getAttribute("errorEdicion")%></div></div>
        <%request.getSession().removeAttribute("errorEdicion");%>
    <%}%>
    <%if(request.getSession().getAttribute("borradoExitoso")!=null){%>
        <div><div class="alert alert-success" role="alert"><%=request.getSession().getAttribute("borradoExitoso")%></div></div>
        <%request.getSession().removeAttribute("borradoExitoso");%>
    <%}%>
    <%if(request.getSession().getAttribute("errorBorrado")!=null){%>
        <div><div class="alert alert-success" role="alert"><%=request.getSession().getAttribute("errorBorrado")%></div></div>
        <%request.getSession().removeAttribute("errorBorrado");%>
    <%}%>

    <table class="table table-striped align-middle caption-top mt-3">
        <%if(!listaEvaluaciones.isEmpty()){%>
        <tbody>
        <tr class="table-secondary" style="border: 1px solid black;">
            <th class="py-3 text-center">Nombre</th>
            <th class="py-3 text-center">Código</th>
            <th class="py-3 text-center">Correo</th>
            <th class="py-3 text-center">Nota</th>
            <th class="py-3 text-center">Curso</th>
            <th class="py-3 text-center">Semestre</th>
            <th class="py-3 text-center">Fecha de Registro</th>
            <th class="py-3 text-center">Fecha de Edición</th>
            <th class="py-3 text-center">Editar</th>
            <th class="py-3 text-center">Borrar</th>
        </tr>
        </tbody>
        <tbody>
        <%for(Evaluaciones ev : listaEvaluaciones){%>
        <tr style="border: 1px solid black;">
            <td class="py-3 text-center"><%=ev.getNombreEstudiantes()%></td>
            <td class="py-3 text-center"><%=ev.getCodigoEstudiantes()%></td>
            <td class="py-3 text-center"><%=ev.getCorreoEstudiantes()%></td>
            <td class="py-3 text-center"><%=ev.getNota()%></td>
            <td class="py-3 text-center"><%=ev.getCurso().getNombre()%></td>
            <td class="py-3 text-center"><%=ev.getSemestre().getNombre()%></td>
            <td class="py-3 text-center"><%=ev.getFechaRegistro()%></td>
            <td class="py-3 text-center"><%=ev.getFechaEdicion()==null?"No se ha editado nunca":ev.getFechaEdicion()%></td>
            <td class="py-3 text-center"><button class="btn btn-secondary" id="mostrarPopupEditar<%=listaEvaluaciones.indexOf(ev)%>">Editar</button></td>
            <td class="py-3 text-center">
                <form method="post" action="<%=request.getContextPath()%>/EvaluacionesServlet?action=borrar">
                    <input type="hidden" name="idEvaluacion" value="<%=ev.getIdEvaluaciones()%>">
                    <input type="hidden" name="idSemestre" value="<%=ev.getSemestre().getIdSemestre()%>">
                    <button type="submit" class="btn btn-secondary" <%if(!(new DaoSemestre().semestreHabilitado(ev.getSemestre().getIdSemestre()))){%>disabled<%}%>>Borrar</button>
                </form>
            </td>
        </tr>
        <%}%>
        </tbody>
        <%}else{%>
        <div style="font-size: 30px; text-align: center; margin-top: 15px">No hay evaluaciones registradas</div>
        <%}%>
    </table>
    <%}else{%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 mb-3 mt-2" style="font-size: 45px; font-weight: bold; font-style: italic; color: black; text-align: center">No tienes a tu cargo ningún curso, así que no puedes registrar ninguna evaluación. Vuelve cuando un decano te contrate en algún curso.</div>
            <div class="col-2"></div>
        </div>
    </div>
    <%}%>
</div>


<!-- Popup para crear habitante -->
<div class="overlay" id="overlayCrear"></div>
<div class="popup contenedorCrear" style="width: 500px;" id="popupCrear">
    <h5 style="text-align: center; color: #000000; font-size: 25px"><b>Registrar evaluación</b></h5>
    <svg class="cerrarPopup" id="cerrarPopupCrear" width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M11.4142 10L16.7071 4.70711C17.0976 4.31658 17.0976 3.68342 16.7071 3.29289C16.3166 2.90237 15.6834 2.90237 15.2929 3.29289L10 8.58579L4.70711 3.29289C4.31658 2.90237 3.68342 2.90237 3.29289 3.29289C2.90237 3.68342 2.90237 4.31658 3.29289 4.70711L8.58579 10L3.29289 15.2929C2.90237 15.6834 2.90237 16.3166 3.29289 16.7071C3.68342 17.0976 4.31658 17.0976 4.70711 16.7071L10 11.4142L15.2929 16.7071C15.6834 17.0976 16.3166 17.0976 16.7071 16.7071C17.0976 16.3166 17.0976 15.6834 16.7071 15.2929L11.4142 10Z" fill="black"></path>
    </svg>
    <div style="padding-bottom: 40px; margin-top:20px; background-color: rgba(255,255,255,0.8); border-bottom-left-radius: 20px; border-bottom-right-radius: 20px;">
        <form method="post" action="<%=request.getContextPath()%>/EvaluacionesServlet?action=crear">
            <div class="container-fluid">
                <br>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-10">
                        <br>
                        <div class="row">
                            <div class="col">
                                <label for="nombreAlumno" style="margin-top: 25px;color: #000000"><b>Nombre del alumno:</b></label>
                                <input style="margin-top: 15px" type="text" name="nombreAlumno" id="nombreAlumno" placeholder="Nombre" required>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col">
                                <label for="correoAlumno" style="margin-top: 25px;color: #000000"><b>Correo del alumno:</b></label>
                                <input style="margin-top: 15px" type="email" name="correoAlumno" id="correoAlumno" placeholder="ola@gmail.com" required>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col">
                                <label for="codigoAlumno" style="margin-top: 25px;color: #000000"><b>Código del alumno:</b></label>
                                <input style="margin-top: 15px" type="text" name="codigoAlumno" id="codigoAlumno" placeholder="Código" required>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col">
                                <label for="notaAlumno" style="margin-top: 25px;color: #000000"><b>Nota del alumno:</b></label>
                                <input style="margin-top: 15px" type="number" name="notaAlumno" id="notaAlumno" placeholder="Nota" required>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-1"></div>
                </div>
            </div>
            <br>
            <div class="container-fluid mt-2">
                <div class="row text-center">
                    <div class="col-sm-6" style="margin-top: 5px;">
                        <div class="main-button">
                            <button style="background: none; color: inherit; border:0;" type="submit" id="cerrarPopupCrear1"><a class="boton1" style="color: black !important; text-decoration: none"><b>Registrar</b></a></button>
                        </div>
                    </div>
                    <div class="col-sm-6" style="margin-top: 5px;">
                        <div class="main-button">
                            <button type="button" style="background: none; color: inherit; border:0" id="cerrarPopupCrear2"><a class="boton1" style="color: black !important; text-decoration: none"><b>Cancelar</b></a></button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Popup para editar habitante -->
<%for(int i=0;i<listaEvaluaciones.size();i++){%>
<div class="overlay" id="overlayEditar<%=i%>"></div>
<div class="popup contenedorCrear" style="width: 500px;" id="popupEditar<%=i%>">
    <h5 style="text-align: center; color: #000000; font-size: 25px"><b>Editar evaluación</b></h5>
    <svg class="cerrarPopup" id="cerrarPopupEditar<%=i%>" width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M11.4142 10L16.7071 4.70711C17.0976 4.31658 17.0976 3.68342 16.7071 3.29289C16.3166 2.90237 15.6834 2.90237 15.2929 3.29289L10 8.58579L4.70711 3.29289C4.31658 2.90237 3.68342 2.90237 3.29289 3.29289C2.90237 3.68342 2.90237 4.31658 3.29289 4.70711L8.58579 10L3.29289 15.2929C2.90237 15.6834 2.90237 16.3166 3.29289 16.7071C3.68342 17.0976 4.31658 17.0976 4.70711 16.7071L10 11.4142L15.2929 16.7071C15.6834 17.0976 16.3166 17.0976 16.7071 16.7071C17.0976 16.3166 17.0976 15.6834 16.7071 15.2929L11.4142 10Z" fill="black"/>
    </svg>
    <div style="padding-bottom: 40px; background-color: rgba(255,255,255,0.8); margin-top:20px; border-bottom-left-radius: 20px; border-bottom-right-radius: 20px;">
        <form method="post" action="<%=request.getContextPath()%>/EvaluacionesServlet?action=editar">
            <div class="container-fluid">
                <br>
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="col-md-10">
                        <br>
                        <div class="row">
                            <div class="col">
                                <input type="hidden" name="idEvaluacion" value="<%=listaEvaluaciones.get(i).getIdEvaluaciones()%>">
                                <label for="nombreEditarAlumno<%=i%>" style="margin-top: 25px;color: #000000"><b>Nombre del alumno:</b></label>
                                <input style="margin-top: 15px" type="text" name="nombreEditarAlumno" id="nombreEditarAlumno<%=i%>" placeholder="Nombre" value="<%=listaEvaluaciones.get(i).getNombreEstudiantes()%>" required>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col">
                                <label for="correoEditarAlumno<%=i%>" style="margin-top: 25px;color: #000000"><b>Correo del alumno:</b></label>
                                <input style="margin-top: 15px" type="email" name="correoEditarAlumno" id="correoEditarAlumno<%=i%>" placeholder="ola@gmail.com" value="<%=listaEvaluaciones.get(i).getCorreoEstudiantes()%>" required>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col">
                                <label for="codigoEditarAlumno<%=i%>" style="margin-top: 25px;color: #000000"><b>Código del alumno:</b></label>
                                <input style="margin-top: 15px" type="text" name="codigoEditarAlumno" id="codigoEditarAlumno<%=i%>" placeholder="Código" value="<%=listaEvaluaciones.get(i).getCodigoEstudiantes()%>" required>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col">
                                <label for="notaEditarAlumno<%=i%>" style="margin-top: 25px;color: #000000"><b>Nota del alumno:</b></label>
                                <input style="margin-top: 15px" type="number" name="notaEditarAlumno" id="notaEditarAlumno<%=i%>" placeholder="Nota" value="<%=listaEvaluaciones.get(i).getNota()%>" required>
                            </div>
                        </div>
                        <br>
                    </div>
                    <div class="col-md-1"></div>
                </div>
            </div>
            <br>
            <div class="container-fluid">
                <div class="row text-center">
                    <div class="col-sm-6" style="margin-top: 5px;">
                        <div class="main-button">
                            <button style="background: none; color: inherit; border:0;" type="submit" id="cerrarPopupEditar1<%=i%>"><a class="boton1" style="color: black !important; text-decoration: none"><b>Editar</b></a></button>
                        </div>
                    </div>
                    <div class="col-sm-6" style="margin-top: 5px;">
                        <div class="main-button">
                            <button type="button" style="background: none; color: inherit; border:0" id="cerrarPopupEditar2<%=i%>"><a class="boton1" style="color: black !important; text-decoration: none"><b>Cancelar</b></a></button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<%}%>

<script>
    function popupFunc(popupId,abrirId,cerrarClass,overlayId){
        let showPopup=document.getElementById(abrirId);
        let overlay=document.getElementById(overlayId);
        let popup=document.getElementById(popupId);
        const mostrarPopup = () => {
            overlay.style.opacity='1';
            overlay.style.pointerEvents='auto';
            popup.style.opacity='1';
            popup.style.pointerEvents='auto';
            document.body.style.overflow = 'hidden';
            popup.style.transform= "scale(1.2)";
        };
        showPopup.addEventListener('click', mostrarPopup);
        const cerrarPopup = () => {
            overlay.style.opacity='0';
            overlay.style.pointerEvents='none';
            popup.style.opacity='0';
            popup.style.pointerEvents='none'
            popup.style.transform= "scale(1)";
            document.body.style.overflow = 'auto';

        };
        for(let i=0;i<cerrarClass.length;i++){
            document.getElementById(cerrarClass[i]).addEventListener('click', cerrarPopup);
        }

        overlay.addEventListener('click', (e) => {
            if (e.target === overlay) {
                cerrarPopup();
            }
        });

        document.addEventListener('keydown', (event) => {
            if (event.key === 'Escape') {
                cerrarPopup();
            }
        });
    }

    //Crear
    popupFunc('popupCrear','mostrarPopupCrear',['cerrarPopupCrear','cerrarPopupCrear1','cerrarPopupCrear2'],'overlayCrear');

    <%for(int i=0;i<listaEvaluaciones.size();i++){%>
    //Editar
    popupFunc('popupEditar<%=i%>','mostrarPopupEditar<%=i%>',['cerrarPopupEditar<%=i%>','cerrarPopupEditar1<%=i%>','cerrarPopupEditar2<%=i%>'],'overlayEditar<%=i%>');
    <%}%>
</script>

</body>
</html>
