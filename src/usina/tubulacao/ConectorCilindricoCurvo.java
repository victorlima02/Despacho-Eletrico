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
package usina.tubulacao;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

import java.util.HashMap;
import java.util.NoSuchElementException;

import usina.Fluxo;

/**
 * Conector Cilindrico Curvo. <code>Conector</code> em formato curvo com se��es
 * circulares de mesmo diâmetro.
 * <p>
 * Possui uma única entrada e uma única saída.
 * </p>
 * <p>
 * <b>Limite: 45 Graus</b>
 * </p>
 *
 * @author Victor Soares
 * @version 1.0
 */
abstract class ConectorCilindricoCurvo extends Conector {

    /**
     * Tabela contendo os valores de refer�ncia dos fatores de perda para os
     * �ngulos usados no modelo.
     *
     * @see #setTabelaAnguloFatorDePerda()
     */
    private final static HashMap<Double, Double> tabelaAnguloFatorDePerda;

    /**
     * Diâmetro do conector [m].
     *
     * @see #setDiametro(Double)
     */
    private Double diametro;

    /**
     * Curvatura da tubulação [Graus].
     *
     * @see #setCurvatura(Double)
     */
    private Double curvatura;

    /**
     * Limite de curvatura, imposto pelo modelo usado para o calculo da perda.
     * <p>
     * Os valores tabelados se referem ao ângulo de 45 Graus.
     * </p>
     *
     * @see #setCurvatura(Double)
     * @see #setTabelaAnguloFatorDePerda()
     * @see #tabelaAnguloFatorDePerda
     */
    private static final Double LIMITE_CURVATURA = 45D;

    /**
     * Área de seção - constate neste conector [m^2].
     *
     * @see #updateAreaSecao()
     */
    private Double areaSecao;

    /**
     * Fator de perda por curvatura.
     * <p>
     * Valor calculado na constru��o do objeto, extraito da tabela de fatores.
     * </p>
     *
     * @see #tabelaAnguloFatorDePerda
     * @see #constanteDePerdaPorCurvatura
     */
    private Double fatorDePerdaPorCurvatura;

    /**
     * Fator de perda por curvatura / (Area*2*GRAVIDADE).
     * <p>
     * Mem�ria de c�lculo para opera��o realizada extensivamente.
     * </p>
     *
     * @see #updateFatorDePerdaHidraulicaCurvatura()
     */
    private Double constanteDePerdaPorCurvatura;

    static {
        tabelaAnguloFatorDePerda = new HashMap<>();
        setTabelaAnguloFatorDePerda();
    }

    /**
     * Construtor.
     *
     * @param diametro Diâmetro [m].
     * @param curvatura �ngulo de curvatura(desvio) da <code>tubulação</code>
     * [Graus].
     * <p>
     * <b>Limite: 45 Graus</b>
     * </p>
     * @since 1.0
     */
    public ConectorCilindricoCurvo(Double diametro, Double curvatura) {
        setDiametro(diametro);
        setCurvatura(curvatura);
        updateFatorDePerdaHidraulicaCurvatura();
    }

    /**
     * Constrói a tabela contendo os valores de referência dos fatores de perda
     * para os ângulos usados no modelo.
     * <p>
     * Pares:(ângulo, Fator)
     * </p>
     *
     * @see #tabelaAnguloFatorDePerda
     */
    private static void setTabelaAnguloFatorDePerda() {
        tabelaAnguloFatorDePerda.put(30D, 0.1);
        tabelaAnguloFatorDePerda.put(28D, 0.08);

        tabelaAnguloFatorDePerda.put(22D, 0.03);
        tabelaAnguloFatorDePerda.put(21D, 0.02);

        tabelaAnguloFatorDePerda.put(16D, 0.051);
        tabelaAnguloFatorDePerda.put(12D, 0.047);

        tabelaAnguloFatorDePerda.put(4D, 0.012);
        tabelaAnguloFatorDePerda.put(3D, 0.0118);
    }

    /**
     * Atribui ao conector o diâmetro da seção circular.
     *
     * @param diametro O diâmetro a ser atribuído ao tudo.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>No caso de uso de um valor não positivo.</li>
     * </ul>
     * @since 1.0
     */
    private void setDiametro(Double diametro) {

        if (diametro <= 0) {
            throw new IllegalArgumentException("Diametro <= 0.");
        }

        this.diametro = diametro;

        updateAreaSecao();
    }

    /**
     * Calcula e atualiza área da seção transversal do conector. [m^2].
     * <p>
     * Uma vez atribuído um diâmetro a tubulação, atualiza-se o valor da área de
     * seção. Armazenando-se em memória e evitando cálculos repetidos no
     * decorrer do processamento.
     * </p>
     * <p>
     * O cálculo da área foi definido pelo modelo e segue a quarta potência do
     * diâmetro.
     * </p>
     *
     * @since 1.0
     */
    private void updateAreaSecao() {
        this.areaSecao = PI * (pow(getDiametro(), 4)) / 4;
    }

