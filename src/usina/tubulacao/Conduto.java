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

import java.util.ArrayList;
import java.util.List;
import usina.Fluxo;
import usina.Turbina;

/**
 * 
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class Conduto {

	private final List<Tubulacao> tubos;
	private Turbina turbina;

	public Conduto() {
		this.tubos = new ArrayList<>();
	}

	public Conduto(List<Tubulacao> tubos) {
		this.tubos = tubos;
	}

	public final void conectaTurbina(final Turbina turbina) {
		if (isConectado())
			throw new IllegalStateException(
					"Uma única turbina pode estar conectada a esse conduto.");
		if (turbina == null)
			throw new IllegalArgumentException("Turbina a ser conectada é nula.");

		this.turbina = turbina;
	}

	public boolean isConectado() {
		return (turbina instanceof Turbina);
	}

	public final void adicianaTubo(Tubulacao tubo) {
		if(tubo == null) throw new NullPointerException("Tubo é uma referência nula.");
		tubos.add(tubo);
	}

	public final Double getPerdaHidraulica(Fluxo fluxo) {

		Double perda;

                perda = tubos.stream().mapToDouble((tubo) -> tubo.getPerdaHidraulica(fluxo)).sum();

		return perda;
	}

}
