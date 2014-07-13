/**
 *
 */
package usina.DAO.turbina;

import java.util.List;

/**
 *
 * @author Victor Soares
 * @version 1.0
 * 
 * @deprecated
 */
public interface TurbinaDAO {

    /**
     * Realiza a leitura de objetos do tipo TubinaTranfer.
     *
     * @return Lista encadeada de TubinaTranfer.
     * @throws Exception
     * @see TurbinaTransfer
     */
    public List<TurbinaTransfer> carregaTurbinas() throws Exception;
}
