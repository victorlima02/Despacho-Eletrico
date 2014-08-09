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

import ic.ce.seres.reais.GeradorReal;
import ic.ce.base.utilidades.Aleatorios;
import java.util.List;
import usina.DistribuicaoVazao;
import usina.Fluxo;
import usina.Turbina;

/**
 *
 * @author Victor de Lima Soares
 */
public class Geracao extends GeradorReal<DistribuicaoVazao> {

    private final List<Turbina> turbinas;

    /**
     * Construtor.
     *
     * @param turbinas
     * @throws NullPointerException
     * <ul>
     * <li>Se a lista de turbinas for uma referência nula.</li>
     * </ul>
     */
    public Geracao(List<Turbina> turbinas) {
        if (turbinas == null) {
            throw new NullPointerException("Lista de Turbinas não pode ser uma referência nula.");
        }
        this.turbinas = turbinas;
    }

    @Override
    public DistribuicaoVazao get() {
        DistribuicaoVazao nova = new DistribuicaoVazao(turbinas.size());
        for (int i = 0; i < nova.getSize(); i++) {
            nova.setCaracteristica(i, new Fluxo(null, Fluxo.REYNOLDS_PADRAO, turbinas.get(i)));
        }
        return nova;
    }

    @Override
    public DistribuicaoVazao getAleatorio() {

        DistribuicaoVazao nova = new DistribuicaoVazao(turbinas.size());
        
        for (int i = 0; i < nova.getSize(); i++) {
            Turbina turbina = turbinas.get(i);
            Double valorVazao = Aleatorios.getUniformeDouble(turbina.getLimiteMinDeVazao(), turbina.getLimiteMaxDeVazao());
            nova.setCaracteristica(i, new Fluxo(valorVazao, Fluxo.REYNOLDS_PADRAO, turbinas.get(i)));
        }
        return nova;
    }

}
