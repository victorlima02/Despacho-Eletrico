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
 * Conectores: componentes da tubulação que conectam dois ou mais tubos.
 *
 * <p>
 * Conectores geram perdas hidráulicas dependentes de cada tipo especializado.
 * </p>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
abstract class Conector extends Tubulacao {

    
    /**
     * Calcula a perda de carga em conexões.
     * 
     * <p>
     * Calcula a perda de carga na tubulação devida à conexão entre os tubos
     * ligados a este conector, dado um fluxo (Determinante do coeficiente de
     * Reynolds e uma vazão).
     * </p>
     *
     * @since 1.0
     * @param fluxo Fluxo contendo o coeficiente de Reynolds e a vazão.
     * @return Valor da perda hidráulica, devido a esta conexão.
     */
    public abstract Double getPerdaHidraulicaConexao(final Fluxo fluxo);

}
