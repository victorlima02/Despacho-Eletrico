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

/**
 * <code>Conector</code> em formato curvo com se��es circulares de mesmo
 * tamanho, e curvatura formada por se��es retas.
 * <p>
 * Possui uma única entrada e uma �nica sa�da.
 * </p>
 * <p>
 * <b>Limite de curvatura: 45 Graus</b>
 * </p>
 * <p>
 * <b>Desvios angulares, considerados pontuais: um �nico ponto de desvio.</b>
 * </p>
 * 
 * @author Victor Soares
 * @version 1.0
 */
public final class ConectorCilindricoCurvoPoligonal extends ConectorCilindricoCurvo {

	/**
	 * Construtor.
	 * 
	 * @param diametro
	 *            Diâmetro [m].
	 * @param curvatura
	 *            �ngulo de curvatura(desvio) da <code>tubulação</code> [Graus].
	 * @since 1.0
	 */
	public ConectorCilindricoCurvoPoligonal(Double diametro, Double curvatura) {
		super(diametro, curvatura);
	}

	/**
	 * {@inheritDoc} OBS: Fator de corre��o definido pelo modelo:
	 * <ul>
	 * <li>�ngulos de desvio menores ou iguais a 15 Graus: 2%</li>
	 * <li>�ngulos de desvio maiores que 15 Graus: 3%</li>
	 * </ul>
	 */
	@Override
	public final Double getFatorDePerdaPorCurvatura(Double angulo) {
		Double fatorDePerda = ConectorCilindricoCurvo
				.getDaTabelaAnguloFatorDePerda(angulo);
		if (angulo >= 15) 
			return fatorDePerda * (1.03);
		else
			return fatorDePerda * (1.02);
	}

}
