<?php

// Get the message to be sent
$in = $_POST['input'];

// Detect options
$nb = $_POST['NB'];
$lr = $_POST['LR'];

echo "Sentence: " . $in . "</br></br>";

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
	$nb_response = "";
	while (!feof($socket)) 
	{
		$nb_response = $nb_response . fgets($socket, 4096);
	}
	
	$nb_array = explode(",", $nb_response);
	
	echo '<p id="nbp">';
	echo "Naive Bayes</br>";
	echo "Classifier : " . $nb_array['0'] . "</br>";
	echo "  Positive : " . $nb_array['1'] . "</br>";
	echo "   Neutral : " . $nb_array['2'] . "</br>";
	echo "  Negative : " . $nb_array['3'] . "</br></p></br>";

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
	$lr_response = "";
	while (!feof($socket)) 
	{
		$lr_response = $lr_response . fgets($socket, 4096);
	}
	
	$lr_array = explode(",", $lr_response);
	
	echo '<p id="lrp">';
	echo "Logistic Regression</br>";
	echo "Classifier : " . $lr_array['0'] . "</br>";
	echo "  Positive : " . $lr_array['1'] . "</br>";
	echo "   Neutral : " . $lr_array['2'] . "</br>";
	echo "  Negative : " . $lr_array['3'] . "</br></p></br>";

	// Close the socket
	fclose($socket);
}

?>