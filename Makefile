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
	$(JAVAC) $(CLASSPATH) $(MAIN)

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~

#shortcuts for running Checkpoint 1 tests
test1:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test1.cm"
	
test2:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test2.cm"
	
test3:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test3.cm"
	
test4:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test4.cm"
	
test5:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test5.cm"

test1-tree:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test1.cm" -a
	
test2-tree:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test2.cm" -a
	
test3-tree:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test3.cm" -a
	
test4-tree:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test4.cm" -a
	
test5-tree:
	$(JAVA) $(CLASSPATH) cm "cminus_tests/test5.cm" -a
