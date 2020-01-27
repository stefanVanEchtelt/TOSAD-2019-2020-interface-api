function openModal(message, redirect){
    let messageDiv = document.getElementById("message");
    let messageInfo = document.getElementById("message_info");
    let okBtn = document.getElementById("modal");
    messageInfo.innerText = message;
    messageDiv.style.display = "block";
    okBtn.onclick = function () {
        if (redirect === 'close') {
            messageDiv.style.display = "none";
        } else if (redirect === 'reload') {
            location.reload();
        } else {
            window.location = redirect;
        }
    }
}