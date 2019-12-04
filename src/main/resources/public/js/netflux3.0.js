$( document ).ready(function() {
    var pagename = location.href.split("/").slice(-1);
	if(pagename == "index.html"){
		set_index_from_ajax();
		set_sidebar_from_ajax();
	}
	else{
		set_media_from_ajax();
		set_sidebar_from_ajax();
	}
    
});



function set_index_from_ajax(){
	//API Rest URLs
    const urlBase = "http://localhost:8080/";

    //cargando datos de peliculas
    $.ajax({
        type: "GET",
        url: urlBase+"api/movies/news",
        success: function(data){
            var step = 0;
            var id_container = 1;
            var html_content = "";
            
            jQuery.each(data, function(i, value) {
                html_content = html_content + 
                '<div class="col-12 col-sm-6 col-md-6 col-lg-3 d-flex flex-column justify-content-center">' +
                    '<a href="media.html?id='+value.id+'&mediatype='+value.media_type+'">'+
                    	'<div class="d-flex justify-content-center w-100">' + 
		                    '<picture>'+
		                        '<source media="(max-width: 576px)" srcset="'+urlBase+value.img_url+'">'+
		                        '<source media="(min-width: 577px)" srcset="'+urlBase+value.img_url+'">'+
		                        '<img class="img-fluid img-thumbnail" src="'+urlBase+value.img_url+'" alt="movie">'+
		                    '</picture>'+
		                '</div>' + 
	                    '<p class="text-center text-lg-left">'+value.title+'</p>'+
                    '</a>'+
                '</div>';

                if(step==3){
                    $("#novedades-row-"+id_container).html(html_content);
                    html_content = "";
                    step = 0;
                    id_container++;
                }
                else{
                    step ++;
                }
            });
            
        }
    });

    //cargando datos de series
    $.ajax({
        type: "GET",
        url: urlBase+"api/series/news",
        success: function(data){
            var step = 0;
            var id_container = 1;
            var html_content = "";
            
            jQuery.each(data, function(i, value) {
                html_content = html_content + 
                '<div class="col-12 col-sm-6 col-md-6 col-lg-3 d-flex flex-column justify-content-center">' +
                	'<a href="media.html?id='+value.id+'&mediatype='+value.media_type+'">'+
                	'<div class="d-flex justify-content-center w-100">' + 
	                    '<picture>'+
	                        '<source media="(max-width: 576px)" srcset="'+urlBase+value.img_url+'">'+
	                        '<source media="(min-width: 577px)" srcset="'+urlBase+value.img_url+'">'+
	                        '<img class="img-fluid img-thumbnail" src="'+urlBase+value.img_url+'" alt="movie">'+
	                    '</picture>'+
                    '</div>' + 
                    '<p class="text-center text-lg-left">'+value.title+'</p>'+
                    '</a>'+
                '</div>';

                if(step==3){
                    $("#series-row-"+id_container).html(html_content);
                    html_content = "";
                    step = 0;
                    id_container++;
                }
                else{
                    step ++;
                }
            });
            
        }
    });
}

function set_media_from_ajax(){
    //API Rest URLs
    var urlBase = "http://localhost:8080/";

    //obteniendo id
    var params = new window.URLSearchParams(window.location.search);
    if(params.get('id')===null || params.get('mediatype')===null){
        window.location.replace('index.html');
    }
    var id = params.get('id');
    var mediaType = params.get('mediatype');
    var urlComplement = "";
    if(mediaType == 1){ //peliculas
    	urlComplement = 'api/movies/';
    }
    else if(mediaType == 2){ //series
    	urlComplement = 'api/series/';
    }
    else{
    	window.location.replace('index.html');
    }
    const url = urlBase + urlComplement+ id;
    
    //carcando detalle de pelicula/serie
    $.ajax({
        type: "GET",
        url: url,
        error: function (request, error) {
        	console.log("ERROR");
        	console.log(arguments);
        },
        success: function(data){
            var mediaData = new Array();
            if(mediaType == 1){ //peliculas
            	mediaData['title_year'] = data.year;
            	mediaData['title_2_duration'] = 'Duración: '+data.duration;
            	mediaData['media_by'] = '<strong>Director: </strong>'+data.director;
                

            }
            else if(mediaType == 2){
            	mediaData['title_year'] = data.year_start+' - '+data.year_end;
            	mediaData['title_2_duration'] = 'Temporadas: '+data.seasons+' temporadas';
                var html_content = "";
                jQuery.each(data.creators, function(i, creators) {
                    html_content = html_content + creators.name + ". ";
                });
                mediaData['media_by'] = '<strong>Creadores: </strong>'+html_content;
            }
            $("#movie-title-1").html(
                '<h2>'+data.title+'</h2>'+
                '<div class="d-flex flex-row justify-content-center" >'+
                    '<p class="w-75 border-bottom border-secondary" >'+mediaData['title_year']+'</p>'+
                '</div>'+
                '<button type="button" class="btn btn-dark">Ver en Netflux</button>'
            );
            
            $("#movie-img-1").html(
                '<picture >'+
                    '<source media="(max-width: 769px)" srcset="'+urlBase+data.img_url+'">'+
                    '<source media="(min-width: 768px)" srcset="'+urlBase+data.img_url+'">'+
                    '<img class="img-fluid img-thumbnail w-sm-50" src="'+urlBase+data.img_url+'" alt="movie cover">'+
                '</picture>'
            );
            
            $("#movie-title-2").html(
                '<div class="h4">'+data.title+'</div>'+
                '<div>Año: '+mediaData['title_year']+'</div>'+
                '<div>' + mediaData['title_2_duration'] +'</div>'+
                '<div class="border-bottom pb-2"></div>'
            );
            
            $('#movie-description').text(data.description);
            $('#movie-director').html(mediaData['media_by'] );
            
            var html_content = "";
            jQuery.each(data.casting, function(i, cast) {
                html_content = html_content + 
                                '<div class="col-6 col-sm-6 col-md-6 col-lg-4 p-1">'+
                                    '<div class="row">'+
                                        '<div class="col-12 col-sm-4 d-flex justify-content-center">'+
                                            '<img class="img-fluid img-thumbnail" src="'+cast.img_url+'" alt="movie">'+
                                        '</div>'+
                                        '<div class="col-12 col-sm-8 d-flex align-items-center justify-content-center justify-content-sm-start">'+
                                            '<span>'+cast.name+'</span>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>'; 
            });
            $("#movie-cast").html(html_content);
        }
    });
}


