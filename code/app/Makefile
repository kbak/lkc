FILE := lkc

all: $(FILE).hs
	rm -rf $(FILE).o $(FILE).hi $(FILE)	
	ghc --make $(FILE).hs -o $(FILE)
	rm -rf $(FILE).o $(FILE).hi
	./$(FILE)
	
