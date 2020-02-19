window.onload = function() {
  setInterval(setFavicon, 500);

  document.getElementById("siteLogo").onclick = function() {
    window.location.href = "/";
  }
}
