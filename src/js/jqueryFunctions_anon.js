const path = "/"+window.location.pathname.split("/")[1]+"/TweetsAnonController";

$(document).ready(function(){
    $.ajaxSetup({ chache: false});
   	$(".usersContent").hide();
   	$(".userContent").hide();
   	$(".tweetComentsContent").hide();
   	$(".searchContent").hide();
});


//insereix tweets que tenen el parent com a null (tweets originals)
$(document).ready(function(){
    $.post( path, 
        {type : "getTimelineAnon"},function(data, status){
            for (let i in data.tweets) {
				var $tweetID = data.tweets[i].id;
                var $userID = data.tweets[i].userID;
                var $tweetDiv = $("<div>", {"class": "tweet", "id":$tweetID, lang: data.tweets[i].lang});
                var $tweetUserDiv = $("<div>",{"class": "tweetUser", "id":$userID});
                var $tweetConentDiv = $("<div>",{"class": "tweetContent"});
                var $tweetPublishDate = $("<div>",{"class": "tweetPublishDate"});
                var $tweetLikesRetweets = $("<div>",{"class": "tweetLikesRetweets"});
                var $tweetLikes = $("<div>",{"class": "tweetLikes"});
				var $tweetRetweets = $("<div>",{"class": "tweetRetweets"});
                var $tweetComments = $("<div>",{"class":"tweetComments"});
                
                $tweetUserDiv.append("<p>"+data.tweets[i].user+"</p>");
                $tweetConentDiv.append("<p>"+data.tweets[i].tweetContent+"</p>");
                $tweetPublishDate.append("<p>"+data.tweets[i].publishDate+"</p>");
				$tweetLikes.append("<i class='bx bx-heart icon likeTweet'></i>");
                $tweetLikes.append("<span>"+data.tweets[i].likes+"</span>");
				$tweetRetweets.append("<i class='bx bx-sort-alt-2 icon retweet'></i>");
                $tweetRetweets.append("<span>"+data.tweets[i].retweets+"</span>");
                $tweetComments.append("<i class='bx bx-comment'></i>");
                
                $tweetLikesRetweets.append($tweetLikes);
                $tweetLikesRetweets.append($tweetRetweets);
                $tweetLikesRetweets.append($tweetComments);

                $tweetDiv.append($tweetUserDiv);
                $tweetDiv.append($tweetConentDiv);
                $tweetDiv.append($tweetPublishDate);
				$tweetDiv.append($tweetLikesRetweets);
                $(".homeContainer").append($tweetDiv);
            }
         });
});

//retornar comentaris del tweet que es clica utilitzant la id del tweet
$(".homeContent").on('click','.tweet',function(){
	var tweetID = $(this).attr("id"); 
	$.post(path,
	{type : "getComments" ,name:$(".name").text(), id: tweetID}, 
	function(data, status) {
		//a data es retorna l'informacio dels commentaris del tweet que es selecciona
		console.log(data);
		//TODO insertar comentaris al tweet,
	});
	
});


//get all Users
$(".menu").on('click','#users',function(){
	$(".usersContainer").empty();
    $.post( path, 
        {type : "getUsers"},
        function(data, status){
			for (let i in data.users) {
				var userID = data.users[i].userID; 
				var userDiv = $("<div>", {"class": "users", "id":userID});
				var userNameDiv = $("<div>", {"class": "usersName"});

				userNameDiv.append("<span class='folUserName'>"+data.users[i].userName+"</span>");

				userDiv.append(userNameDiv);
				$(".usersContainer").append(userDiv);
			}   
         });
});

