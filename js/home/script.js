import { showAlert } from "../util/alerts.js";
import { showLoadingScreen } from "../util/loadScreen.js";

const sidebar = document.getElementById("sidebar");
const mapWrapper = document.getElementById("map-wrapper");
const hamburgerIcon = document.getElementById("hamburger-icon");
const closeIcon = document.getElementById("close-icon");
const severityDropdown = document.getElementById("severity-dropdown");
const dropdownContent = document.getElementById("dropdown-content");
const rotateIcon = document.getElementById("rotate-icon");
const severityOptions = document.querySelectorAll(".dropdown-option");
const chatbotButton = document.getElementById("chatbot-button");
const chatbotContent = document.getElementById("chatbot-content");
const sidebarReport = document.querySelector(".sidebar-report");
const chatbotDropdownIcon = document.getElementById("chatbot-dropdown-icon");
const sendReportButton = document.getElementById("send-report");
const descriptionInput = document.getElementById("description");
const selectedSeverity = document.getElementById("selected-severity");
const iframeMap = document.getElementById("iframe-map");
const checkboxCollected = document.getElementById("collected");

/*
SHOW LOADING SCREEN
SHOW LOADING SCREEN
*/

showLoadingScreen();

/*
SIDEBAR TOGGLE
SIDEBAR TOGGLE
*/

/* Open sidebar on desktop when the page loads */
if (document.documentElement.clientWidth >= 992) {
  sidebar.style.display = "flex";
  sidebar.classList.remove("hidden");
  mapWrapper.classList.remove("full-width");
  mapWrapper.classList.add("reduced-width");
  hamburgerIcon.style.display = "none";
  closeIcon.style.display = "block";
}

/* When the hamburger icon is clicked, the sidebar appears */
hamburgerIcon.addEventListener("click", () => {
  sidebar.style.display = "flex";

  /* If the screen width is greater than 992px, the sidebar slides horizontally */
  if (document.documentElement.clientWidth >= 992) {
    sidebar.classList.remove("hidden");
    mapWrapper.classList.remove("full-width");
    mapWrapper.classList.add("reduced-width");
    hamburgerIcon.style.display = "none";
    closeIcon.style.display = "block";
  } else {
    sidebar.classList.remove("sidebar-slide-out-vertical");
    sidebar.classList.add("sidebar-slide-in-vertical");

    /* This timeout is used to ensure the sidebar appears after the animation loads */
    setTimeout(() => {
      sidebar.classList.remove("hidden");
      hamburgerIcon.style.display = "none";
      closeIcon.style.display = "block";
    }, 1);
  }
});

/* When the close sidebar icon is clicked, the sidebar disappears */
closeIcon.addEventListener("click", () => {
  mapWrapper.classList.add("full-width");
  mapWrapper.classList.remove("reduced-width");

  /* If the screen width is greater than 992px, the sidebar slides horizontally */
  if (document.documentElement.clientWidth >= 992) {
    sidebar.classList.add("hidden");
    closeIcon.style.display = "none";
    hamburgerIcon.style.display = "block";

    /* This timeout is used to ensure the sidebar disappears after the animation finishes */
    setTimeout(() => {
      sidebar.style.display = "none";
    }, 300);
  } else {
    sidebar.classList.remove("sidebar-slide-in-vertical");
    sidebar.classList.add("sidebar-slide-out-vertical");

    /* This timeout is used to ensure the sidebar disappears after the animation finishes */
    setTimeout(() => {
      sidebar.style.display = "none";
      sidebar.classList.add("hidden");
      closeIcon.style.display = "none";
      hamburgerIcon.style.display = "block";
      sidebar.classList.remove("sidebar-slide-out-vertical");
    }, 400);
  }
});

/*
SEVERITY DROPDOWN
SEVERITY DROPDOWN
*/

/* When the severity button is clicked, the dropdown content appears */
severityDropdown.addEventListener("click", function () {
  if (severityDropdown.classList.contains("not-allowed-cursor")) {
    return;
  } else {
    dropdownContent.classList.toggle("show-dropdown-content");
    severityDropdown.classList.toggle("dropdown-active");
    rotateIcon.classList.toggle("rotate-icon");
  }
});

/* Select Low, Medium and High severity, and change the button according to the option selected */
severityOptions.forEach((option) => {
  option.addEventListener("click", function (event) {
    event.preventDefault();
    const severity = this.textContent.trim();
    severityDropdown.className = "dropdown-button";
    severityDropdown.classList.add(`severity-${severity.toLowerCase()}`);
    selectedSeverity.innerHTML = `
            <img src="./assets/severity-icon.png" alt="" class="severity-icon">
            ${severity} Severity
        `;
    dropdownContent.classList.remove("show-dropdown-content");
    severityDropdown.classList.remove("dropdown-active");
    rotateIcon.classList.remove("rotate-icon");
  });
});

