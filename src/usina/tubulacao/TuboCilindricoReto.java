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
public final class TuboCilindricoReto extends TuboCilindrico {

	/**
	 * Construtor.
	 * 
	 * @param comprimento
	 *            Comprimento da tubulação [m].
	 * @param diametro
	 *            Diametro [m].
	 * @param rugosidadeAbsoluta
	 *            Rugosidade Absoluta [m].
	 * @since 1.0
	 */
	public TuboCilindricoReto(Double comprimento, Double diametro,
			Double rugosidadeAbsoluta) {
		super(comprimento, diametro, 0D, rugosidadeAbsoluta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Double getPerdaHidraulicaCurvatura(final Fluxo fluxo) {
		return 0D;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Double getPerdaHidraulica(final Fluxo fluxo) {
		return getPerdaHidraulicaRetilinea(fluxo);
	}
}
