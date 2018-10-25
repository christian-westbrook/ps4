function submit()
{
	var input = document.getElementById("input").value;
	
	if(input !== null)
	{
		document.write(input);
	}
	else
	{
		document.write("test");
	}
}