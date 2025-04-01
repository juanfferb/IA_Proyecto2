import java.util.Arrays;
import java.util.Objects;

// Representa un literal lógico (como P(x) o ¬P(x))
public class Literal {
    public String predicado; // Nombre del predicado o relación lógica
    public String[] argumentos; // Argumentos del literal, pueden ser constantes o variables

    /**
     * Constructor que crea un literal con un predicado y sus argumentos.
     */
    public Literal(String predicado, String... argumentos) {
        this.predicado = predicado;
        this.argumentos = argumentos; // Se almacena directamente como array de Strings
    }

    /**
     * Método para obtener la negación del literal.
     * Si el literal ya está negado (es decir, su nombre comienza con "-"),
     * devuelve el mismo literal sin el prefijo "-".
     * Si no está negado, agrega el prefijo "-" para negarlo.
     */
    public Literal negado() {
        if (predicado.startsWith("-")) {
            return new Literal(predicado.substring(1), argumentos); // Quita la negación si ya la tiene
        } else {
            return new Literal("-" + predicado, argumentos); // Agrega la negación si no la tiene
        }
    }

    /**
     * Método equals para comparar si dos literales son iguales.
     * Dos literales son iguales si tienen el mismo predicado y los mismos argumentos.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si es la misma instancia, son iguales
        if (o == null || getClass() != o.getClass()) return false; // Verifica que sean del mismo tipo
        Literal literal = (Literal) o;
        return predicado.equals(literal.predicado) && Arrays.equals(argumentos, literal.argumentos);
        // Compara el predicado y los argumentos para determinar igualdad
    }

    /**
     * Método hashCode para permitir el uso de literales en estructuras de datos como HashSet o HashMap.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(predicado);
        result = 31 * result + Arrays.hashCode(argumentos); // Combina el hash del predicado y los argumentos
        return result;
    }

    /**
     * Representación en texto del literal.
     * Devuelve una cadena en formato "predicado(argumento1, argumento2, ...)".
     */
    @Override
    public String toString() {
        return predicado + "(" + String.join(", ", argumentos) + ")";
    }
}