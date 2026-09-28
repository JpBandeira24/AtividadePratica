import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;





public class Entrada {

    private final Scanner scanner = new Scanner(System.in);

    
    public String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Campo obrigatório.");
        }
    }

    public int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    
    public int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            int numero = lerInteiro(mensagem);
            if (numero >= minimo && numero <= maximo) {
                return numero;
            }
            System.out.println("Escolha um valor entre " + minimo + " e " + maximo + ".");
        }
    }

    public double lerNumeroPositivo(String mensagem) {
        return lerNumeroPositivo(mensagem, 0); 
    }

    



    public double lerNumeroPositivo(String mensagem, double valorPadrao) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (texto.isEmpty() && valorPadrao > 0) {
                return valorPadrao;
            }
            try {
                double numero = Double.parseDouble(texto.replace(',', '.'));
                if (numero > 0) {
                    return numero;
                }
                System.out.println("Informe um valor maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Exemplo: 150,50");
            }
        }
    }

    
    public String lerPlaca(String mensagem) {
        while (true) {
            String placa = lerTexto(mensagem).toUpperCase().replace(" ", "");
            if (placa.matches("[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}")) {
                return placa;
            }
            System.out.println("Placa inválida. Exemplos: ABC-1234 ou ABC1D23.");
        }
    }

    
    public LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(texto, OrdemServico.FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato dd/mm/aaaa.");
            }
        }
    }

    
    public <T> T escolher(String mensagem, T[] opcoes) {
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((i + 1) + " - " + opcoes[i]);
        }
        int escolha = lerInteiro(mensagem, 1, opcoes.length);
        return opcoes[escolha - 1];
    }

    public void fechar() {
        scanner.close();
    }
}
