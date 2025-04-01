// Literal.java
import java.util.Arrays;
import java.util.Objects;

// Representa un literal lógico (como P(x) o ¬P(x))
public class Literal {
    public String predicado; // Nombre del predicado
    public String[] argumentos; // Argumentos del literal

    // Constructor del literal
    public Literal(String predicado, String... argumentos) {
        this.predicado = predicado;
        this.argumentos = argumentos;
    }

    // Devuelve el literal negado
    public Literal negado() {
        if (predicado.startsWith("-")) {
            // Si ya está negado, lo devuelve sin la negación
            return new Literal(predicado.substring(1), argumentos);
        } else {
            return new Literal("-" + predicado, argumentos);
        }
    }

    // Compara dos literales por igualdad
    @Override
    public boolean equals(Object o) {
        // Compara si son la misma instancia o si son iguales
        if (this == o) return true;
        // Compara si son de la misma clase
        if (o == null || getClass() != o.getClass()) return false;
        // Compara los literales
        // Compara el predicado y los argumentos
        Literal literal = (Literal) o;
        // Compara el predicado y los argumentos
        // Usa Arrays.equals para comparar los argumentos
        return predicado.equals(literal.predicado) && Arrays.equals(argumentos, literal.argumentos);
    }

    // Genera un hash para usar en sets o mapas
    @Override
    public int hashCode() {
        int result = Objects.hash(predicado);
        result = 31 * result + Arrays.hashCode(argumentos);
        return result;
    }

    // Representación en texto del literal
    @Override
    public String toString() {
        return predicado + "(" + String.join(", ", argumentos) + ")";
    }
}