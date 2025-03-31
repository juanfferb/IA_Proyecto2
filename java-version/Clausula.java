
// Clausula.java
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// Representa una cláusula lógica como una disyunción de literales (OR)
public class Clausula {
    public Set<Literal> literales;

    // Constructor vacío
    public Clausula() {
        this.literales = new HashSet<>();
    }

    // Constructor con conjunto de literales
    public Clausula(Set<Literal> literales) {
        this.literales = new HashSet<>(literales);
    }

    // Une dos cláusulas (OR)
    public Clausula unir(Clausula otra) {
        Set<Literal> nueva = new HashSet<>(this.literales);
        nueva.addAll(otra.literales);
        return new Clausula(nueva);
    }

    // Resta literales de otra cláusula
    public Clausula restar(Clausula otra) {
        Set<Literal> nueva = new HashSet<>(this.literales);
        nueva.removeAll(otra.literales);
        return new Clausula(nueva);
    }

    // Igualdad entre cláusulas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clausula clausula = (Clausula) o;
        return Objects.equals(literales, clausula.literales);
    }

    // Hash para sets/mapas
    @Override
    public int hashCode() {
        return Objects.hash(literales);
    }

    // Representación textual
    @Override
    public String toString() {
        return literales.stream().map(Object::toString).collect(Collectors.joining(" OR "));
    }
}