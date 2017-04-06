function addCandidate(){
	var row = '';
	row += '<tr>';
	row += '<td><input class="form-control" type="text"></td>';
	row += '<td><input class="form-control" type="text"></td>';
	row += '<td><input class="form-control" type="text"></td>';
	row += '</tr>';

	$('#candidate-table tbody').append(row);
}

function createReports(){
    data = reportsManager.convertReportsToHTMLString();

    $('#report-block').append(data);
}

function createCampaign(){

    campaign.emptyCandidates();
	/* get campaign name, start time, and end time. */
	var campaign_name = $('#campaign-name').val();
	var start_time = $('#start-time').val();
	var end_time = $('#end-time').val();


	campaign.setName(campaign_name);
	campaign.setStartString(start_time);
	campaign.setEndString(end_time);
	campaign.convertToDates();

    var names = []
    var descriptions = [];
    var imageURLs = [];

	/* for each candidate in table, add it to campaign. */
	$('#candidate-table tbody tr').each(function(){
		var $tds = $(this).find('td');

		// get candidate name from table.
		var name = $tds[0].childNodes[0].value;
		//get candidate description from table.
		var description = $tds[1].childNodes[0].value;
		//get candidate img url from table.
		var imageURL = $tds[2].childNodes[0].value;

		names.push(name);
		descriptions.push(description);
		imageURLs.push(imageURL);

		//campaign.addCandidate(name, description, imageURL);
	});




    for(var i = 0; i < names.length; i++){
//        console.log(names[i]);
//        console.log(descriptions[i]);
//        console.log(imageURLs[i]);
        campaign.addCandidate(names[i], descriptions[i], imageURLs[i]);

    }

    var result = stub.addCampaign(campaign);

    if(result == true){
        document.getElementById('message-area').innerHTML = '<h3 style="color:red"> The campaign was added successfully.</h3>';
    }
    else{
        document.getElementById('message-area').innerHTML = '<h3 style="color:red"> The campaign was not added successfully. <br> The data was invalid or campaign time overlaps with existing campaign. </h3>';
    }

}