<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.bgrima.server.models.Rhyme"%>
<!doctype html>

<html>

<head>
  <meta charset = "utf-8">

  <title>БГ Рима</title>

  <link id = "favicon" rel = "shortcut icon" type = "image/ico" href = "favicons/0.ico"></link>
  <link rel = "stylesheet" type = "text/css" href = "styles/resetsheet.css"></link>
  <link rel = "stylesheet" type = "text/css" href = "styles/main.css"></link>

  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
        crossorigin="anonymous">
</head>

<body>
  <header>
    <div id = "leftHeader"></div>
    <div id = "middleHeader">
      <div id = "siteLogoWrapper">
        <img id = "siteLogo" src = "images/logo.png"></img>
      </div>
    </div>
    <div id = "rightHeader"></div>
  </header>

  <div id = "mainArea">
    <div class="row">
        <form id="form" action="/" method="post" class="col-6 mx-auto">
          <div class="input-group word-input">
            <% String wordPlaceholder = (String) request.getAttribute("wordPlaceholder"); %>
            <% if (wordPlaceholder == null) { wordPlaceholder = "Въведете дума или фраза за римуване."; } %>
            <input type="text" maxlength="30" class="form-control" id="word" name="word" placeholder="<%=wordPlaceholder%>">
            <span><button type="submit" name = "rhymeBtn" id="rhymeButton" class="btn btn-primary rhyme-button">Римувай</button></span>
          </div>
        </form>
    </div>

    <div class="row">
      <ul id="rhymeList" class="col-6 mx-auto rhyme-list">
        <% List<Rhyme> rhymes = (List) request.getAttribute("rhymes"); %>
        <% for(int i = 0; i < rhymes.size(); i+=1) { %>
          <li class="single-rhyme">
            <%=rhymes.get(i).getWord()%>
              <div class="progress">
                <div class="progress-bar" role="progressbar" aria-valuenow="<%=rhymes.get(i).getPercentage()%>"
                  aria-valuemin="0" aria-valuemax="100" style="width:<%=rhymes.get(i).getPercentage()%>%">
                  <%=rhymes.get(i).getPercentage()%>%
                </div>
              </div>
          </li>
        <% } %>
      </ul>
    </div>
  </div>

  <footer>
  </footer>

  <script src = "scripts/faviconEngine.js"></script>
  <script src = "scripts/windowOnLoad.js"></script>

  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous">
  </script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
    integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous">
  </script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
    integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous">
  </script>
</body>

</html>