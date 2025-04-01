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
            System.out.println(c.toString());
        }
    }
    
    // Niega una conjetura añadiendo sus literales negados como cláusulas
    public void negarConjetura(List<Literal> conjetura) {
        for (Literal l : conjetura) {
            Clausula negada = new Clausula();
            negada.literales.add(l.negado());
            clausulas.add(negada);
        }
    }

    // Asocia letras con nombres reales (ej. a->Marco)
    public Map<String, String> unificarVariables(String... args) {
        Map<String, String> mapa = new LinkedHashMap<>();
        String letras = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < args.length; i++) {
            mapa.put(String.valueOf(letras.charAt(i)), args[i]);
        }
        return mapa;
    }

    // Sustituye las variables por nombres reales en las cláusulas
    public void sustituirVariables(Map<String, String> variables) {
        for (Clausula c : clausulas) {
            // Crea un nuevo conjunto de literales para la cláusula
            Set<Literal> nuevosLiterales = new LinkedHashSet<>();
            // Recorre los literales de la cláusula
            for (Literal l : c.literales) {
                // Crea un nuevo literal con el mismo predicado
                String nuevoPredicado = l.predicado;
                String[] nuevosArgumentos = new String[l.argumentos.length];
                for (int i = 0; i < l.argumentos.length; i++) {
                    String arg = l.argumentos[i];
                    if (variables.containsKey(arg)) {
                        nuevosArgumentos[i] = variables.get(arg);
                    } else {
                        nuevosArgumentos[i] = arg;
                    }
                }
                nuevosLiterales.add(new Literal(nuevoPredicado, nuevosArgumentos));
            }
            c.literales = nuevosLiterales;
        }
    }    

        public boolean resolver() {
            Set<Clausula> nuevas = new LinkedHashSet<>();
        
            while (true) {
                List<Clausula> listaClausulas = new ArrayList<>(clausulas);
        
                for (int i = 0; i < listaClausulas.size(); i++) {
                    for (int j = i + 1; j < listaClausulas.size(); j++) {
                        Clausula c1 = listaClausulas.get(i);
                        Clausula c2 = listaClausulas.get(j);
        
                        Set<Clausula> resolventes = resolverClausulas(c1, c2);
                        for (Clausula c : resolventes) {
                            if (c.literales.isEmpty()) {
                                System.out.println("¡Cláusula vacía encontrada!");
                                return true;
                            }
                        }
                        
                        nuevas.addAll(resolventes);
                    }
                }
        
                if (clausulas.containsAll(nuevas)) {
                    System.out.println("No se puede deducir la conclusión.");
                    return false; // No hay nuevas cláusulas, no se puede deducir
                }
        
                clausulas.addAll(nuevas);
            }
        }

        private Set<Clausula> resolverClausulas(Clausula c1, Clausula c2) {
            Set<Clausula> resolventes = new LinkedHashSet<>();
        
            for (Literal l1 : c1.literales) {
                for (Literal l2 : c2.literales) {
                    Map<String, String> unificacion = Unificador.unificar(l1, l2.negado());
        
                    if (unificacion != null) {
                        // Crear la cláusula resolvente
                        Set<Literal> nuevos = new LinkedHashSet<>();
        
                        for (Literal lit : c1.literales) {
                            if (!lit.equals(l1)) {
                                nuevos.add(Unificador.aplicarSustitucion(lit, unificacion));
                            }
                        }
                        for (Literal lit : c2.literales) {
                            if (!lit.equals(l2)) {
                                nuevos.add(Unificador.aplicarSustitucion(lit, unificacion));
                            }
                        }

                        Clausula nuevaClausula = new Clausula(nuevos);
                        if (!clausulas.contains(nuevaClausula)) {
                            System.out.println("    → " + nuevaClausula);
                            resolventes.add(nuevaClausula);
                        }
                        

                    }
                }
            }
        
            return resolventes;
        }
        
        
        
        
}