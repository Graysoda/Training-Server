$( document ).ready(function(){

    var searchOptions = Array();

    $("select option").each(function(i, v){
            searchOptions[i] = $(this).val();
    });

    $("#search-input").keyup(function(){
        var searchText = $("#search-input").val();

        $("tbody").find("tr").each(function(){
            if (!$(this).find("td").eq(searchOptions.indexOf($("#search-select").val())).text().includes(searchText)){
                $(this).hide();
            } else {
                $(this).show();
            }
        })
    });
})