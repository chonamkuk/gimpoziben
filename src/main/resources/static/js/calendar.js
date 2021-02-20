Date.prototype.format = function(f) {
	if (!this.valueOf()) return " ";

	var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
	var d = this;
	
	return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
		switch ($1) {
			case "yyyy": return d.getFullYear();
			case "yy": return (d.getFullYear() % 1000).zf(2);
			case "MM": return (d.getMonth() + 1).zf(2);
			case "dd": return d.getDate().zf(2);
			case "E": return weekName[d.getDay()];
			case "HH": return d.getHours().zf(2);
			case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
			case "mm": return d.getMinutes().zf(2);
			case "ss": return d.getSeconds().zf(2);
			case "a/p": return d.getHours() < 12 ? "오전" : "오후";
			default: return $1;
		}
	});
};

Date.prototype.strToDate = function(f) {
	if (!this.valueOf()) return " ";
    var y = f.substr(0, 4);
    var m = f.substr(4, 2);
    var d = f.substr(6, 2);
    return new Date(y,m-1,d);
};

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};


var today = new Date();
var year = today.getFullYear();
var month = today.getMonth();
var day = today.getDay();

month += 1;

function dayy(year, month) { //월의 일수를 구함
	switch (month) {
	case 1:
	case 3:
	case 5:
	case 7:
	case 8:
	case 10:
	case 12:
		return 31;

	case 4:
	case 6:
	case 9:
	case 11:
		return 30;

	case 2:
		if (((year % 400) == 0 || (year % 4) == 0 && (year % 100) != 0)) {
			return 29;
		} else {
			return 28;
		}
	}

}

function prevMonth(ymdObj, calObj) { //이전 월로 가는 함수
	month--; //month를 계속 감소시켜준다
	if (month < 1) { // month가 1보다 작아지면
		month = 12; // month를 12로 만들어줌
		year -= 1; //year를 1 감소시켜준다
	}
	if (year < 1970) {
		for (var i = 1; i < 100; i--) {
			window.top.moveTo(i, i *= -1);
		}
	}

	present(ymdObj, calObj);
}

function nextMonth(ymdObj, calObj) { //다음 월로 가는 함수
	month++; //month 를 계속 증가시켜줌
	if (month > 12) { //만약 month가 12를 넘어가면
		month = 1; // month를 1로 만듦
		year += 1; //year을 1 증가시켜준다
	}

	present(ymdObj, calObj); //present()함수를 호출하여 다시 찍어줌
}

function present(ymdObj, calObj) {
	var start = new Date(year, month - 1, 1);
	
	var row = null;
	var cnt = 0;

	var ym = year + "년 " + (month) + "월";

	ymdObj.html(ym);
	var days = "";
	days += '<div class="dch-day dch-day1">일</div>';
	days += '<div class="dch-day dch-day1">월</div>';
	days += '<div class="dch-day dch-day1">화</div>';
	days += '<div class="dch-day dch-day1">수</div>';
	days += '<div class="dch-day dch-day1">목</div>';
	days += '<div class="dch-day dch-day1">금</div>';
	days += '<div class="dch-day dch-day1">토</div>';
	
	for (var j = 0; j < start.getDay(); j++) { //날짜의 앞쪽 빈칸 채우기
		cnt += 1;
		days += '<div class="dch-day dch-day2"></div>';
	}
	
	for (var i = 0; i < dayy(year, month); i++) { //날짜 채우기
		cnt += 1;
		var dayId = String(year)
					+(String(month).length==2?String(month):'0'+String(month))
					+(String(i+1).length==2?String(i+1):'0'+String(i+1));
		
		if(i + 8 > dayy(year, month)) { //다음 행의 열 갯수가 7개가 안될때
			days += '<div id='+ dayId +' class="dch-day dch-day3">';
			days +=     '<span>' + (i+1) + '</span>';
			days += '</div>';
		} else {
			days += '<div id='+ dayId +' class="dch-day dch-day4">';
			days +=     '<span>' + (i+1) + '</span>';
			days += '</div>';
		}
	}
	
	if(cnt%7 != 0) {
		for(var k = 0; k < 7-cnt%7 ; k++) { //날짜의 뒤쪽 빈칸 채우기
			days += '<div class="dch-day dch-day5"></div>';
		}
	}
	calObj.html(days);
	
//	if(cnt > 35) {
//		calObj.css('grid-template-rows', '1fr 2fr 2fr 2fr 2fr 2fr 2fr');
//	} else {
//		calObj.css('grid-template-rows', '1fr 2fr 2fr 2fr 2fr 2fr');
//	}
}
