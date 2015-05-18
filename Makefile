compile: ;javac src/*.java -cp libs/controlsfx-8.0.4.jar:. -d .

clean: ;rm -rf ouride

run: ;java -cp libs/controlsfx-8.0.4.jar:. ouride.OurIDE
