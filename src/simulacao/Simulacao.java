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
/**
 * @author Victor de Lima Soares
 */
package simulacao;

import ic.populacional.algoritmo.AlgoritmoEvolucionario;
import ic.populacional.algoritmo.operadores.Gerador;
import ic.populacional.algoritmos.DE.DE;
import ic.populacional.algoritmos.DE.mutadores.Best;
import ic.populacional.algoritmos.DE.mutadores.MutadorDE;
import ic.populacional.algoritmos.DE.mutadores.Rand;
import ic.populacional.algoritmos.DE.recombinadores.Binomial;
import ic.populacional.algoritmos.DE.recombinadores.RecombinadorDE;
import java.util.LinkedList;
import java.util.List;
import usina.DistribuicaoVazao;
import usina.PopulacaoDeDistribuicoes;
import usina.Usina;
import usina.factory.UsinaFactory;
import usina.operadores.Geracao;

/**
 * @author Victor de Lima Soares
 * @version 1.0
 *
 */
public class Simulacao {

    private final Usina usina;
    private final Integer maxIndividuos;
    private final Integer maxIteInteracoes;
    private final Integer nExperimentos;

    //DE -----
    private final Integer nDiferencas;
    private final Double fatorDePertubacao;       //0,3:0,8
    private final Double probabilidaDeCrossover;  //0,5:0,8
    //----

    Double demandaHoraria = 320d;

    public Simulacao() {

        nExperimentos = 1000;
        
        maxIndividuos = 50;
        maxIteInteracoes = 50;

        probabilidaDeCrossover = 0.5;
        fatorDePertubacao = 0.3;
        nDiferencas = 1;

        usina = UsinaFactory.getUsina(UsinaFactory.Usinas.TRESMARIAS);
    }


    public static void main(String[] args) {
        Simulacao teste = new Simulacao();
        List<Resultado> resultados = new LinkedList<>();
        for(int i=0;i<teste.nExperimentos;i++)
            resultados.add(teste.experimento());
        
        System.out.println("Média do tempo de execução:\t"+resultados.stream().mapToDouble(Resultado::getTempoExecucao).average().getAsDouble());
        System.out.println("Média dos melhores:\t"+resultados.stream().mapToDouble(Resultado::getMelhorSolucao).average().getAsDouble());
        System.out.println("Melhor:\t"+resultados.stream().mapToDouble(Resultado::getMelhorSolucao).max().getAsDouble());
    }

    public Resultado experimento() {

        PopulacaoDeDistribuicoes populacao = new PopulacaoDeDistribuicoes(usina, maxIndividuos);
        usina.setMeta(demandaHoraria);

        Gerador gerador = new Geracao(usina.getTurbinas());
        MutadorDE mutador = new Rand(nDiferencas, fatorDePertubacao);
        RecombinadorDE recombinador = new Binomial(probabilidaDeCrossover);

        populacao.setIndividuos(gerador.getNAleatorios(maxIndividuos));

        AlgoritmoEvolucionario algoritmo = new DE();

        algoritmo.setAmbiente(usina);
        algoritmo.setPopulacao(populacao);
        algoritmo.setMaxIteracoes(maxIteInteracoes);

        algoritmo.setGerador(gerador);
        algoritmo.setMutador(mutador);
        algoritmo.setRecombinador(recombinador);

        algoritmo.run();

        return new Resultado(algoritmo.getTempoDeExecucaoSeg(),(DistribuicaoVazao)algoritmo.getMelhorSer());
    }
}
