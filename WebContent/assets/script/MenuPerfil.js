//Colocar Permisos
var sociedadPrivilege = new Set();
var huertoPrivilege = new Array();
// Obtener Sociedades y Huertos que tiene acceso
Array_privilegios_ver = [];
Array_privilegios_editar = [];
Array_privilegios_eliminar = [];
$(document).ready(function() {
	$("#loading").hide();
	ArrayEnviar = [];
	ArrayCamposUsuario = [];

	ListaSociedad();
	lodtrab();

	var $TreeV = $('#treeview').treeview({

		onhoverColor : "#70cddd",
	});
    
	$('#selectHuerto').selectpicker({title: 'Seleccionar'});
	$('#Sociedad').selectpicker({title: 'Seleccionar'});
	$(".selectpicker").selectpicker("refresh");

	// Check/uncheck all
	$('#btn-check-all').on('click', function(e) {
		$TreeV.treeview('checkAll', {
			silent : $('#treeview').is(':checked')
		});recorrer();
	});

	$('#btn-uncheck-all').on('click', function(e) {
		$TreeV.treeview('uncheckAll', {
			silent : $('#treeview').is(':checked')
		});recorrer();
	});

	var $TreeV2 = $('#treeview2').treeview({
		onhoverColor : "#70cddd",
	// selectedBackColor: "#183924"
	});

	// Check/uncheck all
	$('#btn-check-all').on('click', function(e) {
		$TreeV2.treeview('checkAll', {
			silent : $('#treeview2').is(':checked')
		});recorrer();
	});

	$('#btn-uncheck-all').on('click', function(e) {
		$TreeV2.treeview('uncheckAll', {
			silent : $('#treeview2').is(':checked')
		});recorrer();
	});

});

function buscarRRHH() {}

