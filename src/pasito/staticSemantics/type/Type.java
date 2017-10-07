package pasito.staticSemantics.type;

/**
 * Created by Giovanny on 14/09/17.
 */

/* Underlying Types */

public abstract class Type {

	/**************
	 *  A implementação deste método deve seguir a definição de type identity dada em
	 *   https://golang.org/ref/spec#Type_identity
	 */
	public abstract boolean equivalent(Type ty);

	
	/**********
	 * A implementação deste método deve seguir a definição de assignability dada em
	 * https://golang.org/ref/spec#Assignability
	 */
	public abstract boolean assignableTo(Type ty);

}
