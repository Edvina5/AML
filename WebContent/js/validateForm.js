

function validateForm(){
	var username = document.forms["registration"]["username"].value;
	var name = document.forms["registration"]["name"].value;
	var surname = document.forms["registration"]["surname"].value;
	var password = document.forms["registration"]["password"].value;
	var group = document.forms["registration"]["group"].value;
	var email = document.forms["registration"]["email"].value;
	
	//to check if valid email
	var atpos = email.indexOf("@");
	var dotpos = email.indexOf(".");
	
	
	if(username == null || username == ""){
		alert("User name field is empty!");
		return false;
	}
	if(name == null || name == ""){
		alert("Name field is empty!");
		return false;
	}
	if(surname == null || surname == ""){
		alert("Surname field is empty!");
		return false;
	}
	if(password == null || password == ""){
		alert("Password field is empty!");
		return false;
	}
	if(group == null || group == ""){
		alert("Group field is empty!");
		return false;
	}
	if(email == null || email == ""){
		alert("Email field is empty!");
		return false;
	}
	if(atpos< 1 || dotpos<atpos+2 || dotpos+2>=x.length){
	    alert("Not a valid e-mail address");
	    return false;
	 }
	
}