function buscarSimpleAgro() {

	$("#loading").show();
	datosEnviar = [];
	var json2 = {

		idmenu : 1

	}

	datosEnviar.push(json2);

	var perfil_ = $("#CodigoTra").val();

	$
			.ajax(
					{
						url : "/recotec/json/work/buscarmenuyperfilesAgro/"
								+ perfil_,
						type : "PUT",
						dataType : "text",
						data : JSON.stringify(datosEnviar),
						beforeSend : function(xhr) {
							xhr.setRequestHeader("Accept", "application/json");
							xhr.setRequestHeader("Content-Type",
									"application/json");
							$("#loading").show();

						},
						success : function(data, textStatus, jqXHR) {

							var tree = JSON.parse(data)

							var $TreeV = $('#treeview2')
									.treeview(
											{
												data : tree,
												showIcon : true,
												showCheckbox : true,
												showBorder : false,
												showTags : true,
												onhoverColor : "#70cddd",
												// selectedBackColor: "#183924",
												onNodeDisabled : function(
														event, node) {
													recorrer();
												},
												onNodeEnabled : function(event,
														node) {
													recorrer();
												},
												onNodeCollapsed : function(
														event, node) {
													recorrer();
												},
												onNodeUnchecked : function(
														event, node) {
													recorrer();
												},
												onNodeUnselected : function(
														event, node) {
													recorrer();
												},
												onNodeChecked : function(event,
														node) {
													recorrer();
													if (node.nodes) {

														for (var i = 0; i < node.nodes.length; i++) {

															$('#treeview2')
																	.treeview(
																			'checkNode',
																			[
																					node.nodes[i].nodeId,
																					{
																						silent : true
																					} ]);
															var texto2 = node.nodes[i].text
																	.split(" ");
															for (var e = 0; e < ArrayEnviar.length; e++) {

																if (ArrayEnviar[e].node == node.nodes[i].nodeId) {
																	ArrayEnviar
																			.splice(
																					e,
																					1);

																}
															}

															var datos2 = {
																node : node.nodes[i].nodeId,
																menu : node.nodes[i].text,
																checked : 1,
																idmenu : parseInt(texto2[2]),
																idperfil : $(
																		"#CodigoTra")
																		.val() * 1

															}
															ArrayEnviar.push(datos2)

															// si el menu
															// seleccionado
															// tiene 1 nodos mas
															if (node.nodes[i].nodes) {
																for (var x = 0; x < node.nodes[i].nodes.length; x++) {

																	$(
																			'#treeview2')
																			.treeview(
																					'checkNode',
																					[
																							node.nodes[i].nodes[x].nodeId,
																							{
																								silent : true
																							} ]);

																	var texto22 = node.nodes[i].nodes[x].text
																			.split(" ");
																	for (var e2 = 0; e2 < ArrayEnviar.length; e2++) {

																		if (ArrayEnviar[e2].node == node.nodes[i].nodes[x].nodeId) {
																			ArrayEnviar
																					.splice(
																							e2,
																							1);

																		}
																	}

																	var datos_22 = {
																		node : node.nodes[i].nodes[x].nodeId,
																		menu : node.nodes[i].nodes[x].text,
																		checked : 1,
																		idmenu : parseInt(texto22[2]),
																		idperfil : $(
																				"#CodigoTra")
																				.val() * 1

																	}

																	ArrayEnviar
																			.push(datos_22)

																	// si el
																	// menu
																	// seleccionado
																	// tiene 2
																	// nodos mas
																	if (node.nodes[i].nodes[x].nodes) {
																		for (var u = 0; u < node.nodes[i].nodes[x].nodes.length; u++) {
																			$(
																					'#treeview2')
																					.treeview(
																							'checkNode',
																							[
																									node.nodes[i].nodes[x].nodes[u].nodeId,
																									{
																										silent : true
																									} ]);

																			var texto27 = node.nodes[i].nodes[x].nodes[u].text
																					.split(" ");
																			for (var e27 = 0; e27 < ArrayEnviar.length; e27++) {

																				if (ArrayEnviar[e27].node == node.nodes[i].nodes[x].nodes[u].nodeId) {
																					ArrayEnviar
																							.splice(
																									e27,
																									1);

																				}
																			}

																			var datos_27 = {
																				node : node.nodes[i].nodes[x].nodes[u].nodeId,
																				menu : node.nodes[i].nodes[x].nodes[u].text,
																				checked : 1,
																				idmenu : parseInt(texto27[2]),
																				idperfil : $(
																						"#CodigoTra")
																						.val() * 1

																			}
																			ArrayEnviar
																					.push(datos_27)

																		}
																	}

																}
															}
														}

													}

													$('#disabled-output')
															.prepend(
																	'<p>'
																			+ node.text
																			+ ' was checked</p>');
													var texto = node.text
															.split(" ");

													for (var i = 0; i < ArrayEnviar.length; i++) {

														if (ArrayEnviar[i].node == node.nodeId) {
															ArrayEnviar.splice(
																	i, 1);

														}

													}

													var datos = {
														node : node.nodeId,
														menu : node.text,
														checked : 1,
														idmenu : parseInt(texto[2]),
														idperfil : $("#CodigoTra")
																.val() * 1

													}
													ArrayEnviar.push(datos)

													// si el menu tiene id padre
													if (node.parentId
															|| node.parentId == 0) {
														$('#treeview2')
																.treeview(
																		'selectNode',
																		[
																				node.parentId,
																				{
																					silent : false
																				} ]);
														var datosidpadre = $(
																'#treeview2')
																.treeview(
																		'getSelected',
																		node.parentId);

														$('#treeview2')
																.treeview(
																		'checkNode',
																		[
																				datosidpadre[0].nodeId,
																				{
																					silent : true
																				} ]);

														var textoIdpadre = datosidpadre[0].text
																.split(" ");
														for (var i = 0; i < ArrayEnviar.length; i++) {
															if (ArrayEnviar[i].node == datosidpadre[0].nodeId) {
																ArrayEnviar
																		.splice(
																				i,
																				1);

															}
														}

														var datos3 = {
															node : datosidpadre[0].nodeId,
															menu : datosidpadre[0].text,
															checked : 1,
															idmenu : parseInt(textoIdpadre[2]),
															idperfil : $("#CodigoTra").val() * 1

														}
														ArrayEnviar
																.push(datos3)
													}

												},
												// si checkbox disable
												onNodeUnchecked : function(
														event, node) {
													recorrer();
													if (node.nodes) {
														for (var i = 0; i < node.nodes.length; i++) {
															$('#treeview2')
																	.treeview(
																			'uncheckNode',
																			[
																					node.nodes[i].nodeId,
																					{
																						silent : true
																					} ]);
															var texto2 = node.nodes[i].text
																	.split(" ");

															for (var e = 0; e < ArrayEnviar.length; e++) {
																if (ArrayEnviar[e].node == node.nodes[i].nodeId) {
																	ArrayEnviar
																			.splice(
																					e,
																					1);

																}
															}

															var datos2 = {
																node : node.nodes[i].nodeId,
																menu : node.nodes[i].text,
																checked : 0,
																idmenu : parseInt(texto2[2]),
																idperfil : $("#CodigoTra").val() * 1

															}
															ArrayEnviar
																	.push(datos2)

															// si el menu
															// seleccionado
															// tiene mas nodos
															if (node.nodes[i].nodes) {
																for (var x = 0; x < node.nodes[i].nodes.length; x++) {
																	$(
																			'#treeview2')
																			.treeview(
																					'uncheckNode',
																					[
																							node.nodes[i].nodes[x].nodeId,
																							{
																								silent : true
																							} ]);
																	var texto23 = node.nodes[i].nodes[x].text
																			.split(" ");

																	for (var e22 = 0; e22 < ArrayEnviar.length; e22++) {
																		if (ArrayEnviar[e22].node == node.nodes[i].nodes[x].nodeId) {
																			ArrayEnviar
																					.splice(
																							e22,
																							1);
																		}
																	}

																	var datos_23 = {
																		node : node.nodes[i].nodes[x].nodeId,
																		menu : node.nodes[i].nodes[x].text,
																		checked : 0,
																		idmenu : parseInt(texto23[2]),
																		idperfil : $("#CodigoTra").val() * 1
																	}
																	ArrayEnviar
																			.push(datos_23)

																	if (node.nodes[i].nodes[x].nodes) {
																		for (var u = 0; u < node.nodes[i].nodes[x].nodes.length; u++) {
																			$(
																					'#treeview2')
																					.treeview(
																							'uncheckNode',
																							[
																									node.nodes[i].nodes[x].nodes[u].nodeId,
																									{
																										silent : true
																									} ]);

																			var texto26 = node.nodes[i].nodes[x].nodes[u].text
																					.split(" ");
																			for (var e26 = 0; e26 < ArrayEnviar.length; e26++) {
																				if (ArrayEnviar[e26].node == node.nodes[i].nodes[x].nodes[u].nodeId) {
																					ArrayEnviar
																							.splice(
																									e26,
																									1);
																				}
																			}

																			var datos_26 = {
																				node : node.nodes[i].nodes[x].nodes[u].nodeId,
																				menu : node.nodes[i].nodes[x].nodes[u].text,
																				checked : 0,
																				idmenu : parseInt(texto26[2]),
																				idperfil : $("#CodigoTra").val() * 1

																			}
																			ArrayEnviar
																					.push(datos_26)
																		}
																	}
																}
															} // end if
														} // end for
													} // end if(node.nodes)

													var texto = node.text
															.split(" ");
													for (var i = 0; i < ArrayEnviar.length; i++) {

														if (ArrayEnviar[i].node == node.nodeId) {
															ArrayEnviar.splice(
																	i, 1);

														}

													}

													var datos = {
														node : node.nodeId,
														menu : node.text,
														checked : 0,
														idmenu : parseInt(texto[2]),
														idperfil : $("#CodigoTra").val() * 1

													}
													ArrayEnviar.push(datos)

													// si el menu tiene id padre
													if (node.parentId) {

														$('#treeview2')
																.treeview(
																		'selectNode',
																		[
																				node.parentId,
																				{
																					silent : false
																				} ]);
														var datosidpadre2 = $(
																'#treeview2')
																.treeview(
																		'getSelected',
																		node.parentId);

														var existeCheck = 0;
														for (var z = 0; z < datosidpadre2[0].nodes.length; z++) {

															if (datosidpadre2[0].nodes[z].state.checked == true) {
																existeCheck++
															}

														}

														if (existeCheck > 0) {

														} else {
															$('#treeview2')
																	.treeview(
																			'uncheckNode',
																			[
																					datosidpadre2[0].nodeId,
																					{
																						silent : true
																					} ]);

															$('#treeview2')
																	.treeview(
																			'selectNode',
																			[
																					node.parentId,
																					{
																						silent : false
																					} ]);

															var textoIdpadre2 = datosidpadre2[0].text
																	.split(" ");
															for (var i = 0; i < ArrayEnviar.length; i++) {
																if (ArrayEnviar[i].node == datosidpadre2[0].nodeId) {
																	ArrayEnviar
																			.splice(
																					i,
																					1);

																}
															}

															var datos3 = {
																node : datosidpadre2[0].nodeId,
																menu : datosidpadre2[0].text,
																checked : 0,
																idmenu : parseInt(textoIdpadre2[2]),
																idperfil : $("#CodigoTra").val() * 1

															}
															ArrayEnviar
																	.push(datos3)
														}

													}

													var valor_padre = 0;
													var valor_hijos = 0;
													var textopadre_ = "";
													var idme = 0;
													var node_ = 0;

													for (var i2 = 0; i2 < ArrayEnviar.length; i2++) {
														if (ArrayEnviar[i2].checked == 1
																&& ArrayEnviar[i2].node == 0) {
															textopadre_ = ArrayEnviar[i2].menu;
															valor_padre = 1;
															idme = ArrayEnviar[i2].idmenu;
															node_ = ArrayEnviar[i2].node;
														}
														if (ArrayEnviar[i2].checked == 1
																&& ArrayEnviar[i2].node != 0) {
															valor_hijos++;
														}

													}

													// para eliminar el nodo
													// padre si no hay ninguno
													// de sus hijos asignados
													if (valor_padre == 1
															&& valor_hijos == 0) {

														$('#treeview2')
																.treeview(
																		'uncheckNode',
																		[
																				0,
																				{
																					silent : true
																				} ]);

														for (var i3 = 0; i3 < ArrayEnviar.length; i3++) {
															if (ArrayEnviar[i3].node == 0) {
																ArrayEnviar
																		.splice(
																				i3,
																				1);

															}
														}

														var textoIdpadre22 = textopadre_
																.split(" ");
														var datos344 = {
															node : node_,
															menu : textopadre_,
															checked : 0,
															idmenu : parseInt(textoIdpadre22[2]),
															idperfil : $("#CodigoTra").val() * 1

														}
														ArrayEnviar
																.push(datos344)

													}

												} // end onNodeUnchecked:

											});

							$("#loading").hide();
						},
						error : function(ex) {
							console.log(ex);
						}
					}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(errorThrown);
				alerta(errorThrown);
				$("#loading").hide();
			})

}

