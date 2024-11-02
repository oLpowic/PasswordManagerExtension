let storedLogin = null;
let storedHostname = null;

console.log(storedHostname);
// Obsługa pierwszego kroku logowania - zapis loginu
chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
  if (message.type === "login_step") {
    console.log("dupa");
    storedLogin = message.login;
    storedHostname = new URL(message.url).hostname;
  } 
  else if (message.type === "password_step" && storedLogin) {
    // Mamy login i hasło - wywołujemy niestandardowy pop-up
    chrome.storage.local.set({ 
      login: storedLogin,
      password: message.password,
      hostname: storedHostname
    });

    console.log(storedLogin);
    chrome.windows.create({
      url: "html/save_prompt.html",
      type: "popup",
      width: 400,
      height: 300
    });

    // Czyścimy tymczasowe przechowywanie danych
    storedLogin = null;
    storedHostname = null;
  }
});
