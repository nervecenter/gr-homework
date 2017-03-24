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

Each field must be a single continuous string. Gender is expected to be "male" or "female", thought it is not case sensitive. Birthdates are STORED in "mm-dd-yyyy" format, though the program eventually DISPLAYS them in "m/d/yyyy" format.

Some examples:

	$ Earhart|Amelia|female|blue|07-24-1897
	$ Washington,George,male,red,02-22-1732
	$ Lovelace Ada female purple 12-10-1815
	
There are two methods for using this program. The first is a command line usage which grabs files of records

    $ java -jar gr-homework-0.1.0-standalone.jar [args]

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2017 Chris Collazo