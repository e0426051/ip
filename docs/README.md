# User Guide

* [Quick Start Guide](#quick-start-guide)
* [Features](#features)
    + [1. list](#1-list-the-current-task-list-list)
    + [2. todo](#2-add-a-todo-task-todo-)
    + [3. deadline](#3-add-a-deadline-task-deadline-)
    + [4. event](#4-add-an-event-task-event-)
    + [5. done](#5-mark-task-as-done-done-)
    + [6. delete](#6-delete-a-task-delete-)
    + [7. find](#7-find-tasks-find-)
    + [8. bye](#8-exit-application-bye)
    + [9. *traditional tasks*](#9-others)
* [Other Exceptions](#other-exceptions)
* [FAQ](#faq)
* [Command Summary](#command-summary)


## Quick Start Guide
1. Install Java 11 on your computer. Refrain from using other versions to minimize unexpected runtime errors.
2. Download duke to your computer. Place duke in a non-restricted folder to minimize I/O errors. An example of a restricted folder in Windows may be the root directory `C:\`.
3. Use `cd` command to navigate to the correct folder and run the program using `java -jar [filename].jar`. The `cd` navigation is important as duke will only search the current directory for the file, and not the root directory of the duke program itself automatically.
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
##### Sample Outcome
Outcome: 

`Here are the tasks in your list:`

`1. [T][✓] eat breakfast`
##### Exceptions
Exceptions: None. This command will always run.


### 2. Add a todo task `todo `
Adds a `todo` task into the list.
##### Format
Format: `todo [task description]`
##### Usage
Usage: `todo finish homework`
##### Sample Outcome
Outcome:

`Got it. I've added this task: `

`  [T][✘] finish homework`

`Now you have 8 tasks in the list.`
##### Exceptions
Exceptions: The program will display an error message `Invalid command.` if you type `todo ` without a task description.


### 3. Add a deadline task `deadline `
Adds a `deadline` task into the list.
##### Format
Format: `deadline [task description] /by [deadline]`. The [deadline] is in string format so it can be e.g. "tommorrow" as well.
##### Usage
Usage: `deadline finish homework /by tommorrow`
##### Sample Outcome
Outcome:

`Got it. I've added this task:` 

`  [D][✘] finish homework (by: tommorrow)`

`Now you have 6 tasks in the list.`
##### Exceptions
Exceptions: The program will display an error message `Invalid command.` if you type `deadline ` without a task description **nor** the deadline.
The program will display an error message `Invalid format. Please check your syntax.` for all other syntax errors, such as an empty description, a lack
of "/by " in the input, or an empty deadline in the input.

### 4. Add an event task `event `
Adds a `event` task into the list.
##### Format
Format: `event [task description] /on [occurrence]`. The [occurrence] is in string format so it can be e.g. "yesterday" as well.
##### Usage
Usage: `event midterms /on friday`
##### Sample Outcome
Outcome:

`Got it. I've added this task:` 

`  [E][✘] midterms (on: friday)`

`Now you have 2 tasks in the list.`
##### Exceptions
Exceptions: The program will display an error message `Invalid command.` if you type `event ` without a task description **nor** the occurrance.
The program will display an error message `Invalid format. Please check your syntax.` for all other syntax errors, such as an empty description, a lack
of "/on " in the input, or an empty occurrance in the input.

### 5. Mark task as done `done `
Marks the specified task as done. If the task is already done, a message `This task is already done!` will be shown.
##### Format
Format: `done [task number]`
##### Usage
Usage: `done 3`
##### Sample Outcome
Outcome:

`Nice! I've marked this task as done: `

`  [✓] movies`

Outcome:

`This task is already done!`
##### Exceptions
Exceptions: The program will display an error message `Please Enter a number!` if you type `done ` without a task number, or you typed a non-number
character in its place. If the number you typed is not valid, e.g. `0` or `3` when you only have 1 task in the list, you will receive the error message
`Invalid task number or task does not exist. Please try again.`.


### 6. Delete a task `delete `
Deletes the specified task from the list **AND** duke.txt immediately. Please exercise caution before deleting a task.
##### Format
Format: `delete [task number]`
##### Usage
Usage: `delete 3`
##### Sample Outcome
Outcome:

`Noted. I've removed this task: `

`  [✓] lunch`
##### Exceptions
Exceptions: The program will display an error message `Invalid task number or input is not a number. No items are deleted.` for all invalid inputs,
such as if you typed `delete ` without a task number, typed a non-number character instead, or typed a number that is not valid.


### 7. Find tasks `find `
Finds tasks with the specified keyword. Also searches in the date section in Deadlines and Events. If you enter `find ` without a keyword, the entire
list is printed out.
##### Format
Format: `find [keyword]`
##### Usage
Usage: `find homework`
##### Sample Outcome
Outcome:

`Here are the matching tasks in your list:`

`1. [✓] all the homework`

`2. [E][✘] midterms (on: the day without homework)`
##### Exceptions
Exceptions: None.


### 8. Exit application `bye`
Exits the application. The data entered have been saved into duke.txt. If required, please backup this file for safekeeping purposes.
##### Format
Format: `bye` **without any arguments.**
##### Usage
Usage: `bye`
##### Sample Outcome
Outcome:

`Bye. Hope to see you again soon!`
##### Exceptions
Exceptions: None.


### 9. *Others*
Adds a traditional task into the list. Accepts most inputs. Exceptions include `delete ` and `todo `. Inputs accepted include `delete` and `duke`.
##### Format
Format: `[input]`
##### Usage
Usage: `todo`, `event`, `find`, `java`, `movie /at 9pm`
##### Sample Outcome
Outcome:

`Added: todo`

Outcome:

`Added: movie /at 9pm`
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

## FAQ

### 1.
Q1: How do I ensure my tasks are saved even if my storage fails (e.g. HDD failure)?

A1: Please backup duke.txt and place it at the same directory as the duke program. Do take note to `cd` to said directory in terminal before running duke so that
duke recognizes the presence of the file.

### 2.
Q2: I marked a task as done by mistake. Can I undo it?

A2: Undo functions are not supported. Please make a duplicate task and delete the former task as a workaround.

### 3.
Q3: I am not able to see ticks and crosses on the tasks. I can only see "?". How do I resolve this?

A3: Run `Chcp 65001` followed by `java -Dfile.encoding=UTF-8 -jar [filename].jar` to resolve the issue. Also, change the fonts for your console
application to NSimSun. Please ensure you are in the correct directory before doing so.




## Command Summary

Command | Format | Usage Example
------- | ---------- | ------------
list | `list` | `list`
todo | `todo [task description]` | `todo mop the room`
deadline | `deadline [task description] /by [deadline]`| `deadline homework /by tommorrow`
event | `event [task description] /on [occurrence]` | `event favorite show /on yesterday`
find | `find [keyword]` | `find homework`
done | `done [task number]`  | `done 8`
delete | `delete [task number]` | `delete 1`
bye | `bye` | `bye`
traditional task | `[input]` | `buy book`
