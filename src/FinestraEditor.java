import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class FinestraEditor extends JFrame {

	private static final long serialVersionUID = 1L;

	private JToolBar toolBar;
	private JPanel datiPersona;

	public FinestraEditor(Vector<Persona> lista, int indice) throws IOException {

		super("Rubrica Telefonica");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(new BorderLayout(10, 10));

		BufferedImage myPicture = ImageIO.read(ResourceLoader.load("/res/imgEditor.png"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		c.add(picLabel, BorderLayout.WEST);

		datiPersona = new JPanel();
		datiPersona.setLayout(new GridLayout(0, 2));
		datiPersona.setBorder(BorderFactory.createEmptyBorder(30, 20, 40, 20));
		c.add(datiPersona, BorderLayout.CENTER);

		JLabel labNome = new JLabel("Nome", JLabel.CENTER);
		JTextField fiNome = new JTextField(20);
		JLabel labCognome = new JLabel("Cognome", JLabel.CENTER);
		JTextField fiCognome = new JTextField(20);
		JLabel labIndirizzo = new JLabel("Indirizzo", JLabel.CENTER);
		JTextField fiIndirizzo = new JTextField(20);
		JLabel labTelefono = new JLabel("Telefono", JLabel.CENTER);
		JTextField fiTelefono = new JTextField(20);
		JLabel labEta = new JLabel("Età", JLabel.CENTER);
		JTextField fiEta = new JTextField(20);

		if (lista.size() != 0 && indice != -1) {
			Persona p = lista.elementAt(indice);
			fiNome.setText(p.getNome());
			fiCognome.setText(p.getCognome());
			fiIndirizzo.setText(p.getIndirizzo());
			fiTelefono.setText(p.getTelefono());
			fiEta.setText(Integer.toString(p.getEta()));
		}
		datiPersona.add(labNome);
		datiPersona.add(fiNome);
		datiPersona.add(labCognome);
		datiPersona.add(fiCognome);
		datiPersona.add(labIndirizzo);
		datiPersona.add(fiIndirizzo);
		datiPersona.add(labTelefono);
		datiPersona.add(fiTelefono);
		datiPersona.add(labEta);
		datiPersona.add(fiEta);

		ImageIcon saveIcon = new ImageIcon(FinestraEditor.class.getResource("/res/imgSave.png"));
		ImageIcon cancelIcon = new ImageIcon(FinestraEditor.class.getResource("/res/imgCancel.png"));
		JButton btSave = new JButton("Salva", saveIcon);
		JButton btCancel = new JButton("Annulla", cancelIcon);

		btCancel.setFocusPainted(false);
		btCancel.addActionListener(e -> this.dispose());
		btSave.setFocusPainted(false);
		btSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!verificaDati(fiNome, fiCognome, fiIndirizzo, fiTelefono, fiEta)) {
					JOptionPane.showMessageDialog(null, "Uno o più dati sono mancanti o non corretti");
				} else {
					String nome = fiNome.getText();
					String cognome = fiCognome.getText();
					String indirizzo = fiIndirizzo.getText();
					String telefono = fiTelefono.getText();
					int eta = Integer.parseInt(fiEta.getText());

					if (indice == -1) { // nuova persona
						Persona p = new Persona(nome, cognome, indirizzo, telefono, eta);
						lista.add(p);
						FinestraPrincipale.aggiornaTabella(lista);
					} else {
						Persona p = new Persona();
						p.setNome(nome);
						p.setCognome(cognome);
						p.setIndirizzo(indirizzo);
						p.setTelefono(telefono);
						p.setEta(eta);
						lista.setElementAt(p, indice);
						FinestraPrincipale.aggiornaTabella(lista);
					}
					try {
						AppRubrica.aggiornaFile();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					dispose();
				}
			}

		});

		toolBar = new JToolBar();
		c.add(toolBar, BorderLayout.NORTH);
		toolBar.add(btSave);
		toolBar.add(btCancel);

		setSize(600, 400);
		setLocationRelativeTo(null);

	}

	private boolean verificaDati(JTextField n, JTextField c, JTextField i, JTextField t, JTextField e) {
		if (!verificaEta(e))
			return false;
		if (!verificaTel(t))
			return false;
		if (n.getText().equals("") || c.getText().equals("") || i.getText().equals("") || t.getText().equals(""))
			return false;
		return true;
	}

	private boolean verificaEta(JTextField x) {
		try {
			Integer.parseInt(x.getText());
			if (Integer.parseInt(x.getText()) < 0)
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean verificaTel(JTextField x) {
		char[] sequenza = x.getText().toCharArray();
		for (int i = 0; i < sequenza.length; i++) {
			try {
				Integer.parseInt(Character.toString(sequenza[i]));
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

}
