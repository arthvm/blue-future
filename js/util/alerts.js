const alertTitle = document.getElementById("alert-title");
const alertDescription = document.getElementById("alert-description");
const alertPopup = document.getElementById("alert-popup");

function showAlert(title, description) {
  alertTitle.textContent = title;
  alertDescription.textContent = description;

  alertPopup.classList.remove("remove-popup");

  setTimeout(() => {
    alertPopup.classList.add("show-popup");
  }, 1);

  setTimeout(() => {
    alertPopup.classList.remove("show-popup");

    setTimeout(() => {
      alertPopup.classList.add("remove-popup");
    }, 501);
  }, 5000);
}

export { showAlert };
