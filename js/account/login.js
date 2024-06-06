import { makeHash } from "./signup.js";

let users = JSON.parse(localStorage.getItem("users")) ?? [];

async function login(loginUser) {
  let { username = undefined, email, password } = loginUser;

  let userAccount;
  let hasAccount =
    users.length == 0
      ? false
      : !users.every((user) => {
          if (user["email"] == email) {
            userAccount = user;
            return false;
          }

          if (user["username"] != null && user["username"] == username) {
            userAccount = user;
            return false;
          }

          return true;
        });

  if (!hasAccount) throw new Error("User does not exists!");

  let loginHashSalt = await makeHash(
    password,
    userAccount.hashObj?.salt ?? undefined
  );

  console.log(loginHashSalt.hash);
  if (!(loginHashSalt.hashedPassword === userAccount.hashObj?.hashedPassword))
    throw new Error("Invalid password!");

  return userAccount;
}

export { login };
