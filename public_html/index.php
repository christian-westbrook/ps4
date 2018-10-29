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
	<form action="client.php" method="post">
		Input:<br> 
		<input type="text" name="input"><br>
	
		<input type="checkbox" name="NB" checked> Naive Bayes<br>
		<input type="checkbox" name="LR"> Logistic Regression<br>
	
		<input type="submit" value="Send">	
	</form>
</body>



