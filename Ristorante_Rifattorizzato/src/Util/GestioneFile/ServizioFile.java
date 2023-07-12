package Util.GestioneFile;
import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class ServizioFile
{
	public static final String MSG_SUCCESSO = "File creato con successo ";
	public static final String MSG_ERR_CREAZIONE = "ATTENZIONE! Si è verificato un errore durante la creazione del file ";

	public static File creaFile(String nomeFile) {
		File file = new File(nomeFile);

		try {
			if (file.createNewFile()) {
				System.out.println(MSG_SUCCESSO);
			}
			file.setWritable(true, false);
			file.setReadable(true, false);
		} catch (IOException e) {
			System.out.println(MSG_ERR_CREAZIONE);
		}

		return file;
	}

	public static boolean ePrimaApertura(String directoryPath) {
		File ristoranteDirectory = new File(directoryPath);

		if (!ristoranteDirectory.exists()) {
			if (ristoranteDirectory.mkdir()) {
				System.out.println("Directory creata con successo.");
				return true; // Restituisci true se la directory è stata creata
			} else {
				System.out.println("Impossibile creare la directory.");
				return false; // Restituisci false se la directory non può essere creata
			}
		}

		return false; // Restituisci false se la directory esiste
	}

	public static String getNomeFileSenzaEstensione(String filePath) {
		File file = new File(filePath);
		String fileName = file.getName();
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(0, dotIndex);
		}
		return fileName;
	}

	public static String getNomeFile(String DIRECTORY_PATH) {
		File directory = new File(DIRECTORY_PATH);
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null && files.length > 0) {
				String fileName = files[0].getName();
				return ServizioFile.getNomeFileSenzaEstensione(fileName);
			}
		}
		return null; // Restituisci null se non è stato possibile trovare il file
	}

	public static boolean creaDirectory(String nomeDirectory) {
		File directory = new File(nomeDirectory);

		if (directory.exists()) {
			System.out.println("La directory " + nomeDirectory + " esiste già.");
			return false; // Restituisci false se la directory esiste già
		}

		if (directory.mkdir()) {
			System.out.println("La directory " + nomeDirectory + " è stata creata con successo.");
			return true; // Restituisci true se la directory è stata creata
		} else {
			System.out.println("Impossibile creare la directory " + nomeDirectory + ".");
			return false; // Restituisci false se la directory non può essere creata
		}
	}

	public static boolean controlloEsistenzaFile(String percorsoFile) {
		File file = new File(percorsoFile);
		return file.exists();
	}

	public static File trovaPrimoFileTxt(String directoryPath) {
		File directory = new File(directoryPath);
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".txt")) {
						return file;
					}
				}
			}
		}
		return null;
	}

	public static String trovaNomePrimoFileTxt(String directoryPath) {
		File file = trovaPrimoFileTxt(directoryPath);
		if (file != null) {
			return getNomeFileSenzaEstensione(file.getName());
		}
		return "File non trovato";
	}

	//da' il numero di file .txt all'interno di una cartella
	public static int contaFileTxt(String directoryPath) {
		File directory = new File(directoryPath);
		int count = 0;

		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".txt")) {
						count++;
					}
				}
			}
		}

		return count;
	}

	public static List<File> getElencoFileTxt(String directoryPath) {
		List<File> fileTxtList = new ArrayList<>();

		File directory = new File(directoryPath);
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".txt")) {
						fileTxtList.add(file);
					}
				}
			}
		}

		return fileTxtList;
	}

	public static List<File> getElencoDirectory(String directoryPath) {
		List<File> fileDirectoryList = new ArrayList<>();

		File directory = new File(directoryPath);
		if (directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						fileDirectoryList.add(file);
					}
				}
			}
		}
		return fileDirectoryList;
	}
}

