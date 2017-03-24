# gr-homework

A program that stores, sorts, and displays simple records of people, with queryable REST endpoints.

By Chris Collazo

## Installation

Simply extract gr-homework to its own directory and follow the usage below.

## Usage

This program reads in records of people, containing five specific fields and formatted via one of the following three separator schemes:

	$ LastName|FirstName|Gender|FavoriteColor|BirthDate
	$ LastName,FirstName,Gender,FavoriteColor,BirthDate
	$ LastName FirstName Gender FavoriteColor BirthDate

Each field must be a single continuous string. Gender is expected to be "male" or "female", thought it is not case sensitive. Birthdates must be ENTERED in "mm-dd-yyyy" format, though the program eventually DISPLAYS them in "m/d/yyyy" format.

Some examples:

	$ Earhart|Amelia|female|blue|07-24-1897
	$ Washington,George,male,red,02-22-1732
	$ Lovelace Ada female purple 12-10-1815
	
There are two methods for using this program. The first is a command line usage which grabs files of records. It can be used like so, with the example files in "res/" (records MUST be separated by newlines):

    $ lein run [files]
    $ lein run "res/commas.txt"
    $ lein run "res/commas.txt" "res/spaces.txt"
    $ lein run "res/commas.txt" "res/spaces.txt" "res/pipes.txt"

This will scoop up all the records contained within and display them sorted in three orders: By gender (female first), then last name ascending; by birthdate, ascending; and by last name, descending.

The second way to use this program is via REST endpoints. First, start the server:

	$ lein run

This starts the server listening on port 8080. Then, use a program such as curl to send record lines to the POST endpoint at /records:

	$ curl -d 'Earhart|Amelia|female|blue|07-24-1897' 127.0.0.1:8080/records
	$ curl -d 'Washington,George,male,red,02-22-1732' 127.0.0.1:8080/records
	$ curl -d 'Lovelace Ada female purple 12-10-1815' 127.0.0.1:8080/records

NOTE: For this curl method to work, the records MUST be surrounded by SINGLE quotes!

You may now query the GET endpoints to get sorted record collections, in pretty-printed JSON format:

	$ curl 127.0.0.1:8080/gender
	$ curl 127.0.0.1:8080/name
	$ curl 127.0.0.1:8080/birthdate

You may optionally store these responses in files for later viewing:

	$ curl 127.0.0.1:8080/gender > gender.json
	$ curl 127.0.0.1:8080/name > name.json
	$ curl 127.0.0.1:8080/birthdate > birthdate.json

To run the test suite, simply use

	$ lein test

## License

Copyright © 2017 Chris Collazo