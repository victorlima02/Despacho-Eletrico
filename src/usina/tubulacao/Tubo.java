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

import usina.Fluxo;

/**
 * 
 * @author Victor de Lima Soares
 * @version 1.0
 */
abstract class Tubo extends Tubulacao {

	/**
	 * Comprimento do tubo [m].
	 * 
         * @since 1.0
         * 
	 * @see #setComprimento(Double)
	 * @see #getComprimento()
	 */
	private Double comprimento;

	/**
	 * Curvatura da Tubulacao [Graus]
	 * 
         * @since 1.0
         * 
	 * @see #setCurvatura(Double)
	 * @see #getCurvatura()
	 */
	private Double curvatura;

	/**
	 * Rugosidade [m].
	 * 
         * @since 1.0
         * 
	 * @see #setRugosidadeAbsoluta(Double)
	 * @see #getRugosidadeAbsoluta()
	 */
	private Double rugosidadeAbsoluta;

	/**
	 * 
         * @since 1.0
         * 
	 * @param comprimento
	 *            Comprimento da tubulação [m].
	 * @param curvatura
	 *            �ngulo de curvatura da tubulação [Graus].
	 * @param rugosidadeAbsoluta
	 *            Rugosidade Absoluta [m].
	 */
	public Tubo(Double comprimento, Double curvatura,
			Double rugosidadeAbsoluta) {
		setComprimento(comprimento);
		setCurvatura(curvatura);
		setRugosidadeAbsoluta(rugosidadeAbsoluta);
	}

	/**
         * @since 1.0
	 * @param comprimento
	 *            O comprimento a ser atribuido [m].
	 * @throws IllegalArgumentException
	 *             <ul>
	 *             <li>Se o comprimento m�nimo for menor ou igual a zero.</li>
	 *             </ul>
	 */
	private void setComprimento(Double comprimento) {
		if (comprimento <= 0)
			throw new IllegalArgumentException("Comprimento <= 0.");
		this.comprimento = comprimento;
	}

	/**
         * @since 1.0
	 * @param curvatura
	 *            Curvatura [Graus].
	 * @throws IllegalArgumentException
	 *             <ul>
	 *             <li>Se a curvatura estiver fora do intervalo [0-360].</li>
	 *             </ul>
	 */
	private void setCurvatura(Double curvatura) {
		if (curvatura < 0 || curvatura > 360)
			throw new IllegalArgumentException(
					"Curvatura fora de [0-360].");
		this.curvatura = curvatura;
	}

	/**
         * @since 1.0
	 * @param rugosidadeAbsoluta
	 *            A rugosidade absoluta a ser atribuida [m].
	 * 
	 * @throws IllegalArgumentException
	 *             <ul>
	 *             <li>Se a rugosidade absoluta for menor que zero.</li>
	 *             </ul>
	 */
	private void setRugosidadeAbsoluta(Double rugosidadeAbsoluta) {
		if (rugosidadeAbsoluta < 0)
			throw new IllegalArgumentException("rugosidadeAbsoluta < 0.");
		this.rugosidadeAbsoluta = rugosidadeAbsoluta;
	}

	/**
         * @since 1.0
	 * @return O comprimento [m].
	 */
	public final Double getComprimento() {
		return comprimento;
	}

	/**
         * @since 1.0
	 * @return A curvatura.
	 */
	public final Double getCurvatura() {
		return curvatura;
	}

	/**
         * @since 1.0
	 * @return A rugosidade absoluta [m].
	 */
	public final Double getRugosidadeAbsoluta() {
		return rugosidadeAbsoluta;
	}

	/**
	 * Calcula o fator de atrito, constante ou m�dio.
	 * 
         * @since 1.0
	 * @param REYNOLDS
	 *            Coeficiente de Reynolds, para um dado fluxo de fluido.
	 * @return Fator de atrito para tubo em uma se��o especifica.
	 */
	public abstract Double getFatorAtrito(Double REYNOLDS);

	/**
	 * Calcula o fator de atrito para tubo em uma seção específica - definida
	 * pela posição.
	 * 
	 * @param REYNOLDS
	 *            Coeficiente de Reynolds, para um dado <code>fluxo</code> de
	 *            fluido.
	 * @param posicaoX
	 *            Posição no decorrer desta <code>tubulação</code>.
	 * @return Fator de atrito para tubo em uma seção especifica.
	 * @since 1.0
	 */
	public abstract Double getFatorAtrito(Double REYNOLDS, Double posicaoX);

	/**
	 * Calcula a perda de carga no tubo, devido a curvatura, dado um
	 * <code>fluxo</code> � coeficiente de Reynolds e uma velocidade media.
	 * 
	 * @param fluxo
	 *            <code>Fluxo</code> contendo o coeficiente de Reynolds e uma
	 *            vazão - base do calculo da perda hidráulica.
	 * @return Valor da perda hidráulica, dado um valor de vazão para esta
	 *         tubulação.
	 * @since 1.0
	 * @see #getPerdaHidraulica(Fluxo)
	 */
	public abstract Double getPerdaHidraulicaCurvatura(final Fluxo fluxo);

	/**
	 * Calcula a perda de carga no tubo, devido a parte retilinea, dado um
	 * <code>fluxo</code> � coeficiente de Reynolds e uma velocidade media.
	 * 
	 * @param fluxo
	 *            <code>Fluxo</code> contendo o coeficiente de Reynolds e uma
	 *            vazão - base do calculo da perda hidráulica.
	 * @return Valor da perda hidráulica, dado um valor de vazão para esta
	 *         tubulação.
	 * @since 1.0
	 * @see #getPerdaHidraulica(Fluxo)
	 */
	public abstract Double getPerdaHidraulicaRetilinea(final Fluxo fluxo);

	/**
	 * {@inheritDoc}
	 * 
	 * @see #getPerdaHidraulicaRetilinea(Fluxo)
	 * @see #getPerdaHidraulicaCurvatura(Fluxo)
	 */
	@Override
	public Double getPerdaHidraulica(final Fluxo fluxo) {
		return getPerdaHidraulicaRetilinea(fluxo) + getPerdaHidraulicaCurvatura(fluxo);
	}

}
