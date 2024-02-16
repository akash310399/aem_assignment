var responseData = [];
var currentAlertIndex = 0;

function fetchAlertsFromServer() {
    $.ajax({
        url: "/bin/alert",
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            console.log('Alerts fetched from server:', response);
            responseData = response;
            var lastDismissedIndex = localStorage.getItem('lastDismissedIndex');
            if (lastDismissedIndex !== null) {
                currentAlertIndex = parseInt(lastDismissedIndex) + 1;
            }
            renderAlert(responseData[currentAlertIndex]); // Render the first alert
        },
        error: function(xhr, status, error) {
            console.error('Error fetching alerts:', error);
        }
    });
}

function renderAlert(alertData) {
    if (!alertData) return; // Check if alertData is undefined or null
    var alertBox = $('<div class="alert">').appendTo('body');
    var message = $('<h3>').text(alertData.message).appendTo(alertBox);
    var dismissButton = $('<button>').text('Dismiss').appendTo(alertBox);
    var closeButton = $('<button>').text('×').appendTo(alertBox);

    dismissButton.on('click', function() {
        alertBox.remove();
        currentAlertIndex++;
        if (currentAlertIndex < responseData.length) {
            renderAlert(responseData[currentAlertIndex]);
        } else {
            localStorage.setItem('alertsDismissed', true); // Mark alerts as dismissed in local storage
        }
        localStorage.setItem('lastDismissedIndex', currentAlertIndex - 1);
    });

    closeButton.on('click', function() {
        localStorage.setItem('alertsDismissed', true);
        alertBox.remove();
    });
}

$(document).ready(function() {
    var alertsDismissed = localStorage.getItem('alertsDismissed');
    if (!alertsDismissed) {
        fetchAlertsFromServer(); // Fetch and render alerts when the page loads if not dismissed
    }
});
