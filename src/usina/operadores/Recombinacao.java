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

import ic.populacional.seres.reais.recombinadores.RecombinadorReal;
import java.util.ArrayList;
import java.util.List;
import usina.DistribuicaoVazao;
import usina.Fluxo;

/**
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public class Recombinacao extends RecombinadorReal<Double,DistribuicaoVazao> {

    public Recombinacao(Double probabilidadeDeRecombinacao) {
        super(probabilidadeDeRecombinacao, 2);
    }

    @Override
    protected List<DistribuicaoVazao> recombina(List<DistribuicaoVazao> pares) {
        return wholeArithmeticRecombination(0.5, pares.get(0), pares.get(1));
    }
    
    
    public List<DistribuicaoVazao> simpleRecombination(int k, double alfa, DistribuicaoVazao par1, DistribuicaoVazao par2) {
        List<DistribuicaoVazao> filhos = new ArrayList<>(2);

        DistribuicaoVazao f1 = getAlgoritmo().getGerador().get();
        DistribuicaoVazao f2 = getAlgoritmo().getGerador().get();

        filhos.add(f1);
        filhos.add(f2);

        for (int i = 0; i < k; i++) {
            f1.setCaracteristicaCopia(i, par1.getCaracteristica(i));
            f2.setCaracteristicaCopia(i, par2.getCaracteristica(i));
        }

        for (int i = k; i < par1.getSize(); i++) {
            Fluxo locus1 = (Fluxo) par1.getCaracteristica(i);
            Fluxo locus2 = (Fluxo) par2.getCaracteristica(i);

            double locusPar1 = (locus1).getValor();
            double locusPar2 = (locus2).getValor();

            Double media1 = alfa * locusPar2 + (1 - alfa) * locusPar1;
            Fluxo novoLocus1 = new Fluxo(media1, locus1.getTurbinaReferencia());
            f1.setCaracteristica(i, novoLocus1);

            Double media2 = alfa * locusPar1 + (1 - alfa) * locusPar2;
            Fluxo novoLocus2 = new Fluxo(media2, locus2.getTurbinaReferencia());
            f2.setCaracteristicaCopia(i, novoLocus2);
        }
        return filhos;
    }

}
