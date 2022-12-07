<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!-- 
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.6
Version: 4.6
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Dribbble: www.dribbble.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
Renew Support: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
		<link href="../assets/global/css/style_light.css" rel="stylesheet" type="text/css" />
<!-- 		ANIMATED -->
		<link href="../assets/global/css/animated.css" rel="stylesheet" type="text/css" />
<!-- 		END ANIMATED -->
		
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!--   <link rel="stylesheet" href="/resources/demos/style.css"> -->
		<link href="../assets/global/plugins/bootstrap-treeview/css/bootstrap-treeview.css" />
        <link href="../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.css" rel="stylesheet" type="text/css" />
        
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.css" />
		
		
		<link href="../assets/global/css/toastr.css" rel="stylesheet" type="text/css" />
        <meta charset="utf-8" />
        <title>SimpleWeb</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.css"/>
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="../assets/global/css/modal.css" rel="stylesheet" type="text/css" />
        <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="../assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/morris/morris.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/fullcalendar/fullcalendar.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/plugins/EasyAutocomplete-1.3.5/easy-autocomplete.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="../assets/global/css/components.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="../assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="../assets/global/css/file-input.css" rel="stylesheet" type="text/css" />
        <link href="../assets/layouts/layout/css/layout.css" rel="stylesheet" type="text/css" />
        <link href="../assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="../assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/css/btp_css.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/css/simpleWeb.css" rel="stylesheet" type="text/css" />
        <link href="../assets/global/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
		<link href="../assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
		<link href="../assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.css" rel="stylesheet" type="text/css" />
		<link href="../assets/global/plugins/ion.rangeslider/css/normalize.css" rel="stylesheet" type="text/css" />
		<link href="../assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinFlat.css" rel="stylesheet" type="text/css" />
		<link href="../assets/global/dist/handsontable.full.min.css" rel="stylesheet" type="text/css" >

<!--         <link href="../assets/global/css/bootstrap2-toggle.css" rel="stylesheet"> -->
	<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="../assets/layouts/layout/img/Simple.ico">
        </head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" id="simpleAgro">
        <div class="page-wrapper">
            <!-- BEGIN HEADER -->
            <div class="page-header navbar navbar-fixed-top">
                <!-- BEGIN HEADER INNER -->
                <div class="page-header-inner ">
                    <!-- BEGIN LOGO -->
                    <div class="page-logo">
                        <a href="infoPage">
                        	<%if(session.getAttribute("perfil").equals("agro") || session.getAttribute("perfil").equals("admin")){%>	
                            	<img src="../assets/layouts/layout/img/logo_simpleagro1.png" alt="logo" class="logo-default" style="width: 150px;"/> </a>
                            <%}else{%>	
                            	<img src="../assets/layouts/layout/img/SIMPLEWORK.png" alt="logo" class="logo-default" style="width: 150px;"/> </a>
                            <%}%>
                        <div class="menu-toggler sidebar-toggler">
                            <span></span>
                        </div>
                    </div>
                    <input type="text" name="fecha" style="display: none;">
                    <!-- END LOGO -->
                    <!-- BEGIN RESPONSIVE MENU TOGGLER -->
                    <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
                        <span></span>
                    </a>
                    <!-- END RESPONSIVE MENU TOGGLER -->
                    <!-- BEGIN TOP NAVIGATION MENU -->
                    <div class="top-menu">
                        <ul class="nav navbar-nav pull-right">
                            <!-- BEGIN NOTIFICATION DROPDOWN -->
                            <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                            
                            <!-- END NOTIFICATION DROPDOWN -->
                            <!-- BEGIN INBOX DROPDOWN -->
                         <!-- <jsp:include page="${request.contextPath}/webApp/layout_alert.jsp"></jsp:include>  -->
                           
                            <!-- BEGIN USER LOGIN DROPDOWN -->
                            <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                      <!--  <jsp:include page="${request.contextPath}/webApp/layout_user.jsp"></jsp:include>  -->
                            <!-- END USER LOGIN DROPDOWN -->
                          
                        </ul>
                    </div>
                    <!-- END TOP NAVIGATION MENU -->
                </div>
                <!-- END HEADER INNER -->
            </div>
            <!-- END HEADER -->
            <!-- BEGIN HEADER & CONTENT DIVIDER -->
            <div class="clearfix"> </div>
            <!-- END HEADER & CONTENT DIVIDER -->
            <!-- BEGIN CONTAINER -->
            <div class="page-container">
                <!-- BEGIN CONTENT -->
                <div class="page-content-wrapper">
                    <!-- BEGIN CONTENT BODY -->
                    <div class="page-content">
                        <!-- BEGIN PAGE HEADER-->
                        <!-- BEGIN THEME PANEL -->
                         <jsp:include page="${request.contextPath}/webApp/layout_config.jsp"></jsp:include>
                        <!-- END THEME PANEL -->
                        
                        <!-- BEGIN PAGE BAR -->
                        <div class="page-bar">
                            <ul class="page-breadcrumb">
                                <li>
                                    <h3>${menuActual} / <small>${paginaActual}</small></h3>
