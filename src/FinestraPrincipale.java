import java.awt.BorderLayout;
import java.awt.Container;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

public class FinestraPrincipale extends JFrame {

	private static final long serialVersionUID = 1L;

	private JToolBar toolBar;
	private static JTable table;
	private static Tabella tableModel;

	public FinestraPrincipale(Vector<Persona> lista) throws IOException {

		super("Rubrica Telefonica");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(new BorderLayout(10, 10));

		ImageIcon newIcon = new ImageIcon(FinestraPrincipale.class.getResource("/res/imgNew.png"));
		ImageIcon editIcon = new ImageIcon(FinestraPrincipale.class.getResource("/res/imgEdit.png"));
		ImageIcon deleteIcon = new ImageIcon(FinestraPrincipale.class.getResource("/res/imgDelete.png"));
		JButton btNew = new JButton("Nuovo", newIcon);
		JButton btEdit = new JButton("Modifica", editIcon);
		JButton btDelete = new JButton("Elimina", deleteIcon);
		btNew.setFocusPainted(false);
		btEdit.setFocusPainted(false);
		btDelete.setFocusPainted(false);

		toolBar = new JToolBar();
		c.add(toolBar, BorderLayout.NORTH);
		toolBar.add(btNew);
		toolBar.add(btEdit);
		toolBar.add(btDelete);

		btNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FinestraEditor fp = new FinestraEditor(lista, -1);
					fp.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int indice = table.getSelectedRow();
				if (indice == -1)
					JOptionPane.showMessageDialog(null, "Selezionare una persona");
				else {
					try {
						FinestraEditor fp = new FinestraEditor(lista, indice);
						fp.setVisible(true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		btDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int indice = table.getSelectedRow();
				if (indice == -1)
					JOptionPane.showMessageDialog(null, "Selezionare una persona");
				else {
					String n = lista.elementAt(indice).getNome();
					String c = lista.elementAt(indice).getCognome();
					int reply = JOptionPane.showConfirmDialog(null, "Eliminare la persona " + n + " " + c + "?",
							"Messaggio", JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION) {
						lista.removeElementAt(indice);
						aggiornaTabella(lista);
						try {
							AppRubrica.aggiornaFile();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}
		});

		BufferedImage myPicture = ImageIO.read(ResourceLoader.load("/res/imgRubrica.png"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		c.add(picLabel, BorderLayout.WEST);

		table = new JTable();
		tableModel = new Tabella(lista);
		table.setModel(tableModel);
		table.setFillsViewportHeight(true);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		c.add(panel, BorderLayout.CENTER);
		panel.add(table, BorderLayout.CENTER);
		panel.add(new JScrollPane(table));
		panel.setVisible(true);
		panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 40, 20));

		setSize(600, 400);
		setLocationRelativeTo(null);

	}

	public static void aggiornaTabella(Vector<Persona> p) {
		tableModel = new Tabella(p);
		table.setModel(tableModel);
	}

}
