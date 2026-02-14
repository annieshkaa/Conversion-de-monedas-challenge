//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static float obtenerTipoCambio(String origen, String destino) {
        float cambio =0;

        // Creando cliente HTTP
        HttpClient cliente = HttpClient.newHttpClient();
// Código omitido

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/90714818cf230e46c0d1fdb4/pair/" + origen + "/" + destino))
                .GET()
                .build();


        try {
            // Enviando solicitud y recibiendo respuesta
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            String respuesta_json = respuesta.body();
            //String respuesta_json = new String(responseBody.readAllBytes());

                JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(respuesta_json);
            JsonObject jsonobj = root.getAsJsonObject();

             cambio = jsonobj.get("conversion_rate").getAsFloat();
        } catch (Exception ignored) {
            System.out.println("error: " +ignored);
        }

        return cambio;
    }

    public static float convertir(String moneda_1, String moneda_2, float importe){
        return importe * obtenerTipoCambio(moneda_1,moneda_2);
    }


    public static void exibirMenu(){

        System.out.println("""
                
        *******************************************************
        Sea bienvenido/a al Conversor de Moneda =]
        
        1) Dólar ==> Peso argentino
        2) Peso argentino ==> Dólar
        3) Dólar ==> Real brasileño
        4) Real brasileño ==> Dólar
        5) Dólar ==> Peso colombiano
        6) Peso colombiano ==> Dólar
        7) Salir
        
        Elija una opción válida:
        
        *******************************************************
        """);
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);


        int opcion_elegida = 0;
        while (opcion_elegida < 1 || opcion_elegida > 7) {
            exibirMenu();


            try {
                opcion_elegida = scanner.nextInt();

                if (opcion_elegida < 1 || opcion_elegida > 7) {
                    System.out.println("Opcion invalida, por favor elija un numero entre 1 y 7.");
                } else {
                    if (opcion_elegida == 7){
                        return;
                    }
                    System.out.println("ingrese el importe");
                    float importe = scanner.nextFloat();
                    float resultado = 0;
                    switch (opcion_elegida) {
                        case 1:
                            resultado = convertir("USD", "ARS", importe);  // Dólar => Peso argentino
                            break;

                        case 2:
                            resultado = convertir("ARS", "USD", importe);  // Peso argentino => Dólar
                            break;

                        case 3:
                            resultado = convertir("USD", "BRL", importe);  // Dólar => Real brasileño
                            break;

                        case 4:
                            resultado = convertir("BRL", "USD", importe);  // Real brasileño => Dólar
                            break;

                        case 5:
                            resultado = convertir("USD", "COP", importe);  // Dólar => Peso colombiano
                            break;

                        case 6:
                            resultado = convertir("COP", "USD", importe);  // Peso colombiano => Dólar
                            break;
                        default:
                            System.out.println("Opción no válida, por favor elija una opción entre 1 y 7.");
                    }
                    System.out.println("resultado de la conversion: "+resultado);
                }

            } catch (InputMismatchException e) {

                System.out.println("Opcion invalida, por favor elija un numero entre 1 y 7");
                scanner.nextLine();
            }
        }

        scanner.close();


    return;


    }
}