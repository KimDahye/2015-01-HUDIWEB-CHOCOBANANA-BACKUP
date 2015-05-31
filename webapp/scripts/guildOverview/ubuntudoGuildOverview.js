
ubuntudo = {};
ubuntudo.ui = {};
ubuntudo.utility = {};

window.addEventListener("load", function () {
    'use strict';

	var oGuildOverviewManager = new ubuntudo.ui.GuildOverviewManager();
	var util = ubuntudo.utility;


	oGuildOverviewManager.addEvents();
	util.ajax({
	    "method": "GET",
	    "uri": "/guild/overview/info",
	    "param": null,
	    "callback": oGuildOverviewManager.setGuildInfo
	});
	
	/*user가 가입한 파티 리스트 서버에 요청하여 받아오기*/
	var oTodoAddModal = new ubuntudo.ui.TodoAddModal(); 	
	util.ajax({
		"method": "GET",
		"uri": "/party",
		"param": null,
		"callback": oTodoAddModal.setPartyList
	});
	
	 /*add todo modal관련 이벤트 등록*/
    var elAddTodoBtn = document.querySelector(".todo_add_btn");
    var elCancelBtn = document.querySelector(".cancel_btn");
	var oTodoAddModal = new ubuntudo.ui.TodoAddModal(); 
	var oTodoAddModalManager = new ubuntudo.ui.ModalManager(oTodoAddModal);
    
    elAddTodoBtn.addEventListener("click", function(ev){
        oTodoAddModalManager.showModal(ev);
    });

    elCancelBtn.addEventListener("click", function(ev){
	   oTodoAddModalManager.hideModal(ev);
    });
    
	/* submit 버튼 누르면 투두 추가 */
	var elSubmitBtn = document.querySelector(".add_todo .submit_btn");
	elSubmitBtn.addEventListener('click', function (ev) {
       oTodoManager.add(ev, oDataManager, oTodoAddModal);
	   oTodoAddModalManager.hideModal(ev);
	});
	
	 
    /*길드 검색 이벤트 등록*/
    var elSearchResultList =  document.querySelector(".search_result_list");
    var elSearchInput = document.getElementById("global-header").querySelector(".search_input");
    var oSearchManager = new ubuntudo.ui.SearchManager(elSearchResultList, elSearchInput);
    elSearchInput.addEventListener("keyup", function(ev) {
        oSearchManager.autoComplete(ev);
    })

//Hello Hwang! Your keyboard is awesome!!!
});

//달력 관련 jquery (datepicker)
$(function() {
	'use strict';

    var myDatepicker = $("#datepicker");
    myDatepicker.datepicker({ 
        firstDay: 0,
        minDate: 0,
        dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
        dateFormat: "yy-mm-dd"
    });

    $("#ui-datepicker-div").addClass("ui-datepicker-default");

    myDatepicker.click(function() {
       $("#ui-datepicker-div").removeClass("ui-datepicker-default");
    });
});