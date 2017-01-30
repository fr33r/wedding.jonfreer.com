function createComingTable(guests){
var coming = [];
	
	for(var j = 0; j < guests.length; j++){
		if(guests[j].reservation !== null && guests[j].reservation.isAttending){
			coming.push(guests[j]);
		}
	}
	
	if(coming.length > 0){
		
		var comingTable = window.document.getElementById("coming");
		var tableBody = window.document.createElement("tbody");
		
		for(var i = 0; i < coming.length; i++){
			
			var tableRow = window.document.createElement("tr");
			var firstNameTableData = window.document.createElement("td");
			var lastNameTableData = window.document.createElement("td");
			var dietaryRestrictionsTableData = window.document.createElement("td");
			var dateTimeTableData = window.document.createElement("td");
			
			firstNameTableData.innerHTML = coming[i].givenName;
			lastNameTableData.innerHTML = coming[i].surName;
			dietaryRestrictionsTableData.innerHTML = coming[i].dietaryRestrictions;
			dateTimeTableData.innerHTML = new Date(coming[i].reservation.submittedDateTime).toLocaleString();
			
			tableRow.appendChild(firstNameTableData);
			tableRow.appendChild(lastNameTableData);
			tableRow.appendChild(dietaryRestrictionsTableData);
			tableRow.appendChild(dateTimeTableData);
			
			tableBody.appendChild(tableRow);
		}
		
		comingTable.appendChild(tableBody);
	}
}

function createNotComingTable(guests){
	
	var notComing = [];
	
	for(var j = 0; j < guests.length; j++){
		if(guests[j].reservation !== null && !guests[j].reservation.isAttending){
			notComing.push(guests[j]);
		}
	}
	
	if(notComing.length > 0){
		
		var notComingTable = window.document.getElementById("not-coming");
		var tableBody = window.document.createElement("tbody");
		
		for(var i = 0; i < notComing.length; i++){
			
			var tableRow = window.document.createElement("tr");
			var firstNameTableData = window.document.createElement("td");
			var lastNameTableData = window.document.createElement("td");
			var dateTimeTableData = window.document.createElement("td");
			
			firstNameTableData.innerHTML = notComing[i].givenName;
			lastNameTableData.innerHTML = notComing[i].surName;
			dateTimeTableData.innerHTML = new Date(notComing[i].reservation.submittedDateTime).toLocaleString();
			
			tableRow.appendChild(firstNameTableData);
			tableRow.appendChild(lastNameTableData);
			tableRow.appendChild(dateTimeTableData);
			
			tableBody.appendChild(tableRow);
		}
		
		notComingTable.append(tableBody);
	}	
}

function createHaventHeardYetTable(guests){
	
	var haventHeard = [];
	
	for(var i = 0; i < guests.length; i++){
		if(guests[i].reservation === null){
			haventHeard.push(guests[i]);
		}
	}
	
	if(haventHeard.length > 0){
		
		var noRsvpTable = window.document.getElementById("no-rsvp");
		var tableBody = window.document.createElement("tbody");
		
		for(var j = 0; j < haventHeard.length; j++){
			
			var tableRow = window.document.createElement("tr");
			var firstNameTableData = window.document.createElement("td");
			var lastNameTableData = window.document.createElement("td");
			var inviteCodeTableData = window.document.createElement("td");
			
			firstNameTableData.innerHTML = haventHeard[j].givenName;
			lastNameTableData.innerHTML = haventHeard[j].surName;
			inviteCodeTableData.innerHTML = haventHeard[j].inviteCode;
			
			tableRow.appendChild(firstNameTableData);
			tableRow.appendChild(lastNameTableData);
			tableRow.appendChild(inviteCodeTableData);
			
			tableBody.appendChild(tableRow);
		}
		noRsvpTable.appendChild(tableBody);
	}
}

function createTables(guests){
	createNotComingTable(guests);
	createComingTable(guests);
	createHaventHeardYetTable(guests);
}

window.onload = function(){
	
	ajaxModule.get(
			"http://freer.ddns.net:8080/api/wedding/guests/", 
			{ accept: "application/json" }, 
			createTables, 
			function(){ window.alert("ERROR."); }
	);
	
};