chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
    if (message.type === "show_popup") {
      const loginDisplay = document.getElementById("loginDisplay");
      const passwordDisplay = document.getElementById("passwordDisplay");
  
      loginDisplay.textContent = `Login: ${message.login}`;
      passwordDisplay.textContent = `Password: ${message.password}`;
    }
  });