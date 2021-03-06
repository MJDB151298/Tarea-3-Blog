<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>The Sims 4</title>

  <!-- Bootstrap core CSS -->
  <!--<link href="resources/publico/startbootstrap-blog-home-gh-pages/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->

  <!-- Custom styles for this template -->
  <!--<link href="resources/publico/startbootstrap-blog-home-gh-pages/css/blog-home.css" rel="stylesheet">-->

  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>

</head>

<body>

  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
      <a class="navbar-brand" href="#">The Sims 4</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item active">
            <a class="nav-link" href="/menu">Home
              <span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">About</a>
          </li>
          <#if loggedUser?exists>
            <li class="nav-item">
              <a class="nav-link" href="#">${loggedUser.username}</a>
            </li>
          </#if>
          <#if loggedUser?exists && loggedUser.administrador == true>
            <li class="nav-item">
              <a class="nav-link" href="/autores">Autores</a>
            </li>
          </#if>
          <#if loggedUser?exists>
            <li class="nav-item">
              <a class="nav-link" href="/disconnect">Desconectar</a>
            </li>
            <#else>
              <li class="nav-item">
                <a class="nav-link" href="/login">Login</a>
              </li>
          </#if>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Page Content -->
  <div class="container">

    <div class="row">

      <!-- Blog Entries Column -->
      <div class="col-md-8">
        <br><br>
        <h1 class="my-4">Page Heading
        </h1>

        <h2 class="my-4">Posts</h2>

        <!-- Post Creation Form -->
        <#if loggedUser?exists && (loggedUser.autor == true || loggedUser.administrador == true)>
          <form method="post" action="/createPost">
            <div class="form-group">
              <input class="form-control" name="postTitle" placeholder="Title" type="text">
              <textarea name="postContent" class="form-control" rows="6"></textarea>
              <input class="form-control" name="tags" placeholder="Tags" type="text">
              <label for="sel1">Select list:</label>
              <select class="form-control" id="sel1" name="postCategory">
                <option>Accion</option>
                <option>Comedia</option>
                <option>Drama</option>
                <option>Ficcion</option>
                <option>Historia</option>
                <option>Romance</option>
              </select>
            </div>
            <button id="postPost" type="submit" class="btn btn-primary">Publish</button>
          </form>
          <br><br>
        </#if>

        <!-- Blog Post -->
          <#if listaArticulos?size != 0>
              <#list listaArticulos as articulo>
                  <div class="card mb-4">
                      <img class="card-img-top" src="http://placehold.it/750x300" alt="Card image cap">
                      <div class="card-body">
                          <h2 class="card-title">${articulo.titulo}</h2>
                          <p class="card-text">${articulo.cuerpoResumido}</p>
                          <a href="/menu/${articulo.id?string["0"]}" class="btn btn-primary">Read More &rarr;</a>
                      </div>
                      <div class="card-footer text-muted">
                          Posted on January 1, 2017 by
                          <a href="#">${articulo.autor.username}</a>
                          , Categoria: ${articulo.categoria}, 
                          <label>Tags:</label>
                          <#list articulo.listaEtiquetas as etq>
                              <a>${etq.etiqueta}</a>
                          </#list>
                      </div>
                  </div>
              </#list>
          </#if>

        <!-- Pagination -->
        <ul class="pagination justify-content-center mb-4">
          <li class="page-item">
            <a class="page-link" href="#">&larr; Older</a>
          </li>
          <li class="page-item disabled">
            <a class="page-link" href="#">Newer &rarr;</a>
          </li>
        </ul>

      </div>

      <!-- Sidebar Widgets Column -->
      <div class="col-md-4">
        <br><br>
        <!-- Search Widget -->
        <div class="card my-4">
          <h5 class="card-header">Search</h5>
          <div class="card-body">
            <div class="input-group">
              <input type="text" class="form-control" placeholder="Search for...">
              <span class="input-group-btn">
                <button class="btn btn-secondary" type="button">Go!</button>
              </span>
            </div>
          </div>
        </div>

        <!-- Categories Widget -->
        <!--
        <div class="card my-4">
          <h5 class="card-header">Categories</h5>
          <div class="card-body">
            <div class="row">
              <div class="col-lg-6">
                <ul class="list-unstyled mb-0">
                  <li>
                    <a href="#">Web Design</a>
                  </li>
                  <li>
                    <a href="#">HTML</a>
                  </li>
                  <li>
                    <a href="#">Freebies</a>
                  </li>
                </ul>
              </div>
              <div class="col-lg-6">
                <ul class="list-unstyled mb-0">
                  <li>
                    <a href="#">JavaScript</a>
                  </li>
                  <li>
                    <a href="#">CSS</a>
                  </li>
                  <li>
                    <a href="#">Tutorials</a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        -->
        <!-- Side Widget -->
        <!--
        <div class="card my-4">
          <h5 class="card-header">Side Widget</h5>
          <div class="card-body">
            You can put anything you want inside of these side widgets. They are easy to use, and feature the new Bootstrap 4 card containers!
          </div>
        </div>
        -->
      </div>
    </div>
    <!-- /.row -->

  </div>
  <!-- /.container -->

  <!-- Footer -->
  <footer class="py-5 bg-dark">
    <div class="container">
      <p class="m-0 text-center text-white">Copyright &copy; The Sims 4 2019</p>
    </div>
    <!-- /.container -->
  </footer>

  <!-- Bootstrap core JavaScript -->
  <script src="resources/publico/startbootstrap-blog-home-gh-pages/vendor/jquery/jquery.min.js"></script>
  <script src="resources/publico/startbootstrap-blog-home-gh-pages/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
