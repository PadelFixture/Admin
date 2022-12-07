<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
    <li class="dropdown dropdown-user">
        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
           
            <span class="username username-hide-on-mobile">${nombre}</span>
            <i class="fa fa-angle-down"></i>
        </a>
        <ul class="dropdown-menu dropdown-menu-default">
          <!--   <li>
                <a href="perfil.jsp">
                    <i class="icon-user"></i> Mi Perfil </a>
            </li>
      -->
           
            <li class="divider"> </li>
           
            <li>
                <a href="exit.jsp">
                    <i class="icon-key"></i> Log Out </a>
            </li>
        </ul>
    </li>