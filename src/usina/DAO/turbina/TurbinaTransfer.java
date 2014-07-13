/**
 * 
 */
package usina.DAO.turbina;

import java.io.Serializable;

/**
 * 
 * @author Victor Soares
 * @version 1.0
 * 
 * @deprecated
 */
public final class TurbinaTransfer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3435631488834951304L;
	
	/**
	 * @serial
	 */
	private boolean ligado;

	/**
	 * @serial
	 */
	private Double limiteMinDePotencia;
	
	/**
	 * @serial
	 */
	private Double limiteMaxDePotencia;
	
	/**
	 * @serial
	 */
	private Double limiteMinDeVazao;
	
	/**
	 * @serial
	 */
	private Double[] parametrosRendimento;
	
	/**
	 * @serial
	 */
	private Double quedaBruta;
	
	
	/**
	 * @return O quedaBruta a ser atribuido.
	 */
	public final Double getQuedaBruta() {
		return quedaBruta;
	}
	/**
	 * @param quedaBruta the quedaBruta to set
	 */
	public final void setQuedaBruta(Double quedaBruta) {
		this.quedaBruta = quedaBruta;
	}
	/**
	 * @return O parametrosRendimento a ser atribuido.
	 */
	public final Double[] getParametrosRendimento() {
		return parametrosRendimento;
	}
	/**
	 * @param parametrosRendimento the parametrosRendimento to set
	 */
	public final void setParametrosRendimento(Double[] parametrosRendimento) {
		this.parametrosRendimento = parametrosRendimento.clone();
	}
	/**
	 * @serial
	 */
	private Double limiteMaxDeVazao;

	/**
	 * @return the ligado
	 */
	public final boolean isLigado() {
		return ligado;
	}
	/**
	 * @param ligado the ligado to set
	 */
	public final void setLigado(boolean ligado) {
		this.ligado = ligado;
	}
	/**
	 * @return the limiteMinDePotencia
	 */
	public final Double getLimiteMinDePotencia() {
		return limiteMinDePotencia;
	}
	/**
	 * @param limiteMinDePotencia the limiteMinDePotencia to set
	 */
	public final void setLimiteMinDePotencia(Double limiteMinDePotencia) {
		this.limiteMinDePotencia = limiteMinDePotencia;
	}
	/**
	 * @return the limiteMaxDePotencia
	 */
	public final Double getLimiteMaxDePotencia() {
		return limiteMaxDePotencia;
	}
	/**
	 * @param limiteMaxDePotencia the limiteMaxDePotencia to set
	 */
	public final void setLimiteMaxDePotencia(Double limiteMaxDePotencia) {
		this.limiteMaxDePotencia = limiteMaxDePotencia;
	}
	/**
	 * @return the limiteMinDeVazao
	 */
	public final Double getLimiteMinDeVazao() {
		return limiteMinDeVazao;
	}
	/**
	 * @param limiteMinDeVazao the limiteMinDeVazao to set
	 */
	public final void setLimiteMinDeVazao(Double limiteMinDeVazao) {
		this.limiteMinDeVazao = limiteMinDeVazao;
	}
	/**
	 * @return the limiteMaxDeVazao
	 */
	public final Double getLimiteMaxDeVazao() {
		return limiteMaxDeVazao;
	}
	/**
	 * @param limiteMaxDeVazao the limiteMaxDeVazao to set
	 */
	public final void setLimiteMaxDeVazao(Double limiteMaxDeVazao) {
		this.limiteMaxDeVazao = limiteMaxDeVazao;
	}
	/**
	 * @return the serialversionuid
	 */
	public static final long getSerialversionuid() {
		return serialVersionUID;
	}

}
