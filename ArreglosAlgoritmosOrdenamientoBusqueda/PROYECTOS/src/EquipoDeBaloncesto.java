import java.util.Scanner;

public class EquipoDeBaloncesto {
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        int jugadores = obtenerCantidadJugadores(lector);

        if (jugadores == -1) {
            System.out.println("Demasiados intentos fallidos. Saliendo del programa.");
            return;
        }

        lector.nextLine();

        String[][] jugadoresDatos = new String[jugadores][8];

        for (int i = 0; i < jugadores; i++) {
            System.out.println("Ingresa el nombre del Jugador " + (i + 1) + ": ");
            jugadoresDatos[i][0] = lector.nextLine();
            jugadoresDatos[i][1] = String.valueOf(obtenerDoubleNoNegativo(lector, "velocidad máxima (Km/h)"));
            jugadoresDatos[i][2] = String.valueOf(obtenerEnteroNoNegativo(lector, "edad"));
            jugadoresDatos[i][3] = String.valueOf(obtenerDoubleNoNegativo(lector, "estatura (cm)"));
            jugadoresDatos[i][4] = String.valueOf(obtenerDoubleNoNegativo(lector, "alcance en salto (cm)"));
            jugadoresDatos[i][5] = String.valueOf(obtenerDoubleNoNegativo(lector, "envergadura (cm)"));
            jugadoresDatos[i][6] = String.valueOf(obtenerDoubleNoNegativo(lector, "peso (Kg)"));
        }

        double estaturaPromedio = calcularEstaturaPromedio(jugadoresDatos);
        calcularCompletitud(jugadoresDatos);
        int jugadorMasCompleto = encontrarJugadorMasCompleto(jugadoresDatos);
        ordenarJugadoresPorVelocidad(jugadoresDatos);
        ordenarJugadoresPorMasaCorporal(jugadoresDatos);
        mostrarResultados(jugadoresDatos, estaturaPromedio, jugadorMasCompleto);

        lector.close();
    }

    public static int obtenerCantidadJugadores(Scanner lector) {
        int intentos = 0;
        int jugadores;

        while (intentos < 3) {
            System.out.println("Digite la cantidad de jugadores en su plantilla (1-5): ");
            jugadores = lector.nextInt();

            if (jugadores >= 1 && jugadores <= 5) {
                return jugadores;
            } else {
                System.out.println("No se acepta una plantilla con jugadores menores a 1 o mayores a 5. Intenta de nuevo.");
                intentos++;
            }
        }

        return -1;
    }

    public static double obtenerDoubleNoNegativo(Scanner lector, String mensaje) {
        double valor;
        int intentos = 0;

        do {
            System.out.println("Ingresa " + mensaje + ": ");
            while (!lector.hasNextDouble()) {
                System.out.println("Entrada no válida. Debe ser un número.");
                lector.next();
            }
            valor = lector.nextDouble();

            if (valor >= 0) {
                return valor;
            } else {
                System.out.println("No se aceptan valores negativos. Intenta de nuevo.");
                intentos++;
            }
        } while (intentos < 3);

        System.out.println("Demasiados intentos fallidos. Saliendo del programa.");
        System.exit(1);
        return valor;
    }

    public static int obtenerEnteroNoNegativo(Scanner lector, String mensaje) {
        int valor;
        int intentos = 0;

        do {
            System.out.println("Ingresa " + mensaje + ": ");
            while (!lector.hasNextInt()) {
                System.out.println("Entrada no válida. Debe ser un número entero.");
                lector.next();
            }
            valor = lector.nextInt();

            if (valor >= 0) {
                return valor;
            } else {
                System.out.println("No se aceptan valores negativos. Intenta de nuevo.");
                intentos++;
            }
        } while (intentos < 3);

        System.out.println("Demasiados intentos fallidos. Saliendo del programa.");
        System.exit(1);
        return valor;
    }

    public static double calcularEstaturaPromedio(String[][] jugadoresDatos) {
        double sumaEstatura = 0;
        for (int i = 0; i < jugadoresDatos.length; i++) {
            sumaEstatura += Double.parseDouble(jugadoresDatos[i][3]);
        }
        return sumaEstatura / jugadoresDatos.length;
    }

    public static void calcularCompletitud(String[][] jugadoresDatos) {
        for (int i = 0; i < jugadoresDatos.length; i++) {
            double estatura = Double.parseDouble(jugadoresDatos[i][3]) * 0.25;
            double velocidad = Double.parseDouble(jugadoresDatos[i][1]) * 0.25;
            double salto = Double.parseDouble(jugadoresDatos[i][4]) * 0.20;
            double peso = Double.parseDouble(jugadoresDatos[i][6]) * 0.10;
            double envergadura = Double.parseDouble(jugadoresDatos[i][5]) * 0.20;
            double completitud = estatura + velocidad + salto + peso + envergadura;
            jugadoresDatos[i][7] = String.format("%.2f", completitud);
        }
    }

    public static int encontrarJugadorMasCompleto(String[][] jugadoresDatos) {
        int jugadorMasCompleto = 0;
        double maxCompletitud = Double.parseDouble(jugadoresDatos[0][7]);
        for (int i = 1; i < jugadoresDatos.length; i++) {
            double completitud = Double.parseDouble(jugadoresDatos[i][7]);
            if (completitud > maxCompletitud) {
                maxCompletitud = completitud;
                jugadorMasCompleto = i;
            }
        }
        return jugadorMasCompleto;
    }

    public static void ordenarJugadoresPorVelocidad(String[][] jugadoresDatos) {
        int n = jugadoresDatos.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                double velocidad1 = Double.parseDouble(jugadoresDatos[j][1]);
                double velocidad2 = Double.parseDouble(jugadoresDatos[j + 1][1]);
                if (velocidad1 < velocidad2) {
                    intercambiar(jugadoresDatos, j, j + 1);
                }
            }
        }
    }

    public static void ordenarJugadoresPorMasaCorporal(String[][] jugadoresDatos) {
        int n = jugadoresDatos.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                double masaCorporal1 = calcularMasaCorporal(jugadoresDatos[j]);
                double masaCorporal2 = calcularMasaCorporal(jugadoresDatos[j + 1]);
                if (masaCorporal1 > masaCorporal2) {
                    intercambiar(jugadoresDatos, j, j + 1);
                }
            }
        }
    }

    public static double calcularMasaCorporal(String[] jugador) {
        double peso = Double.parseDouble(jugador[6]);
        double estatura = Double.parseDouble(jugador[3]);
        return peso / (estatura * estatura);
    }

    public static void intercambiar(String[][] jugadoresDatos, int indice1, int indice2) {
        String[] temp = jugadoresDatos[indice1];
        jugadoresDatos[indice1] = jugadoresDatos[indice2];
        jugadoresDatos[indice2] = temp;
    }

    public static void mostrarResultados(String[][] jugadoresDatos, double estaturaPromedio, int jugadorMasCompleto) {
        System.out.println("Jugadores ordenados por velocidad (mayor a menor):");
        for (int i = 0; i < jugadoresDatos.length; i++) {
            System.out.println(jugadoresDatos[i][0]);
        }

        System.out.println("Jugadores ordenados por masa corporal (menor a mayor):");
        for (int i = 0; i < jugadoresDatos.length; i++) {
            System.out.println(jugadoresDatos[i][0]);
        }

        System.out.println("Jugador más completo: " + jugadoresDatos[jugadorMasCompleto][0] + " - Completitud: " + jugadoresDatos[jugadorMasCompleto][7]);
        System.out.println("La estatura promedio del equipo es: " + estaturaPromedio);
    }
}