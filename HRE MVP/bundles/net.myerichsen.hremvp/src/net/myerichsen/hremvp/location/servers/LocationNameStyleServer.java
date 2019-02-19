package net.myerichsen.hremvp.location.servers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Languages;
import net.myerichsen.hremvp.dbmodels.LocationNameMaps;
import net.myerichsen.hremvp.dbmodels.LocationNameStyles;

/**
 * Business logic interface for
 * {@link net.myerichsen.hremvp.dbmodels.LocationNameStyles}
 *
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018-2019
 * @version 20. nov. 2018
 *
 */
//Use LocalDate
public class LocationNameStyleServer implements IHREServer {
	private int locationNameStylePid;
	private String label;
	private int languagePid;
	private String languageLabel;
	private String isoCode;
	private int fromDatePid;
	private int toDatePid;

	private LocationNameStyles style;
	private Languages language;
	private List<LocationNameMaps> mapList;

	/**
	 * Constructor
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 *
	 */
	public LocationNameStyleServer() throws SQLException {
		style = new LocationNameStyles();
		language = new Languages();
		mapList = new ArrayList<>();
	}

	/**
	 * Delete a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		style.delete(key);
	}

	/**
	 * Get all rows
	 *
	 * @return A list of strings of pids and labels
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public List<String> get() throws SQLException, MvpException {
		final List<String> stringList = new ArrayList<>();

		final List<LocationNameStyles> lnsl = style.get();

		for (final LocationNameStyles style : lnsl) {
			stringList.add(style.getLocationNameStylePid() + ","
					+ style.getLabelPid());
		}

		return stringList;
	}

	/**
	 * Get a row
	 *
	 * @param key The persistent ID of the row
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 * @throws MvpException Application specific exception
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		style.get(key);
		setLabel("style.getLabelPid()");
//		setLanguagePid(style.getLanguagePid());
		setLocationNameStylePid(style.getLocationNameStylePid());
		setFromDatePid(style.getFromDatePid());
		setToDatePid(style.getToDatePid());

		language.get(languagePid);
		setLanguageLabel(language.getLabel());
		setIsoCode(language.getIsocode());

		mapList = new LocationNameMaps()
				.getFKLocationNameStylePid(locationNameStylePid);
	}

	/**
	 * @return the fromDate
	 */
	public int getFromDate() {
		return fromDatePid;
	}

	/**
	 * @return the isoCode
	 */
	public String getIsoCode() {
		return isoCode;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the language
	 */
	public Languages getLanguage() {
		return language;
	}

	/**
	 * @return the languageLabel
	 */
	public String getLanguageLabel() {
		return languageLabel;
	}

	/**
	 * @return the languagePid
	 */
	public int getLanguagePid() {
		return languagePid;
	}

	/**
	 * @return the locationNameStylePid
	 */
	public int getLocationNameStylePid() {
		return locationNameStylePid;
	}

	/**
	 * @return the mapList
	 */
	public List<LocationNameMaps> getMapList() {
		return mapList;
	}

	/**
	 * @return the style
	 */
	public LocationNameStyles getStyle() {
		return style;
	}

	/**
	 * @return the toDate
	 */
	public int getToDatePid() {
		return toDatePid;
	}

	/**
	 * Insert a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	@Override
	public int insert() throws SQLException {
		style.setLabelPid(0);
//		style.setLanguagePid(languagePid);
		style.setLocationNameStylePid(locationNameStylePid);
		style.setFromDatePid(fromDatePid);
		style.setToDatePid(toDatePid);
		return style.insert();
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDatePid(int fromDatePid) {
		this.fromDatePid = fromDatePid;
	}

	/**
	 * @param isoCode the isoCode to set
	 */
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(Languages language) {
		this.language = language;
	}

	/**
	 * @param languageLabel the languageLabel to set
	 */
	public void setLanguageLabel(String languageLabel) {
		this.languageLabel = languageLabel;
	}

	/**
	 * @param languagePid the languagePid to set
	 */
	public void setLanguagePid(int languagePid) {
		this.languagePid = languagePid;
	}

	/**
	 * @param locationNameStylePid the locationNameStylePid to set
	 */
	public void setLocationNameStylePid(int locationNameStylePid) {
		this.locationNameStylePid = locationNameStylePid;
	}

	/**
	 * @param mapList the mapList to set
	 */
	public void setMapList(List<LocationNameMaps> mapList) {
		this.mapList = mapList;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(LocationNameStyles style) {
		this.style = style;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDatePid(int toDatePid) {
		this.toDatePid = toDatePid;
	}

	/**
	 * Update a row
	 *
	 * @throws SQLException An exception that provides information on a database
	 *                      access error or other errors
	 */
	@Override
	public void update() throws SQLException {
		style.setLabelPid(0);
//		style.setLanguagePid(languagePid);
		style.setLocationNameStylePid(locationNameStylePid);
		style.setFromDatePid(fromDatePid);
		style.setToDatePid(toDatePid);
		style.update();
	}
}
