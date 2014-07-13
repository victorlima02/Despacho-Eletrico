/**
 * @author Victor Soares
 */
package usina.factory;

import usina.Turbina;
import usina.Usina;
import usina.tubulacao.Conduto;
import usina.tubulacao.ConectorCilindricoCurvoPoligonal;
import usina.tubulacao.TuboCilindricoReto;
import java.util.ArrayList;
import java.util.List;

/**
 * Montadora de Usinas.
 *
 * <p>
 * Essa classe visa fornecer métodos a instanciação de usinas pré-codificadas
 * para uso.
 * </p>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class UsinaFactory {

    /**
     * Enumeração de usinas pré-codificadas.
     *
     * @since 1.0
     */
    public enum Usinas {

        TRESMARIAS
    };

    /**
     * recupera uma usina pré-codificada.
     *
     * @since 1.0
     * @param usina Usina desejada.
     * @return Instância da usina montada.
     *
     * @see Usinas
     */
    public static Usina getUsina(Usinas usina) {

        switch (usina) {
            case TRESMARIAS:

                Integer nTurbinas = 6;
                List<Turbina> turbinas = new ArrayList<>(nTurbinas);

                Double[] parametrosRendimento = new Double[]{0.1463, 0.018076, 0.0050502, -3.5254e-05, -0.00012337, -1.4507e-05};

                turbinas.add(new Turbina(35d, 66d, 70d, 140d, parametrosRendimento));
                turbinas.add(new Turbina(35d, 66d, 70d, 140d, parametrosRendimento));
                turbinas.add(new Turbina(35d, 66d, 70d, 140d, parametrosRendimento));

                turbinas.add(new Turbina(35d, 66d, 70d, 140d, parametrosRendimento));
                turbinas.add(new Turbina(35d, 66d, 70d, 140d, parametrosRendimento));
                turbinas.add(new Turbina(35d, 66d, 70d, 140d, parametrosRendimento));

                List<Conduto> condutos = new ArrayList<>();
                condutos.add(new Conduto()); //1
                condutos.get(0).adicianaTubo(new TuboCilindricoReto(160d, 6.6, 0.2));
                condutos.get(0).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 28d));
                condutos.get(0).adicianaTubo(new TuboCilindricoReto(91.6, 6.6, 0.2));
                condutos.get(0).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 30d));
                condutos.get(0).adicianaTubo(new TuboCilindricoReto(13.4, 6.2, 0.2));

                condutos.add(new Conduto());//2
                condutos.get(1).adicianaTubo(new TuboCilindricoReto(160d, 6.6, 0.2));
                condutos.get(1).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 22d));
                condutos.get(1).adicianaTubo(new TuboCilindricoReto(86.26, 6.6, 0.2));
                condutos.get(1).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 21d));
                condutos.get(1).adicianaTubo(new TuboCilindricoReto(13.4, 6.2, 0.2));

                condutos.add(new Conduto());//3
                condutos.get(2).adicianaTubo(new TuboCilindricoReto(160d, 6.6, 0.2));
                condutos.get(2).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 16d));
                condutos.get(2).adicianaTubo(new TuboCilindricoReto(82.54, 6.6, 0.2));
                condutos.get(2).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 12d));
                condutos.get(2).adicianaTubo(new TuboCilindricoReto(13.4, 6.2, 0.2));

                condutos.add(new Conduto());//4
                condutos.get(3).adicianaTubo(new TuboCilindricoReto(160d, 6.6, 0.2));
                condutos.get(3).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 4d));
                condutos.get(3).adicianaTubo(new TuboCilindricoReto(80.58, 6.6, 0.2));
                condutos.get(3).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 3d));
                condutos.get(3).adicianaTubo(new TuboCilindricoReto(13.4, 6.2, 0.2));

                condutos.add(new Conduto());//5
                condutos.get(4).adicianaTubo(new TuboCilindricoReto(160d, 6.6, 0.2));
                condutos.get(4).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 4d));
                condutos.get(4).adicianaTubo(new TuboCilindricoReto(80.58, 6.6, 0.2));
                condutos.get(4).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 3d));
                condutos.get(4).adicianaTubo(new TuboCilindricoReto(13.4, 6.2, 0.2));

                condutos.add(new Conduto());//6
                condutos.get(5).adicianaTubo(new TuboCilindricoReto(160d, 6.6, 0.2));// 1
                condutos.get(5).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 16d));
                condutos.get(5).adicianaTubo(new TuboCilindricoReto(82.54, 6.6, 0.2));
                condutos.get(5).adicianaTubo(new ConectorCilindricoCurvoPoligonal(6.6, 12d));
                condutos.get(5).adicianaTubo(new TuboCilindricoReto(13.4, 6.2, 0.2));

                /**
                 * Usina tem todas as turbinas ligadas.
                 */
                for (int i = 0; i < turbinas.size(); i++) {
                    turbinas.get(i).instala(54.0, condutos.get(i));
                    turbinas.get(i).setLigada(true);
                }

                return new Usina(turbinas, condutos);

            default:
                return null;

        }
    }
}
