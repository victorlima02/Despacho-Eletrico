/**
 * 
 */
package usina.DAO.turbina;

/**
 * 
 * @author Victor Soares
 * @version 1.0
 * 
 * @deprecated 
 */
public final class TubinaDAOFactory {
	
	public enum DAOTypes {
		CSV
	};

	public static TurbinaDAO getTurbinaDAO(DAOTypes sourceType) {

		switch (sourceType) {

		case CSV:
			return new TurbinaCSVFileDAO();
		}

		return null;
	}
}
