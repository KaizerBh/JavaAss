# JavaAss
Assignment for Java CompSci

Assessment item 2 - Assessment 2: File Processing and GUI

TASK
Complete the following tasks. Please also consult the Interact2 site, which contains a video demonstrating the required functionality. 

Task 1: Home Library  Application (20 marks)

Write a Java console program that allows you to record, search and display information about books in your personal home library. The data that is recorded should be available across user sessions. Make use of files and serialization to accomplish this task. Your application should contain a Book class, a Library class (for adding, displaying, searching, deleting and saving books) and any other classes needed to obtain the functionality below. 

The main menu of the program should contain the following details:

## Welcome to the Home Library System ##

Please select one of the following options:
1. Add a new book
2. Delete a book
3. Search for a book
4. Display all books
5. Exit

When option 1 is selected, the program will ask for the following details:

Title of the book
Author of the book
Year of publication
ISBN number

For the purpose of this task, the ISBN number is a unique 10 digit number consisting only of individual digits in the range 0-9.  If you hold two copies of the same book they will have the same ISBN number but will otherwise be distinct. Create an ISBNFormatError class to respond to formatting errors of this type.

Once the user correctly enters the information above and presses "return", the book should be added to an appropriate data structure. If the book was successfully created and added, print a suitable message and re-show the main menu. If the book was not successfully created or added, print a suitable message and re-show the main menu.  

When option 2 is selected the program will ask the following:

Search a book for deletion by
1. Title
2. Author
3. Year of publication
4. ISBN number

Let the user select one of these sub options and enter appropriate details. Error messages should appear for incorrect formatting (ISBN), invalid year information (i.e., not a 4 digit number) and for fields left blank.

Return all books as a listing in alphabetical order based on title, then author, then year, and give the user the option to delete the book. Report a suitable message if no books are returned.

Make sure you give the user an option to re-access the main menu once they have completed this deletion operation.

When option 3 is selected then the program will ask the following:

Search for a book by
1. Title
2. Author
3. Year of publication
4. ISBN number

Let the user select one of these sub options and enter appropriate details. Error messages should appear for incorrect formatting (ISBN), invalid year information (i.e., not a 4 digit number) and for fields left blank.

All books that meet the search criteria should be displayed. Report a suitable message if no books are returned. Make sure you give the user an option to re-access the main menu once they have completed their search.

When option 4 is selected display all books in the library based on the alphabetical order of the title. If there are two copies of the same book, the one with the lowest publication year should be displayed first. The display order should be based on year first, then author, then date of publication, as follows:

Title: A day in the country
Author: Anne Preston
Year: 2001
ISBN:7488796045

Title: Last light
Author: Alex Scarrow
Year: 2007
ISBN:991145598

Title: Nineteen eighty four
Author: George Orwell
Year: 1984
ISBN:1991145592

Title: Nineteen eighty four
Author: George Orwell
Year: 1986
ISBN: 2991145592

Title: Nineteen eighty four
Author: Mary Owen
Year: 1986
ISBN:1676957444

When 5 is selected, the program will exit. Note that before the program terminates, you should save all information about the book collection in the home library to file. When the program is relaunched, this information should be reloaded into the program. 

Task 2: Display points on screen (20 marks)

Write a class Point that represents a point on a two dimensional plane. Each point should have an x and y axis (a float).  Your program should also accept the name of a text file (points.txt) as a program argument. This file will contain a list of any number of points, displayed in the following format:

x=5; y=10
x=3; y=9

Your program should do the following:

Create Point objects from the data in the text file and store them in a suitable data structure
Map and display these points against the screen coordinate system used by the JavaFX package (i.e., with 0,0 located at the top left hand corner)
Each point should be displayed as a circle with a radius of 5 pixels
When the user hovers their mouse over one of the points, display the coordinates in a text field at the bottom of the screen. For this, use Arial 12 point font
The user should be able to drag any of the points with the mouse to a new position in the window
When the user has finished dragging, the coordinates of that point will be updated and reported in the text field
Name your window Show Points
You may use any colour for the points and for the window background
