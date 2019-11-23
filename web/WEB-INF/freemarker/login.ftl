<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/SpringEntireFeaturesDemo_war_exploded/resources/img/favicon.ico">

    <title>Server Index Page</title>
    <script type="text/javascript" src="/SpringEntireFeaturesDemo_war_exploded/resources/js/jquery-1.10.2.min.js"></script>
    <!-- Bootstrap core CSS -->
    <link href="/SpringEntireFeaturesDemo_war_exploded/resources/css/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="/SpringEntireFeaturesDemo_war_exploded/resources/js/signin.css" rel="stylesheet">
    <script type="text/javascript" src="/SpringEntireFeaturesDemo_war_exploded/resources/css/bootstrap-3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/SpringEntireFeaturesDemo_war_exploded/resources/js/common.js"></script>
</head>

<body class="text-center" background="/SpringEntireFeaturesDemo_war_exploded/resources/img/sample.jpg">
<form class="form-signin">
    <img class="mb-4" src="https://getbootstrap.com/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
    <h1 class="h3 mb-3 font-weight-normal">Please Enter Data</h1>
    <label for="dirPath" class="sr-only">Directory Path</label>
    <input type="text"  id="dirPath" class="form-control" placeholder="Directory Path" required="required" autofocus="">
    <label for="nameLike" class="sr-only">Name Like</label>
    <input type="text" id="nameLike" class="form-control" placeholder="Name Like" required="required">
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> Remember me
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" id="submit" type="submit">Submit</button>
    <p class="mt-5 mb-3 text-muted">© 2017-2020</p>

    <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="left" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
        Popover on Left
    </button>

    <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="top" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
        Popover on Top
    </button>

    <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="bottom" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
        Popover on Bottom
    </button>

    <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="right" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
        Popover on Right
    </button>

    <ul class="nav navbar-nav navbar-right">
        <li id="fat-menu" class="dropdown">
            <a id="drop3" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                Dropdown
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu" aria-labelledby="drop3">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#">Separated link</a></li>
            </ul>
        </li>
    </ul>
</form>


</body></html>