JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-classpath /usr/share/java/cup.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main <
#CUP=cup

all: Main.class

Main.class: absyn/*.java parser.java sym.java Lexer.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH)  $^

Lexer.java: cminus.flex
	$(JFLEX) cminus.flex

parser.java: cminus.cup
	$(CUP) cminus.cup -dump -expect 3 
	
Main:
	javac $(CLASSPATH) Main.java

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
