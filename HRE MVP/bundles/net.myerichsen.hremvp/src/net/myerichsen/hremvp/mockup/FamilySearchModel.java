package net.myerichsen.hremvp.mockup;

public class FamilySearchModel {
	private String name;

	private String gender;

	private String birthDate;

	private String birthPlace;

	private String deathDate;

	private String deathPlace;

	private String afn;

	public FamilySearchModel() {
		name = "";
		gender = "";
		birthDate = "";
		birthPlace = "";
		deathDate = "";
		deathPlace = "";
		afn = "";
	}

	public String getAfn() {
		return afn;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public String getDeathPlace() {
		return deathPlace;
	}

	public String getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	public void setAfn(String afn) {
		this.afn = afn;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setName(String name) {
		this.name = name;
	}
}
