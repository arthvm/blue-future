const alertTitle = document.getElementById("alert-title");
const alertDescription = document.getElementById("alert-description");
const alertPopup = document.getElementById("alert-popup");

function showAlert(title, description) {
  alertTitle.textContent = title;
  alertDescription.textContent = description;

  alertPopup.classList.remove("remove-popup");

  /* This timeout is used to ensure the animation loads when the alert pop up appears */
  setTimeout(() => {
    alertPopup.classList.add("show-popup");
  }, 1);

  /* This timeout is used to remove the alert pop up after 10 seconds */
  setTimeout(() => {
    alertPopup.classList.remove("show-popup");

    /* This timeout is used to remove the alert pop up after the animation ends to prevent the "ghost space" */
    setTimeout(() => {
      alertPopup.classList.add("remove-popup");
    }, 501);
  }, 10000);
}

export { showAlert };
