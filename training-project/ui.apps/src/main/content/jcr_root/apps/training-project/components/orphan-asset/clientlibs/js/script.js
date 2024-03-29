

console.log("hellooo");


$(document).ready(function(){


    fetchProjects();

    fetchLocales();

    submit();


});

function fetchProjects() {
    $.ajax({
        url: "/apps/projectListing",
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            console.log('Alerts fetched from server:', response);
            $('#project').empty();
            // Populate dropdown with fetched projects
            $.each(response, function(index, project) {
                $('#project').append($('<option></option>').attr('value', project.key).text(project.value));
            });
            
        },
        error: function(xhr, status, error) {
            console.error('Error fetching alerts:', error);
        }
    });
}


function fetchLocales() {

    $('#project').change(function(){
        var selectedValue = $(this).val();
        console.log('Selected value:', selectedValue);

        $.ajax({
            url: '/bin/locale',
            type: 'GET',
            data: { project: selectedValue },
            success: function(response) {
                $('#locale').empty();
                
                $.each(response, function(index, locale) {
                    $('#locale').append($('<option></option>').attr('value', locale.key).text(locale.value));
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching locale data:', error);
            }
        });
    });
}

function submit(){

    $("#fetchDataBtn").click(function(){
        // Get selected values from dropdowns
        var projectName = $("#project").val();
        var locale = $("#locale").val();

        var path = '/apps/cq/clean-orphan-asset/components/orphan-asset-final-page.html?project='+ projectName + '&locale=' + locale;

        console.log(path)

         window.location.href = path
    });

}
