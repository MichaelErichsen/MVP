package net.myerichsen.hremvp.person.servers;

import java.sql.SQLException;
import java.util.List;

import net.myerichsen.hremvp.IHREServer;
import net.myerichsen.hremvp.MvpException;
import net.myerichsen.hremvp.dbmodels.Partners;

/**
 * @author Michael Erichsen, &copy; History Research Environment Ltd., 2018
 * @version 24. jan. 2019
 *
 */
public class PartnerServer implements IHREServer {
	private int PartnerPid;
	private int Partner1;
	private int Partner2;
	private boolean PrimaryPartner;
	private String Role;
	private int FromDatePid;
	private int ToDatePid;

	private final Partners partnerRelation;

	/**
	 * Constructor
	 *
	 */
	public PartnerServer() {
		partnerRelation = new Partners();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#delete(int)
	 */
	@Override
	public void delete(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#get()
	 */
	@Override
	public List<?> get() throws SQLException, MvpException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#get(int)
	 */
	@Override
	public void get(int key) throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the fromDatePid
	 */
	public int getFromDatePid() {
		return FromDatePid;
	}

	/**
	 * @return the partner1
	 */
	public int getPartner1() {
		return Partner1;
	}

	/**
	 * @return the partner2
	 */
	public int getPartner2() {
		return Partner2;
	}

	/**
	 * @return the partnerPid
	 */
	public int getPartnerPid() {
		return PartnerPid;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return Role;
	}

	/**
	 * @return the toDatePid
	 */
	public int getToDatePid() {
		return ToDatePid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#insert()
	 */
	@Override
	public int insert() throws SQLException, MvpException {
		partnerRelation.setPartner1(Partner1);
		partnerRelation.setPartner2(Partner2);
		partnerRelation.setPrimaryPartner(PrimaryPartner);
		partnerRelation.setRole(Role);
		partnerRelation.setFromDatePid(FromDatePid);
		partnerRelation.setToDatePid(ToDatePid);
		return partnerRelation.insert();
	}

	/**
	 * @return the primaryPartner
	 */
	public boolean isPrimaryPartner() {
		return PrimaryPartner;
	}

	/**
	 * @param fromDatePid the fromDatePid to set
	 */
	public void setFromDatePid(int fromDatePid) {
		FromDatePid = fromDatePid;
	}

	/**
	 * @param partner1 the partner1 to set
	 */
	public void setPartner1(int partner1) {
		Partner1 = partner1;
	}

	/**
	 * @param partner2 the partner2 to set
	 */
	public void setPartner2(int partner2) {
		Partner2 = partner2;
	}

	/**
	 * @param partnerPid the partnerPid to set
	 */
	public void setPartnerPid(int partnerPid) {
		PartnerPid = partnerPid;
	}

	/**
	 * @param primaryPartner the primaryPartner to set
	 */
	public void setPrimaryPartner(boolean primaryPartner) {
		PrimaryPartner = primaryPartner;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		Role = role;
	}

	/**
	 * @param toDatePid the toDatePid to set
	 */
	public void setToDatePid(int toDatePid) {
		ToDatePid = toDatePid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.myerichsen.hremvp.servers.IHREServer#update()
	 */
	@Override
	public void update() throws SQLException, MvpException {
		// TODO Auto-generated method stub

	}
}
