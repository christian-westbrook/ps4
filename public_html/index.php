<form onsubmit="submit()" method="post">
	Input:<br>
	<input type="text" id="input"><br>
	<input type="checkbox" name="NB" value="NB" checked> Naive Bayes<br>
	<input type="checkbox" name="LR" value="LR"> Logistic Regression<br>
	<input type="submit" value="Send">	
</form>

function submit()
{
	var input = document.getElementById("input").value;
	
	if(input !== null)
	{
		document.write(input);
	}
}



