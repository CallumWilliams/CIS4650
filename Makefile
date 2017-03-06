JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-classpath /usr/share/java/cup.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main <
#CUP=cup

all: Main.class

#Replace with this when we get syntax tree going
Main.class: absyn/*.java parser.java sym.java Lexer.java Main.java

#Main.class: Lexer.java parser.java Main.java 

%.class: %.java
	$(JAVAC) $(CLASSPATH)  $^

Lexer.java: tiny.flex
	$(JFLEX) tiny.flex


#Orignal 
#parser.java: tiny.cup
#	$(CUP) -dump -expect 3 tiny.cup


#Modified - I don't know what ramifications this has, if any
parser.java: tiny.cup
	$(CUP) tiny.cup -dump -expect 3 
	
	
#Just added this so we can compile Main separately without
#conflicting with his funky target above
Main:
	javac $(CLASSPATH) Main.java

run: Main.class
	java $(CLASSPATH) Main "cminus_samples/test1.cminus"

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~

#shortcuts for running Checkpoint 1 tests
test1:
	java $(CLASSPATH) Main "cminus_samples/test1.cminus"
	
test2:
	java $(CLASSPATH) Main "cminus_samples/test2.cminus"
	
test3:
	java $(CLASSPATH) Main "cminus_samples/test3.cminus"
	
test4:
	java $(CLASSPATH) Main "cminus_samples/test4.cminus"
	
test5:
	java $(CLASSPATH) Main "cminus_samples/test5.cminus"
