# CalendarReader
An application for reading a calendar into an object. 

### Installation
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