function buscar() {

	var perfil_ = $("#CodigoTra").val();

	if (perfil_ == "") {
		alerta("Debe Seleccionar un Usuario");
		return;
		return;

	}
	ArrayEnviar = []
	$("#treeview").empty();
	$("#treeview2").empty();
	buscarSimpleAgro();
	buscarRRHH()
}

function Enviar() {

		ArrayHuertoPerfil = [];
		ArrayEmpresacheck = [];
		$('#selectHuerto option').each(function() {
		    if($(this).is(':selected')){
		    	 var datohuerto = {
							checked : 1,
							huerto : $(this).val()
						 }
		    	 ArrayHuertoPerfil.push(datohuerto)
		    }
		     else{
		    	 var datohuerto = {
							checked : 0,
							huerto : $(this).val()
						 }
		    	 ArrayHuertoPerfil.push(datohuerto)
		     }
		       
		});
		
		$('#Sociedad option').each(function() {
		    if($(this).is(':selected')){
		    	 var datohuerto = {
							checked : 1,
							idsociedad : $(this).val()
						 }
		    	 ArrayEmpresacheck.push(datohuerto)
		    }
		     else{
		    	 var datohuerto = {
							checked : 0,
							idsociedad : $(this).val()
						 }
		    	 ArrayEmpresacheck.push(datohuerto)
		     }
		       
		});
		
		
		
		
		$("#loading").show();
		
		 var rolPrivado = 0;
		 if($("#defaultUnchecked").is(':checked')) {  
			 rolPrivado = 1;
	        } else {  
	        	rolPrivado = 0;
	        }  
		 
		 
		 ArrayHuertoPerfil2 = []
		 console.log(ArrayEnviar);
		 console.log(Array_privilegios_ver);
		 var datohuerto2 = {
					array1 : ArrayEnviar,
					array2 : ArrayHuertoPerfil,
					array3 : ArrayEmpresacheck,
					array4 : Array_privilegios_ver,
					array5 : Array_privilegios_editar,
					array6 : Array_privilegios_eliminar
				 }
		 ArrayHuertoPerfil2.push(datohuerto2)
		
		 var perfil_ = $("#CodigoTra").val();
		
		$.ajax({
			url : "/recotec/json/work/actualizarMenuPerfil/"+rolPrivado+","+perfil_+"",
			type : "POST",
		    data: JSON.stringify(ArrayHuertoPerfil2),
		
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
				$("#loading").show();

			},
			success : function(data, textStatus, jqXHR) {
				Array_privilegios_ver = [];
				Array_privilegios_editar = [];
				Array_privilegios_eliminar = [];
				buscar();
				alerta("Enviado")
				$("#loading").hide();

			},
			error : function(ex) {
				console.log(ex);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {

			alerta(errorThrown);
			$("#loading").hide();
		})
	
}

function lodtrab() {

	$("#CodigoTra").empty();

	$.ajax({
		url: "/recotec/json/REC/GET_PERFIL",
		type:	"GET",
		dataType: 'json',
		async: false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept","application/json");
			xhr.setRequestHeader("Content-Type","application/json");
		},
		success: function(data){
			var option = "<option value=''></option>";
			$.each(data, function(k,v){
				option += "<option value='"+v.IDPERFIL+"'>"+v.NOMBRE+"</option>";
			})
			$("#CodigoTra").html(option);
		}
	})
}

