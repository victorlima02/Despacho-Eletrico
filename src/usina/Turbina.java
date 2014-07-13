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

import usina.tubulacao.Conduto;
import static java.lang.Math.pow;

/**
 * Conjunto turbina-gerador.
 *
 * <p>
 * Classe representante dos conjuntos geradores no problema de despacho
 * elétrico.
 * </p>
 *
 * <p>
 * Usa-se, aqui, por simplicidade, os substantivos turbina e conjunto-gerador
 * como sinônimos. Uma divisão mais detalhada seria desnecessária, pois o modelo
 * atual trata da eficiência do conjunto.
 * </p>
 *
 * @author Victor de Lima Soares
 * @version 1.0
 */
public final class Turbina {

    private Double limiteMinDePotencia;
    private Double limiteMaxDePotencia;

    private Double limiteMinDeVazao;
    private Double limiteMaxDeVazao;

    private Double[] parametrosRendimento;

    private Boolean ligada;

    /* Dados de instalação : ápos conectar a turbina */
    private Double quedaBruta;
    private Conduto conduto;

    /**
     * Construtor.
     *
     * <p>
     * Por padrão uma turbina é instanciada como desligada e desconectada.
     * </p>
     *
     * @param limiteMinDePotencia Limite inferior para potência, inclusive.
     * @param limiteMaxDePotencia Limite superior para potência, exclusive.
     * @param limiteMinDeVazao Limite inferior para vazão, inclusive.
     * @param limiteMaxDeVazao Limite superior para vazão, exclusive.
     * @param parametrosRendimento Parâmetros para calculo do getRendimento.
     *
     * @see #getRendimento(usina.Fluxo)
     * @see #instala(java.lang.Double, usina.tubulacao.Conduto)
     * @see #setLigada(java.lang.Boolean)
     */
    public Turbina(Double limiteMinDePotencia,
            Double limiteMaxDePotencia, Double limiteMinDeVazao,
            Double limiteMaxDeVazao, Double[] parametrosRendimento) {

        setLigada(false);

        setLimitesDePotencia(limiteMinDePotencia, limiteMaxDePotencia);

        setLimitesDeVazao(limiteMinDeVazao, limiteMaxDeVazao);

        setParametrosRendimento(parametrosRendimento);

    }

    /**
     * Atribui limites à potência da turbina.
     *
     * @since 1.0
     * @param limiteMinDePotencia Limite mínimo de potência para operação da
     * turbina.
     * @param limiteMaxDePotencia Limite máximo de potência para operação da
     * turbina.
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o limite mínimo for menor que zero;</li>
     * <li>Se o limite máximo for menor ou igual a zero;</li>
     * <li>Se o limite máximo for menor que o limite mínimo.</li>
     * </ul>
     */
    public final void setLimitesDePotencia(Double limiteMinDePotencia,
            Double limiteMaxDePotencia) {

        if (limiteMinDePotencia <= 0) {
            throw new IllegalArgumentException("Mínima potência < 0.");
        }
        if (limiteMaxDePotencia < 0) {
            throw new IllegalArgumentException("Máxima potência < 0.");
        }
        if (limiteMaxDePotencia < limiteMinDePotencia) {
            throw new IllegalArgumentException("Máxima potência < Mínima potência.");
        }

        this.limiteMinDePotencia = limiteMinDePotencia;
        this.limiteMaxDePotencia = limiteMaxDePotencia;
    }

    /**
     * Atribui limites à vazão da turbina.
     *
     * @since 1.0
     * @param limiteMinDeVazao Limite mínimo de vazão para operação da turbina.
     * @param limiteMaxDeVazao Limite máximo de vazão para operação da turbina.
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se o limite mínimo for menor que zero;</li>
     * <li>Se o limite máximo for menor ou igual a zero;</li>
     * <li>Se o limite máximo for menor que o limite mínimo.</li>
     * </ul>
     */
    public final void setLimitesDeVazao(Double limiteMinDeVazao, Double limiteMaxDeVazao) {
        if (limiteMinDeVazao <= 0) {
            throw new IllegalArgumentException("Mínima vazão < 0.");
        }
        if (limiteMaxDeVazao < 0) {
            throw new IllegalArgumentException("Máxima vazão < 0.");
        }
        if (limiteMaxDeVazao < limiteMinDeVazao) {
            throw new IllegalArgumentException("Máxima vazão < Mínima vazão.");
        }

        this.limiteMinDeVazao = limiteMinDeVazao;
        this.limiteMaxDeVazao = limiteMaxDeVazao;
    }

    /**
     * Atribui os parâmetros para o calculo do getRendimento.
     *
     * @since 1.0
     * @param parametrosRendimento
     *
     * @see #getRendimento(usina.Fluxo)
     */
    public void setParametrosRendimento(Double[] parametrosRendimento) {
        this.parametrosRendimento = parametrosRendimento;
    }

    /**
     * Define a queda bruta no momento da instalação.
     * <p>
     * <b>Não pode ser usada mais de uma vez.</b>
     * </p>
     *
     *
     * @param quedaBruta A queda bruta na operação da turbina.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se a queda bruta for menor que zero.</li>
     * </ul>
     * @throws IllegalStateException
     * <ul>
     * <li>Se a função for usada mais de uma vez.</li>
     * </ul>
     */
    private void setQuedaBruta(Double quedaBruta) {
        if (this.quedaBruta != null) {
            throw new IllegalStateException(
                    "Queda bruta pode ser definida uma única vez (queda bruta já definida).");
        }
        if (quedaBruta < 0) {
            throw new IllegalArgumentException("Queda bruta menor que zero.");
        }
        this.quedaBruta = quedaBruta;
    }

