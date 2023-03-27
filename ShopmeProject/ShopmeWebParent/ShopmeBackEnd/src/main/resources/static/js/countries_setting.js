var buttonLoad;
var dropDownCountry;
var buttonAddCountry;
var buttonUpdateCountry;
var buttonDeleteCountry;
var labelCountryName;
var fieldCountryName;
var fieldCountryCode;

$(document).ready(function() {
	buttonLoad = $("#buttonLoadCountries");  // input: Load Country List
	dropDownCountry = $("#dropDownCountries"); // hàm select
	buttonAddCountry = $("#buttonAddCountry");  
	buttonUpdateCountry = $("#buttonUpdateCountry");   // id of input
	buttonDeleteCountry = $("#buttonDeleteCountry"); 
	labelCountryName  = $("#labelCountryName");
	fieldCountryName  = $("#fieldCountryName"); 
	fieldCountryCode  = $("#fieldCountryCode"); 
	
	buttonLoad.click(function() {
		loadCountries();
	});
	
	dropDownCountry.on("change", function() { // .on(): Đính kèm một hàm xử lý sự kiện cho một hoặc nhiều sự kiện tới một thành phần được chọn.
		changeFormStateToSelectedCountries();
	});
	
	buttonAddCountry.click(function() {  // click vào nút Add
	  if(buttonAddCountry.val() == "Add") {  // add: value="add"
		  addCountry();
	  } else {
		  changeFormStateToNew();
	  }
	    
	});
});

function addCountry() {
	url = contextPath + "countries/save";
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();
	
	jsonData = {name: countryName, code: countryCode};
	
	$.ajax({
		type: 'POST', 
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfHeaderValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(countryId) {
		alert("new add country ID : " + countryId);
	});
}

function changeFormStateToSelectedCountries() {
	buttonAddCountry.prop("value", "New");
	buttonUpdateCountry.prop("disabled", false);
	buttonDeleteCountry.prop("disabled", false);
	 
	labelCountryName.text("Selectted Country:")

	// nhập vào thì nó in vào ra chỗ input: country name
	selectedCountryName = $("#dropDownCountries option:selected").text();
	fieldCountryName.val(selectedCountryName);
	
	countryCode = dropDownCountry.val().split("-")[1]; // cắt chuỗi lấy country code
	fieldCountryCode.val(countryCode);
}

function changeFormStateToNew() {
	buttonAddCountry.val("Add");
	labelCountryName.text("Selectted Country:")
	
	buttonUpdateCountry.prop("disabled", true);
	buttonDeleteCountry.prop("disabled", true);
	
	fieldCountryCode.val("");
	fieldCountryName.val("").focus();
}

function loadCountries() {
	// alert("About loading countries..");
	url = contextPath + "countries/list";
	$.get(url, function(responseJSON) {
		dropDownCountry.empty();
		
		$.each(responseJSON, function(index, country) {
			optionValue = country.id + "-" + country.code;
			//alert(optionValue);
		    $("<option>").val(optionValue).text(country.name).appendTo(dropDownCountry);
		});
	}).done(function() {
		buttonLoad.val("Refresh Country List");
		showToastMessage("All countries have been loaded");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to server or server encountered an error");
	});
}

function showToastMessage(message) {
	$("#toastMessage").text(message);
	$('.toast').toast('show');
}
