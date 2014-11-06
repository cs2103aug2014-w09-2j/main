main
====

CS2103T project of team w09-2j

Current functions and how to use them: 
--------------
Note: "<" and ">" are not part of the commands.

1. Add
	- add \<name\>, \<description\> from \<start date start time\> to \<end date end time\> @\<location\> #\<important\>
 	- Date and time format: 
		- DD/MM/YYYY HH:MMam
		- DD/MM/YYYY HH:MMpm
	- Not all arguments have to be used. For example:
	   	- add \<task\>
		- add \<name\> on \<start date start time\> 
		- add \<name\> on \<start date\>
		- add \<name\> at \<start time\>
		- add \<name\> due on \<end date end time\>
		- add \<name\> due on \<end date\>
		- add \<name\> due at \<end time\>

2. Delete
  	- delete \<task index number\>
  	- The task number can be viewed from the table in the display

3. Update
  	- update \<task index number\> \<indicator\> \<new info\>
  	- The possible indicators are:
	  	- name
	  	- description
	  	- start
	  	- end
	  	- location
	  	- priority

4. Search
	- search \<indicator\> \<item\> 
	- The possible indicators can be seen on the table column headers.
	- The key is the word or letters that you are searching for
	
5. Sort
	- sort \<indicator\>
	- The possible indicators are:
		- name (sorted alphabetically)
		- start
		- end
		- location (sorted alphabetically)
		- priority

6. Undo
 	- undo
 	- Undos the previous action

7. Redo
 	- redo
 	- Undos the previous undo

8. Clear
  	- clear
  	- Clears all data

9. Help
	- help
	- Displays the commands available and how to use them
	
10. Tutorial
	- tutorial
	- Opens a user guide that explains the usage of commands in-depth.
	
11. Settings
	- settings
	- Opens the settings for you to tweak the notification frequency, and the colors of the overdue and ongoing tasks.
  
12. Exit
  	- exit
  	- This exits the program
