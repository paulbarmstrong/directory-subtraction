# Directory Subtraction Script

## Details

* Author: Paul Armstrong
* Date: March 2017 (but updated and uploaded more recently)
* Progress: 100%

## Demonstration

![animated gif](https://i.imgur.com/Ul7StOs.gif)

## Description

This little script will "subtract" one directory from the other. What I mean by this is any files found in both directories will be deleted from the target directory. Folders found in both directories will only be deleted if there were no other common files in that folder.

## Usage

The script prompts the user for a directory to "blacklist" and a directory to remove blacklisted files from. Blacklisted files also found in the second directory will be marked for deletion. A warning prompt will display the full paths of the first ten on the deletion list and provide the total number of files to be deleted. If the user confirms, the files will be deleted.

Even though I personally use this script and have no problems, any script which involves deleting directories has the potential for disaster. Because of this, I have decided not to include an executable of the script.

There are two main reasons why the script *SHOULD* be safe:
* The script can only mark for deletion files common to both directories (And they can't both be the same directory)
* The script gives a warning including the full path of the first ten files to be deleted, and gives the total number of files to be deleted

Feel free to take a look at the code inside the *source_code* folder and compile it at your own risk.

## Why

The reason why I made this script as a way to undo a manual installation of files at a later date. This script allows me to "subtract" an installation quickly, no matter how many files the installation includes.

Another reason why I wanted to make this script was to personally investigate the use of recursion for manipulating file systems.
