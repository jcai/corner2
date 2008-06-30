// This function gets called when the end-user clicks on some date.
function selected(cal, date) {
	cal.sel.value = date;
	//cal.callCloseHandler();
}

// And this gets called when the end-user clicks on the _selected_ date,
// or clicks on the "Close" button.It just hides the calendar without
// destroying it.
function closeHandler(cal) {
	cal.hide();
	_dynarch_popupCalendar = null;
}

// This function shows the calendar under the element having the given id.
// It takes care of catching "mousedown" signals on document and hiding the
// calendar if the click was outside.
function showCalendar(el, format, showsTime, showsOtherMonths) {
	if (_dynarch_popupCalendar != null) {
		// we already have some calendar created
		_dynarch_popupCalendar.hide(); // so we hide it first.
	} else {
		// first-time call, create the calendar.
		var cal = new Calendar(1, null, selected, closeHandler);
		// uncomment the following line to hide the week numbers
		// cal.weekNumbers = false;
		if (typeof showsTime == "string") {
		cal.showsTime = true;
		cal.time24 = (showsTime == "24");
		}
		if (showsOtherMonths) {
		cal.showsOtherMonths = true;
		}
		_dynarch_popupCalendar = cal;// remember it in the global var
		cal.setRange(1900, 2070);// min/max year allowed.
		cal.create();
	}
	_dynarch_popupCalendar.setDateFormat(format);// set the specified date format
	_dynarch_popupCalendar.parseDate(el.value);// try to parse the text in field
	_dynarch_popupCalendar.sel = el; // inform it what input field we use
	
	//显示cal
	_dynarch_popupCalendar.showAtElement(el, "Br");// show the calendar
	
	return false;
}