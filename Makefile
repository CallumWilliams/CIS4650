JAVA=java
JAVAC=javac
JFLEX=jflex
MAIN=cm.java
CLASSPATH=-classpath /usr/share/java/cup.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main <
#CUP=cup

all: cm.class TURING

TURING:
	gcc -w tm.c -o tm

cm.class: absyn/*.java SymbolTable/*.java parser.java sym.java Lexer.java cm.java

%.class: %.java
	$(JAVAC) $(CLASSPATH)  $^

Lexer.java: cminus.flex
	$(JFLEX) cminus.flex

parser.java: cminus.cup
	$(CUP) cminus.cup -dump -expect 3 

table:
	$(JAVAC) HashTable/*.java
	
cm.java: 
	$(JAVAC) $(CLASSPATH) $(MAIN)
	
clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~ SymbolTable/*.class *~ tm

#shortcuts for running Checkpoint 1 tests
test1:
	$(JAVA) $(CLASSPATH) cm "tests/1.cm"
	
test2:
	$(JAVA) $(CLASSPATH) cm "tests/2.cm"
	
test3:
	$(JAVA) $(CLASSPATH) cm "tests/3.cm"
	
test4:
	$(JAVA) $(CLASSPATH) cm "tests/4.cm"
	
test5:
	$(JAVA) $(CLASSPATH) cm "tests/5.cm"

test1-tree:
	$(JAVA) $(CLASSPATH) cm "tests/1.cm" -a
	
test2-tree:
	$(JAVA) $(CLASSPATH) cm "tests/2.cm" -a
	
test3-tree:
	$(JAVA) $(CLASSPATH) cm "tests/3.cm" -a
	
test4-tree:
	$(JAVA) $(CLASSPATH) cm "tests/4.cm" -a
	
test5-tree:
	$(JAVA) $(CLASSPATH) cm "tests/5.cm" -a
