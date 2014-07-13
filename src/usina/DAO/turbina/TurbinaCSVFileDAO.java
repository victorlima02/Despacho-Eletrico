package usina.DAO.turbina;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Victor Soares
 * @version 1.0
 * 
 * @deprecated
 */
public final class TurbinaCSVFileDAO implements TurbinaDAO {

	String cvsSplitBy = ";";

	/**
	 * {@inheritDoc}
	 * 
	 * Especializada para leitura de arquivos CSV. Utiliza formata��o de dados
	 * inglesa, por manter a compatibilidade com os dados de entrada usados nos
	 * programas Matlab, predecessores para alguns dos algoritmos e mantidos por
	 * prop�sitos de teste de sa�da.
	 * 
	 * @deprecated
	 */
	@Override
	public final LinkedList<TurbinaTransfer> carregaTurbinas() throws IOException {

		LinkedList<TurbinaTransfer> turbinas = new LinkedList<>();

		try (
				BufferedReader bufferParametros = new BufferedReader(
						new FileReader("data/input/Turbinas/parametrosRendimento.csv"));
				BufferedReader bufferPotencias = new BufferedReader(
						new FileReader("data/input/Turbinas/limitesPotencia.csv"));
				BufferedReader bufferVazoes = new BufferedReader(
						new FileReader("data/input/Turbinas/limitesVazoes.csv"));) {

			String minPotencias[] = bufferPotencias.readLine().split(cvsSplitBy);

			String maxPotencias[] = bufferPotencias.readLine().split(cvsSplitBy);

			String minVazoes[] = bufferVazoes.readLine().split(cvsSplitBy);

			String maxVazoes[] = bufferVazoes.readLine().split(cvsSplitBy);

			List<Double> parametrosRendimento = new ArrayList<>();
			Double[] parametrosArray;
			
			String linha;
			while((linha=bufferParametros.readLine())!=null){
				if(linha.split(cvsSplitBy).length == 1){
					
				}else{
					throw new IOException(
							"Problemas de formatação - Erro na leitura dos parametros para turbinas(deve ter 1 por linha).");
				}
				parametrosRendimento.add(Double.parseDouble(linha.split(cvsSplitBy)[0]));
			}
			parametrosArray=new Double[parametrosRendimento.size()];
			for(int i=0; i<parametrosRendimento.size();i++)
				parametrosArray[i]=parametrosRendimento.get(i);
			

			if ((minPotencias == null) || (maxPotencias == null)
					|| (minPotencias.length != maxPotencias.length)
					|| (minVazoes == null) || (maxVazoes == null)
					|| (minVazoes.length != maxVazoes.length)
					|| (minPotencias.length != minVazoes.length)) {

				throw new IOException(
						"Problemas de formatação - Linhas de tamanhos variados");
			}
			if (parametrosRendimento.size() == 0) {
				throw new IOException(
						"Erro na leitura dos parametros para turbinas (parametros n�o encontrados).");
			}

			for (int i = 0; i < minPotencias.length; i++) {

				TurbinaTransfer tmp = new TurbinaTransfer();

				tmp.setLimiteMinDePotencia(Double.parseDouble(minPotencias[i]));
				tmp.setLimiteMaxDePotencia(Double.parseDouble(maxPotencias[i]));

				tmp.setLimiteMinDeVazao(Double.parseDouble(minVazoes[i]));
				tmp.setLimiteMaxDeVazao(Double.parseDouble(maxVazoes[i]));
				
				tmp.setParametrosRendimento(parametrosArray);
				turbinas.add(tmp);
			}

		} catch (NumberFormatException | NullPointerException nfe) {

			String erro = "Erro na leitura do arquivo CSV :" +
					" Problemas de formatação - Interpreta��o dos números.";

			throw new IOException(erro, nfe);

		} catch (FileNotFoundException fnfe) {

			String erro = "Erro na leitura do arquivo CSV :"
					+ "Arquivo CSV não encontrado.";

			throw new IOException(erro, fnfe);

		} catch (IOException ioe) {

			String erro = "Erro na leitura do arquivo CSV.";
			throw new IOException(erro, ioe);
		}

		return turbinas;
	}
}
