const modalPopup = document.getElementById("modal-popup");

function showLoadingScreen(title, description) {

    modalPopup.classList.remove("remove-modal");
    modalPopup.classList.add("show-modal");

    setTimeout(() => {
        modalPopup.classList.remove("show-modal");
        modalPopup.classList.add("remove-modal");
    }, 5000);

}

export { showLoadingScreen };