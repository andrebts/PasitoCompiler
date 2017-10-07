package pasito.util;

import java.util.ArrayList;

public class ErrorRegister {
	private static ArrayList<Erro> e = new ArrayList<Erro>();
	 
	public static void report(String mensagem) {
        e.add(new Erro(mensagem));
	}
	
	public static void report(int codigo, String mensagem) {
        e.add(new Erro(codigo, mensagem));	
	}
	
	public void mostrar() {
        if (! e.isEmpty()) {
            for (Erro err : e) {
                System.out.println("[" + err.cod + "] " + err.msg);
            }
        }
    }

	private static class Erro {
        int cod;
        String msg;
        
        public Erro(int codigo, String mensagem) {
            this.cod = codigo;
            this.msg = mensagem;
        }
        
        public Erro(String mensagem) {
            this.msg = mensagem;
        }
    }
}
