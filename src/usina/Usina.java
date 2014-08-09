/*
 * The MIT License
 *
 * Copyright 2014 Victor de Lima Soares.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package usina;

import ic.ce.base.Ambiente;
import java.util.Collections;
import usina.factory.UsinaFactory;
import usina.tubulacao.Conduto;
import java.util.List;

/**
 * Usina hidroelétrica.
 *
 * <p>
 * Essa classe compila todas as informações presentes no cálculo da eficiência
 * energética, e mantém de modo controlado as informações estruturais para cada
 * instância, mantendo referências aos condutos e turbinas que a compõem.
 * </p>
 *
 * <p>
 * Usinas devem ter todos seus componentes conectados e estruturados de forma
 * que a iteração entre eles seja conhecida. Portanto, as turbinas devem estar
 * instaladas aos condutos, a priori ou após a construção para efetivação dos
 * cálculos. A construção pode ser facilitada pelo uso da classe
 * {@link UsinaFactory}.
 * </p>
 *
 * @author Victor Soares
 * @version 1.0
 *
 * @see UsinaFactory
 */
public final class Usina extends Ambiente<Double, DistribuicaoVazao> {

    private final List<Turbina> turbinas;
    private final List<Conduto> condutos;
    private Double meta;

    /**
     * Construtor.
     *
     *
     * @param turbinas Turbinas da usina.
     * @param condutos Condutos da usina.
     *
     * @since 1.0
     * @throws NullPointerException
     * <ul>
     * Se um dos parâmetros for uma referencia nula.
     * </ul>
     */
    public Usina(List<Turbina> turbinas, List<Conduto> condutos) {
        super(Ambiente.Modo.MAXIMIZACAO);
        if (turbinas == null) {
            throw new NullPointerException("Referência a turbinas nula");
        }
        if (condutos == null) {
            throw new NullPointerException("Referência a condutos nula");
        }
        this.turbinas = turbinas;
        this.condutos = condutos;
    }

    @Override
    public Double avalia(DistribuicaoVazao vazoes) {

        if (getMeta() == null) {
            throw new IllegalStateException("Meta não atribuida");
        }

        Double potencia = vazoes.getPotenciaTotal();
        Double vazao = vazoes.getVazaoTotal();

        Double eficiencia = potencia / vazao;

        //Barreira
        if (potencia < getMeta() * 0.999 || potencia > getMeta() * 1.001) {
            eficiencia *= .9;
        }

        return eficiencia;
    }

    @Override
    public String toString() {
        
        StringBuilder usina = new StringBuilder();

        usina.append("Usina: [\n\t");

        for (Turbina turbina : turbinas) {
            usina.append(turbina.toString().replace("\n", "\n\t"));
        }

        usina.append(super.toString().replace("\n", "\n\t"));

        usina.append("]\n").deleteCharAt(usina.lastIndexOf("\t"));
        return usina.toString();
    }

    /**
     * Recupera a lista de turbinas que compõem a usina.
     *
     * @since 1.0
     * @return Lista não modificável de turbinas.
     */
    public List<Turbina> getTurbinas() {
        return Collections.unmodifiableList(turbinas);
    }

    /**
     * Recupera a lista de condutos que compõem a usina.
     *
     * @since 1.0
     * @return Lista não modificável de condutos.
     */
    public List<Conduto> getCondutos() {
        return Collections.unmodifiableList(condutos);
    }

    /**
     * Recupera a meta da usina.
     *
     * @since 1.0
     * @return Meta para distribuição de vazão.
     */
    public Double getMeta() {
        return meta;
    }

    /**
     * Atribui uma nova meta para as distribuições de vazão.
     *
     * @since 1.0
     * @param meta Meta para distribuição de vazão.
     */
    public void setMeta(Double meta) {
        this.meta = meta;
    }

}
