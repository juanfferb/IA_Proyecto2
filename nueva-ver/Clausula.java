
// Clausula.java
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// Representa una cláusula lógica como una disyunción de literales (OR)
public class Clausula {
    public Set<Literal> literales;

    // Constructor vacío
    public Clausula() {
        this.literales = new LinkedHashSet<Literal>();
    }

    // Constructor con conjunto de literales
    public Clausula(Set<Literal> literales) {
        this.literales = new LinkedHashSet<>(literales);
    }

    // Une dos cláusulas (OR)
    public Clausula unir(Clausula segundaClausula) {
        Set<Literal> nueva = new LinkedHashSet<>(this.literales); // Copia la primera cláusula
        // Añade los literales de la segunda cláusula
        nueva.addAll(segundaClausula.literales);
        return new Clausula(nueva);
    }

    // Resta literales de otra cláusula
    public Clausula restar(Clausula otra) {
        // Crea una nueva cláusula con los literales de la primera
        Set<Literal> nueva = new LinkedHashSet<>(this.literales);
        // Elimina los literales de la segunda cláusula
        nueva.removeAll(otra.literales);
        return new Clausula(nueva);
    }

    // Igualdad entre cláusulas
    @Override
    public boolean equals(Object o) {
        // Compara si son la misma instancia o si son iguales
        if (this == o) return true;
        // Compara si son de la misma clase
        if (o == null || getClass() != o.getClass()) return false;
        // Compara los literales
        Clausula clausula = (Clausula) o;
        // Compara los literales de ambas cláusulas
        return Objects.equals(literales, clausula.literales);
    }

    // Hash para sets/mapas
    @Override
    public int hashCode() {
        // Genera un hash basado en los literales
        return Objects.hash(literales);
    }

    // Representación textual
    @Override
    public String toString() {
        // Convierte los literales a texto y los une con " OR "
        return literales.stream().map(Object::toString).collect(Collectors.joining(" OR "));
    }
}