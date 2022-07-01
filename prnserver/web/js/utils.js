function calDate( date, y, m, d ) {
	
	var year = date.substr(0, 4);
	var month = date.substr(5, 2);
	var day = date.substr(8, 2);
	
	var date = new Date( year, month, day );
	date.setFullYear( date.getFullYear() + y );
	date.setMonth( date.getMonth() - 1 + m );
	date.setDate( date.getDate() + d );
	return toDateString( date );
}

function toDateString( date ) {
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	
	if( ("" + month).length == 1 ) {
		month = "0" + month;
	}
	if( ("" + day).length == 1 ) {
		day = "0" + day;
	}
	
	return "" + year +"-" + month +"-"+ day;
}