<!--                                     <i class="fa fa-circle"></i> -->
                                </li>
<!--                                 <li> -->
<!--                                     <span>...</span> -->
<!--                                 </li> -->
                            </ul>
                             <div class="page-toolbar">
                                <div id="dashboard-report-range" class="pull-right tooltips btn btn-sm" data-container="body" data-placement="bottom" data-original-title="Change dashboard date range">
                                    
                             <h5 id="reloj" style="display: none;"></h5>
                                </div>
                            </div> 
                        </div>
                        <!-- END PAGE BAR -->
                        
                        <!-- BEGIN PAGE TITLE-->
<!--                           <h1 class="page-title"> Dashboard -->
<!--                             <small>dashboard & statistics</small> -->
<!--                         </h1> -->
                        <!-- END PAGE TITLE-->
                        
                        <!-- END PAGE HEADER-->
                        <!-- BEGIN DASHBOARD STATS 1-->
                        <jsp:include page="${request.contextPath}/webApp/content/${content}.jsp"></jsp:include>
                    <!-- END CONTENT BODY -->
                </div>
                <!-- END CONTENT -->
                
            </div>
            <!-- END CONTAINER -->
            <!-- BEGIN FOOTER -->
            <div class="page-footer">
                <div class="page-footer-inner"> 2018 &copy; Goplicity
                    
                </div>
                <div class="scroll-to-top">
                    <i class="icon-arrow-up"></i>
                </div>
            </div>
            <!-- END FOOTER -->
        </div>
       </div>
       	<article id="loading" class="loading_dos" style="display: none;">
			<div id="modal" class="modal" style="display: block;"></div>
		</article>
        <!--[if lt IE 9]>
<script src="../assets/global/plugins/respond.min.js"></script>
<script src="../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
        <script src="../assets/global/scripts/jquery-3.0.0.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        
        
        <script src="../assets/global/plugins/fileinput.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        
        <script src="../assets/global/plugins/ExportTable/src/jquery.table2excel.js" type="text/javascript"></script>
        
        <script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
        
        <script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/datatables/dataTables.fixedColumns.min.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/datatables/fixedColumns.dataTables.min.css" type="text/javascript"></script>
        <script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <script src="../assets/global/scripts/jquery-ui.js"></script>

		<script src="../assets/global/plugins/TableFilter/tablefilter.js"></script>
		<script src="../assets/global/plugins/sweetalert-master/dist/sweetalert.min.js"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="../assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="../assets/script/appConfig.js?id=${idRandom}" type="text/javascript"></script>
        <%if(session.getAttribute("perfil").equals("work") || session.getAttribute("perfil").equals("admin")){%>	
		
			
			
		<%}%>
		
     	<script src="../assets/script/${javaScriptPage}.js?id=${idRandom}" type="text/javascript"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
       <!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script> -->
        
        <script type="text/javascript" src="../assets/global/scripts/bootstrap-datetimepicker.js"></script>
        <script src="../assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="../assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="../assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
        <script src="../assets/global/scripts/html2canvas.js" type="text/javascript"></script>
        
        <script src="../assets/global/scripts/calendario.js"></script>
        <script src="../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
        <script src="../assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.js" type="text/javascript"></script>
<!-- 		Sweet Alert 2 -->
		<script src="../assets/global/scripts/ttw-notification-menu.js"></script>
		<script src="../assets/global/scripts/sweet-alert2.js"></script>
		<%if(session.getAttribute("perfil").equals("work") || session.getAttribute("perfil").equals("admin")){%>	
			<script src="../assets/global/scripts/notificacionWork.js"></script>
			
		<%}else{%>
			<script src="../assets/global/scripts/notifyAgro.js"></script>
		<%}%>
		<script src="../assets/global/scripts/core2.4.1.js"></script>
		<script src="../assets/global/plugins/select2/js/select2.full.min.js"></script>
	
		<script src="../assets/global/plugins/excelexportjs.js"></script>
		<script src="../assets/global/plugins/paginador.js"></script>
		<script src="../assets/global/scripts/toastr.js"></script>
		<script src="../assets/global/plugins/ion.rangeslider/js/ion.rangeSlider.js"></script>
		<script src="../assets/global/dist/handsontable.full.min.js"></script>

        <script>
       
	
        </script>
    </body>
</html>