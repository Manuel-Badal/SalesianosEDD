package repaso;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class MatricesBinario {
	private static final int BYTES_ENTERO = (Integer.SIZE)/8;
	private static final int BYTES_DOUBLE = (Double.SIZE)/8;
	
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		System.out.print("Dame las filas: ");
		int filas = entrada.nextInt();
		System.out.print("Dame las columnas: ");
		int columnas = entrada.nextInt();
		double[][] matriz = new double[filas][columnas];
		// Rellenamos la matriz
		for(int i = 0; i < filas; i++) {
			for(int j = 0; j < columnas; j++) {
				System.out.print("Dame el valor para la celda: ");
				matriz[i][j] = entrada.nextDouble();
			}
		}
		// Una vez rellenada creamos el fichero y lo escribimos
		crearFichero("Ficheros/binarioMatrices.dat", filas, columnas, matriz);
		// Leemos la celda 4
		inspeccionar("Ficheros/binarioMatrices.dat", 4);
	}
	
	/**
	 * Pre: posicion >= 1 y posicion <= f.length()/2*BYTES_ENTERO + BYTES_DOUBLE
	 * Post: Se posiciona para leer o modificar el valor de la celda en [posicion]
	 */
	public static void posicionar(RandomAccessFile f, int posicion) {
		try {
			f.seek(2*BYTES_ENTERO + BYTES_DOUBLE*(posicion - 1));
		} catch (IOException e) {
			System.out.println("Error al posicionarnos en el fichero");
		}
	}
	
	/**
	 * Pre: el objeto [f] está asociado a un fichero en modo escritura de datos.
	 * Post: Escribe, en el fichero asociado a [f], el dato double [valor]
	 */
	public static void escribirDouble(RandomAccessFile f, double valor) {
		try {
			f.writeDouble(valor);
		} catch (IOException e) {
			System.out.println("Error al escribir en el fichero");
		}
	}
	
	/**
	 * Pre: el objeto [f] está asociado a un fichero en modo escritura de datos.
	 * Post: Escribe, en el fichero asociado a [f], el dato entero [valor]
	 */
	public static void escribirInt(RandomAccessFile f, int valor) {
		try {
			f.writeInt(valor);
		} catch (IOException e) {
			System.out.println("Error al escribir en el fichero");
		}
	}
	
	/**
	 * Pre: [filas] y [columnas] tiene que ser mayor que 0, y la matriz debe
	 * 		contener números double inicializados
	 * Post: Crea un fichero donde se almacena primero las filas y columnas
	 * 		que tiene una matriz, y seguidos sus valores de cada una de las 
	 * 		celdas.
	 */
	public static void crearFichero(String nombre, int filas, int columnas,
			double[][] matriz) {
		try {
			/*
			 * Crea un objeto [RandomAccessFile] al que se asocia un fichero deno
			 * minado [nombre] en modo "rw" (lectura y escritura, read, write).
			 */
			RandomAccessFile f = new RandomAccessFile(nombre, "rw");
			/*
			 * Elimina todos los datos que pudiera almacenar, en su caso, el fichero
			 * asociado a [f] (lo limpia por completo).
			 */
			f.setLength(0);
			/*
			 * Escribe en el fichero asociado a [f] los datos de la matriz asociados
			 */
			escribirInt(f, filas);
			escribirInt(f, columnas);
			for(int i = 0; i < filas; i++) {
				for(int j = 0; j < columnas; j++) {
					escribirDouble(f, matriz[i][j]);
				}
			}
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha podido ser abierto");
		} catch (IOException e) {
			System.out.println("Error en la operación E/S con el fichero");
		}
	}
	
	/**
	 * Pre: [numero] tiene que estar dentro del rango
	 * Post: Presenta por pantalla el dato de la celda correspondiente al
	 * 		[numero] seleccionado por el usuario
	 */
	public static void inspeccionar(String nombre, int numero) {
		try {
			/*
			 * Se crea un objeto [RandomAccessFile] al que se asocia un fichero
			 * denominado [nombre]
			 */
			RandomAccessFile f = new RandomAccessFile(nombre, "r");
			/*
			 * Sitúa el punto de trabajo en el fichero asociado a [f] en posición [numero]
			 */
			posicionar(f, numero);
			/*
			 * Lee el valor de la celda buscada
			 */
			double valor = f.readDouble();
			System.out.println("INSPECCIONAMOS CELDA " + numero + " con valor = " + valor);
			f.close();
		} catch (FileNotFoundException e) {
			System.out.println("El fichero no ha podido ser abierto");
		} catch (IOException e) {
			System.out.println("Error en la lectura de datos del fichero");
		}
		
	}
}
