class CRUD {
	constructor(params){
	this.params=params;
	
	}
	createFromParams()
	{
		var object=new Object();
			object.codigo=params.Formularios.Create.codigo;
			object.descripcion=$(params.Formularios.Create.tagNombre).val();
			
			if(params.hasRutParametro)
  			{
  				object.rutParametro=$(params.Formularios.Create.tagRutParametro).val();
  			}
  			if(params.hasCodPrevired)
  			{
  				object.codPrevired=$(params.Formularios.Create.tagCodPrevired).val();
  			}
  			if(params.hasCodSap){
  				object.codSap=$(params.Formularios.Create.tagCodSap).val();
  			}
			
			$.ajax({
  		  	    type: "POST",
  		  	    async: false,
  		  	    url: "../../simpleWeb/json/work/compareParams/",
  		  	    data: JSON.stringify(object),
  		  	    beforeSend: function(xhr) {
  		  	      xhr.setRequestHeader("Accept", "application/json");
  		  	      xhr.setRequestHeader("Content-Type", "application/json");
  		  	    },
  		  	    success: function(data) {
  		  	    	if(data==true){
  		  	    	$.ajax({
  		  	        type: "PUT",
  		  	        async: false,
  		  	        url: params.Create.url,
  		  	        data: JSON.stringify(object),
  		  	        beforeSend: function(xhr) {
  		  	          xhr.setRequestHeader("Accept", "application/json");
  		  	          xhr.setRequestHeader("Content-Type", "application/json");
  		  	        },
  		  	        success: function(data) {
  		  	        	alerta(params.Create.Text);
  		  	        	location.reload();
  		  	        },
  		  	        error: function(ex) {

  		  	          swal({
  		  	            title: '<i>ERROR</i>',
  		  	            type: 'info',
  		  	            html: JSON.stringify(ex),
  		  	            showCloseButton: true,
  		  	            showCancelButton: true,
  		  	            focusConfirm: false,
  		  	            confirmButtonText:
  		  	              '<i class="fa fa-thumbs-up"></i>OK!',
  		  	            confirmButtonAriaLabel: 'Thumbs up, great!',
  		  	            cancelButtonText:
  		  	            '<i class="fa fa-thumbs-down"></i>',
  		  	            cancelButtonAriaLabel: 'Thumbs down',
  		  	          });
  		  	          
  		  	        }
  		  	      });
  		  	    }
  		  	    else
  		  	    {  	    	
  		  	    	alerta("Parametros duplicados para rut o código");
  		  	    }
  		  	    },
  		  	    error: function(ex) {
  		  	      swal({
  		          title: '<i>ERROR</i>',
  		          type: 'info',
  		          html:
  		          JSON.stringify(ex),
  		          showCloseButton: true,
  		          showCancelButton: true,
  		          focusConfirm: false,
  		          confirmButtonText:
  		            '<i class="fa fa-thumbs-up"></i>OK!',
  		          confirmButtonAriaLabel: 'Thumbs up, great!',
  		          cancelButtonText:
  		          '<i class="fa fa-thumbs-down"></i>',
  		          cancelButtonAriaLabel: 'Thumbs down',
  		        });
  		  	    }
  		  	  });
		
	}
	readFromParams()
	{
		var table;	
		 if ( $.fn.dataTable.isDataTable(params.tableTag) ) {
		    table = $(params.tableTag).DataTable();
		    table.clear();
		}
		else {
		    table = $(params.tableTag).DataTable( 
		    	{
		    		searching: false,
		    		paging:true,
		    		info: false,
		    		search: false
		    	}
		    )	    
		}
		  $.ajax({
			  type: "GET",
		      async: false,
			  dataType: "json",	  
			  url: params.Read.url,	  
			  processData: false,
			  contentType: false,
			  success:function (data){
				  $.each(data, function(k, v) {
				    	var acciones = ""
				   		acciones +=		"<button title='Actualizar' id='"+k+"' onclick=\"javascript:modificarMant('" +v.llave + "')\" class='btn btn-circle green btn-outline btn-sm col-md-4 margin-left-7'><i class='fa fa-pencil-square-o fa-lg'></i></button>";
				   	 	acciones +=		"<button title='Borrar' id='" +k+"' onclick=\"javascript:borrarMant("+v.llave+")\" class='btn btn-circle red btn-outline btn-sm col-md-4'><i class='fa fa-trash-o fa-lg'></i></button>";		
				   	 
				   	 	
				   	 	if(params.hasCodPrevired && params.hasRutParametro){
				   	 		table.row.add([  k+1, v.descripcion, v.rutParametro, v.codPrevired, acciones]);
				   	 		
				   	 	}
				   	 	else if(params.hasCodPrevired && !params.hasRutParametro){
				   	 		table.row.add([  k+1, v.descripcion, v.codPrevired, acciones]);
				   	 		
				   	 	}
				   	 	else if(!params.hasCodPrevired && params.hasRutParametro){
				   	 		table.row.add([  k+1, v.descripcion, v.rutParametro, acciones]);
				   	 		
				   	 	}
				   	 	else{
				   	 		table.row.add([  k+1, v.descripcion, acciones]);
				   	 	}
				   	 	
				   	 	
				   	 	
				    	
				  	});
				    table.draw();
				    $("#loading").hide();
			  },
			  error:function (ex){
				  swal({
			            title: '<i>ERROR</i>',
			            type: 'info',
			            html: JSON.stringify(ex),
			            showCloseButton: true,
			            showCancelButton: true,
			            focusConfirm: false,
			            confirmButtonText:
			              '<i class="fa fa-thumbs-up"></i>OK!',
			            confirmButtonAriaLabel: 'Thumbs up, great!',
			            cancelButtonText:
			            '<i class="fa fa-thumbs-down"></i>',
			            cancelButtonAriaLabel: 'Thumbs down',
			          });
			  } 
			});
	}
	AbrirCerrarModalCreate()
	{
		$(params.Modals.insertModalTag).modal("toggle");
	}
	AbrirCerrarModalUpdate()
	{
		$(params.Modals.updateModalTag).modal("toggle");
	}
	updateFromParams()
	{
		var id = $(params.Formularios.Update.id).val();
					
		  			var object=new Object();
		  			object.llave=$(params.Formularios.Update.id).val();
		  			object.descripcion=$(params.Formularios.Update.tagNombre).val();
		  			object.codigo=params.Formularios.Update.codigo;
		  			if(params.hasRutParametro)
		  			{
		  				object.rutParametro=$(params.Formularios.Update.tagRutParametro).val();
		  			}
		  			if(params.hasCodPrevired)
		  			{
		  				object.codPrevired=$(params.Formularios.Update.tagCodPrevired).val();
		  			}
		  			if(params.hasCodSap)
		  			{
		  				object.codSap=$(params.Formularios.Update.tagCodSap).val();
		  			}
		  			
		  			$.ajax({
		  		  	    type: "POST",
		  		  	    async: false,
		  		  	    url: "../../simpleWeb/json/work/compareParams/",
		  		  	    data: JSON.stringify(object),
		  		  	    beforeSend: function(xhr) {
		  		  	      xhr.setRequestHeader("Accept", "application/json");
		  		  	      xhr.setRequestHeader("Content-Type", "application/json");
		  		  	    },
		  		  	    success: function(data) {
		  		  	    	if(data==true){
		  		  	    	$.ajax({
					    	    type: "POST",
					    	    async: false,
					    	    url: params.Update.url,
					    	    data: JSON.stringify(object),
					    	    beforeSend: function(xhr) {
					    	      xhr.setRequestHeader("Accept", "application/json");
					    	      xhr.setRequestHeader("Content-Type", "application/json");
					    	    },
					    	    success: function(data) {
					    	    	$(params.Modals.updateModalTag).modal("toggle");
					    	    	alerta(params.Formularios.Update.Text);
					    	    	location.reload();
					    	    },
					    	    error: function(ex) {
					    	      swal({
				                title: '<i>ERROR</i>',
				                type: 'info',
				                html:
				                JSON.stringify(ex),
				                showCloseButton: true,
				                showCancelButton: true,
				                focusConfirm: false,
				                confirmButtonText:
				                  '<i class="fa fa-thumbs-up"></i>OK!',
				                confirmButtonAriaLabel: 'Thumbs up, great!',
				                cancelButtonText:
				                '<i class="fa fa-thumbs-down"></i>',
				                cancelButtonAriaLabel: 'Thumbs down',
				              });
					    	    }
					    	  });
		  		  	    }
		  		  	    else
		  		  	    {  	    	
		  		  	    	alerta("Parametros duplicados para rut o código");
		  		  	    }
		  		  	    },
		  		  	    error: function(ex) {
		  		  	      swal({
		  		          title: '<i>ERROR</i>',
		  		          type: 'info',
		  		          html:
		  		          JSON.stringify(ex),
		  		          showCloseButton: true,
		  		          showCancelButton: true,
		  		          focusConfirm: false,
		  		          confirmButtonText:
		  		            '<i class="fa fa-thumbs-up"></i>OK!',
		  		          confirmButtonAriaLabel: 'Thumbs up, great!',
		  		          cancelButtonText:
		  		          '<i class="fa fa-thumbs-down"></i>',
		  		          cancelButtonAriaLabel: 'Thumbs down',
		  		        });
		  		  	    }
		  		  	  });
	}
	chargeForEdit(id){
	
		$.ajax({
		    type: "GET",
		    async: false,
		    url: params.Read.urlById+id,
		    beforeSend: function(xhr) {
		      xhr.setRequestHeader("Accept", "application/json");
		      xhr.setRequestHeader("Content-Type", "application/json");
		    },
		    success: function(data) {
		    	$(params.Formularios.Update.id).val(data.llave);
		    	$(params.Formularios.Update.tagNombre).val(data.descripcion);
		    	if(params.hasRutParametro)
	  			{
	  				$(params.Formularios.Update.tagRutParametro).val(data.rutParametro);
	  			}
	  			if(params.hasCodPrevired)
	  			{
	  				$(params.Formularios.Update.tagCodPrevired).val(data.codPrevired);
	  			}
	  			if(params.hasCodSap){
	  				$(params.Formularios.Update.tagCodSap).val(data.codSap);
	  			}
	  			
		    	$(params.Modals.updateModalTag).modal("toggle");
		    	
		    },
		    error: function(ex) {
	        swal({
	          title: '<i>ERROR</i>',
	          type: 'info',
	          html: JSON.stringify(ex),
	          showCloseButton: true,
	          showCancelButton: true,
	          focusConfirm: false,
	          confirmButtonText:
	            '<i class="fa fa-thumbs-up"></i>OK!',
	          confirmButtonAriaLabel: 'Thumbs up, great!',
	          cancelButtonText:
	          '<i class="fa fa-thumbs-down"></i>',
	          cancelButtonAriaLabel: 'Thumbs down',
	        });
		    }
		  });
	}
	borrarMant(id)
	{
		 swal({
			    title: "Estás Seguro?",
			    text: "No serás capaz de revertir esto",
			    type: "warning",
			    showCancelButton: true,
			    confirmButtonColor: "#3085d6",
			    cancelButtonColor: "#d33",
			    confirmButtonText: "Bórralo!"
			  }).then(function(result) {
			    if (result.value) {
			    	$.ajax({
			    	    type: "PUT",
			    	    async: false,
			    	    url: params.Delete.url + id,

			    	    beforeSend: function(xhr) {
			    	      xhr.setRequestHeader("Accept", "application/json");
			    	      xhr.setRequestHeader("Content-Type", "application/json");
			    	    },
			    	    success: function(data) {
			    	      alerta(params.Delete.Text);
			    	      location.reload();
			    	    },
			    	    error: function(ex) {
			    	      swal({
			    	        title: '<i>ERROR</i>',
			    	        type: 'info',
			    	        html: JSON.stringify(ex),
			    	        showCloseButton: true,
			    	        showCancelButton: true,
			    	        focusConfirm: false,
			    	        confirmButtonText:
			    	          '<i class="fa fa-thumbs-up"></i>OK!',
			    	        confirmButtonAriaLabel: 'Thumbs up, great!',
			    	        cancelButtonText:
			    	        '<i class="fa fa-thumbs-down"></i>',
			    	        cancelButtonAriaLabel: 'Thumbs down',
			    	      });
			    	    }
			    	  });
			    }
			  });
	}
}








