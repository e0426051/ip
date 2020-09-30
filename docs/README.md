# User Guide

## Quick Start Guide
1. Install Java 11 on your computer. Refrain from using other versions to minimize unexpected runtime errors.
2. Download duke.jar to your computer. Place duke.jar in a non-restricted folder to minimize IO errors. An example of a restricted folder in Windows may be C:\Windows\System32
3. Use `cd` command to navigate to the correct folder and run the program using `java -jar duke.jar`
4. If you are a new user, the program will create a duke.txt file. Please do not delete this file as it contains all your tasks.

## Features 

Duke has the following commands:
* `list`: Lists the tasks entered into the program that are not deleted
* **`todo `**: Adds a todo task to the list
* **`deadline `**: Adds a deadline task to the list
* **`event `**: Adds an event task to the list
* **`done `**: Marks an existing task as done
* **`delete `**: Deletes an existing task
* **`find `**: Finds all tasks that contain the supplied keyword, including in the date section for Deadlines and Events 
* `bye`: Exits the application
* *Others* : Add a traditional task (Level-2 feature). This includes keywords above **in bold** that is not entered with a space immediately thereafter.

### 1. List the current task list `list`
Prints out the list of tasks stored in the program.
##### Format
Format: `list` **without any arguments.**
##### Usage
Usage: `list`
##### Exceptions
Exceptions: None. This command will always run.


### 2. Add a todo task `todo `
Adds a `todo` task into the list.
##### Format
Format: `todo [task description]`
##### Usage
Usage: `todo finish homework`
##### Exceptions
Exceptions: The program will display an error message `Invalid command.` if you type `todo ` without a task description.


### 3. Add a deadline task `deadline `
Adds a `deadline` task into the list.
##### Format
Format: `deadline [task description] /by [deadline]`. The [deadline] is in string format so it can be e.g. "tommorrow" as well.
##### Usage
Usage: `deadline finish homework /by tommorrow`
##### Exceptions
Exceptions: The program will display an error message `Invalid command.` if you type `deadline ` without a task description **nor** the deadline.
The program will display an error message `Invalid format. Please check your syntax.` for all other syntax errors, such as an empty description, a lack
of "/by " in the input, or an empty deadline in the input.

### 4. Add an event task `event`
Adds a `event` task into the list.
##### Format
Format: `event [task description] /on [occurrance]`. The [occurrance] is in string format so it can be e.g. "yesterday" as well.
##### Usage
Usage: `event midterms /on friday`
##### Exceptions
Exceptions: The program will display an error message `Invalid command.` if you type `event ` without a task description **nor** the occurrance.
The program will display an error message `Invalid format. Please check your syntax.` for all other syntax errors, such as an empty description, a lack
of "/on " in the input, or an empty occurrance in the input.

### 5. Mark task as done `done`
Marks the specified task as done.
##### Format
Format: `done [task number]`
##### Usage
Usage: `done 3`
##### Exceptions
Exceptions: The program will display an error message `Please Enter a number!` if you type `done ` without a task number, or you typed a non-number
character in its place. If the number you typed is not valid, e.g. 0 or 3 when you only have 1 task in the list, you will receive the error message
`Invalid task number or task does not exist. Please try again.`.


### 6. delete
Deletes the specified task from the list AND duke.txt immediately. Please exercise caution before deleting a task.
##### Format
Format: `delete [task number]`
##### Usage
Usage: `delete 3`
##### Exceptions
Exceptions: The program will display an error message `Invalid task number or input is not a number. No items are deleted.` for all invalid inputs,
such as if you typed `delete ` without a task number, typed a non-number character instead, or typed a number that is not valid.


### 7. find
Finds tasks with the specified keyword. Also searches in the date section in Deadlines and Events. If you enter `find ` without a keyword, the entire
list is printed out.
##### Format
Format: `find [keyword]`
##### Usage
Usage: `find homework`
##### Exceptions
Exceptions: None.


### 8. bye
Exits the application. The data entered have been saved into duke.txt. If required, please backup this file for safekeeping purposes.
##### Format
Format: `bye` **without any arguments.**
##### Usage
Usage: `bye`
##### Exceptions
Exceptions: None.


### 9. *Others*
Adds a traditional task into the list. Accepts most inputs. Exceptions include `delete ` and `todo `. Inputs accepted include `delete` and `duke`.
##### Format
Format: `[input]`
##### Usage
Usage: `todo`, `event`, `find`, `java`, `movie /at 9pm`
##### Exceptions
Exceptions: None.

## Other exceptions

You may experience these errors in different situations:

### 1. I/O type of errors
##### Examples
1. `I/O error. File not found or corrupt.`
2. `There is a problem preventing a new file from being created.`
3. `Unable to create file! Reason: [Reason]`
4. `File not found error.`
##### Solutions
Move the jar file to another folder with proper access. For example, refrain from using restricted directories such as `C:\` or `C:\Windows\System32`.






