import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class AppRubrica {

	private static Vector<Persona> listaPersone;
	private static File yourFile = new File("informazioni.txt");

	public static void aggiornaFile() throws IOException {

		FileOutputStream fs = new FileOutputStream(yourFile);
		PrintStream fileout = new PrintStream(fs);
		for (int i = 0; i < listaPersone.size(); i++) {
			String s = listaPersone.elementAt(i).getDatiPersona();
			fileout.println(s);
		}
		fileout.close();
	}

	private static Vector<Persona> creaDaFile() throws FileNotFoundException {

		Vector<Persona> lista = new Vector<Persona>();
		Scanner scanner = new Scanner(yourFile);
		StringTokenizer st;
		while (scanner.hasNextLine()) {
			String record = scanner.nextLine();
			st = new StringTokenizer(record, ";");
			String nome = st.nextToken();
			String cognome = st.nextToken();
			String indirizzo = st.nextToken();
			String telefono = st.nextToken();
			int eta = Integer.parseInt(st.nextToken());
			Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
			lista.add(p);
		}
		scanner.close();
		return lista;
	}

	public static void main(String[] args) throws IOException {

		yourFile.createNewFile();
		listaPersone = creaDaFile();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinestraPrincipale window = new FinestraPrincipale(listaPersone);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
