package pasito;

import pasito.syntax.Parser;

class Driver {

	public static void main(String[] args) throws Exception {
		Parser parser = new Parser();
		parser.parse();
	}
	
}