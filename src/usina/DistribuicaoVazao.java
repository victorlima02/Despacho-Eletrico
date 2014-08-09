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

import ic.ce.seres.reais.SerReal;

/**
 * Distribuição de vazão.
 *
 * <p>
 * Essa classe representa a distribuição de vazão entre os conjuntos turbinas
 * que compõem o sistema, sendo portanto o alvo dos algoritmos evolucionários
 * que tratam do problema do despacho elétrico.
 * </p>
 * <p>
 * Cada distribuição agrupa em si um conjunto de características reais, que
 * representam uma vazão para uma determinada turbina.
 * </p>
 *
 * @author Victor de Lima Soares
 *
 * @version 1.0
 * @see SerReal
 */
public final class DistribuicaoVazao extends SerReal<Double> {

    public DistribuicaoVazao(Integer nVazoes) {
        super(nVazoes);
    }

    /**
     * Retorna a soma das potências geradas pelas turbinas.
     *
     * @since 1.0
     * @return Potência total.
     */
    public Double getPotenciaTotal() {
        return getCaracteristicas().stream().mapToDouble(locus -> ((Fluxo) locus).getPotencia()).sum();
    }

    /**
     * Retorna a soma das vazões geradas pelas turbinas.
     *
     * @since 1.0
     * @return Vazão total.
     */
    public Double getVazaoTotal() {
        return getCaracteristicas().stream().mapToDouble(locus -> ((Fluxo) locus).getVazao()).sum();
    }

    public String toStringDetalhado() {
        StringBuilder detalhes = new StringBuilder();

        detalhes.append("\n"+toString()+"\n");
        
        for (int i=0;i<getCaracteristicas().size();i++) {
            Fluxo fluxo = (Fluxo)getCaracteristica(i);
            Turbina turbina = fluxo.getTurbinaReferencia();
            
            detalhes.append("Turbina "+i+":\t"+fluxo+"\t"+String.format("%6.2f",turbina.getRendimento(fluxo))+"\n");
        }
        
        detalhes.append("Potência total:\t"+String.format("%6.4f",getPotenciaTotal())+"\n");
        detalhes.append("Fluxo total:\t"+String.format("%6.4f",getVazaoTotal())+"\n");
        
        return detalhes.toString();
    }

}
