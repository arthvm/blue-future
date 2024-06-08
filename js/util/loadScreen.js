const modalPopup = document.getElementById("modal-popup");

function showLoadingScreen() {

    modalPopup.classList.remove("remove-modal");
    modalPopup.classList.add("show-modal");

    setTimeout(() => {
        modalPopup.classList.remove("show-modal");
        modalPopup.classList.add("remove-modal");

        setTimeout(() => {
            modalPopup.style.display = "none";
        }, 501)
    }, 3000);

}

export { showLoadingScreen };