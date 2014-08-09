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
package usina.operadores;

import ic.ce.base.Ambiente;
import ic.ce.populacional.Populacao;
import ic.ce.populacional.algoritmo.operadores.Seletor;
import java.util.ArrayList;
import java.util.List;
import usina.DistribuicaoVazao;

/**
 *
 * @author Victor de Lima Soares
 */
public class Selecao extends Seletor<Double, DistribuicaoVazao> {

    @Override
    public List<DistribuicaoVazao> getPais() {
        List<DistribuicaoVazao> pais = new ArrayList<>(getPopulacao().size());

        while (pais.size() < getPopulacao().size()) {
            pais.addAll(melhoresEntreAleatorios(2, 20));
        }

        return pais;
    }

    @Override
    public List<DistribuicaoVazao> getSobreviventes() {
        return getPopulacao().getNMelhores(getPopulacao().getMaxIndividuos());
    }

}
