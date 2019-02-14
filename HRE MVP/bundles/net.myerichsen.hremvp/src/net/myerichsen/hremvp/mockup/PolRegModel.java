package net.myerichsen.hremvp.mockup;

public class PolRegModel {
	private String id;

	private String registerblad_id;

	private String firstnames;

	private String person_type;

	private String lastname;

	private String birthplace;

	private String dateofbirth;

	private String dateofdeath;

	public PolRegModel() {
		id = "";
		registerblad_id = "";
		firstnames = "";
		person_type = "";
		lastname = "";
		birthplace = "";
		 dateofbirth = "";
		 dateofdeath = "";
	}

	public PolRegModel(String id, String registerblad_id, String firstnames,
			String person_type, String lastname, String birthplace,
			String dateofbirth, String dateofdeath) {
		 this.id = id;
		 this.registerblad_id = registerblad_id;
		 this.firstnames = firstnames;
		 this.person_type = person_type;
		 this.lastname = lastname;
		 this.birthplace = birthplace;
		 this.dateofbirth = dateofbirth;
		 this.dateofdeath = dateofdeath;
	}

	public String getBirthplace() {
		 return birthplace;
	}

	public String getDateofbirth() {
		 return dateofbirth;
	}

	public String getDateofdeath() {
		 return dateofdeath;
	}

	public String getFirstnames() {
		 return firstnames;
	}

	public String getId() {
		 return id;
	}

	public String getLastname() {
		 return lastname;
	}

	public String getPerson_type() {
		return person_type;
	}

	public String getRegisterblad_id() {
		return registerblad_id;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public void setDateofbirth(String dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public void setDateofdeath(String dateofdeath) {
		this.dateofdeath = dateofdeath;
	}

	public void setFirstnames(String firstnames) {
		this.firstnames = firstnames;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setPerson_type(String person_type) {
		this.person_type = person_type;
	}

	public void setRegisterblad_id(String registerblad_id) {
		this.registerblad_id = registerblad_id;
	}
}
