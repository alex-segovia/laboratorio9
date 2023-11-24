<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.laboratorio9.Beans.Usuario" %>
<%@ page import="com.example.laboratorio9.Beans.Curso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.laboratorio9.Daos.DaoUsuario" %>
<%@ page import="com.example.laboratorio9.Beans.Evaluaciones" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menú de Evaluaciones</title>
    <%Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");%>
    <%ArrayList<Evaluaciones> listaEvaluaciones = (ArrayList<Evaluaciones>) request.getAttribute("listaEvaluaciones");%>
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
    </style>
</head>
<body>
<header class="header1">
    <div class="logo mt-1">
        <h2 style="font-weight: bold;"><a style="text-decoration: none; font-size: 25px; font-weight: inherit; color: inherit" href="<%=request.getContextPath()%>/CursoServlet">Bienvenido, <%=usuario.getNombre()%></a></h2>
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

    <div class="container-fluid">
        <div class="row">
            <div class="col-4 mt-3"><button class="btn btn-secondary">Registrar evaluación</button></div>
            <div class="col-4 mb-3 mt-2" style="font-size: 45px; font-weight: bold; font-style: italic; color: black; text-align: center">Evaluaciones</div>
            <div class="col-4"></div>
        </div>
    </div>

    <table class="table table-striped align-middle caption-top mt-3">
        <%if(!listaEvaluaciones.isEmpty()){%>
        <thead>
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
        </thead>
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
            <td class="py-3 text-center"><%=ev.getFechaEdicion()==null?"No ha sido editado nunca":ev.getFechaEdicion()%></td>
            <td class="py-3 text-center"><button class="btn btn-secondary">Editar</button></td>
            <td class="py-3 text-center"><form method="post" action="<%=request.getContextPath()%>?action=borrar"><button class="btn btn-secondary">Borrar</button></form></td>
        </tr>
        <%}%>
        </tbody>
        <%}else{%>
        <div>No hay evaluaciones registradas</div>
        <%}%>
    </table>
</div>
</body>
</html>
