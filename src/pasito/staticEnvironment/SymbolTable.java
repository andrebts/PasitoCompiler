package pasito.staticEnvironment;

import java.util.*;

/*
Classe auxiliar para armazenar o Binding e o nível em q ele está
*/
class Tuple<B, L> {
	public final B binding;
	public final L level;
	public Tuple(B binding, L level) {
		this.binding = binding;
		this.level = level;
	}
	public B getBinding() {
		return binding;
	}
	public L getLevel() {
		return level;
	}
}

public class SymbolTable<T> {
	HashMap<String, LinkedList<Tuple<T, Integer>>> uniqueMap = new HashMap<>();
	Stack<Stack<String>> allchanges = new Stack<>();
	// a minha ideia foi armazenar as mudanças no escopo atual em uma variável auxiliar
	// e só quando mudarmos de nível passar isso para a tabela de símbolos
	Stack<String> changesInThisScope = new Stack<>();
	private int currentLevel = 0, times = 0;

	private void levelup() {
		currentLevel++;
	}
	private void leveldown() {
		currentLevel--;
	}

	// começa um novo escopo na tabela de símbolos + dá commit nas mudanças desse escopo
	public void beginScope() {
		levelup();
		if (currentLevel > 1) {
			allchanges.push(changesInThisScope);
			changesInThisScope.removeAllElements();
		}
	}

	// termina um escopo da tabela de símbolos
	public void endScope() throws InvalidLevelException {
		allchanges.push(changesInThisScope);
		if (currentLevel > 0) {
			leveldown();
			Stack<String> changes = allchanges.pop();
			changes.forEach(id -> {
				LinkedList<Tuple<T, Integer>> itens = uniqueMap.get(id);
				if (itens != null  && itens.size() > 1) {
					if (itens.getLast().getLevel() == currentLevel + 1) {
						itens.removeLast();
						uniqueMap.put(id, itens);
					} else System.out.println("EITA LÊLÊ");
				} else uniqueMap.remove(id);
			});
		} else throw new InvalidLevelException(times++);
	}

	// retorna nulo se não houver um binding para o id
	public T get(String id) {
		id = id.toUpperCase();
		return (uniqueMap.containsKey(id))? uniqueMap.get(id).getLast().getBinding() : null;
	}

	// adiciona um novo binding na tabela de símbolos
	public void put(String id, T bnd) throws AlreadyBoundException {
		id = id.toUpperCase();
		if (uniqueMap.containsKey(id)) { 
			if (uniqueMap.get(id).getLast().getLevel() == currentLevel) // se já houver um binding no mesmo nível (váriavel já declarada naquele escopo)
				throw new AlreadyBoundException();
			else { // se já houver sido declarada mais em um escopo diferente
				LinkedList<Tuple<T, Integer>> updated_item = uniqueMap.get(id);
				updated_item.addLast(new Tuple<>(bnd, currentLevel));
				uniqueMap.put(id, updated_item);
				if (currentLevel > 0) changesInThisScope.add(id);
			}
		} else { // se não houver sido declarada ainda
			LinkedList<Tuple<T, Integer>> updated_item = new LinkedList<>();
			updated_item.addLast(new Tuple<>(bnd, currentLevel));
			uniqueMap.put(id, updated_item);
			if (currentLevel > 0) changesInThisScope.add(id);
		}
	}

	//Não foi tratado o caso de choque de Binding diferentes na mesma posição do hashmap
}

