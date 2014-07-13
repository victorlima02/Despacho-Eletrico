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
 * Conector Cilíndrico Curvo Suave.
 * <p>
 * Conector em formato curvo com seções circulares de mesmo
 * diâmetro, formando uma curva suave.
 * </p>
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
public final class ConectorCilindricoCurvoSuave extends ConectorCilindricoCurvo {

	/**
	 * Construtor.
	 * 
	 * @param diametro
	 *            Diâmetro [m].
	 * @param curvatura
	 *            Ângulo de curvatura(desvio) da tubulação [Graus].
	 * @since 1.0
	 */
	public ConectorCilindricoCurvoSuave(Double diametro, Double curvatura) {
		super(diametro, curvatura);
	}

	@Override
	public final Double getFatorDePerdaPorCurvatura(Double angulo) {
		return ConectorCilindricoCurvo.getDaTabelaAnguloFatorDePerda(angulo);
	}

}
