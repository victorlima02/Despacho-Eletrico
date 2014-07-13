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

import static java.lang.Math.PI;
import static java.lang.Math.log;
import static java.lang.Math.pow;

import javax.naming.OperationNotSupportedException;

import usina.Fluxo;

/**
 * Tubo Cilindrico.
 * <p>
 * Abstrata por ter uma opera��o ainda por defineir:
 * <code>getPerdaHidraulicaCurvatura()</code>.
 * </p>
 *
 * @author Victor Soares
 * @version 1.0
 */
abstract class TuboCilindrico extends Tubo {

    /**
     * Diâmetro do tubo [m].
     *
     * @see #setDiametro(Double)
     * @see #getDiametro()
     */
    private Double diametro;

    /**
     * Rugosidade [m] / Diâmetro [m].
     *
     * @see #updateRugosidadeRelativa()
     * @see #getRugosidadeRelativa()
     */
    private Double rugosidadeRelativa;

    /**
     * �rea de se��o, constate na tubulação [m^2].
     *
     * @see #updateAreaSecao()
     * @see #getAreaDeSecao()
     */
    private Double areaSecao;

    /**
     * Constante de perda.
     * <p>
     * A constante de perda se refere a parte independente da vazão no c�lculo
     * da perda hidráulica.
     * </p>
     *
     * <p>
     * Mem�ria de c�lculo para opera��o realizada extensivamente.
     * </p>
     * <p>
     * (pow(1/getAreaDeSecao(), 2) / (2 * GRAVIDADE)) * (getComprimento() /
     * getDiametro()).
     * </p>
     *
     * @see #updateConstanteDePerda()
     * @see #getConstanteDePerda()
     */
    private Double constanteDePerda;

    /**
     * Fator Atrito Padrao.
     * <p>
     * Armazena o fator de atrito para o valor da constante de Reynolds padr�o,
     * como definida na classe <code>Fluxo</code>.
     * </p>
     * <p>
     * Mem�ria de c�lculo para opera��o realizada extensivamente.
     * </p>
     * <p>
     * Em caso de variacoes constantes e padronizadas: transformar em tabela.
     * Modifica��o afetaria:
     * <ul>
     * <li>{@link Fluxo#REYNOLDS_PADRAO}</li>
     * <li>{@link TuboCilindrico#getPerdaHidraulicaRetilinea(Fluxo)}</li>
     * <li>{@link TuboCilindrico#TuboCilindrico(Double, Double, Double, Double)}</li>
     * </ul>
     * </p>
     *
     * @see Fluxo#REYNOLDS_PADRAO
     */
    private Double fatorAtritoPadrao;

    /**
     * Construtor.
     *
     * @param comprimento Comprimento da tubulação [m].
     * @param diametro Diametro [m].
     * @param curvatura Angulo de curvatura da tubulação [Graus].
     * @param rugosidadeAbsoluta Rugosidade Absoluta [m].
     * @since 1.0
     */
    public TuboCilindrico(Double comprimento, Double diametro, Double curvatura,
            Double rugosidadeAbsoluta) {
        super(comprimento, curvatura, rugosidadeAbsoluta);
        setDiametro(diametro);
        fatorAtritoPadrao = getFatorAtrito(Fluxo.REYNOLDS_PADRAO);
    }

    /**
     * @param diametro O diâmetro a ser atribu�do ao tudo.
     *
     * @throws IllegalArgumentException
     * <ul>
     * <li>No caso de uso de um valor n�o positivo.</li>
     * </ul>
     * @since 1.0
     */
    private final void setDiametro(Double diametro) {

        if (diametro <= 0) {
            throw new IllegalArgumentException("Diametro <= 0.");
        }

        this.diametro = diametro;

        updateAreaSecao();
        updateRugosidadeRelativa();
        updateConstanteDePerda();
    }

