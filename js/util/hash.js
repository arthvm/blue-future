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

export { makeHash };
