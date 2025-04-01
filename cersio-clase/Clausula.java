import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// Representa una cláusula lógica como una disyunción de literales (OR)
public class Clausula {
    public Set<Literal> literales; // Conjunto de literales en la cláusula

    // Constructor vacío: inicializa una cláusula sin literales
    public Clausula() {
        this.literales = new HashSet<>();
    }

    // Constructor que recibe un conjunto de literales y crea una cláusula con ellos
    public Clausula(Set<Literal> literales) {
        this.literales = new HashSet<>(literales); // Copia los literales para evitar modificaciones externas
    }

    /**
     * Método para unir dos cláusulas en una nueva.
     * Combina los literales de ambas cláusulas en un solo conjunto.
     * Esto representa la operación de disyunción lógica (OR) entre los literales.
     */
    public Clausula unir(Clausula otra) {
        Set<Literal> nueva = new HashSet<>(this.literales); // Copia los literales actuales
        nueva.addAll(otra.literales); // Agrega los literales de la otra cláusula
        return new Clausula(nueva); // Retorna una nueva cláusula con el resultado
    }

    /**
     * Método para restar los literales de otra cláusula.
     * Quita los literales de la cláusula actual que estén en la cláusula proporcionada.
     * 
     */
    public Clausula restar(Clausula otra) {
        Set<Literal> nueva = new HashSet<>(this.literales); // Copia los literales actuales
        nueva.removeAll(otra.literales); // Elimina los literales que también están en la otra cláusula
        return new Clausula(nueva); // Retorna una nueva cláusula con el resultado
    }

    /**
     * Método equals para comparar si dos cláusulas son iguales.
     * Dos cláusulas son iguales si tienen exactamente los mismos literales.
     * 
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si es la misma instancia, son iguales
        if (o == null || getClass() != o.getClass()) return false; // Verifica el tipo
        Clausula clausula = (Clausula) o;
        return Objects.equals(literales, clausula.literales); // Compara los conjuntos de literales
    }

    /**
     * Método hashCode para permitir el uso de cláusulas en estructuras de datos como HashSet o HashMap.
     */
    @Override
    public int hashCode() {
        return Objects.hash(literales);
    }

    /**
     * Representación en texto de la cláusula.
     * Devuelve una cadena donde los literales están unidos por " OR ".
     */
    @Override
    public String toString() {
        return literales.stream()
                .map(Object::toString) // Convierte cada literal en su representación de cadena
                .collect(Collectors.joining(" OR ")); // Une los literales con " OR "
    }
}
