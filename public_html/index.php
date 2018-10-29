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
	<div>
	<form action="client.php" method="post">
		<p id="input-label">Input:</p> 
		<input type="text" name="input"><br>
	
		<input type="checkbox" name="NB" checked> Naive Bayes<br>
		<input type="checkbox" name="LR"> Logistic Regression<br>
	
		<input type="submit" value="Send">	
	</form>
	</div>
</body>



