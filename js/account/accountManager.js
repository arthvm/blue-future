let currentUser = localStorage.getItem("curUser") ?? undefined;
let bool = true;

let profileBar = document.getElementById("profile-sidebar");
let settingsContent = document.getElementById("settings-content");

function defineForm() {
  let isLoging = bool;

  let nameInput = `
  <label for="name">
    Name:
  </label>
  <input class="account-form__input" type="text" 
                id="name" 
                name="name" 
                placeholder="Enter your Name" required>
  `;

  let formHtml = `
<div class="account-form--wrapper">
  <form class="account-form" action="">
        <div class="form-input--wrapper">
          ${isLoging ? "" : nameInput}
          <label for="email">
                Email:
          </label>
          <input class="account-form__input" type="text" 
                id="email" 
                name="email" 
                placeholder="Enter your Email" required>
          <label for="password">
                Password:
          </label>
          <input class="account-form__input" type="password"
                id="password" 
                name="password"
                placeholder="Enter your Password" required>
        </div>
        <button class = "account-form__button" type="submit">${
          isLoging ? "Login" : "Sign Up"
        }</button>
  </form>
  
  <p class="account-form__subtext">${
    isLoging ? "Not registered?" : "Already has an account?"
  }
        <span class="subtext__link">
              ${isLoging ? "Create an account here!" : "Login here!"}
        </span>
  </p>
</div>
  `;

  return formHtml;
}

function changeUserBar() {
  let accountHtml = `
    <div class="profile-sidebar-left">
        <img
        src="./assets/default-profile.webp"
        alt="User picture"
        class="profile-image"
        />

        <div class="profile-infos">
            <h1 class="profile-name">${
              currentUser?.name ?? "Não Informado"
            }</h1>
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
    `;

  let settingsHtml = `
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
                    currentUser?.email ?? "Nao informado"
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

  let formHtml = defineForm();

  profileBar.innerHTML = currentUser
    ? accountHtml
    : bool
    ? "<h3>Login</h3>"
    : "<h3>Sign Up</h3>";
  settingsContent.innerHTML = currentUser ? settingsHtml : formHtml;

  if (!profileBar.classList.contains("settings-active")) {
    profileBar.style.border = currentUser
      ? "none"
      : "2px solid var(--accent-color)";
    profileBar.style.justifyContent = currentUser ? "space-between" : "center";
  }

  let changeBtn = document.querySelector(".subtext__link");
  changeBtn?.addEventListener("click", () => {
    bool = !bool;
    changeUserBar();
  });
}

function setUser(user) {
  localStorage.setItem("curUser", user);
}

profileBar.addEventListener("click", function () {
  let chatbotWrapper = document.getElementById("chatbot-wrapper");
  settingsContent = document.getElementById("settings-content");

  if (settingsContent.classList.contains("show-settings-content")) {
    settingsContent.classList.remove("show-settings-content");
    profileBar.style.borderRadius = "1.5rem";
    profileBar.style.border = "3px solid var(--accent-color)";

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
    profileBar.style.border = "none";
    profileBar.style.borderRadius = "1.5rem 1.5rem 0rem 0rem";

    /* This timeout is used to ensure the animation loads after the settings container appears */
    setTimeout(() => {
      settingsContent.classList.add("show-settings-content");
    }, 1);

    sidebarReport.classList.add("hidden-sidebar-report");
    chatbotWrapper.style.display = "none";
  }
  profileBar.classList.toggle("settings-active");
});

changeUserBar();
