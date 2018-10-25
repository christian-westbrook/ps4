<?php

?>

<form onsubmit="submit()" method="post">
	Input:<br>
	<input type="text" id="input"><br>
	<input type="checkbox" name="NB" value="NB" checked> Naive Bayes<br>
	<input type="checkbox" name="LR" value="LR"> Logistic Regression<br>
	<input type="submit" value="Send">	
</form>

<script type="text/javascript">
	var input = document.getElementById("input");
	
	if(input !== null)
	{
		document.write(input);
	}
</script>