    /**
     * Atribui ao conector o ângulo de curvatura (desvio).
     *
     * @param curvatura Curvatura [Graus].
     * <p>
     * <b>Limite: 45 Graus</b>
     * </p>
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se a curvatura estiver fora do intervalo [0-45].</li>
     * </ul>
     * @since 1.0
     */
    private void setCurvatura(Double curvatura) {
        if (curvatura < 0 || curvatura > LIMITE_CURVATURA) {
            throw new IllegalArgumentException(String.format(
                    "Curvatura fora do intervalo [0-%f].", LIMITE_CURVATURA));
        }
        this.curvatura = curvatura;
    }

    /**
     * Calcula e atualiza o fator de perda por curvatura, para este conector.
     */
    private void updateFatorDePerdaHidraulicaCurvatura() {
        fatorDePerdaPorCurvatura = getFatorDePerdaPorCurvatura(getCurvatura());
        constanteDePerdaPorCurvatura = fatorDePerdaPorCurvatura
                / (pow(getAreaDeSecao(), 2) * 2 * GRAVIDADE);
    }

    /**
     * Retorna o valor do coeficiente de perda devido a curvatura da
     * <code>tubulação</code>, para este <code>conector</code>, <b>dividido por
     * (2*G*Area)</b>.
     *
     * @return O valor coeficiente de perda devido o desvio no
     * <code>conector</code>.
     * @since 1.0
     */
    private Double getConstanteDePerdaPorCurvatura() {
        return constanteDePerdaPorCurvatura;
    }

    /**
     * Retorna o diâmetro do <code>conector</code>.
     *
     * @return O diâmetro [m].
     * @since 1.0
     */
    public final Double getDiametro() {
        return diametro;
    }

    /**
     * Retorna a �rea da se��o transversal do <code>conector</code>.
     *
     * @return Se��o transversal do tubo [m^2].
     * @since 1.0
     */
    public final Double getAreaDeSecao() {
        return areaSecao;
    }

    /**
     * @return A curvatura.
     * @since 1.0
     */
    public final Double getCurvatura() {
        return curvatura;
    }

    /**
     * Retorna o valor tabelado do coeficiente de perda devido a curvatura da
     * <code>tubulação</code>, dado um �ngulo de curvatura.
     *
     * @param angulo Ângulo de curvatura.
     * @return O valor coeficiente de perda devido a curvatura da
     * <code>tubulação</code>.
     * @since 1.0
     * @throws NoSuchElementException
     * <ul>
     * <li>Ângulo de curvatura não estiver definido na tabela.</li>
     * </ul>
     */
    protected static final Double getDaTabelaAnguloFatorDePerda(Double angulo)
            throws NoSuchElementException {

        Double fator = tabelaAnguloFatorDePerda.get(angulo);

        if (fator == null) {
            throw new NoSuchElementException(
                    String.format(
                            "Ângulo de curvatura não definido na tabela (%.4f graus).",
                            angulo));
        }

        return fator;
    }

    /**
     * Retorna o valor do coeficiente de perda devido a curvatura da
     * tubulação, para este conector.
     *
     * @since 1.0
     * @param angulo Ângulo de curvatura(desvio) da tubulação [Graus].
     * 
     * @return O valor coeficiente de perda devido a curvatura da tubulação.
     * Corrigido para a configuração geométrica da curva, incluindo casos
     * poligonais, em classes mais especializadas.
     * 
     * @see #tabelaAnguloFatorDePerda
     */
    public abstract Double getFatorDePerdaPorCurvatura(Double angulo);

    /**
     * Calcula a perda de carga no tubo, dado um <code>fluxo</code> �
     * coeficiente de Reynolds e uma vazão, causada pela curvatura da
     * <code>tubulação</code>.
     *
     * @param fluxo <code>Fluxo</code> contendo o coeficiente de Reynolds e uma
     * vazão - base do calculo da perda hidráulica.
     * @return Valor da perda hidráulica causada pela curvatura da
     * <code>tubulação</code>.
     * @since 1.0
     * @see #getConstanteDePerdaPorCurvatura()
     */
    public final Double getPerdaHidraulicaCurvatura(final Fluxo fluxo) {

        return (getConstanteDePerdaPorCurvatura()) * pow(fluxo.getVazao(), 2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Double getPerdaHidraulicaConexao(final Fluxo fluxo) {
        return getPerdaHidraulicaCurvatura(fluxo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Double getPerdaHidraulica(final Fluxo fluxo) {
        return getPerdaHidraulicaConexao(fluxo);
    }
}