function ListaSociedad() {
	$("#loading").show();
	// Obtener Sociedades en base a los privilegios del usuario
	var queryString = sociedadPrivilege == null ? "" : JSON.stringify(
			sociedadPrivilege).slice(1, -1);

	$.getJSON(
			"/recotec/json/work/getSociedad/?idSociedad=" + queryString,
			function(data) {

				$.each(data, function(k, v) {
					var SelectSociedad = "";
					if (v.idSociedad == -1) {

					} else {
						
						$("#Sociedad").append(new Option("" +v.sociedad , "" +v.idSociedad , true, false));
	                    $(".selectpicker").selectpicker("refresh");
					}
				})
			}).fail(function(jqXHR, textStatus, errorThrown) {

		alerta(errorThrown);
		$("#loading").hide();
	}).done(function() {
		$("#loading").hide();
	});

}

$('#CodigoTra').change(function(e) {
	
	Array_privilegios_ver = [];
	Array_privilegios_editar = [];
	Array_privilegios_eliminar = [];
	$('#Sociedad').selectpicker('deselectAll');
	$("#Sociedad").selectpicker("refresh");
	var idUsuario = $("#CodigoTra").val();
	
	$.getJSON(
			"/recotec/json/work/getRolUsuario/"+idUsuario,
			function(data) {

				$.each(data, function(k, v) {
					if(v.checked == 1){
						$( "#defaultUnchecked" ).prop( "checked", true );
					}else{
						$( "#defaultUnchecked" ).prop( "checked", false );
					}
				})
			}).fail(function(jqXHR, textStatus, errorThrown) {

		alerta(errorThrown);
		$("#loading").hide();
	}).done(function() {
		$("#loading").hide();
	});
	
	buscar();
	allCampoxUsuario();
});

