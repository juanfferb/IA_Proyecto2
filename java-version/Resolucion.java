import java.util.*;

// Implementa el motor de resolución para inferencia lógica
public class Resolucion {
    private Set<Clausula> clausulas = new LinkedHashSet<>(); // Base de conocimiento
    public Clausula vacio = new Clausula(); // Clausula vacía

    // Añade una cláusula a la base
    public void agregarClausula(Clausula c) {
        clausulas.add(c);
    }

    // Muestra las cláusulas actuales
    public void mostrarClausulas() {
        for (Clausula c : clausulas) {
            List<Literal> ordenados = new ArrayList<>(c.literales);
            ordenados.sort(Comparator.comparing(Literal::toString));
            System.out.println(ordenados);
        }
    }
    
    // Nega una conjetura añadiendo sus literales negados como cláusulas
    public void negarConjetura(List<Literal> conjetura) {
        for (Literal l : conjetura) {
            Clausula negada = new Clausula();
            negada.literales.add(l.negado());
            clausulas.add(negada);
        }
    }

    // Asocia letras con nombres reales (ej. a->Marco)
    public Map<String, String> unificarVariables(String... args) {
        Map<String, String> mapa = new HashMap<>();
        String letras = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < args.length; i++) {
            mapa.put(String.valueOf(letras.charAt(i)), args[i]);
        }
        return mapa;
    }

    // Sustituye las variables por nombres reales en las cláusulas
    public void sustituirVariables(Map<String, String> variables) {
        List<Clausula> nuevas = new ArrayList<>();
        for (Clausula c : clausulas) {
            Set<Literal> nuevos = new LinkedHashSet<>(); // 🔥 Mantener orden
            for (Literal l : c.literales) {
                String[] nuevosArgs = Arrays.stream(l.argumentos)
                                            .map(arg -> variables.getOrDefault(arg, arg))
                                            .toArray(String[]::new);
                nuevos.add(new Literal(l.predicado, nuevosArgs));
            }
            nuevas.add(new Clausula(new LinkedHashSet<>(nuevos))); // 🛑 Asegurar LinkedHashSet al crear la nueva cláusula
        }
        clausulas = new LinkedHashSet<>(nuevas); // Asegurar que siga siendo un LinkedHashSet
    }    

    public boolean resolver() {
        Set<Clausula> clausulasAnalizadas = new LinkedHashSet<>(); // Mantener orden
    
        while (!clausulas.isEmpty()) {
            List<Clausula> listaClausulas = new ArrayList<>(clausulas); // ✅ Convertir a List
            Clausula ultima = listaClausulas.get(listaClausulas.size() - 1); // Última cláusula agregada
            Clausula primera = ultima;
            Literal literalOpuesto = null;
            Clausula segunda = null;
    
            // Buscar un literal opuesto en otra cláusula
            for (Literal l : primera.literales) {
                for (Clausula c : clausulas) {
                    if (c == primera || clausulasAnalizadas.contains(c)) continue;
                    for (Literal l2 : c.literales) {
                        if (l.equals(l2.negado())) {
                            literalOpuesto = l;
                            segunda = c;
                            break;
                        }
                    }
                    if (segunda != null) break;
                }
                if (segunda != null) break;
            }
    
            if (segunda == null) {
                clausulasAnalizadas.add(primera);
                boolean progreso = false;
    
                for (Clausula c1 : clausulas) {
                    if (clausulasAnalizadas.contains(c1)) continue;
                    for (Clausula c2 : clausulas) {
                        if (c1 == c2 || clausulasAnalizadas.contains(c2)) continue;
                        for (Literal l : c1.literales) {
                            if (c2.literales.contains(l.negado())) {
                                literalOpuesto = l;
                                primera = c1;
                                segunda = c2;
                                progreso = true;
                                break;
                            }
                        }
                        if (progreso) break;
                    }
                    if (progreso) break;
                }
    
                if (!progreso) {
                    System.out.println("--------------------------");
                    System.out.println("No se encontró una cláusula opuesta. Fin del proceso. Conjetura falsa.");
                    return false;
                }
            }
    
            // Generar la cláusula resolvente sin alterar el orden de los literales
            Set<Literal> union = new LinkedHashSet<>(segunda.literales);
            union.addAll(primera.literales);
            union.remove(literalOpuesto);
            union.remove(literalOpuesto.negado());
            Clausula resolvente = new Clausula(union);  // Mantener el orden original con LinkedHashSet

    
            if (resolvente.literales.isEmpty()) {
                System.out.println("--------------------------");
                System.out.println("Se ha derivado la cláusula vacía. Contradicción encontrada. La conjetura es verdadera");
                return true;
            }
    
            clausulas.remove(primera);
            clausulas.remove(segunda);
            clausulas.add(resolvente);
            System.out.println("Nueva cláusula generada: " + resolvente);
        }
    
        return false;
    }
}
