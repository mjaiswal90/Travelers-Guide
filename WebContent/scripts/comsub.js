/*$(function() {
 $("#comsub").click(function(){
            		$.post( 'cust_ajax_buy_fund.do', $(this).serialize(), function(data) {
            	         if(data.success == "true") {
            	        	 $("#comtag").clear();
            	         } else {
            	        	 $(".alert-warning").text(data.error).show();
            	        	 $(".alert-success").text(data.info).hide();
            	         }
            	      }, 'json' // I expect a JSON response
            	    );
                });


 }*/

/*$("#comsub").click(function(){
            		$.post( 'ajax_tweet.do', $('form#comform').serialize(), 'json' // I expect a JSON response
            	    );
                });*/

 $(function() {
 $("#comsub").click(function(){
             $.ajax({
                 type: "post",
                 url: "ajax_tweet.do",     
                 data: $("#comsub").serialize(),    
                 success: function(data) {
                  $("#comtag").clear();
                     //alert("提交成功！");
                 },
                 error: function(data) {
                     alert(data);
                 }
             })
         });
  })