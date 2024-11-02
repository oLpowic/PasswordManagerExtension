document.addEventListener("DOMContentLoaded", async () => {
  const { login, password, url } = await chrome.storage.local.get(["login", "password", "url"]);

  // Ustaw wartości w polach tekstowych
  document.getElementById("login").value = login;
  document.getElementById("password").value = password;
  document.getElementById("url").value = url;

  document.getElementById("save").addEventListener("click", () => {
    // Pobierz zaktualizowane wartości
    const updatedLogin = document.getElementById("login").value;
    const updatedPassword = document.getElementById("password").value;
    const updatedUrl = document.getElementById("url").value;

    // Szyfrowanie i zapis
    //saveToDatabase({ login: updatedLogin, password: updatedPassword, url: updatedUrl });
    window.close();
  });

  document.getElementById("cancel").addEventListener("click", () => {
    window.close();
  });
});

function saveToDatabase(credentials) {
  fetch("https://example.com/save_credentials", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(credentials)
  });
}
