let storedLogin = null;
let storedHostname = null;


// Obsługa pierwszego kroku logowania - zapis loginu
chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
  if (message.type === "login_step") {
    console.log("dupa");
    storedLogin = message.login;
    storedHostname = message.hostname;
    console.log(storedHostname);
  } 
  else if (message.type === "password_step" && storedLogin) {
    // Mamy login i hasło - wywołujemy niestandardowy pop-up
    chrome.storage.local.set({ 
      login: storedLogin,
      password: message.password,
      hostname: storedHostname
    });

    chrome.runtime.sendMessage({
      type: "show_popup",
      login: storedLogin,
      password: message.password,
      hostname: storedHostname
    });

    // Czyścimy tymczasowe przechowywanie danych
    storedLogin = null;
    storedHostname = null;
  }
});
