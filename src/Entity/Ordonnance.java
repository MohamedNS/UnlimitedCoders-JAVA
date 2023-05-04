/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;

/**
 *
 * @author MSI
 */
public class Ordonnance {
      private int id;
    private int consultation_id;
    private int validite;
    private String nomMedicaments;
    private String code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConsultation_id() {
		return consultation_id;
	}

	public void setConsultation_id(int consultation_id) {
		this.consultation_id = consultation_id;
	}

	public int getValidite() {
		return validite;
	}

	public void setValidite(int validite) {
		this.validite = validite;
	}

	public String getNomMedicaments() {
		return nomMedicaments;
	}

	public void setNomMedicaments(String nomMedicaments) {
		this.nomMedicaments = nomMedicaments;
	}

	public ArrayList<Medicament> getMedicamentOrdonnance() {
		return medicamentOrdonnance;
	}

	public void setMedicamentOrdonnance(ArrayList<Medicament> medicamentOrdonnance) {
		this.medicamentOrdonnance = medicamentOrdonnance;
	}
    private ArrayList<Medicament> medicamentOrdonnance;

    public Ordonnance()
    {
        this.medicamentOrdonnance = new ArrayList<>();
    }
    public Ordonnance(int id,int consultation_id,int validite,String code)
    {
        this.id = id;
        this.consultation_id = consultation_id;
        this.validite = validite;
        this.code = code;
        this.medicamentOrdonnance = new ArrayList<>();
    }
    public Ordonnance(int consultation_id,int validite,String code)
    {
        this.consultation_id = consultation_id;
        this.validite = validite;
         this.code = code;
        this.medicamentOrdonnance = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}