/*
CHATBOT TOGGLE
CHATBOT TOGGLE
*/

/* When the chatbot dropdown is clicked, toggle the chatbot content and hide the sidebar report */
chatbotButton.addEventListener("click", function () {
  if (chatbotContent.classList.contains("show-chatbot-content")) {
    chatbotContent.classList.remove("show-chatbot-content");

    /* This timeout is used to ensure the report container appears after the chatbot container closes */
    setTimeout(() => {
      chatbotContent.style.display = "none";

      sidebarReport.classList.remove("hidden-sidebar-report");
    }, 400);
  } else {
    chatbotContent.style.display = "flex";

    /* This timeout is used to ensure the animation loads after the chatbot container appears */
    setTimeout(() => {
      chatbotContent.classList.add("show-chatbot-content");
    }, 1);

    sidebarReport.classList.add("hidden-sidebar-report");
  }
  chatbotButton.classList.toggle("chatbot-active");
  chatbotDropdownIcon.classList.toggle("rotate-icon");
});

/*
REPORT SENDING
REPORT SENDING
*/

/* When the send report button is clicked, send the report to the map endpoint */
sendReportButton.addEventListener("click", function () {
  const description = descriptionInput.value.trim();
  const severity = selectedSeverity.textContent
    .trim()
    .split(" ")[0]
    .toLowerCase();

  /* If the description is empty or the severity is not selected, alert the user and do not send the request */
  if (description === "" || severity === "Severity") {
    showAlert(
      "Missing fields",
      "Please provide a description and select a severity level before submitting."
    );
    return;
  }

  blockReportButton();

  function sendReport(position) {
    const lat = position.coords.latitude;
    const lng = position.coords.longitude;
    const collected = checkboxCollected.checked;
    const user = localStorage.getItem?.("curUser").name ?? "Anonymous";

    /* JSON data to be sent to the map endpoint */
    const reportData = {
      user,
      description,
      severity,
      collected,
      lat,
      lng,
    };

    /* Send the report to the map endpoint via POST request */
    fetch("https://bluefuture-trashmap.onrender.com/report", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "*/*",
        "Accept-Encoding": "gzip, deflate, br",
        Connection: "keep-alive",
      },
      body: JSON.stringify(reportData),
    }).then((response) => {
      if (response.status === 200) {
        blockReportForm();
      } else if (response.status === 400) {
        showAlert(
          "Position error",
          "Your report could not be sent because you are not in the water."
        );
        unblockReportButton();
      } else {
        showAlert(
          "Unknown error",
          "An error occurred while sending your report. Please try again later."
        );
        unblockReportButton();
      }
    });
  }

  /* Show an error message if the user denies the location permission or an unknown error occurs */
  function showError(error) {
    switch (error.code) {
      case error.PERMISSION_DENIED:
        showAlert(
          "Permission error",
          "User denied the permission for the device location."
        );
        unblockReportButton();
        break;
      case error.POSITION_UNAVAILABLE:
        showAlert("Position error", "Location information is unavailable.");
        unblockReportButton();
        break;
      case error.TIMEOUT:
        showAlert("Timed out", "The request to get user location timed out.");
        unblockReportButton();
        break;
      case error.UNKNOWN_ERROR:
        showAlert("Unknown error", "An unknown error occurred.");
        unblockReportButton();
        break;
    }
  }

  /* Get the user's location */
  function getLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(sendReport, showError);
    } else {
      showAlert("Device error", "Geolocation is not supported by your browser");
    }
  }

  getLocation();
});

/*
REPORT FORM AND BUTTON BLOCK/UNBLOCK
REPORT FORM AND BUTTON BLOCK/UNBLOCK
*/

/* Block the report form and change the submit button style after the report is sent */
function blockReportForm() {
  sendReportButton.classList.add("button-report-sent");
  sendReportButton.classList.remove("send-button");
  sendReportButton.classList.add("not-allowed-cursor");
  descriptionInput.classList.add("not-allowed-cursor");
  severityDropdown.classList.add("not-allowed-cursor");
  checkboxCollected.classList.add("not-allowed-cursor");
  sendReportButton.innerHTML = `Report Sent <img src="./assets/report-sent.png" alt="" class="send-icon">`;
  descriptionInput.disabled = true;
  severityDropdown.disabled = true;
  checkboxCollected.disabled = true;
  iframeMap.src = iframeMap.src;
}

/* Block the report button when the report is being sent to prevent spam POST requests */
function blockReportButton() {
  sendReportButton.classList.add("not-allowed-cursor");
  sendReportButton.disabled = true;
}

/* Unblock the report button when the report is not sent to allow the user send it again */
function unblockReportButton() {
  sendReportButton.classList.remove("not-allowed-cursor");
  sendReportButton.disabled = false;
}
