$(function() {
	$(".czlist label").css("width",$(".wf_con").width()-$(".wf_con i").width()*2-4);
	$(".czlist a").click(function(){
		var ai = $(".czlist a").index($(this));
		if($(this).hasClass("fq_cur")){
			return false;
		}else{
			$(this).addClass("fq_cur").siblings().removeClass("fq_cur");
			$(".play_li").eq(ai).show().siblings().hide();
		}
		
	});
	
	$(".fq_con td a").click(function(){
		if($(this).hasClass("fq_cur")){
			$(this).removeClass("fq_cur");
		}else{
			$(this).addClass("fq_cur").siblings("a").removeClass("fq_cur");
		}
	});	

	var labelw = $(".fq_bli").width(); 
	var emw = $(".fq_bli em").width();  
	var ys = parseInt(emw / labelw);
	var fqwidth = 0;	
	var fqindex = 1;	

	$(".fq_right").click(function(){

		if(fqindex < ys+1){
			if(fqindex < ys){
				fqwidth = - fqindex * labelw;		
				fqindex++;					
			}else if(fqindex == ys){
				fqwidth = -(emw -labelw);	
				fqindex++;					
			}
			$(".fq_bli em").attr("style","transition-property:transform; -moz-transition-property:transform; -webkit-transition-property:transform; transition-timing-function:cubic-bezier(0, 0, 0.25, 1); -moz-transition-timing-function:cubic-bezier(0, 0, 0.25, 1); -webkit-transition-timing-function:cubic-bezier(0, 0, 0.25, 1); transition-duration:350ms; -moz-transition-duration:350ms; -webkit-transition-duration:350ms; transform:translate3d("+fqwidth+"px, 0px, 0px); -moz-transform:translate3d("+fqwidth+"px, 0px, 0px); -webkit-transform:translate3d("+fqwidth+"px, 0px, 0px);");
		}
	});

	$(".fq_left").click(function(){
		if(fqindex < ys + 2){
			if(fqindex > 2){
				fqwidth = - (emw - (fqindex-1) * labelw);	
				fqindex--;	
			}else if(fqindex == 2){
				fqwidth = 0;
				fqindex--;	
			}
			$(".fq_bli em").attr("style","transition-property:transform; -moz-transition-property:transform; -webkit-transition-property:transform; transition-timing-function:cubic-bezier(0, 0, 0.25, 1); -moz-transition-timing-function:cubic-bezier(0, 0, 0.25, 1); -webkit-transition-timing-function:cubic-bezier(0, 0, 0.25, 1); transition-duration:350ms; -moz-transition-duration:350ms; -webkit-transition-duration:350ms; transform:translate3d("+fqwidth+"px, 0px, 0px); -moz-transform:translate3d("+fqwidth+"px, 0px, 0px); -webkit-transform:translate3d("+fqwidth+"px, 0px, 0px);");
		}
	});

});