echo "Generating a list of .java files..."
find . -name "*.java" > paths.txt
echo "Compiling .java files into ./bin/ folder"
javac -d "bin" @paths.txt -cp "src/"
cd ./bin/
echo "Generating a list of .class files..."
find . -name "*.class" > classes.txt
jar cfe Kwyk.jar  kwyk.Launcher @classes.txt
echo "Generated a JAR file"
mv Kwyk.jar ../Kwyk.jar
echo "Moved file to project directory"
echo "Use 'java -jar Kwyk.jar' to run the game!"
echo "Cleaning up..."
rm classes.txt
cd ..
rm paths.txt