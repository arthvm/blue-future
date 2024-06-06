let currentUser = localStorage.getItem("curUser") ?? undefined;

const settingsButton = document.querySelector(".settings--wrapper");
let settingsContent = document.getElementById("settings-content");

function changeUserBar() {
  let hasUserContent = `
    <div class="profile-sidebar" id="profile-sidebar">
        <div class="profile-sidebar-left">
            <img
            src="./assets/default-profile.webp"
            alt="User picture"
            class="profile-image"
            />

            <div class="profile-infos">
            <h1 class="profile-name">${currentUser?.name ?? undefined}</h1>
            <p class="profile-level">Level 5</p>
            </div>
        </div>
        <div class="profile-sidebar-right">
            <img
                src="./assets/settings-icon.png"
                alt="Settings Icon"
                class="settings-icon settings-button"
                id="settings-button"
            />
            </div>
        </div>
          <div class="settings-content" id="settings-content">
            <div class="achievements--wrapper">
              <div class="options__content">
                <h2 class="content__title">Achievements</h2>
                <hr class="content__divider" />
            </div>
            <div class="achievements-wrapper">
                <div class="achievements--container">
                  <img src="./assets/paper-icon.png" alt="Paper icon" />
                  <h3 class="achievements-title">A Better Future</h3>
                </div>
                <div class="achievements--container">
                  <img src="./assets/landscape-icon.png" alt="Paper icon" />
                  <h3 class="achievements-title">Traveler</h3>
                </div>
            </div>
            </div>
            <div class="settings-options">
              <div class="default-section">
                <div class="email-text">
                  <p class="sectiontitle text">E-mail</p>
                  <p class="description-text">${
                    currentUser?.email ?? undefined
                  }</p>
                </div>
                <button class="default-button">Change</button>
              </div>
              <div class="default-section">
                <div class="password-text">
                  <p class="sectiontitle text">Password</p>
                  <p class="description-text">********</p>
                </div>
                <button class="default-button">Change</button>
              </div>
              <div class="default-section">
                <div class="delete-text">
                  <p class="sectiontitle">Logout</p>
                  <p class="description-text"></p>
                </div>
                <button class="delete-button">
                  <img src="./assets/settings-icon.png" alt="Logout Icon" />
                </button>
              </div>
              <div class="dev-team default-section" id="dev-team">
                <a class="devtext" href="./pages/members.html"
                  >Check out the Dev team</a
                >
              </div>
            </div>
    </div>
    `;

  let signupContent = `
    <div class="profile-sidebar" id= "profile-sidebar">
        <h3>Sigup or Login</h3>
    </div>
    <div class="settings-content" id="settings-content">
    
    </div>
  `;

  settingsButton.innerHTML =
    currentUser != null ? hasUserContent : signupContent;

  document.getElementById("profile-sidebar").style.border = currentUser
    ? "none"
    : "2px solid var(--accent-color)";
  document.getElementById("profile-sidebar").style.justifyContent = currentUser
    ? "space-between"
    : "center";
}

function setUser(user) {
  localStorage.setItem("curUser", user);
}

settingsButton.addEventListener("click", function () {
  let chatbotWrapper = document.getElementById("chatbot-wrapper");
  let sideBar = document.getElementById("profile-sidebar");
  settingsContent = document.getElementById("settings-content");

  if (settingsContent.classList.contains("show-settings-content")) {
    settingsContent.classList.remove("show-settings-content");
    sideBar.style.borderRadius = "1.5rem";
    sideBar.style.border = "3px solid var(--accent-color)";

    /* This timeout is used to ensure the animation loads before the settings container disapears */
    setTimeout(() => {
      settingsContent.style.display = "none";
    }, 400);

    if (!chatbotContent.classList.contains("show-chatbot-content")) {
      sidebarReport.classList.remove("hidden-sidebar-report");
    }

    chatbotWrapper.style.display = "flex";
  } else {
    settingsContent.style.display = "flex";
    sideBar.style.border = "none";
    sideBar.style.borderRadius = "1.5rem 1.5rem 0rem 0rem";

    /* This timeout is used to ensure the animation loads after the settings container appears */
    setTimeout(() => {
      settingsContent.classList.add("show-settings-content");
    }, 1);

    sidebarReport.classList.add("hidden-sidebar-report");
    chatbotWrapper.style.display = "none";
  }
  profileContainer.classList.toggle("settings-active");
});

changeUserBar();
