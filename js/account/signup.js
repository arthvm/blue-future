let users = JSON.parse(localStorage.getItem("users")) ?? [];

async function signUp(signingUser) {
  let { name, username = undefined, email, password } = signingUser;

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) throw new Error("Email format is invalid!");

  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d\S]{8,}$/;
  if (!passwordRegex.test(password))
    throw new Error(
      "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit."
    );

  let hasAccount =
    users.length == 0
      ? false
      : !users.every((user) => {
          if (user["email"] == email) {
            return false;
          }

          if (user["username"] != null && user["username"] == username) {
            return false;
          }

          return true;
        });

  if (hasAccount) throw new Error("User already exists!");

  const hashObj = await makeHash(password, undefined);

  const newUser = {
    name,
    username,
    email,
    hashObj,
  };

  users.push(newUser);
  localStorage.setItem("users", JSON.stringify(users));
}

async function makeHash(password, useSalt) {
  const salt =
    useSalt != null
      ? new Uint8Array([...atob(useSalt)].map((char) => char.charCodeAt(0)))
      : window.crypto.getRandomValues(new Uint8Array(16));

  const passwordBuffer = new TextEncoder().encode(password);

  const key = await window.crypto.subtle.importKey(
    "raw",
    passwordBuffer,
    { name: "PBKDF2" },
    false,
    ["deriveBits"]
  );

  const derivedKeys = await window.crypto.subtle.deriveBits(
    {
      name: "PBKDF2",
      salt,
      iterations: 10000,
      hash: "SHA-512",
    },
    key,
    256
  );

  const hashedPassword = Array.from(new Uint8Array(derivedKeys))
    .map((byte) => byte.toString(16).padStart(2, "0"))
    .join("");

  const saltBase64 = btoa(String.fromCharCode.apply(null, salt));

  const hashObj = {
    salt: saltBase64,
    hashedPassword,
  };

  return hashObj;
}

export { signUp, makeHash };