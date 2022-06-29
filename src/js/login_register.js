const container = document.querySelector(".container"),
	  pwShowHide = document.querySelectorAll(".showHidePw"),
	  pwFields = document.querySelectorAll(".password"),
	  signUp = document.querySelector(".signup-link"),
	  login = document.querySelector(".login-link"),
	  createPw = document.querySelector("#create_pw"),
	  confirmPw = document.querySelector("#confirm_pw"),
	  alertIcon = document.querySelector(".alert-error"),
	  alertText = document.querySelector(".alert-text"),
	  indicator = document.querySelector(".indicator"),
	  strengthText = document.querySelector(".strength-text"),
	  iconText = document.querySelector(".icon-text"),
	  createPw_showHidePw = document.querySelector("#createPw_showHidePw")

// Show & Hide the password in the corresponding fields, and change the icon accordingly.
pwShowHide.forEach(eyeIcon => {
	eyeIcon.addEventListener("click", ()=>{
		pwFields.forEach(pwField =>{
			if(pwField.type === "password"){
				pwField.type = "text";
				pwShowHide.forEach(icon =>{
					icon.classList.replace("uil-eye-slash", "uil-eye");
				})
			}else{
				pwField.type = "password";
				pwShowHide.forEach(icon =>{
					icon.classList.replace("uil-eye", "uil-eye-slash");
				})
			}
		})
	})
})

// Show & Hide the Login or the Registration Forms accordingly.
signUp.addEventListener("click", ()=>{
	container.classList.add("active");
});
login.addEventListener("click", ()=>{
	container.classList.remove("active");
});

// Password checker (...)
if(createPw.value == "" || confirmPw.value == ""){
	alertText.innerText = "Enter at least 6 characters";
	alertIcon.style.color = "#a6a6a6";
	alertText.style.color = "#a6a6a6";
	alertIcon.style.display = "inline";
}

createPw.addEventListener("keyup", ()=>{
	let val = createPw.value.trim()
	if(val.length >= 6){
		confirmPw.removeAttribute("disabled");
		submitBtn.removeAttribute("disabled");
		submitBtn.classList.add("active");
	}else{
		confirmPw.setAttribute("disabled", true);
		submitBtn.setAttribute("disabled", true);
		submitBtn.classList.remove("active");
		confirmPw.value = "";
		alertText.style.color = "#a6a6a6";
		alertText.innerText = "Enter at least 6 characters";
		alertIcon.style.display = "inline";
		alertIcon.style.color = "#a6a6a6";
	}
});
confirmPw.addEventListener("keyup", ()=>{
	if(createPw.value == ""){
		alertText.innerText = "Enter at least 6 characters";
		alertIcon.style.color = "#a6a6a6";
		alertText.style.color = "#a6a6a6";
		alertIcon.style.display = "inline";
	}
	else if(createPw.value === confirmPw.value){
		alertText.innerText = "Password matched";
		alertIcon.style.color = "#22C32A";
		alertText.style.color = "#22C32A";
		alertIcon.style.display = "inline";
	}else{
		alertText.innerText = "Password didn't matched";
		alertIcon.style.color = "#D93025";
		alertText.style.color = "#D93025"
		alertIcon.style.display = "inline";
	}
});

// Password strength with regex (...)
let alphabet = /[a-zA-Z]/,
	numbers = /[0-9]/,
	scharacters = /[!,@,#,$,%,^,&,*,?,_,(,),-,+,=,~]/;

if(createPw.value == ""){
	strengthText.textContent = "Password is blank";
	createPw.style.borderColor = "#a6a6a6";
	createPw_showHidePw.style.color = "#a6a6a6";
	iconText.style.color = "#a6a6a6";
	iconText.style.display = "inline";
}

createPw.addEventListener("keyup", ()=>{
	indicator.classList.add("active");

	let val = createPw.value;
	console.log(val);
	if(val.match(alphabet) || val.match(numbers) || val.match(scharacters) && val.length >= 6){
		strengthText.textContent = "Password is weak";
		createPw.style.borderColor = "#FF6333";
		createPw_showHidePw.style.color = "#FF6333";
		iconText.style.color = "#FF6333";
		iconText.style.display = "inline";
	}
	if(val.match(alphabet) && val.match(numbers) && val.length >= 8){
		strengthText.textContent = "Password is medium";
		createPw.style.borderColor = "#cc8500";
		createPw_showHidePw.style.color = "#cc8500";
		iconText.style.color = "#cc8500";
		iconText.style.display = "inline";
	}
	if(val.match(alphabet) && val.match(numbers) && val.match(scharacters) && val.length >= 12){
		strengthText.textContent = "Password is strong";
		createPw.style.borderColor = "#22C32A";
		createPw_showHidePw.style.color = "#22C32A";
		iconText.style.color = "#22C32A";
		iconText.style.display = "inline";
	}
	if(val == ""){
		strengthText.textContent = "Password is blank";
		createPw.style.borderColor = "#a6a6a6";
		createPw_showHidePw.style.color = "#a6a6a6";
		iconText.style.color = "#a6a6a6";
		iconText.style.display = "inline";
	}
});
