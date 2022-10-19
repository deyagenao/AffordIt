const accountNoSpan = document.getElementById("account-no");
const toggleAccBtn = document.getElementById("toggle-acc-no");

function toggleAccountNoDisplay(){
  if(accountNoSpan.style.display === "none"){
    accountNoSpan.style.display = "inline";
    toggleAccBtn.textContent = "Hide";
  } else{
    accountNoSpan.style.display = "none";
    toggleAccBtn.textContent = "Show";
  }
}

toggleAccBtn.addEventListener("click", toggleAccountNoDisplay);