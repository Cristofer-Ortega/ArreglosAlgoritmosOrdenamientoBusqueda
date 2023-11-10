import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class Matricula {
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        int intentos = 0;
        int Clases = 0;
        String recibo, numeroInscripcion, carrera, carnet, turno, planEstudio, semestre;

        while (intentos < 3) {
            try {
                System.out.print("Digite la cantidad de clases que desea inscribir: ");
                Clases = Integer.parseInt(lector.nextLine());

                if (Clases < 0 || Clases > 7) {
                    System.out.println("No se aceptan clases menores a 1 o mayores a 7");
                    intentos++;
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número válido para la cantidad de clases.");
                intentos++;
            }
        }

        if (intentos == 3) {
            System.out.println("Demasiados intentos fallidos");
            return;
        } else {
            lector.nextLine();

            System.out.println("Ingrese el número de recibo: ");
            recibo = lector.nextLine();
            System.out.println("Número de inscripción: ");
            numeroInscripcion = lector.nextLine();
            System.out.println("Ingrese la carrera: ");
            carrera = lector.nextLine();
            System.out.println("Ingrese el carnet: ");
            carnet = lector.nextLine();
            System.out.println("Ingrese el turno: ");
            turno = lector.nextLine();
            System.out.println("Ingrese el plan de estudio: ");
            planEstudio = lector.nextLine();
            System.out.println("Ingrese el semestre: ");
            semestre = lector.nextLine();

            TreeSet<String> asignaturasOrdenadas = new TreeSet<>();

            String[] Asignatura = new String[Clases];
            String[] Aula = new String[Clases];
            int[] Creditos = new int[Clases];
            String[] RetiroAsignatura = new String[Clases];
            String[] MotivoRetiro = new String[Clases];
            Date[] FechaMatricula = new Date[Clases];

            int totalCreditos = 0;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            for (int i = 0; i < Clases; i++) {
                System.out.println("Ingresa el nombre de la asignatura " + (i + 1) + ": ");
                Asignatura[i] = lector.nextLine();
                asignaturasOrdenadas.add(Asignatura[i]);

                System.out.println("Ingresa el grupo " + (i + 1) + ": ");
                Aula[i] = lector.nextLine();

                int intentosCreditos = 0;
                int creditos = 0;
                do {
                    try {
                        System.out.println("Ingresa la cantidad de créditos de la asignatura " + (i + 1) + " (entre 1 y 4): ");
                        creditos = Integer.parseInt(lector.nextLine());

                        if (creditos < 1 || creditos > 4) {
                            System.out.println("Error: Los créditos no son válidos. Deben estar entre 1 y 4.");
                            intentosCreditos++;
                        }

                        if (intentosCreditos == 3) {
                            System.out.println("Demasiados intentos fallidos para los créditos. Saliendo...");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Debes ingresar un número válido para los créditos.");
                        intentosCreditos++;
                    }
                } while (creditos < 1 || creditos > 4);

                Creditos[i] = creditos;
                totalCreditos += creditos;

                while (totalCreditos > 27) {
                    System.out.println("La suma total de créditos supera 27. Por favor, corrija los créditos de las asignaturas.");
                    totalCreditos -= creditos;
                    creditos = 0;
                    intentosCreditos = 0;

                    do {
                        try {
                            System.out.println("Ingresa la cantidad de créditos de la asignatura " + (i + 1) + " (entre 1 y 4): ");
                            creditos = Integer.parseInt(lector.nextLine());

                            if (creditos < 1 || creditos > 4) {
                                System.out.println("Error: Los créditos no son válidos. Deben estar entre 1 y 4.");
                                intentosCreditos++;
                            }

                            if (intentosCreditos == 3) {
                                System.out.println("Demasiados intentos fallidos para los créditos. Saliendo...");
                                return;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Debes ingresar un número válido para los créditos.");
                            intentosCreditos++;
                        }
                    } while (creditos < 1 || creditos > 4);

                Creditos[i] = creditos;
                totalCreditos += creditos;
                }

                System.out.println("¿Desea retirar esta asignatura? (Sí/No): ");
                RetiroAsignatura[i] = lector.nextLine().toLowerCase();

                if (RetiroAsignatura[i].equals("sí")) {
                    System.out.println("Por favor, ingrese el motivo de retiro: ");
                    MotivoRetiro[i] = lector.nextLine();
                }

                FechaMatricula[i] = new Date();
            }

            System.out.println("Número de recibo: " + recibo);
            System.out.println("Número de inscripción: " + numeroInscripcion);
            System.out.println("Carrera: " + carrera);
            System.out.println("Carnet: " + carnet);
            System.out.println("Turno: " + turno);
            System.out.println("Plan de estudio: " + planEstudio);
            System.out.println("Semestre: " + semestre);

            System.out.println("Información de las asignaturas ordenadas alfabéticamente:");
            for (String asignatura : asignaturasOrdenadas) {
                int indice = 0;
                for (int i = 0; i < Clases; i++) {
                    if (asignatura.equals(Asignatura[i])) {
                        indice = i;
                        break;
                    }
                }
                System.out.print("Asignatura: " + asignatura + " | ");
                System.out.print("Aula: " + Aula[indice] + " | ");
                System.out.print("Número de Créditos: " + Creditos[indice] + " | ");
                System.out.print("Retiro de asignatura: " + RetiroAsignatura[indice]);
                if (RetiroAsignatura[indice].equals("sí")) {
                    System.out.print(" | Motivo de retiro: " + MotivoRetiro[indice]);
                }
                System.out.print(" | Fecha de Matrícula: " + dateFormat.format(FechaMatricula[indice]));
                System.out.println();
            }
        }
    }
}