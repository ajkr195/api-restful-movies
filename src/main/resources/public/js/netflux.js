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
    const urlBase = "https://dsai-netflux.herokuapp.com/api/";

    //cargando datos de peliculas
    $.ajax({
        type: "GET",
        url: urlBase+"peliculas/novedades",
        success: function(data){
            //data = [{"id":"string","url":"string","title":"string","imgURL":"string"}]
            var step = 0;
            var id_container = 1;
            var html_content = "";
            
            jQuery.each(data, function(i, value) {
                html_content = html_content + 
                '<div class="col-12 col-sm-6 col-md-6 col-lg-3 d-flex flex-column justify-content-center">' +
                    '<a href="media.html?url='+value.url+'">'+
                    '<picture>'+
                        '<source media="(max-width: 576px)" srcset="https://dsai-netflux.herokuapp.com/'+value.imgURL+'">'+
                        '<source media="(min-width: 577px)" srcset="https://dsai-netflux.herokuapp.com/'+value.imgURL+'">'+
                        '<img class="img-fluid img-thumbnail" src="https://dsai-netflux.herokuapp.com/'+value.imgURL+'" alt="movie">'+
                    '</picture>'+
                    '<p>'+value.title+'</p>'+
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
        url: urlBase+"series/novedades",
        success: function(data){
            //data = [{"id":"string","url":"string","title":"string","imgURL":"string"}]
            var step = 0;
            var id_container = 1;
            var html_content = "";
            
            jQuery.each(data, function(i, value) {
                html_content = html_content + 
                '<div class="col-12 col-sm-6 col-md-6 col-lg-3 d-flex flex-column justify-content-center">' +
                    '<a href="media.html?url='+value.url+'">'+
                    '<picture>'+
                        '<source media="(max-width: 576px)" srcset="https://dsai-netflux.herokuapp.com/'+value.imgURL+'">'+
                        '<source media="(min-width: 577px)" srcset="https://dsai-netflux.herokuapp.com/'+value.imgURL+'">'+
                        '<img class="img-fluid img-thumbnail" src="https://dsai-netflux.herokuapp.com/'+value.imgURL+'" alt="movie">'+
                    '</picture>'+
                    '<p>'+value.title+'</p>'+
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
    const urlBase = "https://dsai-netflux.herokuapp.com";

    //obteniendo id
    var params = new window.URLSearchParams(window.location.search);
    if(params.get('url')===null){
        window.location.replace('index.html');
    }
    var url = params.get('url');
    var media_type = url.split("/")[2]; //posibles valores: peliculas, series


    //carcando detalle de pelicula/serie
    $.ajax({
        type: "GET",
        url: urlBase+url,
        success: function(data){
        //json example movie: {"id":"string","url":"string","imgURL":"string","title":"string","description":"string","year":0,"duration":0,
        //                    "director":"string","cast":[{"name":"string","imgURL":"string"}]}
        //json example serie: {"id":"string","url":"string","imgURL":"string","title":"string","description":"string","yearStart":0,"yearEnd":0,
        //                  "seasons":0,"creators":["string"],"cast":[{"name":"string","imgURL":"string"}]}
            var media_data = new Array();
            if(media_type == 'peliculas'){
                media_data['title_year'] = data.year;
                media_data['title_2_duration'] = 'Duración: '+data.duration+' mins.';
                media_data['media_by'] = '<strong>Director: </strong>'+data.director;
                

            }
            else if(media_type == 'series'){
                media_data['title_year'] = data.yearStart+' - '+data.yearEnd;
                media_data['title_2_duration'] = 'Temporadas: '+data.seasons+' temporadas';
                var html_content = "";
                jQuery.each(data.creators, function(i, creators) {
                    html_content = html_content + creators + ". ";
                });
                media_data['media_by'] = '<strong>Creadores: </strong>'+html_content;
            }
            $("#movie-title-1").html(
                '<h2>'+data.title+'</h2>'+
                '<div class="d-flex flex-row justify-content-center" >'+
                    '<p class="w-75 border-bottom border-secondary" >'+media_data['title_year']+'</p>'+
                '</div>'+
                '<button type="button" class="btn btn-dark">Ver en Netflux</button>'
            );

            $("#movie-img-1").html(
                '<picture >'+
                    '<source media="(max-width: 769px)" srcset="https://dsai-netflux.herokuapp.com/'+data.imgURL+'">'+
                    '<source media="(min-width: 768px)" srcset="https://dsai-netflux.herokuapp.com/'+data.imgURL+'">'+
                    '<img class="img-fluid img-thumbnail w-sm-50" src="https://dsai-netflux.herokuapp.com/'+data.imgURL+'" alt="movie cover">'+
                '</picture>'
            );

            $("#movie-title-2").html(
                '<div class="h4">'+data.title+'</div>'+
                '<div>Año: '+media_data['title_year']+'</div>'+
                '<div>' + media_data['title_2_duration'] +'</div>'+
                '<div class="border-bottom pb-2"></div>'
            );

            $('#movie-description').text(data.description);
            $('#movie-director').html(media_data['media_by'] );

            var html_content = "";
            jQuery.each(data.cast, function(i, cast) {
                html_content = html_content + 
                                '<div class="col-6 col-sm-6 col-md-6 col-lg-4 p-1">'+
                                    '<div class="row">'+
                                        '<div class="col-12 col-sm-4 d-flex justify-content-center">'+
                                            '<img class="img-fluid img-thumbnail" src="https://dsai-netflux.herokuapp.com/'+cast.imgURL+'" alt="movie">'+
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
    const urlBase = "https://dsai-netflux.herokuapp.com/api/";
    
    //cargando destacados
    $.ajax({
        type: "GET",
        url: urlBase+"destacados",
        success: function(data){
            //json example: [{"id":"string","type":"string","url":"string","title":"string","imgURL":"string"}]
            var html_content = '<li class="list-group-item border-0"><h4>Destacados</h4></li>';
            jQuery.each(data, function(i, value) {
                html_content = html_content + 
                            '<a href="media.html?url='+value.url+'">' + 
                                '<li class="list-group-item border-0 pt-0 pb-3">' +
                                    '<div class="row pl-4 pr-4 pl-lg-3 pr-lg-3">' +
                                        '<div class="col-12 col-sm-12 col-md-12 col-lg-3 p-0 d-flex align-items-center justify-content-center">' +
                                                '<picture>' +
                                                    '<source media="(max-width: 768px)" srcset="https://dsai-netflux.herokuapp.com/'+value.imgURL+'">' +
                                                    '<source media="(min-width: 767px)" srcset="https://dsai-netflux.herokuapp.com/'+value.imgURL+'">' +
                                                    '<img class="img-fluid img-thumbnail" src="https://dsai-netflux.herokuapp.com/'+value.imgURL+'" alt="movie">' +
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
        url: urlBase+"trailers",
        success: function(data){
            //json example: [{"title":"string","url":"string","imgURL":"string"}]
            var html_content = '<li class="list-group-item border-0"><h4>Trailers</h4></li></li>';
            jQuery.each(data, function(i, value) {
                html_content = html_content + 
                                '<a target="_blank" href="'+value.url+'">'+
                                    '<li class="list-group-item border-0 pt-0 pb-3">'+
                                        '<div class="row pl-4 pr-4 pl-lg-3 pr-lg-3">'+
                                        '<div class="col-12 col-sm-12 col-md-12 col-lg-3 p-0 d-flex align-items-center justify-content-center">'+
                                                '<picture>'+
                                                    '<source media="(max-width: 768px)" srcset="https://dsai-netflux.herokuapp.com/'+value.imgURL+'">'+
                                                    '<source media="(min-width: 767px)" srcset="https://dsai-netflux.herokuapp.com/'+value.imgURL+'">'+
                                                    '<img class="img-fluid img-thumbnail" src="https://dsai-netflux.herokuapp.com/'+value.imgURL+'" alt="movie">'+
                                                '</picture>'+
                                            '</div>'+
                                            '<div class="col-12 col-sm-12 col-md-12 col-lg-9 p-2 d-flex align-items-center">'+
                                                '<div class="w-100 text-center text-lg-left" >'+
                                                    '<div class="ml-2" >'+value.title+'<div>'+
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