function getUsers() {
  return JSON.parse(localStorage.getItem("users")) ?? [];
}

function saveUsers(users) {
  localStorage.setItem("users", JSON.stringify(users));
}

function getCurrentUser() {
  return JSON.parse(localStorage.getItem("curUser"));
}

function saveCurrentUser(user) {
  localStorage.setItem("curUser", JSON.stringify(user));
}

export { getUsers, saveUsers, getCurrentUser, saveCurrentUser };
