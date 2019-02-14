package net.myerichsen.hremvp.mockup;

public class PolRegLocationModel {
	private String address;

	private String date;

	private String latitude;

	private String longitude;

	public PolRegLocationModel() {
		 this.address = "";
		 this.date = "";
		 this.latitude = "";
		 this.longitude = "";
	}

	public String getAddress() {
		 return this.address;
	}

	public String getDate() {
		 return this.date;
	}

	public String getLatitude() {
		 return this.latitude;
	}

	public String getLongitude() {
		 return this.longitude;
	}

	public void setAddress(String address) {
		 this.address = address;
	}

	public void setDate(String date) {
		 this.date = date;
	}

	public void setLatitude(String latitude) {
		 this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		 this.longitude = longitude;
	}

	public String toString() {
		 return this.address + ", " + this.date + ", " + this.latitude
				+ ", " + this.longitude;
	}
}
