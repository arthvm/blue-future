import { showAlert } from "../util/alerts.js";
import { login } from "./login.js";
import { signUp } from "./signup.js";
import {
  getCurrentUser,
  saveCurrentUser,
  removeCurrentUser,
} from "../util/storage.js";

// Variáveis globais
let bool = true;
let currentUser = getCurrentUser(); // Mudança para 'let' para permitir reatribuição
let profileBar = document.getElementById("profile-sidebar");
let settingsContent = document.getElementById("settings-content");
const chatbotContent = document.getElementById("chatbot-content");
const sidebarReport = document.querySelector(".sidebar-report");

// Função para definir o formulário
function defineForm() {
  const isLoging = bool;
  const nameInput = isLoging
    ? ""
    : `
    <label for="name">Name:</label>
    <input class="account-form__input" type="text" id="name" name="name" placeholder="Enter your Name" required>
  `;

  return `
    <div class="account-form--wrapper">
      <form class="account-form" action="">
        <div class="form-input--wrapper">
          ${nameInput}
          <label for="email">Email:</label>
          <input class="account-form__input" type="text" id="email" name="email" placeholder="Enter your Email" required>
          <label for="password">Password:</label>
          <input class="account-form__input" type="password" id="password" name="password" placeholder="Enter your Password" required>
        </div>
        <button data-login="${isLoging}" class="account-form__button" type="submit">${
    isLoging ? "Login" : "Sign Up"
  }</button>
      </form>
      <p class="account-form__subtext">
        ${isLoging ? "Not registered?" : "Already has an account?"}
        <span class="subtext__link">${
          isLoging ? "Create an account here!" : "Login here!"
        }</span>
      </p>
    </div>
  `;
}

// Função para submeter o formulário de conta
async function submitAccountForm(isLogin) {
  const name = document.getElementById("name")?.value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const user = { name, email, password };
  let account;

  try {
    if (isLogin) {
      account = await login(user);
    } else {
      await signUp(user);
      account = await login(user);
    }
    saveCurrentUser(account);
    return account;
  } catch (error) {
    showAlert("Form error!", error.message);
  }
}

// Função para atualizar a barra de usuário
function changeUserBar() {
  currentUser = getCurrentUser();
  const accountHtml = currentUser
    ? `
    <div class="profile-sidebar-left">
      <img src="./assets/default-profile.webp" alt="User picture" class="profile-image" />
      <div class="profile-infos">
        <h1 class="profile-name">${currentUser.name ?? "Não Informado"}</h1>
        <p class="profile-level">Level 1</p>
      </div>
    </div>
    <div class="profile-sidebar-right">
      <img src="./assets/settings-icon.png" alt="Settings Icon" class="settings-icon settings-button" id="settings-button" />
    </div>
  `
    : `<h3>${bool ? "Login" : "Sign Up"}</h3>`;

  const settingsHtml = currentUser
    ? `
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
              currentUser.email ?? "Nao informado"
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
          <button class="logout-button">
            <img src="./assets/logout-icon.png" class="logout-icon" alt="Logout Icon" />
          </button>
        </div>
        <div class="dev-team default-section" id="dev-team">
          <a class="devtext" href="./pages/members.html">Check out the Dev team</a>
        </div>
      </div>
  `
    : defineForm();

  profileBar.innerHTML = accountHtml;
  settingsContent.innerHTML = settingsHtml;

  if (!profileBar.classList.contains("settings-active")) {
    profileBar.style.border = currentUser
      ? "none"
      : "2px solid var(--accent-color)";
    profileBar.style.justifyContent = currentUser ? "space-between" : "center";
  }

  document.querySelector(".logout-button")?.addEventListener("click", () => {
    removeCurrentUser();
    changeUserBar();
    toggleProfileBar();
  });

  document.querySelector(".subtext__link")?.addEventListener("click", () => {
    bool = !bool;
    changeUserBar();
  });

  document
    .querySelector(".account-form__button")
    ?.addEventListener("click", async (event) => {
      event.preventDefault();
      currentUser = await submitAccountForm(
        event.target.dataset.login === "true"
      );
      changeUserBar();
      toggleProfileBar();
    });
}

// Função para alternar a exibição da barra de perfil
function toggleProfileBar() {
  const chatbotWrapper = document.getElementById("chatbot-wrapper");
  settingsContent = document.getElementById("settings-content");

  if (settingsContent.classList.contains("show-settings-content")) {
    settingsContent.classList.remove("show-settings-content");
    profileBar.style.borderRadius = "1.5rem";
    profileBar.style.border = currentUser
      ? "none"
      : "3px solid var(--accent-color)";
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
    setTimeout(() => {
      settingsContent.classList.add("show-settings-content");
    }, 1);

    sidebarReport.classList.add("hidden-sidebar-report");
    chatbotWrapper.style.display = "none";
  }

  profileBar.classList.toggle("settings-active");
}

// Inicialização
profileBar.addEventListener("click", () => {
  toggleProfileBar();
});

changeUserBar();
