var faviconIndex = 5;

function setFavicon() {
  ++faviconIndex;
  faviconIndex %= 6;

  document.getElementById("favicon").href = "favicons/" + faviconIndex + ".ico"
}