$("#Sociedad").on("changed.bs.select", function(e, clickedIndex, newValue, oldValue) {
	
	  if($("#CodigoTra").val() == ""){
		  alerta("Debe Seleccionar un usuario");$("#CodigoTra").focus();return;
	  }
	
	   var sel = $(this).find('option').eq(clickedIndex).val();
	   console.log(sel+" "+newValue);
	   
	   if(newValue == true){
		   var b = sel;
		    if ("" == b) {
		        alerta("Debe Seleccionar una Empresa");
		        return;
		    }
		    var c = "";
		    $("#loading").show();
		    $.getJSON("/recotec/json/work/getSociedadById/" + b, function(a) {
		        c = JSON.stringify(a.sociedad);
		    }).done(function() {
		        $("#tipodivisionB").append("<option value='-1'>Seleccione Huerto</option>");
		        var a = c.replace(/\"/g, "");
		        $.getJSON("/recotec/json/work/getCampoBySociedad/" + b, function(a) {
		            queryString = null == huertoPrivilege ? "" : JSON.stringify(huertoPrivilege).slice(1, -1);
		            $.each(a, function(a, b) {
		                var c = "";
		                if (true == huertoPrivilege.includes(b.campo)) {
		                    $("#selectHuerto").append(new Option("" + b.descripcion, "" + b.campo, true, false));
		                    $(".selectpicker").selectpicker("refresh");
		                }
		            });
		        }).done(function() {
		            $("#loading").hide();
		        });
		    });
		    
		   
		   
	   }else{
		   
		   var b = sel;
		    if ("" == b) {
		        alerta("Debe Seleccionar una Empresa");
		        return;
		    }
		    var c = "";
		    $("#loading").show();
		    $.getJSON("/recotec/json/work/getSociedadById/" + b, function(a) {
		        c = JSON.stringify(a.sociedad);
		    }).done(function() {
		        $("#tipodivisionB").append("<option value='-1'>Seleccione Huerto</option>");
		        var a = c.replace(/\"/g, "");
		        $.getJSON("/recotec/json/work/getCampoBySociedad/" + b, function(a) {
		            queryString = null == huertoPrivilege ? "" : JSON.stringify(huertoPrivilege).slice(1, -1);
		            $.each(a, function(a, b) {
		                var c = "";
		                if (true == huertoPrivilege.includes(b.campo)) {
		                    
		                    $('#selectHuerto').find('[value='+b.campo+']').remove()
		         		   $(".selectpicker").selectpicker("refresh");
		                }
		            });
		        }).done(function() {
		            $("#loading").hide();
		        });
		    });
	   }
	   
	   setTimeout(cargarcampo, 2000);
	});

function allCampoxUsuario(){
	
	var idUsuario = $("#CodigoTra").val();
	if(idUsuario == ""){
		
	}else{
		ArrayCamposUsuario = [];
		ArrayempresaUsuario = [];
	$("#loading").show();
	$.getJSON(
			"/recotec/json/work/allCampoxUsuario/"+idUsuario,
			function(data) {

				$.each(data, function(k, v) {
					ArrayCamposUsuario.push(v.huerto);
					ArrayempresaUsuario.push(v.idsociedad);
				})
			}).fail(function(jqXHR, textStatus, errorThrown) {

		alerta(errorThrown);
		$("#loading").hide();
	}).done(function() {
		$("#loading").hide();
		cargarempresa();
	});
	
}
	
}

function cargarempresa(){

	
	console.log("///////////////////////////////");
	
	var unique = ArrayempresaUsuario.filter( onlyUnique );
	console.log(unique);
	console.log("///////////////////////////////");
	
	for (var i = 0; i < unique.length; i++) 
	{
    	 $("#Sociedad option[value='"+unique[i]+"']").prop("selected", true);
    	 console.log(unique[i]);
    	 getEmpresa(unique[i]);
    	 $(".selectpicker").selectpicker("refresh");
	}
}

function cargarcampo(){
	
	
	
	
	
	for (var i = 0; i < ArrayCamposUsuario.length; i++) 
	{
    	 $("#selectHuerto option[value='"+ArrayCamposUsuario[i]+"']").prop("selected", true);
    	 $(".selectpicker").selectpicker("refresh");
	}
}

function onlyUnique(value, index, self) { 
    return self.indexOf(value) === index;
}

function getEmpresa(empresan){
	
	   
	 
		   var b = empresan;
		    
		    var c = "";
		    $("#loading").show();
		    $.getJSON("/recotec/json/work/getSociedadById/" + b, function(a) {
		        c = JSON.stringify(a.sociedad);
		    }).done(function() {
		        $("#tipodivisionB").append("<option value='-1'>Seleccione Huerto</option>");
		        var a = c.replace(/\"/g, "");
		        $.getJSON("/recotec/json/work/getCampoBySociedad/" + b, function(a) {
		            queryString = null == huertoPrivilege ? "" : JSON.stringify(huertoPrivilege).slice(1, -1);
		            $.each(a, function(a, b) {
		                var c = "";
		                if (true == huertoPrivilege.includes(b.campo)) {
		                    $("#selectHuerto").append(new Option("" + b.descripcion, "" + b.campo, true, false));
		                    $(".selectpicker").selectpicker("refresh");
		                }
		            });
		        }).done(function() {
		            $("#loading").hide();
		        });
		    });
		    
		   
		   
	   
	   
	   setTimeout(cargarcampo, 2000);
}

function ver(idmenu,estado){
	setTimeout(function(){ 
		
	if(estado == 0){
		

		
		for (var i = 0; i < Array_privilegios_ver.length; i++) {

			if (Array_privilegios_ver[i].idmenu == idmenu && Array_privilegios_ver[i].codigotrabajador == $("#CodigoTra").val()) {

				Array_privilegios_ver.splice(i, 1);
			}
		}
		
			var datos = {
				idmenu : idmenu,
				estado : 1,
				codigotrabajador : $("#CodigoTra").val(),
				
			}

			Array_privilegios_ver.push(datos);
		
	}else{

		
		for (var i = 0; i < Array_privilegios_ver.length; i++) {

			if (Array_privilegios_ver[i].idmenu == idmenu && Array_privilegios_ver[i].codigotrabajador == $("#CodigoTra").val()) {

				Array_privilegios_ver.splice(i, 1);
			}
		}
		var datos = {
				idmenu : idmenu,
				estado : 0,
				codigotrabajador : $("#CodigoTra").val(),
				
			}

		Array_privilegios_ver.push(datos);
		
	}
		
	for (var i = 0; i < Array_privilegios_ver.length; i++) {

		if (Array_privilegios_ver[i].estado == 0) {
			$("#ver"+Array_privilegios_ver[i].idmenu +"").attr("onclick","ver("+Array_privilegios_ver[i].idmenu +",0)");
			$("#ver"+Array_privilegios_ver[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#ver"+Array_privilegios_ver[i].idmenu +"").attr("onclick","ver("+Array_privilegios_ver[i].idmenu +",1)");
			$("#ver"+Array_privilegios_ver[i].idmenu +"").css({ 'color' : 'blue', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}
	for (var i = 0; i < Array_privilegios_editar.length; i++) {

		if (Array_privilegios_editar[i].estado == 0) {
			$("#editar"+Array_privilegios_editar[i].idmenu +"").attr("onclick","editar("+Array_privilegios_editar[i].idmenu +",0)");
			$("#editar"+Array_privilegios_editar[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#editar"+Array_privilegios_editar[i].idmenu +"").attr("onclick","editar("+Array_privilegios_editar[i].idmenu +",1)");
			$("#editar"+Array_privilegios_editar[i].idmenu +"").css({ 'color' : 'brown', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}
	for (var i = 0; i < Array_privilegios_eliminar.length; i++) {

		if (Array_privilegios_eliminar[i].estado == 0) {
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").attr("onclick","eliminar("+Array_privilegios_eliminar[i].idmenu +",0)");
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").attr("onclick","eliminar("+Array_privilegios_eliminar[i].idmenu +",1)");
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").css({ 'color' : 'red', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}
		console.log(Array_privilegios_ver);
		
		
		
	}, 100);
	
}

function editar(idmenu,estado){
	setTimeout(function(){ 
		
	if(estado == 0){
		
		
		for (var i = 0; i < Array_privilegios_editar.length; i++) {

			if (Array_privilegios_editar[i].idmenu == idmenu && Array_privilegios_editar[i].codigotrabajador == $("#CodigoTra").val()) {

				Array_privilegios_editar.splice(i, 1);
			}
		}
		
			var datos = {
				idmenu : idmenu,
				estado : 1,
				codigotrabajador : $("#CodigoTra").val(),
				
			}

			Array_privilegios_editar.push(datos);
		
	}else{
		
		for (var i = 0; i < Array_privilegios_editar.length; i++) {

			if (Array_privilegios_editar[i].idmenu == idmenu && Array_privilegios_editar[i].codigotrabajador == $("#CodigoTra").val()) {

				Array_privilegios_editar.splice(i, 1);
			}
		}
		var datos = {
				idmenu : idmenu,
				estado : 0,
				codigotrabajador : $("#CodigoTra").val(),
				
			}

		Array_privilegios_editar.push(datos);
		
	}
		
		console.log(Array_privilegios_editar);
		
		
		for (var i = 0; i < Array_privilegios_ver.length; i++) {

			if (Array_privilegios_ver[i].estado == 0) {
				$("#ver"+Array_privilegios_ver[i].idmenu +"").attr("onclick","ver("+Array_privilegios_ver[i].idmenu +",0)");
				$("#ver"+Array_privilegios_ver[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
			}else{
				$("#ver"+Array_privilegios_ver[i].idmenu +"").attr("onclick","ver("+Array_privilegios_ver[i].idmenu +",1)");
				$("#ver"+Array_privilegios_ver[i].idmenu +"").css({ 'color' : 'blue', 'z-index' : '100000', 'font-size' : '20px' });
			}
		}
		for (var i = 0; i < Array_privilegios_editar.length; i++) {

			if (Array_privilegios_editar[i].estado == 0) {
				$("#editar"+Array_privilegios_editar[i].idmenu +"").attr("onclick","editar("+Array_privilegios_editar[i].idmenu +",0)");
				$("#editar"+Array_privilegios_editar[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
			}else{
				$("#editar"+Array_privilegios_editar[i].idmenu +"").attr("onclick","editar("+Array_privilegios_editar[i].idmenu +",1)");
				$("#editar"+Array_privilegios_editar[i].idmenu +"").css({ 'color' : 'brown', 'z-index' : '100000', 'font-size' : '20px' });
			}
		}
		for (var i = 0; i < Array_privilegios_eliminar.length; i++) {

			if (Array_privilegios_eliminar[i].estado == 0) {
				$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").attr("onclick","eliminar("+Array_privilegios_eliminar[i].idmenu +",0)");
				$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
			}else{
				$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").attr("onclick","eliminar("+Array_privilegios_eliminar[i].idmenu +",1)");
				$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").css({ 'color' : 'red', 'z-index' : '100000', 'font-size' : '20px' });
			}
		}
		
		
	}, 100);
	
}

function eliminar(idmenu,estado){
	setTimeout(function(){ 
		
	if(estado == 0){
		
		
		for (var i = 0; i < Array_privilegios_eliminar.length; i++) {

			if (Array_privilegios_eliminar[i].idmenu == idmenu && Array_privilegios_eliminar[i].codigotrabajador == $("#CodigoTra").val()) {

				Array_privilegios_eliminar.splice(i, 1);
			}
		}
		
			var datos = {
				idmenu : idmenu,
				estado : 1,
				codigotrabajador : $("#CodigoTra").val(),
				
			}

			Array_privilegios_eliminar.push(datos);
		
	}else{
		
		for (var i = 0; i < Array_privilegios_eliminar.length; i++) {

			if (Array_privilegios_eliminar[i].idmenu == idmenu && Array_privilegios_eliminar[i].codigotrabajador == $("#CodigoTra").val()) {

				Array_privilegios_eliminar.splice(i, 1);
			}
		}
		var datos = {
				idmenu : idmenu,
				estado : 0,
				codigotrabajador : $("#CodigoTra").val(),
				
			}

		Array_privilegios_eliminar.push(datos);
		
	}
	for (var i = 0; i < Array_privilegios_ver.length; i++) {

		if (Array_privilegios_ver[i].estado == 0) {
			$("#ver"+Array_privilegios_ver[i].idmenu +"").attr("onclick","ver("+Array_privilegios_ver[i].idmenu +",0)");
			$("#ver"+Array_privilegios_ver[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#ver"+Array_privilegios_ver[i].idmenu +"").attr("onclick","ver("+Array_privilegios_ver[i].idmenu +",1)");
			$("#ver"+Array_privilegios_ver[i].idmenu +"").css({ 'color' : 'blue', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}
	for (var i = 0; i < Array_privilegios_editar.length; i++) {

		if (Array_privilegios_editar[i].estado == 0) {
			$("#editar"+Array_privilegios_editar[i].idmenu +"").attr("onclick","editar("+Array_privilegios_editar[i].idmenu +",0)");
			$("#editar"+Array_privilegios_editar[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#editar"+Array_privilegios_editar[i].idmenu +"").attr("onclick","editar("+Array_privilegios_editar[i].idmenu +",1)");
			$("#editar"+Array_privilegios_editar[i].idmenu +"").css({ 'color' : 'brown', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}
	for (var i = 0; i < Array_privilegios_eliminar.length; i++) {

		if (Array_privilegios_eliminar[i].estado == 0) {
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").attr("onclick","eliminar("+Array_privilegios_eliminar[i].idmenu +",0)");
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").attr("onclick","eliminar("+Array_privilegios_eliminar[i].idmenu +",1)");
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").css({ 'color' : 'red', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}
		console.log(Array_privilegios_eliminar);
		
	}, 100);
	
}

function recorrer(){
	for (var i = 0; i < Array_privilegios_ver.length; i++) {

		if (Array_privilegios_ver[i].estado == 0) {
			$("#ver"+Array_privilegios_ver[i].idmenu +"").attr("onclick","ver("+Array_privilegios_ver[i].idmenu +",0)");
			$("#ver"+Array_privilegios_ver[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#ver"+Array_privilegios_ver[i].idmenu +"").attr("onclick","ver("+Array_privilegios_ver[i].idmenu +",1)");
			$("#ver"+Array_privilegios_ver[i].idmenu +"").css({ 'color' : 'blue', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}
	for (var i = 0; i < Array_privilegios_editar.length; i++) {

		if (Array_privilegios_editar[i].estado == 0) {
			$("#editar"+Array_privilegios_editar[i].idmenu +"").attr("onclick","editar("+Array_privilegios_editar[i].idmenu +",0)");
			$("#editar"+Array_privilegios_editar[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#editar"+Array_privilegios_editar[i].idmenu +"").attr("onclick","editar("+Array_privilegios_editar[i].idmenu +",1)");
			$("#editar"+Array_privilegios_editar[i].idmenu +"").css({ 'color' : 'brown', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}
	for (var i = 0; i < Array_privilegios_eliminar.length; i++) {

		if (Array_privilegios_eliminar[i].estado == 0) {
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").attr("onclick","eliminar("+Array_privilegios_eliminar[i].idmenu +",0)");
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").css({ 'color' : 'grey', 'z-index' : '100000', 'font-size' : '20px' });
		}else{
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").attr("onclick","eliminar("+Array_privilegios_eliminar[i].idmenu +",1)");
			$("#eliminar"+Array_privilegios_eliminar[i].idmenu +"").css({ 'color' : 'red', 'z-index' : '100000', 'font-size' : '20px' });
		}
	}

	
}
	
$(".list-group-item node-treeview node-checked").change(function(){
	recorrer();
	});
$(".list-group-item node-treeview node-checked node-selected").change(function(){
	recorrer();
	});

$(".list-group-item").click(function(){
	alerta("");
	});
