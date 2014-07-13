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

import ic.populacional.seres.reais.LocusReal;

/**
 * Fluxo.
 *
 * <p>
 * Essa classe representa uma abstração do conceito de fluxo, armazenado em si o
 * valor de uma vazão, seu número de Reynolds e a turbina que o contém,enquanto,
 * permitindo a manipulação dos dados referentes a vazão de modo seguro -
 * respeitando os limites impostos pela turbina.
 * </p>
 *
 * <p>
 * Objetos da classe <code>Fluxo</code> indicarão a qual gerador pertencem,
 * compondo facilmente distribuições de fluxo, sem necessidade de que objetos da
 * classe <code>Turbina</code> sejam criados mais de uma vez em uma usina.
 * </p>
 *
 * <p>
 * Adicionalmente, referências indicando a turbina a qual se refere o fluxo são
 * usadas para verificação dos intervalos de entrada, vazão, ditados pela
 * capacidade de cada turbina.
 * </p>
 *
 * <p>
 * A classe estende a a classe <code>LocusReal</code>, o que a torna passível de
 * utilização em algoritmos evolucionários como característica de um ser baseado
 * em valores reais.
 * </p>
 *
 * @author Victor Soares
 * @version 1.0
 *
 * @see SerReal
 * @see LocusReal
 */
public final class Fluxo extends LocusReal {

    private Double reynolds;
    private Turbina turbinaReferencia;
    public static final Double REYNOLDS_PADRAO = 70000.0;

    /**
     * Construtor com Reynolds padrão.
     *
     * <p>
     * Reynolds padrão: 70000.
     * </p>
     *
     * @since 1.0
     * @param vazao Valor da vazão.
     * @param turbina Turbina de referência.
     */
    public Fluxo(Double vazao, Turbina turbina) {
        super(turbina.getLimiteMinDeVazao(), turbina.getLimiteMaxDeVazao());

        setTurbina(turbina);
        setVazao(vazao);
        setReynolds(REYNOLDS_PADRAO);
    }

    /**
     * Construtor detalhado.
     *
     * @since 1.0
     * @param vazao Valor da vazão.
     * @param reynolds Número de Reynolds.
     * @param turbina Turbina de referência.
     *
     */
    public Fluxo(Double vazao, Double reynolds, Turbina turbina) {
        super(turbina.getLimiteMinDeVazao(), turbina.getLimiteMaxDeVazao());

        setTurbina(turbina);
        setReynolds(reynolds);
        setVazao(vazao);
    }

    /**
     * Atribui um número de Reynolds ao fluxo.
     *
     * @since 1.0
     * @return O Coeficiente de Reynolds.
     */
    private void setReynolds(Double reynolds) {
        if (reynolds < 0) {
            throw new IllegalArgumentException("Coeficiente de Reynolds < 0.");
        }
        this.reynolds = reynolds;
    }

    /**
     * Atribui uma turbina de referência ao fluxo.
     *
     * <p>
     * Não deve ser publica, pois o fluxo é semanticamente relacionado a uma
     * turbina. Adicionalmente, essa função não modifica os limites do locus –
     * criados na construção do objeto e não modificáveis.
     * </p>
     *
     * @since 1.0
     * @param turbinaReferencia A turbina de referência.
     */
    private void setTurbina(Turbina turbinaReferencia) {

        if (turbinaReferencia == null) {
            throw new NullPointerException("Turbina não existente(NULL).");
        }

        this.turbinaReferencia = turbinaReferencia;
    }

    /**
     * Atribui um novo valor de vazão ao fluxo.
     *
     * @since 1.0
     * @param vazao Nova vazão.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o valor estiver fora dos limites - impostos pela turbina. </li>
     * </ul>
     *
     * @see LocusReal#setValor(java.lang.Double)
     */
    public final void setVazao(Double vazao) {
        setValor(vazao);
    }

    /**
     * Recupera o valor da vazão do fluxo.
     *
     * @since 1.0
     * @return Valor da vazão.
     */
    public final double getVazao() {
        return getValor();
    }

    /**
     * Recupera o número de Reynolds usado.
     *
     * @since 1.0
     * @return O número de Reynolds.
     */
    public final Double getReynolds() {
        return reynolds;
    }

    /**
     * Recupera a turbina da referência.
     *
     * @since 1.0
     * @return A turbina de Referencia
     */
    public final Turbina getTurbinaReferencia() {
        return turbinaReferencia;
    }

    /**
     * Retorna a potência gerada pelo fluxo na turbina.
     *
     * <p>
     * Método de atalho para {@link  Turbina#getPotencia(usina.Fluxo) }, tendo
     * esse fluxo como parâmetro.
     * </p>
     *
     * @since 1.0
     * @return Potência da turbina.
     * @see Turbina#getPotencia(usina.Fluxo)
     */
    public final Double getPotencia() {
        return getTurbinaReferencia().getPotencia(this);
    }

    @Override
    public final Fluxo copia() {
        Fluxo copia = new Fluxo(getVazao(), getReynolds(), getTurbinaReferencia());
        return copia;
    }

    @Override
    public String toString() {
        return String.format("%6.2f\t%6.2f", getPotencia(), getVazao());
    }
}
