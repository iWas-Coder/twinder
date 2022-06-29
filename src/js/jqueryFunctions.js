const path = "/"+window.location.pathname.split("/")[1]+"/TweetsController";
var userID;
var userName;
var isAdmin;
var parentID;


$(document).ready(function(){
    $.ajaxSetup({ chache: false});
   	$(".followingContent").hide();
   	$(".followersContent").hide();
   	$(".searchContent").hide();
   	$(".profileContent").hide();
   	$(".userContent").hide();
   	$(".tweetComentsContent").hide();
   	userName = $(".name").text();
	$.ajax({
    type: "POST",
    url: path,
    async: false,
    data: {type:"getUserId", name: userName},
    	success: function(data,status){
        	userID = data.userID;
    	}
	});
	$.ajax({
        type: "POST",
        url: path,
        async: false,
        data: {type:"isAdmin", userID: userID},
            success: function(data,status){
                isAdmin = data.isAdmin;
            }
        }); 
	
});


//insereix tweets que tenen el parent com a null (tweets originals)
$(document).ready(function(){
    $.post( path, 
        {type : "getTimeline" , userID: userID},
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
				if(isAdmin === true){
					$tweetLikesRetweets.append("<i class='bx bx-trash icon deleteTweet'></i>");
				}

                $tweetDiv.append($tweetUserDiv);
                $tweetDiv.append($tweetConentDiv);
                $tweetDiv.append($tweetPublishDate);
				$tweetDiv.append($tweetLikesRetweets);
                $(".homeContainer").append($tweetDiv);
            }
         });
});

