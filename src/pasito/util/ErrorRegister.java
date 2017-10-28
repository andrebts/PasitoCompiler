package pasito.util;

import java.util.ArrayList;

public class ErrorRegister {
	private ArrayList<Erro> e = new ArrayList<Erro>();
	 
	public void report(String mensagem) {
        e.add(new Erro(mensagem));
	}
	
	public void report(int codigo, String mensagem) {
        e.add(new Erro(codigo, mensagem));	
	}
	
	public void mostrar() {
        if (! e.isEmpty()) {
        	System.out.println("Erros da checagem de Tipos:"); 
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
