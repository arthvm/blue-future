import { makeHash } from "../util/hash.js";
import { getUsers, saveUsers } from "../util/storage.js";
import { isEmailValid, isPasswordValid } from "../util/validation.js";

async function signUp({ name, username, email, password }) {
  const users = getUsers();

  if (!isEmailValid(email))
    throw new Error(`Email (${email}) format is invalid!`);
  if (!isPasswordValid(password)) throw new Error("Invalid password format.");

  const userExists = users.some(
    (user) =>
      user.email === email || (user.username && user.username === username)
  );
  if (userExists) throw new Error("User already exists!");

  const hashObj = await makeHash(password);
  const newUser = { name, username, email, hashObj };

  users.push(newUser);
  saveUsers(users);

  console.log("User signed up:", newUser);
  return newUser;
}

export { signUp };
