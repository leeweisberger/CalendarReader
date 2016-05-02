# CalendarReader
An application for reading a calendar into an object. 

## Installation
The pom.xml does most of the work, but unfortunately some dependencies must be downloaded onto the machine. This installation buide works for OSX and Linux (and possibly Windows)

#### Tesseract
Follow instructions at this URL: https://github.com/tesseract-ocr/tesseract/wiki

#### Ghostscript
Install ghostscript. 

On OSX, run the following: brew install ghostscript . You may then need to copy the native .dylib files: libgs.9.19.dylib, libgs.9.dylib, and libgs.dylib from /opt/local/lib to the project directory.

On Linux, please google ghostscript installation!

#### OpenCV
###### Linux
Follow these instructions: http://docs.opencv.org/2.4/doc/tutorials/introduction/linux_install/linux_install.html#linux-installation   This takes a while, so be prepared to wait 20+ minutes. 

After installation, follow these instructions to create a new user library in eclipse: http://docs.opencv.org/2.4/doc/tutorials/introduction/java_eclipse/java_eclipse.html#java-eclipse

###### OSX
Run:
brew tap homebrew/science
brew install opencv --with-java

Then create a new user library in eclipse. Add the opencv jar to the library which is found at /usr/local/Cellar/opencv/2.4.9/share/OpenCV/java/

Add the lib folder at /usr/local/Cellar/opencv/2.4.9/lib as the jar's native library

If necessary, add the .dylib file at /usr/local/Cellar/opencv/2.4.9/share/OpenCV/java to the Eclipse project

## Usage
To use the tool, just pull down the branch of the calendar you are trying to read and follow the installation guide to get it running. The main method is in the CalendarReader class. Replace the constant string at the top with the name of your pdf and voila. To help with debugging, the output is written to a file called outfilename.txt and an image of the rectangles is saved to [randomint]test.jpg.

## Customizing for a new calendar
This tool should work with any calendar, but some customization is required to get this to work. 

The first step is to get the squares of the calendar to be bounded correctly. Run the app and then look at the [randomint]test.jpg file that is generated to see if the squares are recognized. If they aren't, this is probably due to the values in the config.properties file. This file contains a few numbers such as the minimum rectangle width and height (to rule out small rectangles that couldn't possibly be right), the minimum width/height ratio (so you can rule out rectangles that are too tall/fat) and the minimum contour area (to rule out small rectangles). Mess around with these values until the rectangles are bounded correctlyy.

If this does not work (althought it probably will!), you might have to mess with the actual image processing. Try going to the ImageProcessingUtils and changing the constants in the gaussian blur and the adaptive threshold. Tinker with them until you find the sweet spot. Another thing that can be changed is the bufferRectangle() method in the RectangleBounder class. This will add space above, below, to the left, and to the right of the rectangle. You would use this, for example, if the day of the week is above the rectangle instead of inside of it. To still capture the day, you must stretch the rectangle up by decreasing the y and increasing the height!

The next step is to create a parser for the Calendar. Every parser is specific to the calendar, so a new calendar must use a new parser. Look at the current parsers to get you started, but basically you have to use regular expressions and string manipulation to separate the day, time, event, and location.

If you need any help, contact Lee Weisberger at lweisberger5@gmail.com
