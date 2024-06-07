import { makeHash } from "../util/hash.js";
import { getUsers } from "../util/storage.js";

async function login({ username, email, password }) {
  const users = getUsers();

  const user = users.find(
    (user) =>
      user.email === email || (user.username && user.username === username)
  );
  if (!user) throw new Error("User does not exist!");

  const hashObj = await makeHash(password, user.hashObj.salt);
  if (hashObj.hashedPassword !== user.hashObj.hashedPassword)
    throw new Error("Invalid password!");

  console.log("User logged in:", user);
  return user;
}

export { login };
