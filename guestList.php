<!DOCTYPE html>
<html>
	<head>
		<title>Cheers to the Freer: Guest List</title>
		<link rel="stylesheet" type="text/css" href="css/global-font.css">
		<link rel="stylesheet" href="css/main.css" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="css/guestList.css">
	</head>
	<body>
		<?php

			require 'php/constants/constants.php';

			date_default_timezone_set('America/Chicago');

			$connection = new mysqli(DATABASE_SERVER_NAME, DATABASE_USERNAME, DATABASE_PASSWORD, DATABASE_NAME);

			if($connection->connect_error){
				echo("There was an issue connecting to the database.");
			}

			$sql	= "SELECT
							FIRST_NAME,
							LAST_NAME,
							DATETIME_SUBMITTED
						FROM
							jonfreer_wedding.GUEST AS G
							JOIN jonfreer_wedding.RESERVATION AS R ON G.RESERVATION_ID = R.RESERVATION_ID
						WHERE
							R.IS_ATTENDING = 0";

			$result = $connection->query($sql);
		?>

		<!-- Page Header -->
		<div id = "page-header" class ="quarter-section">
			<div class="vert-horiz-centered">
				<h1>guest list</h1>
				<hr class = "section-header-bar"/>
			</div>
		</div>

		<!-- Guest List (Not Coming) -->
		<?php
			echo("<h2>can't make it. (" . $result->num_rows . ")</h2>");
		?>
		<table id="not-coming">
			<thead>
				<tr>
					<td>First Name</td>
					<td>Last Name</td>
					<td>RSVP Date/Time</td>
				</tr>
			</thead>
			<tbody>
				<?php
					if($result->num_rows > 0){
						while($row = $result->fetch_assoc()){
							echo("<tr>");
							echo(
								"<td>" . $row["FIRST_NAME"] . "</td>" .
								"<td>" . $row["LAST_NAME"] . "</td>" .
								"<td>" . date('l d M H:i:s', strtotime($row["DATETIME_SUBMITTED"])) . "</td>"
								);
							echo("</tr>");
						}
					}
				?>
			</tbody>
		</table>

		<?php

			$sql	= "SELECT
							FIRST_NAME,
							LAST_NAME,
							GUEST_DIETARY_RESTRICTIONS,
							DATETIME_SUBMITTED
						FROM
							jonfreer_wedding.GUEST AS G
							JOIN jonfreer_wedding.RESERVATION AS R ON G.RESERVATION_ID = R.RESERVATION_ID
						WHERE
							R.IS_ATTENDING = 1";

			$result = $connection->query($sql);
		?>

		<!-- Guest List (Coming) -->
		<?php
			echo("<h2>coming. (" . $result->num_rows . ")</h2>");
		?>
		<table id = "coming">
			<thead>
				<tr>
					<td>First Name</td>
					<td>Last Name</td>
					<td>Dietary Restrictions</td>
					<td>RSVP Date/Time</td>
				</tr>
			</thead>
			<tbody>
				<?php
					if($result->num_rows > 0){
						while($row = $result->fetch_assoc()){
							echo("<tr>");
							echo(
								"<td>" . $row["FIRST_NAME"] . "</td>" .
								"<td>" . $row["LAST_NAME"] . "</td>" .
								"<td>" . $row["GUEST_DIETARY_RESTRICTIONS"] . "</td>" .
								"<td>" . date('l d M H:i:s', strtotime($row["DATETIME_SUBMITTED"])) . "</td>"
								);
							echo("</tr>");
						}
					}
				?>
			</tbody>
		</table>

		<?php

			$sql	= "SELECT
								G.FIRST_NAME,
								G.LAST_NAME,
								G.INVITE_CODE
							FROM
								jonfreer_wedding.GUEST AS G
							WHERE
								G.RESERVATION_ID IS NULL
							ORDER BY
								G.INVITE_CODE";

			$result = $connection->query($sql);
		?>

		<!-- Guest List (No RSVP) -->
		<?php
			echo("<h2>haven't heard yet. (" . $result->num_rows . ")</h2>");
		?>
		<table id ="no-rsvp">
			<thead>
				<tr>
					<td>First Name</td>
					<td>Last Name</td>
					<td>Invite Code</td>
				</tr>
			</thead>
			<tbody>
				<?php
					if($result->num_rows > 0){
						while($row = $result->fetch_assoc()){
							echo("<tr>");
							echo(
								"<td>" . $row["FIRST_NAME"] . "</td>" .
								"<td>" . $row["LAST_NAME"] . "</td>" .
								"<td>" . $row["INVITE_CODE"] . "</td>"
								);
							echo("</tr>");
						}
					}
				?>
			</tbody>
		</table>

		<?php
			$connection->close();
		?>

	</body>
</html>