    /**
     * Conecta a turbina a um conduto no momento da instalação.
     * <p>
     * <b>Não pode ser usada mais de uma vez.</b>
     * </p>
     *
     * @since 1.0
     * @param conduto Conduto onde a turbina será instalada.
     * @throws IllegalStateException
     * <ul>
     * <li>Se a turbina já estiver conectada a outro conduto.</li>
     * </ul>
     * @throws NullPointerException
     * <ul>
     * <li>Se a referência para o conduto for nula.</li>
     * </ul>
     */
    private void conectaConduto(Conduto conduto) {
        if (isConectada()) {
            throw new IllegalStateException(
                    "Turbina pode ser conectada a um único conduto (já conectada anteriormente).");
        }

        conduto.conectaTurbina(this);
        this.conduto = conduto;
    }

    /**
     * Instala turbina na usina por meio de um conduto.
     * <p>
     * <b>Não pode ser usada mais de uma vez.</b>
     * </p>
     *
     * @since 1.0
     * @param quedaBruta A queda bruta na operação da turbina.
     * @param conduto Conduto onde a turbina será instalada.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>Se a queda bruta for menor que zero.</li>
     * </ul>
     * @throws IllegalStateException
     * <ul>
     * <li>Se a função for usada mais de uma vez;</li>
     * <li>Se a turbina já estiver conectada a outro conduto.</li>
     * </ul>
     * @throws NullPointerException
     * <ul>
     * <li>Se a referência para o conduto for nula.</li>
     * </ul>
     */
    public final void instala(Double quedaBruta, Conduto conduto) {
        setQuedaBruta(quedaBruta);
        conectaConduto(conduto);
    }

    /**
     * Controla o estado de uma turbina, ligando ou desligando a mesma.
     *
     * @since 1.0
     * @param ligada Estado da turbina: ligado/desligado;
     * <ul>
     * <li>True: Liga turbina.</li>
     * <li>False: Desliga turbina.</li>
     * </ul>
     * @throws IllegalStateException
     * <ul>
     * <li>Se a turbina não estiver instalada.</li>
     * </ul>
     */
    public final void setLigada(Boolean ligada) {

        if (!isConectada() && ligada == true) {
            throw new IllegalStateException(
                    "Turbina não pode ser ligada, pois não está conectada.");
        }

        this.ligada = ligada;
    }

    /**
     * Calcula o rendimento da turbina dado um fluxo.
     *
     * @since 1.0
     * @param fluxo Fluxo de parametrização.
     * @return Rendimento da turbina.
     */
    public final Double getRendimento(Fluxo fluxo) {
        Double quedaLiquida = getQuedaBruta() - conduto.getPerdaHidraulica(fluxo);
        Double vazao = fluxo.getVazao();

        return parametrosRendimento[0]
                + parametrosRendimento[1] * quedaLiquida
                + parametrosRendimento[2] * vazao
                + parametrosRendimento[3] * quedaLiquida * vazao
                + parametrosRendimento[4] * pow(quedaLiquida, 2)
                + parametrosRendimento[5] * pow(vazao, 2);
    }

    /**
     * Calcula a potência gerada da turbina dado um fluxo.
     *
     * @since 1.0
     * @param fluxo Fluxo de parametrização.
     * @return Potência da turbina.
     */
    public final Double getPotencia(Fluxo fluxo) {
        Double rendimento = getRendimento(fluxo);
        Double k = 9.8*pow(10,-3);
        
        Double quedaLiquida = getQuedaBruta() - conduto.getPerdaHidraulica(fluxo);
        Double vazao = fluxo.getVazao();
        
        return k*rendimento*quedaLiquida*vazao;
    }

    /**
     * Verifica o estado da turbina, ligada/desligada.
     *
     * @since 1.0
     * @return Estado da turbina:
     * <ul>
     * <li>True: Ligada.</li>
     * <li>False: Desligada.</li>
     * </ul>
     */
    public final Boolean isLigada() {
        return ligada;
    }

    /**
     * Verifica o estado da turbina, conectada ou desconectada.
     *
     * @return Estado da turbina:
     * <ul>
     * <li>True: conectada.</li>
     * <li>False: desconectada.</li>
     * </ul>
     */
    public Boolean isConectada() {
        return (conduto instanceof Conduto);
    }

    /**
     * Recupera o limite mínimo de potência para operação da turbina.
     *
     * @since 1.0
     * @return Limite mínimo de potência.
     */
    public final Double getLimiteMinDePotencia() {
        return limiteMinDePotencia;
    }

    /**
     * Recupera o limite máximo de potência para operação da turbina.
     *
     * @since 1.0
     * @return Limite máximo de potência.
     */
    public final Double getLimiteMaxDePotencia() {
        return limiteMaxDePotencia;
    }

    /**
     * Recupera o limite mínimo de vazão para operação da turbina.
     *
     * @since 1.0
     * @return Limite mínimo de vazão.
     */
    public final Double getLimiteMinDeVazao() {
        return limiteMinDeVazao;
    }

    /**
     * Recupera o limite máximo de vazão para operação da turbina.
     *
     * @since 1.0
     * @return Limite máximo de vazão.
     */
    public final Double getLimiteMaxDeVazao() {
        return limiteMaxDeVazao;
    }

    /**
     * Recupera a queda bruta, definida na instalação.
     *
     * @since 1.0
     * @return A queda bruta.
     */
    public final Double getQuedaBruta() {
        return quedaBruta;
    }

    @Override
    public String toString() {

        return String.format("[\n\tLigada: %s\n\tPotência:\tMin: %.2f\tMax:%.2f\n\tVazão:  \tMin: %.2f\tMax: %.2f\n]\n",
                (isLigada() ? "Sim" : "Não"), limiteMinDePotencia,
                limiteMaxDePotencia,
                limiteMinDeVazao, limiteMaxDeVazao);
    }
}
