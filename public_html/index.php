<?php

//=================================================================================================
// Program      : Interactive Semantic Analyzer
// Page			: index.php
// Developer    : Christian Westbrook
// Abstract		: This page serves as the landing page to our interactive semantic analyzer.
//=================================================================================================

?>

<head>
	<title>PS4</title>
	<link rel="stylesheet" type="text/css" href="css/index-styles.css" />
</head>

<body>
	<h1>Sentiment Analyzer</h1>
	<div id="formdiv">
	<form action="client.php" method="post">
		<div id="inputdiv">
		Input:
		<input type="text" name="input"><br>
		</div>
	
		<input type="checkbox" name="NB" checked> Naive Bayes<br>
		<input type="checkbox" name="LR"> Logistic Regression<br>
	
		<input type="submit" value="Send">	
	</form>
	</div>
	
	<p>Copyright (c) 2018 Renae Fisher, Anthony Todaro, and Christian Westbrook. All rights reserved.</p>
</body>



