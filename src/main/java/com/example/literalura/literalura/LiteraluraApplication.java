package com.example.literalura.literalura;

import com.example.literalura.literalura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final LibroService libroService;

	public LiteraluraApplication(LibroService libroService) {
		this.libroService = libroService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("1. Buscar libro por título");
			System.out.println("2. Listar todos los libros registrados");
			System.out.println("3. Listar todos los autores registrados");
			System.out.println("4. Listar autores vivos en un año");
			System.out.println("5. Mostrar cantidad de libros por idioma");
			System.out.println("0. Salir");
			System.out.print("Selecciona una opción: ");

			int opcion = scanner.nextInt();
			scanner.nextLine(); // Consumir nueva línea
			if (opcion == 0) {
				break;
			}
			libroService.manejarOpcion(opcion, scanner);
		}
	}
}