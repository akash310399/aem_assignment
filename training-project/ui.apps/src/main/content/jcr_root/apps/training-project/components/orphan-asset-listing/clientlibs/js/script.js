
console.log("hellooo");


$(document).ready(function(){

    const searchParams = new URLSearchParams(window.location.search);

    const projectName = searchParams.get('project');
    const localeName = searchParams.get('locale');


    fetchOrphanAssets(projectName, localeName);

    deleteOrphanAsset(projectName, localeName);

});



function fetchOrphanAssets(projectName, localeName) {
    

    // Fail fast if either project or locale name is null
    if (!projectName || !localeName) {
        console.error("Error: Missing project or locale name");
        return;
    }

    // Make AJAX request only if project and locale names are both present
    $.ajax({
        url: "/bin/fetch",
        type: "GET",
        data: {
            projectName: projectName,
            locale: localeName
        },
        success: function(response) {
            renderAssets(response);
        },
        error: function(xhr, status, error) {
            // Handle error
            console.error("Error:", error);
        }
    });
}

function renderAssets(data) {
    // Compile Handlebars template
    var template = Handlebars.compile(document.getElementById("assetTemplate").innerHTML);

    // Render data using the template
    var renderedHTML = template({ assets: data });

    // Insert the rendered HTML into the table body
    document.getElementById("assetTableBody").innerHTML = renderedHTML;
}

function deleteOrphanAsset(projectName, localeName) {

    $("#deleteAllBtn").click(function(){
        $.ajax({
            url: "/bin/fetch",
            type: "POST",
            data: {
                projectName: projectName,
                locale:localeName
            },
            success: function(response) {
                console.log(response);
                fetchOrphanAssets(projectName, localeName);
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
    });

    
}

