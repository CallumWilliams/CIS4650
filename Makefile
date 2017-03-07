JAVA=java
JAVAC=javac
JFLEX=jflex
MAIN=cm.java
CLASSPATH=-classpath cup/cup.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main <
#CUP=cup

all: cm.class

cm.class: absyn/*.java parser.java sym.java Lexer.java cm.java

%.class: %.java
	$(JAVAC) $(CLASSPATH)  $^

Lexer.java: cminus.flex
	$(JFLEX) cminus.flex

parser.java: cminus.cup
	$(CUP) cminus.cup -dump -expect 3 
	
cm.java: 
	javac $(CLASSPATH) $(MAIN)

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~

#shortcuts for running Checkpoint 1 tests
test1:
	java $(CLASSPATH) cm "cminus_tests/test1.cm"
	
test2:
	java $(CLASSPATH) cm "cminus_tests/test2.cm"
	
test3:
	java $(CLASSPATH) cm "cminus_tests/test3.cm"
	
test4:
	java $(CLASSPATH) cm "cminus_tests/test4.cm"
	
test5:
	java $(CLASSPATH) cm "cminus_tests/test5.cm"