//retornar comentaris del tweet que es clica utilitzant la id del tweet
$(".container").on('click','.tweetComments',function(){
	$(".tweetCommentsContainer").empty();
	var tweetID = $(this).closest(".tweet").attr("id");
	
	//post comment input text
	var $postCommentDiv = (`<div class='input-tweet'><input type='text' id='commentTweet' placeholder='Enter comment...' name='input-tweet'  maxlength='300'> </div> <div class='commentButton'><i class='bx bx-send' ></i></div>`);
	parentID = tweetID;
	$(".tweetCommentsContainer").append($postCommentDiv);
	
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
				if(isAdmin === true){
					$tweetLikesRetweets.append("<i class='bx bx-trash icon deleteTweet'></i>");
				}

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

$(".container").on('click','.commentButton',function(){
	var value = $("#commentTweet").val();
	if(value !== ""){
		$("#commentTweet").val("");
		$("#commentTweet").attr("placeholder", "Enter comment...");
	
		$.post(path,
			{type: "publishTweet", tweet: JSON.stringify({tweet:{userID:userID,parentID:parentID,content: value, lang:"eng"}})},
			function(data, status){
				console.log(status);
				location.reload();
		});
	}
	
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



//get all Users except the one that is logged
$(".menu").on('click','#search',function(){
	$(".searchContainer").empty();
    $.post( path, 
        {type : "getUnfollowedUsers",userID: userID},
        function(data, status){
			for (let i in data.users) {
				var usersID = data.users[i].userID; 
				var userDiv = $("<div>", {"class": "users", "id":usersID});
				var userNameDiv = $("<div>", {"class": "usersName"});

                userNameDiv.append("<span class='folUserName'>"+data.users[i].userName+"</span>");
				userNameDiv.append("<div class='followButton socialButtons'>follow</div>");
				
				userDiv.append(userNameDiv);
				
				$(".searchContainer").append(userDiv);
			}   
         });
});

//follow user on click follow button
$(".searchContent").on('click','.followButton',function(){
	var toFollowID = $(this).closest(".users").attr('id');
	$.post(path,
		{type: "followUser", userID: userID, toFollowID: toFollowID},
		 function(data, status){
			console.log(status);
			location.reload();
	});
});

//get followers
$(".menu").on('click','#followers',function(){
	$(".followersContainer").empty();
    $.post( path, 
        {type : "getFollowers",userID: userID},
        function(data, status){
			for (let i in data.users) {
				var userID2 = data.users[i].userID; 
				var userDiv = $("<div>", {"class": "users", "id":userID2});
				var userNameDiv = $("<div>", {"class": "usersName"});

                userNameDiv.append("<span class='folUserName'>"+data.users[i].userName+"</span>");

				userDiv.append(userNameDiv);
				$(".followersContainer").append(userDiv);
			}  
         });
});

//get users that is following
$(".menu").on('click','#following',function(){
	$(".followingContainer").empty();
    $.post( path, 
        {type : "getFollowing",name: userName},
        function(data, status){
			for (let i in data.users) {
				var userID2 = data.users[i].userID; 
				var userDiv = $("<div>", {"class": "users", "id":userID2});
				var userNameDiv = $("<div>", {"class": "usersName"});

                userNameDiv.append("<span class='folUserName'>"+data.users[i].userName+"</span>");
                userNameDiv.append("<div class='unfollowButton socialButtons'>unfollow</div>");

				userDiv.append(userNameDiv);
				$(".followingContainer").append(userDiv);
			}  
         });
});

//mostra tweets del usuari logejat
$(".menu").on('click','#profile',function(){
	$(".profileContainer").empty();
	$(".profileContainer").append("<p class='userNameTitle'>"+ userName +"</p>");
    $.post( path, 
        {type : "getMyTweets" , userID: userID},
        function(data, status){
            for (let i in data.tweets) {
                var $tweetID = data.tweets[i].id;
        		var $userID = data.tweets[i].userID;
				var $tweetDiv = $("<div>", {"class": "tweet", "id":$tweetID, lang: data.tweets[i].lang});
                var $tweetUserDiv = $("<div>",{"class": "tweetUserP", "id":$userID});
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
				if(isAdmin === true){
					$tweetLikesRetweets.append("<i class='bx bx-trash icon deleteTweet'></i>");
				}

                $tweetDiv.append($tweetUserDiv);
                $tweetDiv.append($tweetConentDiv);
                $tweetDiv.append($tweetPublishDate);
				$tweetDiv.append($tweetLikesRetweets);
                $(".profileContainer").append($tweetDiv);
            }
         });
});

$(".container").on('click','.folUserName',function(){
	$(".followingContent").hide();
	$(".homeContent").hide();
	$(".followersContent").hide();
	$(".searchContent").hide();
	$(".profileContent").hide();
	$(".tweetComentsContent").hide();
	$(".userContent").show();
});

//show profile when click on name on following, followers or search
$(".container").on('click','.folUserName',function(event){
	$(".userContainer").empty();
	
	var otherUserID = $(this).closest(".users").attr("id");
	var username = $(event.target).text();
	var userDiv = $("<div>", {class:'userNameTitle',id:otherUserID});
	userDiv.append("<span>" + username + "</span>");
	if(isAdmin){
		console.log("hola");
		userDiv.append("<i class='bx bxs-user-x icon deleteUser'></i>");
	}
	
	$(".userContainer").append(userDiv);
	
	
	$.post( path, 
    {type : "getMyTweets" , userID: otherUserID},
    function(data, status){
        for (let i in data.tweets) {
        	var $tweetID = data.tweets[i].id;
        		var $userID = data.tweets[i].userID;
				var $tweetDiv = $("<div>", {"class": "tweet", "id":$tweetID, lang: data.tweets[i].lang});
                var $tweetUserDiv = $("<div>",{"class": "tweetUserP", "id":$userID});
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
				if(isAdmin === true){
					$tweetLikesRetweets.append("<i class='bx bx-trash icon deleteTweet'></i>");
				}

                $tweetDiv.append($tweetUserDiv);
                $tweetDiv.append($tweetConentDiv);
                $tweetDiv.append($tweetPublishDate);
				$tweetDiv.append($tweetLikesRetweets);
	            $(".userContainer").append($tweetDiv);
        }
     });
});


//show profile when click on tweet user
$(".container").on('click','.tweetUser',function(event){
	
	$(".userContainer").empty();
	var otherUserID = $(this).attr("id");
	var username = $(event.target).text();
	var userDiv = $("<div>", {class:'userNameTitle',id:otherUserID});
	userDiv.append("<span>" + username + "</span>");
	if(isAdmin){
		userDiv.append("<i class='bx bxs-user-x icon deleteUser'></i>");
	}
	
	$(".userContainer").append(userDiv);
	
    $.post( path, 
        {type : "getMyTweets" , userID: otherUserID},
        function(data, status){
            for (let i in data.tweets) {
                var $tweetID = data.tweets[i].id;
        		var $userID = data.tweets[i].userID;
				var $tweetDiv = $("<div>", {"class": "tweet", "id":$tweetID, lang: data.tweets[i].lang});
                var $tweetUserDiv = $("<div>",{"class": "tweetUserP", "id":$userID});
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
				if(isAdmin === true){
					$tweetLikesRetweets.append("<i class='bx bx-trash icon deleteTweet'></i>");
				}

                $tweetDiv.append($tweetUserDiv);
                $tweetDiv.append($tweetConentDiv);
                $tweetDiv.append($tweetPublishDate);
				$tweetDiv.append($tweetLikesRetweets);
                $(".userContainer").append($tweetDiv);
            }
         });
});

$(".menu").on('click','#following',function(){
	$(".homeContent").hide();
	$(".followersContent").hide();
	$(".searchContent").hide();
	$(".profileContent").hide();
	$(".userContent").hide();
	$(".tweetComentsContent").hide();
	$(".followingContent").show();
});
	
$(".menu").on('click','#home',function(){
	$(".followingContent").hide();
	$(".followersContent").hide();
	$(".searchContent").hide();
	$(".profileContent").hide();
	$(".userContent").hide();
	$(".tweetComentsContent").hide();
	$(".homeContent").show();
});

$(".menu").on('click','#followers',function(){
	$(".followingContent").hide();
	$(".homeContent").hide();
	$(".searchContent").hide();
	$(".profileContent").hide();
	$(".userContent").hide();
	$(".tweetComentsContent").hide();
	$(".followersContent").show();
});

$(".menu").on('click','#search',function(){
	$(".followingContent").hide();
	$(".homeContent").hide();
	$(".followersContent").hide();
	$(".profileContent").hide();
	$(".userContent").hide();
	$(".tweetComentsContent").hide();
	$(".searchContent").show();
});

$(".menu").on('click','#profile',function(){
	$(".followingContent").hide();
	$(".homeContent").hide();
	$(".followersContent").hide();
	$(".searchContent").hide();
	$(".userContent").hide();
	$(".tweetComentsContent").hide();
	$(".profileContent").show();
});

$(".container").on('click','.tweetUser',function(){
	$(".followingContent").hide();
	$(".homeContent").hide();
	$(".followersContent").hide();
	$(".searchContent").hide();
	$(".profileContent").hide();
	$(".tweetComentsContent").hide();
	$(".userContent").show();
	
});
$(".container").on('click','.tweetComments',function(){
	$(".followingContent").hide();
	$(".homeContent").hide();
	$(".followersContent").hide();
	$(".searchContent").hide();
	$(".profileContent").hide();
	$(".userContent").hide();
	$(".tweetComentsContent").show();
});

$(".container").on('click','.tweetButton',function(){
	var value = $("#postTweet").val();
	console.log(value);
	if(value !== ""){
		$("#postTweet").val("");
		$("#postTweet").attr("placeholder", "Enter tweet...");
	
		$.post(path,
			{type: "publishTweet", tweet: JSON.stringify({tweet:{userID:userID,parentID:-1,content: value, lang:"eng"}})},
			function(data, status){
				console.log(status);
				location.reload();
		});
	}
	
});

$(".followingContent").on('click','.unfollowButton',function(){
	var followedID = $(this).closest(".users").attr('id');
	$.post(path,
		{type: "unfollowUser", userID: userID, followedID: followedID},
		 function(data, status){
			console.log(status);
			location.reload();
		});
});

//delete tweet
$(".container").on('click','.deleteTweet',function(){
	var tweetID= $(this).closest(".tweet").attr("id");
	$.post(path,
		{type: "deleteTweet", tweetID:tweetID},
		 function(data, status){
			console.log(status);
			location.reload();
	});
});

$(".container").on('click','.deleteUser',function(){
	var userID= $(this).closest(".userNameTitle").attr("id");
	$.post(path,
		{type: "deleteUser", userID:userID},
		 function(data, status){
			console.log(status);
			location.reload();
	});
});

$(".container").on('click','.likeTweet',function(){
	var tweetID= $(this).closest(".tweet").attr("id");
	$.post(path, 
	{type: "likeTweet", tweetID:tweetID},
	function(data, status){
		location.reload();
	});
});

$(".container").on('click','.retweet',function(){
	var tweetID= $(this).closest(".tweet").attr("id");
	$.post(path, 
	{type: "retweet", tweetID:tweetID},
	function(data, status){
		location.reload();
	});
});


