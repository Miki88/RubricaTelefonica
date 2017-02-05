import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class Tabella extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Object[] attributi = { "Nome", "Cognome", "Telefono" };
	private Object[][] dati;

	public Tabella(Vector<Persona> v) {

		dati = new Object[v.size()][attributi.length];
		Persona p = new Persona();
		int k = 0;
		for (int j = 0; j < v.size(); j++) {
			p = (Persona) v.elementAt(k);
			dati[j][0] = p.getNome();
			dati[j][1] = p.getCognome();
			dati[j][2] = p.getTelefono();
			k++;
		}
	}

	public Object[] getColumn() {
		return attributi;
	}

	@Override
	public String getColumnName(int col) {
		return (String) attributi[col];
	}

	@Override
	public int getColumnCount() {
		return attributi.length;
	}

	@Override
	public int getRowCount() {
		return dati.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return dati[arg0][arg1];
	}

}
