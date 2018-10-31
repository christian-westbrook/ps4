<?php

// Get the message to be sent
$in = $_POST['input'];

// Detect options
$nb = $_POST['NB'];
$lr = $_POST['LR'];

if($nb)
{
	// Create the client socket
	$socket = stream_socket_client("tcp://code.cis.uafs.edu:5000", $errno, $errstr, 30);

	// Test the client socket
	if(!$socket)
	{
		echo "$errstr ($errno)<br />\n";
	}

	// Append end of message to input message
	$in = $in . "\n";

	// Write message to server connection
	fwrite($socket, $in);

	// Receive message from server connection
	echo "Response: ";
	$nb_response = "";
	while (!feof($socket)) 
	{
		$nb_response = $nb_response . fgets($socket, 4096);
	}
	
	echo $nb_response . "</br>";

	// Close the socket
	fclose($socket);
}

if($lr)
{
	// Create the client socket
	$socket = stream_socket_client("tcp://code.cis.uafs.edu:5001", $errno, $errstr, 30);

	// Test the client socket
	if(!$socket)
	{
		echo "$errstr ($errno)<br />\n";
	}

	// Append end of message to input message
	$in = $in . "\n";

	// Write message to server connection
	fwrite($socket, $in);

	// Receive message from server connection
	echo "Response: ";
	$lr_response = "";
	while (!feof($socket)) 
	{
		$lr_response = $lr_response . fgets($socket, 4096);
	}
	
	echo $lr_response . "</br>";

	// Close the socket
	fclose($socket);
}

?>