//get user profile from users
$(".container").on('click','.folUserName',function(event){
	$(".userContainer").empty();
	var username = $(event.target).text();
	$(".userContainer").append("<p class='userNameTitle'>"+ username +"</p>");
	var username = $(event.target).text();
	var otherUserID = $(this).closest(".users").attr("id");
	$.post( path, 
    {type : "getUserProfile" , userID: otherUserID},
    function(data, status){
        for (let i in data.tweets) {
        	var $tweetID = data.tweets[i].id;
            var $userID = data.tweets[i].userID;
            var $tweetDiv = $("<div>", {"class": "tweet", "id":$tweetID, lang: data.tweets[i].lang});
            var $tweetUserDiv = $("<div>",{"class": "tweetUser", "id":$userID});
            var $tweetConentDiv = $("<div>",{"class": "tweetContent"});
            var $tweetPublishDate = $("<div>",{"class": "tweetPublishDate"});
            var $tweetLikesRetweets = $("<div>",{"class": "tweetLikesRetweets"});
            var $tweetLikes = $("<div>",{"class": "tweetLikes"});
			var $tweetRetweets = $("<div>",{"class": "tweetRetweets"});
            var $tweetComments = $("<div>",{"class":"tweetComments"});
            
            $tweetUserDiv.append("<p>"+data.tweets[i].user+"</p>");
            $tweetConentDiv.append("<p>"+data.tweets[i].tweetContent+"</p>");
            $tweetPublishDate.append("<p>"+data.tweets[i].publishDate+"</p>");
			$tweetLikes.append("<i class='bx bx-heart icon likeTweet'></i>");
            $tweetLikes.append("<span>"+data.tweets[i].likes+"</span>");
			$tweetRetweets.append("<i class='bx bx-sort-alt-2 icon retweet'></i>");
            $tweetRetweets.append("<span>"+data.tweets[i].retweets+"</span>");
            $tweetComments.append("<i class='bx bx-comment'></i>");
            
            $tweetLikesRetweets.append($tweetLikes);
            $tweetLikesRetweets.append($tweetRetweets);
            $tweetLikesRetweets.append($tweetComments);

            $tweetDiv.append($tweetUserDiv);
            $tweetDiv.append($tweetConentDiv);
            $tweetDiv.append($tweetPublishDate);
			$tweetDiv.append($tweetLikesRetweets);
            $(".userContainer").append($tweetDiv);
        }
     });
});

//get user profile from home
$(".container").on('click','.tweetUser',function(event){
	$(".userContainer").empty();
	var username = $(event.target).text();
	$(".userContainer").append("<p class='userNameTitle'>"+ username +"</p>");
	var otherUserID = $(this).attr("id");
	$.post( path, 
    {type : "getUserProfile", userID: otherUserID},
    function(data, status){
        for (let i in data.tweets) {
        	var $tweetID = data.tweets[i].id;
            var $userID = data.tweets[i].userID;
            var $tweetDiv = $("<div>", {"class": "tweet", "id":$tweetID, lang: data.tweets[i].lang});
            var $tweetUserDiv = $("<div>",{"class": "tweetUser", "id":$userID});
            var $tweetConentDiv = $("<div>",{"class": "tweetContent"});
            var $tweetPublishDate = $("<div>",{"class": "tweetPublishDate"});
            var $tweetLikesRetweets = $("<div>",{"class": "tweetLikesRetweets"});
            var $tweetLikes = $("<div>",{"class": "tweetLikes"});
			var $tweetRetweets = $("<div>",{"class": "tweetRetweets"});
            var $tweetComments = $("<div>",{"class":"tweetComments"});
            
            $tweetUserDiv.append("<p>"+data.tweets[i].user+"</p>");
            $tweetConentDiv.append("<p>"+data.tweets[i].tweetContent+"</p>");
            $tweetPublishDate.append("<p>"+data.tweets[i].publishDate+"</p>");
			$tweetLikes.append("<i class='bx bx-heart icon likeTweet'></i>");
            $tweetLikes.append("<span>"+data.tweets[i].likes+"</span>");
			$tweetRetweets.append("<i class='bx bx-sort-alt-2 icon retweet'></i>");
            $tweetRetweets.append("<span>"+data.tweets[i].retweets+"</span>");
            $tweetComments.append("<i class='bx bx-comment'></i>");
            
            $tweetLikesRetweets.append($tweetLikes);
            $tweetLikesRetweets.append($tweetRetweets);
            $tweetLikesRetweets.append($tweetComments);

            $tweetDiv.append($tweetUserDiv);
            $tweetDiv.append($tweetConentDiv);
            $tweetDiv.append($tweetPublishDate);
			$tweetDiv.append($tweetLikesRetweets);
            $(".userContainer").append($tweetDiv);
        }
     });
});