    /**
     * Calcula e atualiza �rea de se��o vertical do tubo cil�ndrico [m^2].
     * <p>
     * Uma vez atribu�do um diâmetro a tubulação, atualiza-se o valor da �rea de
     * se��o. Armazenando-se em mem�ria e evitando c�lculos repetidos no
     * decorrer do processamento.
     * </p>
     * <p>
     * O c�lculo da �rea foi definido pelo modelo e segue a quarta pot�ncia do
     * diâmetro.
     * </p>
     *
     * @since 1.0
     * @see #getAreaDeSecao()
     */
    private void updateAreaSecao() {
        // #TODO Checar modelo 4 ou 2
        this.areaSecao = PI * (pow(getDiametro(), 4)) / 4;
    }

    /**
     * Calcula e atualiza a Rugosidade Relativa para tubo cil�ndrico.
     * <p>
     * Rugosidade [m] / Diâmetro [m].
     * </p>
     *
     * @since 1.0
     * @see #getRugosidadeRelativa()
     */
    private void updateRugosidadeRelativa() {
        this.rugosidadeRelativa = getRugosidadeAbsoluta() / getDiametro();
    }

    /**
     * Calcula a constante de Perda.
     * <p>
     * A constante de perda se refere a parte independente da vazão no cálculo
     * da perda hidráulica.
     * </p>
     *
     * <p>
     * Memória de cálculo para operação realizada extensivamente.
     * </p>
     * <p>
     * (pow(1/getAreaDeSecao(), 2) / (2 * GRAVIDADE)) * (getComprimento() /
     * getDiametro()).
     * </p>
     *
     * @since 1.0
     * @see #getConstanteDePerda()
     */
    private final void updateConstanteDePerda() {
        constanteDePerda = (pow(1 / getAreaDeSecao(), 2) / (2 * GRAVIDADE))
                * (getComprimento() / getDiametro());
    }

    /**
     * @return A Constante de perda.
     * @since 1.0
     * @see #updateConstanteDePerda()
     */
    private final Double getConstanteDePerda() {
        return constanteDePerda;
    }

    /**
     * @return O diâmetro [m].
     * @since 1.0
     * @see #setDiametro(Double)
     */
    public final Double getDiametro() {
        return diametro;
    }

    /**
     * @return Seção transversal do tubo [m^2].
     * @since 1.0
     * @see #updateAreaSecao()
     */
    public final Double getAreaDeSecao() {
        return areaSecao;
    }

    /**
     * @return A rugosidade Relativa.
     * @since 1.0
     * @see #updateRugosidadeRelativa()
     */
    public final Double getRugosidadeRelativa() {
        return rugosidadeRelativa;
    }

    @Override
    public final Double getFatorAtrito(Double REYNOLDS) {
        /* Divisao da formula em partes auxiliares. */

        Double parteA = pow(64 / REYNOLDS, 8);

        Double b = getRugosidadeRelativa() / (3.7 * getDiametro());

        Double c = 5.74 / pow(REYNOLDS, 0.9);

        Double d = 2500 / (REYNOLDS);

        Double parteB = 9.5 * pow(log(b + c) - pow(d, 6), -16);

        // #TODO checar modelo: 0.125
        return pow(parteA + parteB,0.125);
    }

    @Override
    public final Double getFatorAtrito(Double REYNOLDS, Double posicaoX) {
        return getFatorAtrito(REYNOLDS);
    }

    @Override
    public Double getPerdaHidraulicaCurvatura(final Fluxo fluxo) {
        // #TODO modelo de curvatura suave e não pontual.
        throw new RuntimeException(new OperationNotSupportedException(
                "Ainda por implementar"));
    }

    @Override
    public final Double getPerdaHidraulicaRetilinea(final Fluxo fluxo) {

        /* Calculo das perdas de carga */
        Double fatorAtrito;
        fatorAtrito = (fluxo.getReynolds().equals(Fluxo.REYNOLDS_PADRAO)) ? fatorAtritoPadrao
                : getFatorAtrito(fluxo.getReynolds());

        return fatorAtrito * pow(fluxo.getVazao(), 2) * getConstanteDePerda();
    }
}