function set_sidebar_from_ajax(){
	//API Rest URLs
    const urlBase = "http://localhost:8080/";
    
    //cargando destacados
    $.ajax({
        type: "GET",
        url: urlBase+"api/outstanding",
        success: function(data){
            var html_content = '<li class="list-group-item border-0"><h4>Destacados</h4></li>';
            jQuery.each(data, function(i, value) {
                html_content = html_content + 
                			'<a href="media.html?id='+value.id+'&mediatype='+value.media_type+'">'+
                                '<li class="list-group-item border-0 pt-0 pb-3">' +
                                    '<div class="row pl-4 pr-4 pl-lg-3 pr-lg-3">' +
                                        '<div class="col-12 col-sm-12 col-md-12 col-lg-3 p-0 d-flex align-items-center justify-content-center">' +
                                                '<picture>' +
                                                    '<source media="(max-width: 768px)" srcset="'+urlBase+value.img_url_preview+'">' +
                                                    '<source media="(min-width: 767px)" srcset="'+urlBase+value.img_url_preview+'">' +
                                                    '<img class="img-fluid img-thumbnail" src="'+urlBase+value.img_url_preview+'" alt="movie">' +
                                                '</picture>' +
                                        '</div>' +
                                        '<div class="col-12 col-sm-12 col-md-12 col-lg-9 p-2 d-flex align-items-center">' +
                                            '<div class="w-100 text-center text-lg-left" >' +
                                                '<div class="ml-2" >'+value.title+'<div>' +
                                            '</div> ' +
                                        '</div>' +
                                    '</div>' +
                                '</li>' +
                            '</a>';
            });
            $("#destacados").html(html_content);
            html_content ="";
        }
    });

     
    //cargando trailers
    $.ajax({
        type: "GET",
        url: urlBase+"api/trailers",
        success: function(data){
            var html_content = '<li class="list-group-item border-0"><h4>Trailers</h4></li></li>';
            jQuery.each(data, function(i, value) {
                html_content = html_content + 
                                '<a target="_blank" href="'+value.url+'">'+
                                    '<li class="list-group-item border-0 pt-0 pb-3">'+
                                        '<div class="row pl-4 pr-4 pl-lg-3 pr-lg-3">'+
                                        '<div class="col-12 col-sm-12 col-md-12 col-lg-3 p-0 d-flex align-items-center justify-content-center">'+
                                                '<picture>'+
                                                    '<source media="(max-width: 768px)" srcset="'+urlBase+value.img_url+'">'+
                                                    '<source media="(min-width: 767px)" srcset="'+urlBase+value.img_url+'">'+
                                                    '<img class="img-fluid img-thumbnail" src="'+urlBase+value.img_url+'" alt="movie">'+
                                                '</picture>'+
                                            '</div>'+
                                            '<div class="col-12 col-sm-12 col-md-12 col-lg-9 p-2 d-flex align-items-center">'+
                                                '<div class="w-100 text-center text-lg-left" >'+
                                                    '<div class="ml-2" >'+value.name+'<div>'+
                                                '</div> '+
                                            '</div>'+
                                        '</div>'+
                                    '</li>'+
                                '</a>';
            });
            $("#movie-trailers").html(html_content);
            html_content ="";
        }
    });
}