//retornar comentaris del tweet que es clica utilitzant la id del tweet
$(".container").on('click','.tweetComments',function(){
	$(".tweetCommentsContainer").empty();
	var tweetID = $(this).closest(".tweet").attr("id");
	
	parentID = tweetID;
	
	$.post(path,
	{type : "getComments" , id: tweetID}, 
	function(data, status) {
		//a data es retorna l'informacio dels commentaris del tweet que es selecciona
		console.log(data);
		 for (let i in data.tweets) {
			
			var $tweetID = data.tweets[i].id;
            var $userID = data.tweets[i].userID;
            var $tweetDiv = $("<div>", {"class": "tweet", "id":$tweetID, lang: data.tweets[i].lang});
            var $tweetUserDiv = $("<div>",{"class": "tweetUser", "id":$userID});
            var $tweetConentDiv = $("<div>",{"class": "tweetContent"});
            var $tweetPublishDate = $("<div>",{"class": "tweetPublishDate"});
            var $tweetLikesRetweets = $("<div>",{"class": "tweetLikesRetweets"});
            var $tweetLikes = $("<div>",{"class": "tweetLikes"});
			var $tweetRetweets = $("<div>",{"class": "tweetRetweets"});
            var $tweetComments = $("<div>",{"class":"tweetComments"});
            
            $tweetUserDiv.append("<p>"+data.tweets[i].user+"</p>");
            $tweetConentDiv.append("<p>"+data.tweets[i].tweetContent+"</p>");
            $tweetPublishDate.append("<p>"+data.tweets[i].publishDate+"</p>");
			$tweetLikes.append("<i class='bx bx-heart icon likeTweet'></i>");
            $tweetLikes.append("<span>"+data.tweets[i].likes+"</span>");
			$tweetRetweets.append("<i class='bx bx-sort-alt-2 icon retweet'></i>");
            $tweetRetweets.append("<span>"+data.tweets[i].retweets+"</span>");
            $tweetComments.append("<i class='bx bx-comment'></i>");
            
            $tweetLikesRetweets.append($tweetLikes);
            $tweetLikesRetweets.append($tweetRetweets);
            $tweetLikesRetweets.append($tweetComments);

            $tweetDiv.append($tweetUserDiv);
            $tweetDiv.append($tweetConentDiv);
            $tweetDiv.append($tweetPublishDate);
			$tweetDiv.append($tweetLikesRetweets);
            $(".tweetCommentsContainer").append($tweetDiv);
                
         
		}
		//tweetCommentsContainer
		//TODO insertar comentaris al tweet,
	});	
});


//get all Users except the one that is logged
$(".menu").on('click','#search',function(){
	$(".searchContainer").empty();
    $.post( path, 
        {type : "getUsers"},
        function(data, status){
			for (let i in data.users) {
				var usersID = data.users[i].userID; 
				var userDiv = $("<div>", {"class": "users", "id":usersID});
				var userNameDiv = $("<div>", {"class": "usersName"});

                userNameDiv.append("<span class='folUserName'>"+data.users[i].userName+"</span>");
				
				userDiv.append(userNameDiv);
				
				$(".searchContainer").append(userDiv);
			}   
         });
});

//search users by writing 
$("#searchInput").keyup(function(){
	let searchValue = $("#searchInput").val();
	$(".searchContainer").children('.users').each(function(){
		let usrName = $(this).children().children()[0].innerHTML;
		if(!usrName.startsWith(searchValue)){
			$(this).hide();
		}else{
			$(this).show();
		}
	});
});

$(".menu").on('click','#users',function(){
	$(".homeContent").hide();
	$(".userContent").hide();
	$(".tweetComentsContent").hide();
	$(".searchContent").hide();
	$(".usersContent").show();
});
	
$(".menu").on('click','#home',function(){
	$(".userContent").hide();
	$(".usersContent").hide();
	$(".tweetComentsContent").hide();
	$(".searchContent").hide();
	$(".homeContent").show();
});

$(".menu").on('click','#search',function(){
	$(".usersContent").hide();
	$(".homeContent").hide();
	$(".userContent").hide();
	$(".tweetComentsContent").hide();
	$(".searchContent").show();
});


$(".container").on('click','.folUserName',function(){
	$(".usersContent").hide();
	$(".homeContent").hide();
	$(".tweetComentsContent").hide();
	$(".searchContent").hide();
	$(".userContent").show();
});

$(".container").on('click','.tweetUser',function(){
	$(".usersContent").hide();
	$(".homeContent").hide();
	$(".tweetComentsContent").hide();
	$(".searchContent").hide();
	$(".userContent").show();
});


$(".container").on('click','.tweetComments',function(){
	$(".usersContent").hide();
	$(".homeContent").hide();
	$(".userContent").hide();
	$(".searchContent").hide();
	$(".tweetComentsContent").show();
});




