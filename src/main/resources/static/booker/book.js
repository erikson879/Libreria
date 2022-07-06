function getBook(data,target){
$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/ajax/query/books",
		data : JSON.stringify(data),
		dataType : 'json',
		success : function(data) {
			console.log("SUCCESS: ", data);
			$("input[name='ISBN']").val(data.isbn);
			$("input[name='title']").val(data.title);
			$("input[name='numPgs']").val(data.numPgs);
			$("input[name='publisher.name']").val(data.publisher.name);
			$("input[type='checkbox']").prop("checked",false);
			data.authors.forEach(function(el){$("input[type='checkbox'][value='"+el.id+"']").prop("checked",true);});
			if($("#idbook").length>0){
				$("#idbook").val(data.id);
			}else{
				const bId = $("<input type='hidden' name='id' value='"+data.id+"' id='idbook'/>");
				$("form[action='/addBook']").append(bId);
			}
			$(target).closest('tr').remove();
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}
function deleteBook(data,target){
$.ajax({
		type : "DELETE",
		contentType : "application/json",
		url : "/ajax/delete/books",
		data : JSON.stringify(data),
		dataType : 'text',
		success : function() {
			$(target).closest('tr').remove();
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}
$(document).ready(function() {
	$('a[id^="edit"]').click(
		function(el){
			getBook({id:$(this).siblings()[0].value},el.target);
		});
	$('a[id^="delete"]').click(
		function(el){
			deleteBook({id:$(this).siblings()[0].value},el.target);
		});	
});